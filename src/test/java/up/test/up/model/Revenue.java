package up.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Revenue {
	private long id;
	private Node startNode;
	private ArrayList<Amount> amounts;
	
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
	public ArrayList<Amount> getAmounts() {
		return amounts;
	}
	
	public void setAmounts(ArrayList<Amount> amounts) {
		this.amounts = amounts;
	}	
}
