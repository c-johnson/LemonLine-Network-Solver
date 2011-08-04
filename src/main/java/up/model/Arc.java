package up.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Arc {

	private long id;
	
	@XmlTransient //This is temporary until we implement Chris' refactored models
	private Node startNode;
	
	@XmlTransient //This is temporary until we implement Chris' refactored models
	private Node endNode;
	
	private int maxTrains;
	private double distance;
	private double fuelAdjustFactor;
	@XmlTransient
	private Network network;
	
public Arc(Node startNode, Node endNode, int maxTrains, double distance, double fuelAdjFactor){
			this.startNode = startNode;
			this.endNode = endNode;
			this.maxTrains = maxTrains;
			this.distance = distance;
			this.fuelAdjustFactor = fuelAdjFactor;
	}
	
	
	public Arc() {
	// TODO Auto-generated constructor stub
}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	public Node getStartNode() {
		return startNode;
	}
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	
	@ManyToOne

	public Node getEndNode() {
		return endNode;
	}
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	public int getMaxTrains() {
		return maxTrains;
	}
	public void setMaxTrains(int maxTrains) {
		this.maxTrains = maxTrains;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getFuelAdjustFactor() {
		return fuelAdjustFactor;
	}
	public void setFuelAdjustFactor(double fuelAdjFactor) {
		this.fuelAdjustFactor = fuelAdjFactor;
	}
	
	@ManyToOne
	@XmlTransient
	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}
	
	public boolean validate() {
		if (
				startNode.getId().equals(endNode.getId()) ||
				maxTrains < 0 ||
				distance <= 0 ||
				fuelAdjustFactor < 0
		) {
			return false;
		} else {
			return true;
		}
	}
}
