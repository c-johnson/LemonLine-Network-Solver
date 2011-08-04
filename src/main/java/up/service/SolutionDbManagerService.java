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
import up.bean.SolutionDbManager;
import up.model.Network;
import up.model.solution.Solution;
import up.solver.NetworkSimplex;


@Path("/solve/network")
@ManagedBean
public class SolutionDbManagerService {

	@EJB
	SolutionDbManager solutionManager;
	@EJB
	NetworkDbManager networkManager;

	@POST
	@Path("/solvenetwork")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void solveNetwork(
			
			@FormParam("name") String name
			)
			 {
				Network network = networkManager.findNetwork(name);
				NetworkSimplex networkSimplex = new NetworkSimplex();
				networkSimplex.setNetwork(network);
				
				Solution solution = networkSimplex.solve(networkSimplex.makeObjFunction(network), networkSimplex.makeConstraints(network));
				
				
				solutionManager.createSolution(solution);
			 }
	@GET
	@Path("/getsolution/{name}.xml")
	@Produces(MediaType.TEXT_XML)
	public Solution getSolutionXML(@PathParam("name") String name) {
		return solutionManager.findSolution(1);
	}
}
