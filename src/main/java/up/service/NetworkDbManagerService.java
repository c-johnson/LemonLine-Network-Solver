package up.service;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import up.bean.NetworkDbManager;
import up.controller.Populator;
import up.model.Arc;
import up.model.Network;
import up.model.Node;
import up.solver.NetworkSimplex;


@Path("/manage/network")
@ManagedBean
public class NetworkDbManagerService {

	@EJB
	NetworkDbManager networkManager;

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void createNetwork(
			@FormParam("name") String name,
			@FormParam("maxCarsPerTrain") double maxCarsPerTrain,
			@FormParam("nonFuelCostPerMile") double nonFuelCostPerMile,
			@FormParam("fuelCostPerMile") double fuelCostPerMile,
			@FormParam("carCostPerMile") double carCostPerMile ) {
		Network network = new Network();
		network.setName(name);
		network.setMaxCarsPerTrain(maxCarsPerTrain);
		network.setNonFuelCostPerMile(nonFuelCostPerMile);
		network.setFuelCostPerMile(fuelCostPerMile);
		network.setCarCostPerMile(carCostPerMile);

		networkManager.createNetwork(network);
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void removeNetwork(@FormParam("name") String name) {
		networkManager.removeNetwork(networkManager.findNetwork(name));
	}
	
	@POST
	@Path("/addnode")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void addNode(
			
			@FormParam("network") String networkName,
			@FormParam("id") String id,
			@FormParam("name") String name,
			@FormParam("latitude") double latitude,
			@FormParam("longitude") double longitude,
			@FormParam("maxCars") int maxCars ) {
		Network network = networkManager.findNetworkByName(networkName);
		Node node = new Node();
		node.setId(id);
		node.setName(name);
		node.setLatitude(latitude);
		node.setLongitude(longitude);
		node.setMaxCars(maxCars);
		node.setNetwork(network);
				
		network.addNode(node);
		
		networkManager.updateNetwork(network);
	}
	
	@POST
	@Path("/addarc")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void addArc(
			
			@FormParam("network") String networkName,
			@FormParam("startNode") String startNode,
			@FormParam("endNode") String endNode,
			@FormParam("distance") double distance,
			@FormParam("maxTrains") int maxTrains,
			@FormParam("fuelAdjustFactor") double fuelAdjustFactor)
			 {
		Network network = networkManager.findNetworkByName(networkName);
		Node StartNode = new Node();
		StartNode = network.findNode(startNode);
		Node EndNode = new Node();
		EndNode = network.findNode(endNode);

		Arc arc = new Arc();
		arc.setStartNode(StartNode);
		arc.setEndNode(EndNode);
		arc.setMaxTrains(maxTrains);
		arc.setDistance(StartNode.getLongitude());
		arc.setFuelAdjustFactor(fuelAdjustFactor);
		arc.setNetwork(network);
		
		network.addArc(arc);
		
		networkManager.updateNetwork(network);
	}
	
	@POST
	@Path("/loadnetwork")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void loadNetwork(
			
			@FormParam("url") String theURL
			)
			 {
					//theURL = "http://cse-cig.unl.edu:8080/upproject/rest/networkGenerator?groupId=3"
					Populator pop = new Populator();
					pop.getXML(theURL, "network");
					Network network = pop.populateNetwork();
					networkManager.createNetwork(network);
			 }
	
	@POST
	@Path("/loadnetworkinputs")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void loadNetworkInputs(
			
			@FormParam("url") String theURL
			)
			 {
					//theURL = "http://cse-cig.unl.edu:8080/upproject/rest/networkGenerator?groupId=3"
					Populator pop = new Populator();
					pop.getXML(theURL, "networkInputs");
					
					//DO SOMETHING WITH THE XML CRAP I JUST READ
			 }
	


	@GET
	@Path("/getnetwork/{name}.xml")
	@Produces(MediaType.TEXT_XML)
	public Network getNetworkXML(@PathParam("name") String name) {
		return networkManager.findNetworkByName(name);
	}
	
	@GET
	@Path("/getnetwork/{name}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Network getNetworkJson(@PathParam("name") String name) {
		return networkManager.findNetworkByName(name);
	}


	
	/*
	@GET
	@Path("/load/{id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Student loadStudentJson(@PathParam("id") long id) {
		return studentManager.findStudentById(id);
	}

	@GET
	@Path("/find/{sid}/xml")
	@Produces(MediaType.TEXT_XML)
	public Student findStudentXml(@PathParam("sid") String sid) {
		return studentManager.findStudentBySid(sid);
	}

	@GET
	@Path("/find/{sid}/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Student findStudentJson(@PathParam("sid") String sid) {
		return studentManager.findStudentBySid(sid);
	}
*/
	
	
	/*
	@POST
	@Path("/addGrade")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Student addGrade(@FormParam("id") long id,
			@FormParam("score") float score, @FormParam("letter") String letter) {
		Student s = studentManager.findStudentById(id);
		Grade g = new Grade();
		g.setScore(score);
		g.setLetter(letter);
		g.setStudent(s);

		if (s.getGrades() == null) {
			s.setGrades(new ArrayList<Grade>());
		}

		s.getGrades().add(g);

		studentManager.updateStudent(s);

		return studentManager.findStudentById(id);
	}
	*/
}
