package up.service;

import java.util.ArrayList;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import up.bean.NetworkDbManager;
import up.model.Arc;
import up.model.Network;
import up.model.Node;


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
		
		String temp = network.getName();
		
		network.addNode(node);
		
		networkManager.updateNetwork(network);
	}
/*
	@GET
	@Path("/load/{id}.xml")
	@Produces(MediaType.TEXT_XML)
	public Student loadStudentXml(@PathParam("id") long id) {
		return studentManager.findStudentById(id);
	}

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
