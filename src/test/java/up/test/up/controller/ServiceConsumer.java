package up.controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import up.model.*;


public class ServiceConsumer {

	private static Logger LOG = Logger.getLogger(ServiceConsumer.class.getName());
	
	public Network buildNetworkFromService(URL serviceURL) throws IOException { 
		
		XMLStreamReader reader = null;
		URLConnection conn = serviceURL.openConnection();
		
		try {
			reader = XMLInputFactory.newInstance().createXMLStreamReader(conn.getInputStream());
			
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "Could not connect to the service at URL "+serviceURL.toString(), e);
			ConnectException ce = new ConnectException("Could not process the network XML");
			ce.initCause(e);
			throw ce;
		}

		Network network = null;
		try {
			String element_name;
			String element_text = null;
			
			HashMap<String, Node> nodes = new HashMap<String, Node>();
			
			while (reader.hasNext()) {
				reader.next();
				
				if ( reader.getEventType() == XMLStreamReader.START_ELEMENT ) {
					element_name = reader.getLocalName();
					
					if ( element_name.equals("network") ) {
						network = new Network();
						network.setName(reader.getAttributeValue(0));
						
					} else if ( element_name.equals("nodes") ) {
						network.setNodes(new ArrayList<Node>());
						
					} else if ( element_name.equals("arcs") ) { 
						network.setArcs(new ArrayList<Arc>());
						
					} else if ( element_name.equals("node") ) { 
						Node n = new Node();
						n.setId(reader.getAttributeValue(0));
						n.setName(reader.getAttributeValue(1));
						n.setLatitude(Double.parseDouble(reader.getAttributeValue(2)));
						n.setLongitude(Double.parseDouble(reader.getAttributeValue(3)));
						n.setMaxCars(Integer.parseInt(reader.getAttributeValue(4)));
						
						nodes.put(n.getId(), n);
						network.getNodes().add(n);
						
					} else if ( element_name.equals("arc") ) { 
						Arc a = new Arc();
						Node start = nodes.get(reader.getAttributeValue(0));
						Node end = nodes.get(reader.getAttributeValue(1));
						
						a.setMaxTrains(Integer.parseInt(reader.getAttributeValue(2)));
						a.setDistance(Double.parseDouble(reader.getAttributeValue(3)));
						a.setFuelAdjustFactor(Double.parseDouble(reader.getAttributeValue(4)));
						
						a.setStartNode(start);
						a.setEndNode(end);
						
						if ( start.getOutgoingArcs() == null ) { 
							start.setOutgoingArcs(new ArrayList<Arc>());
						}
						start.getOutgoingArcs().add(a);
						
						if ( end.getIncomingArcs() == null ) { 
							end.setIncomingArcs(new ArrayList<Arc>());
						}
						end.getIncomingArcs().add(a);
						
						network.getArcs().add(a);
					}
					
				} else if ( reader.getEventType() == XMLStreamReader.CHARACTERS ) {
					element_text = reader.getText();
					
				} else if ( reader.getEventType() == XMLStreamReader.END_ELEMENT ) {
					element_name = reader.getLocalName();
					if ( element_name.equals("maxCarsPerTrain") ) { 
						network.setMaxCarsPerTrain((int)Double.parseDouble(element_text));
					
					} else if ( element_name.equals("nonFuelCostPerMile") ) { 
						network.setNonFuelCostPerMile(Double.parseDouble(element_text));
					
					} else if ( element_name.equals("fuelCostPerMile") ) { 
						network.setFuelCostPerMile(Double.parseDouble(element_text));
						
					} else if ( element_name.equals("carCostPerMile") ) { 
						network.setCarCostPerMile(Double.parseDouble(element_text));
						
					}
				}
			}
		} catch ( XMLStreamException xse ) { 
			LOG.log(Level.SEVERE, "An error occurred parsing the XML stream", xse);
			IOException ioe = new IOException("Could not process the network XML");
			ioe.initCause(xse);
			throw ioe;
		}
		
		return network;
	}
}
