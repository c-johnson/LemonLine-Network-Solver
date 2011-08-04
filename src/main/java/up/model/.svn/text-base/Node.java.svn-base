package up.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Node {
	
	public Node(){
		this.outgoingArcs = new ArrayList<Arc>();
		this.incomingArcs = new ArrayList<Arc>();
	}

	public Node(String id, String Name, double Latitude, double Longitude, int MaxCars){
		this.id = id;
		this.name = Name;
		this.latitude = Latitude;
		this.longitude = Longitude;
		this.maxCars = MaxCars;
	}
	
	public Node(String id, String Name, double Latitude, double Longitude, int MaxCars, Network network){
		this.id = id;
		this.name = Name;
		this.latitude = Latitude;
		this.longitude = Longitude;
		this.maxCars = MaxCars;
		this.network = network;
	}
	
	
	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private int maxCars;
	@XmlTransient
	private List<Arc> outgoingArcs;
	@XmlTransient
	private List<Arc> incomingArcs;
	@XmlTransient
	private List<Flow> incomingFlows;
	@XmlTransient
	private Flow outgoingFlow;
	@XmlTransient
	private List<Revenue> incomingRevenues;
	@XmlTransient
	private Revenue outgoingRevenue;
	@XmlTransient
	private Network network;
	
	@Id
	@Column(unique=true)
	public String getId() {
		return id;
	}
	public void setId(String iD) {
		id = iD;
	}
	
	@OneToMany(mappedBy="startNode", cascade=CascadeType.ALL)
		@XmlTransient

	public List<Arc> getOutgoingArcs() {
		return outgoingArcs;
	}

	public void setOutgoingArcs(List<Arc> outgoingArcs) {
		this.outgoingArcs = outgoingArcs;
	}
	
	@OneToMany(mappedBy="endNode", cascade=CascadeType.ALL)
		@XmlTransient

	public List<Arc> getIncomingArcs() {
		return incomingArcs;
	}

	public void setIncomingArcs(List<Arc> incomingArcs) {
		this.incomingArcs = incomingArcs;
	}
	

	public List<Flow> getIncomingFlows() {
		return incomingFlows;
	}

	public void setIncomingFlows(List<Flow> incomingFlows) {
		this.incomingFlows = incomingFlows;
	}
	
	@OneToOne(mappedBy="startNode", cascade=CascadeType.ALL)
	public Flow getOutgoingFlow() {
		return outgoingFlow;
	}

	public void setOutgoingFlow(Flow outgoingFlow) {
		this.outgoingFlow = outgoingFlow;
	}

	public List<Revenue> getIncomingRevenues() {
		return incomingRevenues;
	}

	public void setIncomingRevenues(List<Revenue> incomingRevenues) {
		this.incomingRevenues = incomingRevenues;
	}

	@OneToOne(mappedBy="startNode", cascade=CascadeType.ALL)
	public Revenue getOutgoingRevenue() {
		return outgoingRevenue;
	}

	public void setOutgoingRevenue(Revenue outgoingRevenue) {
		this.outgoingRevenue = outgoingRevenue;
	}
	
	@ManyToOne
	@XmlTransient

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getMaxCars() {
		return maxCars;
	}
	public void setMaxCars(int maxCars) {
		this.maxCars = maxCars;
	}
	
	public boolean validate() {
		if (
				id.length() > 0 && maxCars > 0 && name.length() > 0
		) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
