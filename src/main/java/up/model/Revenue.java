package up.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Revenue {
	private long id;
	@XmlTransient
	private Node startNode;
	private List<Amount> amounts;
	@XmlTransient
	private Network network;
	
	
	@ManyToOne
	public Network getNetwork() {
		return network;
	}
	public void setNetwork(Network network) {
		this.network = network;
	}
	public Revenue(){
		amounts = new ArrayList<Amount>();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToOne
	public Node getStartNode() {
		return startNode;
	}
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	
	@OneToMany(mappedBy="revenue", cascade=CascadeType.ALL)
	public List<Amount> getAmounts() {
		return amounts;
	}
	
	public void setAmounts(List<Amount> amounts) {
		this.amounts = amounts;
	}
	
	public void addAmount(Amount amount){
		//Only add the Node if the node's values validate, and it is not already in the network.
		amounts.add(amount);
	}
}
