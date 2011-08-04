package up.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@XmlRootElement
public class Network {
	
	public Network(){
		arcs = new ArrayList<Arc>();
		nodes = new ArrayList<Node>();
	}
	
	public Network(String Name, List<Node> Nodes, List<Arc> Arcs, double mCPT, double nFCPM, double fCPM, double cCPM){
		this.name =Name;
		this.nodes = Nodes;
		this.arcs = Arcs;
		this.maxCarsPerTrain = mCPT;
		this.nonFuelCostPerMile = nFCPM;
		this.fuelCostPerMile = fCPM;
		this.carCostPerMile = cCPM;
		
	}
	
	private String name;
	private List<Node> nodes;
	private List<Arc> arcs;
	private double maxCarsPerTrain;
	private double nonFuelCostPerMile;
	private double fuelCostPerMile;
	private double carCostPerMile;
	
	
	@Id
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name.length() > 0)
		{
			this.name = name;
		}
		else
		{
			this.name = "defaultNetwork";
		}
	}
	
	@OneToMany(mappedBy="network", cascade=CascadeType.ALL)
	public List<Node> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public boolean addNode(Node node){
		//Only add the Node if the node's values validate, and it is not already in the network.
		if (node.validate() && findNode(node.getId()) == null)
		{
			nodes.add(node);
			return true;
		}
		return false;
	}
	
	@OneToMany(mappedBy="network", cascade=CascadeType.ALL)
	public List<Arc> getArcs() {
		return arcs;
	}
	
	public void setArcs(List<Arc> arcs) {
		this.arcs = arcs;
	}
	
	public boolean addArc(Arc arc){
		//Only add the Arc if the arc's values validate, and its start and end nodes are in the network.
		if (arc.validate() && findNode(arc.getStartNode().getId()) != null && findNode(arc.getEndNode().getId()) != null) {
			arcs.add(arc);
			return true;
		}
		return false;
	}
	
	public double getMaxCarsPerTrain() {
		return maxCarsPerTrain;
	}
	
	public void setMaxCarsPerTrain(double maxCarPerTrain) {
		if (maxCarPerTrain > 0)
		{
			this.maxCarsPerTrain = maxCarPerTrain;
		}
		else
		{
			this.maxCarsPerTrain = 0;//default?
		}
	}
	
	public double getNonFuelCostPerMile() {
		return nonFuelCostPerMile;
	}
	
	public void setNonFuelCostPerMile(double nonFuelCostPerMile) {
		if (nonFuelCostPerMile > 0)
		{
			this.nonFuelCostPerMile = nonFuelCostPerMile;
		}
		else
		{
			this.nonFuelCostPerMile = 0;
		}
	}
	public double getFuelCostPerMile() {
		return fuelCostPerMile;
	}
	public void setFuelCostPerMile(double fuelCostPerMile) {
		if (fuelCostPerMile > 0)
		{
			this.fuelCostPerMile = fuelCostPerMile;
		}
		else
		{
			this.fuelCostPerMile = 0;
		}
	}
	public double getCarCostPerMile() {
		return carCostPerMile;
	}
	public void setCarCostPerMile(double carCostPerMile) {
		
		if (carCostPerMile > 0)
		{
			this.carCostPerMile = carCostPerMile;
		}
		else
		{
			this.carCostPerMile = 0;
		}
	}
	public Node findNode(String idToFind){
		for(Node node: nodes){
			if(node.getId().equals(idToFind)){
				return node;
			}
		}
		return null;
	}

	
	
	
}
