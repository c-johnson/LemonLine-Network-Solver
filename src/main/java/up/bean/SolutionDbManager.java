package up.bean;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import up.model.Network;
import up.model.solution.Solution;

@Stateless
public class SolutionDbManager {

	private final Logger LOG = Logger.getLogger(NetworkDbManager.class.getName());
	
	@PersistenceContext(unitName="SolverUnit")
	private EntityManager manager;

	
	public Solution createSolution(Solution solution) { 
		try { 
			manager.persist(solution);
			return solution;
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "The solution could not be persisted", e);
			return null;
		}
	}
	
	public void removeSolution(Solution solution) { 
		try { 
			solution = manager.merge(solution);
			manager.remove(solution);
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "The solution could not be removed", e);
			throw new RuntimeException("A database error occurred.");
		}
	}
	
	public void updateSolution(Solution solution) { 
		try { 
			manager.merge(solution);
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "The solution could not be updated", e);
			throw new RuntimeException("A database error occurred.");
		}
	}
	

	public Solution findSolution(long id) { 
		try { 
			return manager.find(Solution.class, id);
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "An error occurred while finding a solution", e);
			return null;
		}
	}
	
	
	public Solution findSolutionByNetwork(String name) { 
		try {
			List<Solution> solutions = manager.createQuery("select n from Solution as n where n.network_name = ?1").setParameter(1, name).getResultList();
			return solutions.get(0);
		} catch ( Exception e ) { 
			LOG.log(Level.SEVERE, "An error occurred while finding a network", e);
			return null;
		}
	}

}
