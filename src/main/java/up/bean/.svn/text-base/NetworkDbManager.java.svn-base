package up.bean;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import up.model.Network;

@Stateless
public class NetworkDbManager {

	private final Logger LOG = Logger.getLogger(NetworkDbManager.class.getName());
	
	@PersistenceContext(unitName="SolverUnit")
	private EntityManager manager;

	
	public Network createNetwork(Network network) { 
		try { 
			manager.persist(network);
			return network;
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "The network could not be persisted", e);
			return null;
		}
	}
	
	public void removeNetwork(Network network) { 
		try { 
			network = manager.merge(network);
			manager.remove(network);
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "The network could not be removed", e);
			throw new RuntimeException("A database error occurred.");
		}
	}
	
	public void updateNetwork(Network network) { 
		try { 
			manager.merge(network);
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "The network could not be updated", e);
			throw new RuntimeException("A database error occurred.");
		}
	}
	

	public Network findNetwork(String name) { 
		try { 
			return manager.find(Network.class, name);
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "An error occurred while finding a network", e);
			return null;
		}
	}
	
	
	public Network findNetworkByName(String name) { 
		try {
			List<Network> networks = manager.createQuery("select n from Network as n where n.name = ?1").setParameter(1, name).getResultList();
			if ( networks.size() != 1 ) { 
				throw new RuntimeException("more than one network was found, this should not be the case");
			} else { 
				return networks.get(0);
			}
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "An error occurred while finding a network", e);
			return null;
		}
	}

}
