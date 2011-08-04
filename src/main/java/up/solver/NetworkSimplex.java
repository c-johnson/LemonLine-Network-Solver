package up.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.SimplexSolver;

import up.model.inputs.Flow;
import up.model.network.Network;
import up.model.inputs.Revenue;
import up.model.solution.Solution;

public class NetworkSimplex {
	
	
	Network network;
	List<Flow> flows;
	List<Revenue> revenues;
	Solution solution;
	LinearObjectiveFunction obj;
	Collection<LinearConstraint> constraints;
	SimplexSolver solver = new SimplexSolver();
	
	
	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public List<Flow> getFlows() {
		return flows;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public List<Revenue> getRevenues() {
		return revenues;
	}

	public void setRevenues(List<Revenue> revenues) {
		this.revenues = revenues;
	}

	public LinearObjectiveFunction getObj() {
		return obj;
	}

	public void setObj(LinearObjectiveFunction obj) {
		this.obj = obj;
	}

	public Collection<LinearConstraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(Collection<LinearConstraint> constraints) {
		this.constraints = constraints;
	}

	public SimplexSolver getSolver() {
		return solver;
	}

	public void setSolver(SimplexSolver solver) {
		this.solver = solver;
	}
	
	public LinearObjectiveFunction makeObjFunction(Network network)
	{
		//this will create an obj function based on the network and linked inputs
		LinearObjectiveFunction objective = new LinearObjectiveFunction (new double[] { 2, 2, 1 }, 0);
		return objective;
		
	}
	
	public Collection<LinearConstraint> makeConstraints(Network network)
	{
		//this will create contrains based on the network and linked inputs
		Collection<LinearConstraint> constraints = null;
		return  constraints;
	}
	
	public Solution solve(LinearObjectiveFunction objective, Collection<LinearConstraint> constraints) {
		//This will tell the solver library to solve the given problem and return the final cost solution.
		Solution solution = new Solution();
		solution.setNetwork(network);
		solution.setTotalCost(25.0);
		
		
		return solution;
	}
	
	public List<Double> getSolutionVariables() {
		
		ArrayList<Double> variables = new ArrayList();
		return variables;
	}
	
	

}
