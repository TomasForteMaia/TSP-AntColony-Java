package ao;

import java.util.ArrayList;


/**
 * The IAnt interface represents an ant and defines all the basic methods belonging to it.
 *
 * <p>
 * An ant in the ACO algorithm represents a solution agent that traverses a graph in search of an optimal path.
 * It keeps track of its current node, unvisited nodes, and the path it has traversed.
 * The ant interacts with a colony and utilizes optimization algorithms to make movement decisions.
 * </p>
 *
 * <p>
 * Implementations of this interface should provide specific logic for moving the ant, updating pheromone levels,
 * and retrieving information about the ant's current state.
 * </p>
 *
 * @see IColony
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 * 
 */
public interface IAnt {
	
    /**
     * Gets the current node of the ant.
     *
     * @return the current node
     */
	public int getCurrentNode();
	
    /**
     * Sets the current node of the ant.
     *
     * @param node the node to set as current
     */
	public void setCurrentNode(int node);
	
    /**
     * Gets the list of unvisited nodes by the ant.
     *
     * @return the list of unvisited nodes
     */
	public ArrayList<Integer> getUnvisitedNodes();
	
    /**
     * Gets the path traversed by the ant.
     *
     * @return the path of the ant
     */
	public ArrayList<Integer> getPath();
	
    /**
     * Gets the colony that the ant belongs to.
     *
     * @return the colony
     */
	public IColony getColony();
	
	/**
     * Moves the ant to the next node based on the given parameters.
     *
     * @param gamma the gamma parameter for the ant's movement
     * @param alfa  the alfa parameter for the ant's movement
     * @param beta  the beta parameter for the ant's movement
     * @param delta the delta parameter for the ant's movement
     * @return the time it takes to traverse an edge
     */
	public double move(double gamma, double alfa, double beta, double delta);
	
	/**
     * Updates the pheromone levels of the colony after finding a Hamiltonian cycle.
     *
     * @param colony the colony to update pheromones for
     * @param gamma  the gamma parameter for updating pheromones
     * @return the weight of the Hamiltonian cycle
     */
	public int updatePheromones(IColony colony, double gamma);
}
