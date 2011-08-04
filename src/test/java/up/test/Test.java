package up.test;
import java.util.ArrayList;
import java.util.List;

import up.model.Amount;
import up.model.Arc;
import up.model.Destination;
import up.model.Network;
import up.model.Node;
import up.model.Flow;
import up.model.Revenue;

import up.controller.Populator;

import junit.framework.TestCase;



public class Test extends TestCase 
{


	public void testNodePopulation()//populate node
	{
		Node node = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);

		assertEquals("SEA", node.getId());
		assertEquals("Seattle", node.getName());
		assertEquals(47.12312, node.getLatitude());
		assertEquals(-122.12312, node.getLongitude());
		assertEquals(1450, node.getMaxCars());		
	}

	public void testArcPopulation()//populate arcs
	{
		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNode = new Node("LIN", "Lincoln", 49.12312, -1.12312, 150);
		Arc arc= new Arc(startNode, endNode, 50, 40, 5);

		assertEquals(startNode, arc.getStartNode());
		assertEquals(endNode, arc.getEndNode());
		assertEquals(50, arc.getMaxTrains());
		assertEquals(40.0, arc.getDistance());
		assertEquals(5.0, arc.getFuelAdjustFactor());		
	}

	public void testNetworkPopulation()//populate network
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNode = new Node("LIN", "Lincoln", 49.12312, -1.12312, 150);
		Arc arc= new Arc(startNode, endNode, 50, 40, 5);
		Network network = new Network();
		network.addNode(startNode);
		network.addNode(endNode);
		network.addArc(arc);
		network.setName("demoNetwork");
		network.setMaxCarsPerTrain(100f);
		network.setNonFuelCostPerMile(45f);
		network.setFuelCostPerMile(.5);
		network.setCarCostPerMile(1);

		assertEquals("demoNetwork", network.getName());
		assertEquals(startNode, network.getNodes().get(0));
		assertEquals(endNode, network.getNodes().get(1));
		assertEquals(arc, network.getArcs().get(0));
		assertEquals(100.0, network.getMaxCarsPerTrain());
		assertEquals(45.0, network.getNonFuelCostPerMile());
		assertEquals(.5, network.getFuelCostPerMile());
		assertEquals(1.0, network.getCarCostPerMile());	
	}

	public void testNetworkCostRestrictions()//make sure all costs > 0
	{
		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNode = new Node("LIN", "Lincoln", 49.12312, -1.12312, 150);
		Arc arc= new Arc(startNode, endNode, 50, 40, 5);
		Network network = new Network();
		network.addNode(startNode);
		network.addNode(endNode);
		network.addArc(arc);
		network.setName("demoNetwork");
		network.setMaxCarsPerTrain(-1);
		network.setNonFuelCostPerMile(-1);
		network.setFuelCostPerMile(-1);
		network.setCarCostPerMile(-1);

		assertEquals(0.0, network.getMaxCarsPerTrain());
		assertEquals(0.0, network.getNonFuelCostPerMile());
		assertEquals(0.0, network.getFuelCostPerMile());
		assertEquals(0.0, network.getCarCostPerMile());
	}

	public void testNetworkBlankName()//test blank name
	{
		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNode = new Node("LIN", "Lincoln", 49.12312, -1.12312, 150);
		Arc arc= new Arc(startNode, endNode, 50, 40, 5);
		Network network = new Network();
		network.addNode(startNode);
		network.addNode(endNode);
		network.addArc(arc);
		network.setName("");
		network.setMaxCarsPerTrain(100f);
		network.setNonFuelCostPerMile(45f);
		network.setFuelCostPerMile(.5);
		network.setCarCostPerMile(1);

		assertEquals("defaultNetwork", network.getName());
	}

	public void testArcUniqStartEnd()//arcs can't have same start/end node
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		network.addNode(startNode);
		network.addNode(endNode);

		Arc arc= new Arc(startNode, endNode, 50, 40, 5);
		assertEquals(false, network.addArc(arc));
	}

	public void testArcDistancePositive() //Arc distance must be >0
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNode = new Node("LIN", "Lincoln", 49.12312, -1.12312, 150);
		network.addNode(startNode);
		network.addNode(endNode);

		Arc arc= new Arc(startNode, endNode, 50, 0, 5);
		assertEquals(false, network.addArc(arc));

		arc= new Arc(startNode, endNode, 50, -1, 5);
		assertEquals(false, network.addArc(arc));
	}

	public void testArcNodesExist()//when we add an arc make sure both nodes are present?
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNodeFake = new Node ("QBC", "Quebec", 100, -100, 100);
		network.addNode(startNode); //Only add one of the nodes

		Arc arc= new Arc(startNode, endNodeFake, 50, 50, 5);
		assertEquals(false, network.addArc(arc));
	}

	public void testNodeIDExists()//Node ID cannot be blank
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node node = new Node("", "Seattle", 47.12312, -122.12312, 1450);

		assertEquals(false,network.addNode(node));
	}

	public void testNodeMaxTrainsPositive()//Node's max number of trains can't be negative
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node node = new Node("SEA", "Seattle", 47.12312, -122.12312, -12);

		assertEquals(false,network.addNode(node));
	}

	public void testNodeNameExists()//Node Name cannot be blank
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node node = new Node("SEA", "", 47.12312, -122.12312, 1450);

		assertEquals(false,network.addNode(node));
	}

	//Node cannot have a duplicate ID
	public void testUniqNodeID() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node node = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);

		//Adding the first time should return true, but adding the second time will return false.
		assertEquals(true,network.addNode(node));
		assertEquals(false,network.addNode(node));
	}

	public void testConnectionFails() {
		String theURL = "i like potatoes";
		Populator pop = new Populator();
		assertEquals(false, pop.getXML(theURL, "network"));
	}

	public void testXMLandPopulator() {
		String theURL = "http://cse-cig.unl.edu:8080/upproject/rest/networkGenerator?groupId=3";
		Populator pop = new Populator();
		pop.getXML(theURL, "network");
		Network network = pop.populateNetwork();
		List<Node> nodes = network.getNodes();
		List<Arc> arcs = network.getArcs();
		assertEquals("SEA", nodes.get(0).getId());
		assertEquals("CHI", arcs.get(33).getStartNode().getId());
	}

	//Test that the flow model populates properly and the maxCars value adds up to individual destination numCars
	public void testFlow() {
		Flow flow = new Flow();
		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node destination1 = new Node("LIN", "Lincoln", 49.12312, -1.12312, 150);
		Node destination2 = new Node("CHI", "Chicago", 49.12312, -1.12312, 150);

		flow.setStartNode(startNode);
		
		ArrayList<Destination> destinations = new ArrayList();
		Destination tempDest = new Destination();
		tempDest.setNumCars(50);
		tempDest.setToNode(destination1);
		destinations.add(tempDest);
		tempDest = new Destination();
		tempDest.setNumCars(100);
		tempDest.setToNode(destination2);
		destinations.add(tempDest);
		flow.setDestinations(destinations);
		
		int numCars = 150;
		flow.setNumCars(numCars);

		int tempSum = 0;
		for (Destination destination : destinations) {
			tempSum += destination.getNumCars();
		}

		assertEquals(tempSum, numCars);
		assertEquals("SEA", flow.getStartNode().getId());
		assertEquals("LIN", flow.getDestinations().get(0).getToNode().getId());
		assertEquals("CHI", flow.getDestinations().get(1).getToNode().getId());

	}
	
	
	//Test that the revenue model populates properly
	public void testRevenue() {
		Node node = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node nodeDest1 = new Node("MON", "Montana", 47.12312, -122.12312, 1450);
		Node nodeDest2 = new Node("ABU", "Abu Dhabi", 47.12312, -122.12312, 1450);
		
		Revenue revenue = new Revenue();
		revenue.setStartNode(node);
		
		ArrayList<Amount> amounts = new ArrayList();
		Amount tempAmount = new Amount();
		tempAmount.setToNode(nodeDest1);
		tempAmount.setAmount(1000);
		tempAmount.setRevenue(revenue);
		amounts.add(tempAmount);
		tempAmount = new Amount();
		tempAmount.setToNode(nodeDest2);
		tempAmount.setAmount(2000);
		tempAmount.setRevenue(revenue);
		amounts.add(tempAmount);
		
		revenue.setAmounts(amounts);
		
		assertEquals("SEA", revenue.getStartNode().getId());
		assertEquals("MON", revenue.getAmounts().get(0).getToNode().getId());
		assertEquals("ABU", revenue.getAmounts().get(1).getToNode().getId());


	}
	
}
