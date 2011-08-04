package up.test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import up.controller.Populator;
import up.model.Amount;
import up.model.Arc;
import up.model.Destination;
import up.model.Flow;
import up.model.Network;
import up.model.Node;
import up.model.Revenue;



public class TestNetwork
{
	private static EJBContainer container;
	
	@BeforeClass
	public static void setUpContainer() throws Exception { 
		
		Properties props = new Properties();
		props.load(Test.class.getClassLoader().getResourceAsStream("server.properties"));
		
		String persistence_path = Test.class.getClassLoader().getResource("META-INF/persistence.xml").getFile();
		String class_path = persistence_path.substring(0, persistence_path.indexOf("META-INF"));
		
		props.put(EJBContainer.MODULES, new File(class_path));
		
		container = EJBContainer.createEJBContainer(props);
		
	}
	
	@AfterClass
	public static void tearDownContainer() throws Exception { 
		container.close();
	}
	@Test
	public void testNodePopulation()//populate node
	{
		Node node = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);

		Assert.assertEquals("SEA", node.getId());
		Assert.assertEquals("Seattle", node.getName());
		Assert.assertEquals(47.12312, node.getLatitude());
		Assert.assertEquals(-122.12312, node.getLongitude());
		Assert.assertEquals(1450, node.getMaxCars());		
	}
	@Test
	public void testArcPopulation()//populate arcs
	{
		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNode = new Node("LIN", "Lincoln", 49.12312, -1.12312, 150);
		Arc arc= new Arc(startNode, endNode, 50, 40, 5);

		Assert.assertEquals(startNode, arc.getStartNode());
		Assert.assertEquals(endNode, arc.getEndNode());
		Assert.assertEquals(50, arc.getMaxTrains());
		Assert.assertEquals(40.0, arc.getDistance());
		Assert.assertEquals(5.0, arc.getFuelAdjustFactor());		
	}
	@Test
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

		Assert.assertEquals("demoNetwork", network.getName());
		Assert.assertEquals(startNode, network.getNodes().get(0));
		Assert.assertEquals(endNode, network.getNodes().get(1));
		Assert.assertEquals(arc, network.getArcs().get(0));
		Assert.assertEquals(100.0, network.getMaxCarsPerTrain());
		Assert.assertEquals(45.0, network.getNonFuelCostPerMile());
		Assert.assertEquals(.5, network.getFuelCostPerMile());
		Assert.assertEquals(1.0, network.getCarCostPerMile());	
	}
	@Test
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

		Assert.assertEquals(0.0, network.getMaxCarsPerTrain());
		Assert.assertEquals(0.0, network.getNonFuelCostPerMile());
		Assert.assertEquals(0.0, network.getFuelCostPerMile());
		Assert.assertEquals(0.0, network.getCarCostPerMile());
	}
	@Test
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

		Assert.assertEquals("defaultNetwork", network.getName());
	}
	@Test
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
		Assert.assertEquals(false, network.addArc(arc));
	}
	@Test
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
		Assert.assertEquals(false, network.addArc(arc));

		arc= new Arc(startNode, endNode, 50, -1, 5);
		Assert.assertEquals(false, network.addArc(arc));
	}
	@Test
	public void testArcNodesExist()//when we add an arc make sure both nodes are present?
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node startNode = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);
		Node endNodeFake = new Node ("QBC", "Quebec", 100, -100, 100);
		network.addNode(startNode); //Only add one of the nodes

		Arc arc= new Arc(startNode, endNodeFake, 50, 50, 5);
		Assert.assertEquals(false, network.addArc(arc));
	}
	@Test
	public void testNodeIDExists()//Node ID cannot be blank
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node node = new Node("", "Seattle", 47.12312, -122.12312, 1450);

		Assert.assertEquals(false,network.addNode(node));
	}
	@Test
	public void testNodeMaxTrainsPositive()//Node's max number of trains can't be negative
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node node = new Node("SEA", "Seattle", 47.12312, -122.12312, -12);

		Assert.assertEquals(false,network.addNode(node));
	}
	@Test
	public void testNodeNameExists()//Node Name cannot be blank
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node node = new Node("SEA", "", 47.12312, -122.12312, 1450);

		Assert.assertEquals(false,network.addNode(node));
	}

	@Test
	public void testUniqNodeID() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		Network network = new Network("demoNetwork", nodes, arcs, 100, 45,.5,1);

		Node node = new Node("SEA", "Seattle", 47.12312, -122.12312, 1450);

		//Adding the first time should return true, but adding the second time will return false.
		Assert.assertEquals(true,network.addNode(node));
		Assert.assertEquals(false,network.addNode(node));
	}
	@Test
	public void testConnectionFails() {
		String theURL = "i like potatoes";
		Populator pop = new Populator();
		Assert.assertEquals(false, pop.getXML(theURL, "network"));
	}
	@Test
	public void testXMLandPopulator() {
		String theURL = "http://cse-cig.unl.edu:8080/upproject/rest/networkGenerator?groupId=3";
		Populator pop = new Populator();
		pop.getXML(theURL, "network");
		Network network = pop.populateNetwork();
		List<Node> nodes = network.getNodes();
		List<Arc> arcs = network.getArcs();
		Assert.assertEquals("SEA", nodes.get(0).getId());
		Assert.assertEquals("CHI", arcs.get(33).getStartNode().getId());
	}

	//Test that the flow model populates properly and the maxCars value adds up to individual destination numCars
	@Test
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

		Assert.assertEquals(tempSum, numCars);
		Assert.assertEquals("SEA", flow.getStartNode().getId());
		Assert.assertEquals("LIN", flow.getDestinations().get(0).getToNode().getId());
		Assert.assertEquals("CHI", flow.getDestinations().get(1).getToNode().getId());

	}
	
	
	//Test that the revenue model populates properly
	@Test
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
		
		Assert.assertEquals("SEA", revenue.getStartNode().getId());
		Assert.assertEquals("MON", revenue.getAmounts().get(0).getToNode().getId());
		Assert.assertEquals("ABU", revenue.getAmounts().get(1).getToNode().getId());


	}
	
}
