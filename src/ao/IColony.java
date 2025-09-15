package ao;

import java.util.ArrayList;
import java.util.PriorityQueue;

import dss.Observer;

/**
 * The IColony interface represents a colony of ants in the Ant Colony Optimization (ACO) algorithm.
 * It provides methods for managing the colony and interacting with the ants.
 * 
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public interface IColony {
	
    /**
     * Adds an observer to the colony.
     *
     * @param observer the observer to be added
     */
	void addObserver(Observer observer);
	
    /**
     * Removes an observer from the colony.
     *
     * @param observer the observer to be removed
     */
	void removeObserver(Observer observer);
	
    /**
     * Notifies the observers of the colony about a change in state.
     *
     * @param startNode the start node of the observed event
     * @param endNode   the end node of the observed event
     */
	void notifyObservers(int startNode, int endNode);  
	
    /**
     * Gets the number of ants in the colony.
     *
     * @return the number of ants in the colony
     */
	int getNumAnts();
	
    /**
     * Gets the nest node of the colony where the ants are initialized.
     *
     * @return the nest node of the colony
     */
	int getNestNode();
	
    /**
     * Gets the pheromone level between two nodes in the graph.
     *
     * @param startNode the start node
     * @param endNode   the end node
     * @return the pheromone level between the nodes
     */
	double getLevel(int startNode, int endNode);
	
    /**
     * Gets the pheromones table representing the pheromone levels between nodes in the graph.
     *
     * @return the pheromones table
     */
	double[][] getPheromonesTable();
	
    /**
     * Updates the pheromone level between two nodes in the graph by the specified value.
     *
     * @param startNode the start node
     * @param endNode   the end node
     * @param value     the value by which the pheromone level is updated
     * @return the updated pheromone level between the nodes
     */
	double updateLevel(int startNode, int endNode, double value);
	
    /**
     * Gets a list of ants in the colony.
     *
     * @return a list of ants in the colony
     */
	ArrayList<IAnt> getAnts();
	
    /**
     * Adds a Hamiltonian cycle to the colony with its weight.
     *
     * @param cycle  the Hamiltonian cycle represented as a list of nodes
     * @param weight the weight of the Hamiltonian cycle
     */
	public void addHamiltonianCycle(ArrayList<Integer> cycle, int weight);
	
    /**
     * Prints the top N Hamiltonian cycles (Excluding the best one) in the colony based on their weights.
     *
     * @param n the number of top cycles to print
     */
	public void printTopHamiltonianCycles(int n);
	

    /**
     * Prints the best Hamiltonian cycle in the colony based on its weight.
     */
	public void printBestHamiltonianCycle();
	
    /**
     * Gets the priority queue of Hamiltonian cycles in the colony.
     *
     * @return the priority queue of Hamiltonian cycles
     */
	public PriorityQueue<CycleAndWeight> getHamiltonianCycles();
}






