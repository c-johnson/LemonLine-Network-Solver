package up.controller;
import java.net.*;
import java.util.Iterator;
import java.io.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import up.model.Arc;
import up.model.Network;
import up.model.Node;

public class Populator {

	public Populator(){
		
	}
	
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
 /* public Flow populateFlow(){
    	
    }
    
    public Revenue populateRevenue(){
    	
    }
    */
	public Network populateNetwork(){
		File infile = new File("network.xml");
		Network network = new Network();
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
							
	//						if(startNode.getOutgoingArcs() == null){
								
		//					}
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
