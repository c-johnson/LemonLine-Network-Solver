package up.controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import up.model.Amount;
import up.model.Arc;
import up.model.Destination;
import up.model.Flow;
import up.model.Network;
import up.model.Node;
import up.model.Revenue;

public class Populator {

	private Network network;

	// http://cse-cig.unl.edu:8080/upproject/rest/networkGenerator?groupId=3
	public boolean getXML(String theURL, String type){
		try {
			URL xmlstream = new URL(theURL);
			URLConnection xmlc = xmlstream.openConnection();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							xmlc.getInputStream()));
			String inputLine;
			String fileName;
			if (type.equals("network"))
			{
				fileName = "network.xml";
			}
			else
			{
				fileName = "revenuesAndFlows.xml";
			}
			FileWriter outfile = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(outfile);
			while ((inputLine = in.readLine()) != null){
				out.write(inputLine);
			}
			in.close();
			out.close();

		} catch (Exception e){
			System.out.println ("Could not make connection to server");
			return false;
		}

		return true;
	}

	public ArrayList<Flow> populateFlow(){
		File infile = new File("revenuesAndFlows.xml");
		ArrayList<Flow> flows = new ArrayList<Flow>();
		try{
			XMLInputFactory factory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(infile);
			XMLStreamReader reader = factory.createXMLStreamReader(in);

			String element_name;
			String element_text = null;
			Flow flow = null;
			ArrayList<Destination> destinations = null;
			while (reader.hasNext()) {
				reader.next();
				if ( reader.isStartElement()) {
					element_name = reader.getLocalName();
					if ( element_name.equals("Flow") ) {
						flow = new Flow();
						flow.setNetwork(network);
						Node startNode = network.findNode(reader.getAttributeValue(0));
						flow.setStartNode(startNode);
						flow.setNumCars(Integer.parseInt(reader.getAttributeValue(1)));
						destinations = new ArrayList<Destination>();
					} else if ( element_name.equals("Destination") ) {
						Destination dest = new Destination();
						dest.setFlow(flow);
						Node toNode = network.findNode(reader.getAttributeValue(0));
						dest.setToNode(toNode);
						dest.setNumCars(Integer.parseInt(reader.getAttributeValue(1)));
						destinations.add(dest);
					} 

				} else if(reader.isEndElement()){
					if(reader.getLocalName().equals("Flow")){
						flow.setDestinations(destinations);
						flows.add(flow);
					}
				} 

			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(XMLStreamException e){
			e.printStackTrace();
		}
		return flows;
	}



	public ArrayList<Revenue> populateRevenue(){
		File infile = new File("revenuesAndFlows.xml");
		ArrayList<Revenue> revenues = new ArrayList<Revenue>();
		try{
			XMLInputFactory factory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(infile);
			XMLStreamReader reader = factory.createXMLStreamReader(in);

			String element_name;
			String element_text = null;
			Revenue rev = null;
			Amount amt = null;
			ArrayList<Amount> amounts = null;
			while (reader.hasNext()) {
				reader.next();

				if ( reader.isStartElement()) {
					element_name = reader.getLocalName();
					if ( element_name.equals("Revenue") ) {
						rev = new Revenue();
						Node fromNode = network.findNode(reader.getAttributeValue(0));
						rev.setStartNode(fromNode);
						amounts = new ArrayList<Amount>();
					} else if ( element_name.equals("Amount") ) {
						amt = new Amount();
						Node toNode = network.findNode(reader.getAttributeValue(0));
						amt.setToNode(toNode);
						amt.setRevenue(rev);
					} 

				} else if ( reader.isCharacters()) {
					element_text = reader.getText();

				} else if ( reader.isEndElement()) {
					element_name = reader.getLocalName();
					if ( element_name.equals("Amount") ) { 
						amt.setAmount(Integer.parseInt(element_text));
						amounts.add(amt);
					}else if(element_name.equals("Revenue")){
						rev.setAmounts(amounts);
						revenues.add(rev);
					}
				}
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(XMLStreamException e){
			e.printStackTrace();
		}
		return revenues;
	}

	public Network populateNetwork(){
		File infile = new File("network.xml");
		network = new Network();
		try{
			XMLInputFactory factory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(infile);
			XMLEventReader reader = factory.createXMLEventReader(in);

			while(reader.hasNext()){
				XMLEvent event = reader.nextEvent();

				if(event.isStartElement()){
					StartElement start = event.asStartElement();
					if(start.getName().getLocalPart() == "network"){
						Iterator<Attribute> attributes = start.getAttributes();
						Attribute attribute = attributes.next();
						if(attribute.getName().toString().equals("name")){
							network.setName(attribute.getValue());
						}
					}else if(start.getName().getLocalPart() == "node"){
						Node node = new Node();
						Iterator<Attribute> attributes = start.getAttributes();
						while(attributes.hasNext()){
							Attribute attr = attributes.next();
							if(attr.getName().toString().equals("id")){
								node.setId(attr.getValue());
							}else if(attr.getName().toString().equals("name")){
								node.setName(attr.getValue());
							}else if(attr.getName().toString().equals("latitude")){
								node.setLatitude(Double.parseDouble(attr.getValue()));
							}else if(attr.getName().toString().equals("longitude")){
								node.setLongitude(Double.parseDouble(attr.getValue()));
							}else if (attr.getName().toString().equals("maxCars")){
								node.setMaxCars(Integer.parseInt(attr.getValue()));
							}
						}
						network.addNode(node);
					}else if(start.getName().getLocalPart() == "arc"){
						Arc arc = new Arc();
						Iterator<Attribute> attributes = start.getAttributes();
						while(attributes.hasNext()){
							Attribute attr = attributes.next();
							Node startNode = null;
							Node endNode;

							if(attr.getName().toString().equals("startNode")){
								startNode = network.findNode(attr.getValue());
								arc.setStartNode(startNode);
							}else if(attr.getName().toString().equals("endNode")){
								endNode = network.findNode(attr.getValue());
								arc.setEndNode(endNode);
							}else if(attr.getName().toString().equals("maxTrains")){
								arc.setMaxTrains(Integer.parseInt(attr.getValue()));
							}else if(attr.getName().toString().equals("distance")){
								arc.setDistance(Double.parseDouble(attr.getValue()));
							}else if(attr.getName().toString().equals("fuelAdjustFactor")){
								arc.setFuelAdjustFactor(Double.parseDouble(attr.getValue()));
							}
						}
						network.addArc(arc);
					}else if(start.getName().getLocalPart() == "maxCarsPerTrain"){
						event = reader.nextEvent();
						network.setMaxCarsPerTrain(
								Double.parseDouble(event.asCharacters().getData()));

					}else if(start.getName().getLocalPart() == "nonFuelCostPerMile"){
						event = reader.nextEvent();
						network.setNonFuelCostPerMile(
								Double.parseDouble(event.asCharacters().getData()));
					}else if(start.getName().getLocalPart() == "fuelCostPerMile"){
						event = reader.nextEvent();
						network.setFuelCostPerMile(
								Double.parseDouble(event.asCharacters().getData()));
					}else if(start.getName().getLocalPart() == "carCostPerMile"){
						event = reader.nextEvent();
						network.setCarCostPerMile(
								Double.parseDouble(event.asCharacters().getData()));
					}
				}
			}			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(XMLStreamException e){
			e.printStackTrace();
		}
		return network;
	}

}
