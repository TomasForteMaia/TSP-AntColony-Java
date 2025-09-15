package ao;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Collections;
import dss.Observer;
import graph.IWeightedGraph;

/**
 * The Colony class represents a colony of ants.
 * It implements the IColony interface.
 *
 * <p>
 * This class stores all the ants and the common variables belonging to them, such as the pheromone levels,
 *  the nest node and the Hamiltonian cycles within a graph.
 * It provides methods for adding and removing observers, notifying observers of state changes and
 * retrieving and updating information on the colony's content.
 * </p>
 *
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 * @see IColony
 */
public class Colony implements IColony{

	private int numAnts;
	private int nestNode;
	private double[][] pheromones;
	private ArrayList<IAnt> ants;
	private PriorityQueue<CycleAndWeight> hamiltonianCycles;
	private List<Observer> observers;

	/**
	 * Constructs a Colony object with the specified number of ants, nest node, and other parameters.
	 *
	 * @param numAnts    the number of ants in the colony
	 * @param nestNode   the nest node where the ants are initialized
	 * @param numNodes   the number of nodes in the graph
	 * @param ant        the factory for creating ant instances
	 * @param graph      the weighted graph
	 * @param algorithm  the ant optimization algorithm
	 */
	public Colony(int numAnts, int nestNode, int numNodes, IAntFactory ant, IWeightedGraph graph, AntOptimizationAlgorithm algorithm) {
		this.numAnts = numAnts;
		this.nestNode = nestNode;
		this.pheromones = new double[numNodes][numNodes];

		this.observers = new ArrayList<>();

		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < numNodes; j++) {
				this.pheromones[i][j] = 0;
			}
		}
		ants = new ArrayList<>(numAnts);
		for(int i = 0; i < numAnts; i++) {
			ants.add(ant.createAntInstance(nestNode, graph, this, algorithm)); 
		}

		hamiltonianCycles = new PriorityQueue<CycleAndWeight>();      
	}

    /**
     * Adds an observer to the colony.
     *
     * @param observer the observer to be added
     */
    @Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
	}

    /**
     * Removes an observer from the colony.
     *
     * @param observer the observer to be removed
     */
    @Override
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}

    /**
     * Notifies all observers in the colony of an event with the specified start and end nodes.
     *
     * @param startNode the start node of the event
     * @param endNode   the end node of the event
     */
    @Override
	public void notifyObservers(int startNode, int endNode) {
		for (Observer observer : this.observers)
			observer.update(startNode, endNode);
	}

	/**
	 * Gets the number of ants in the colony.
	 *
	 * @return the number of ants in the colony
	 */
	public int getNumAnts() {
		return numAnts;
	}

	/**
	 * Gets the nest node of the colony where the ants are initialized.
	 *
	 * @return the nest node of the colony
	 */
	public int getNestNode() {
		return nestNode;
	}
	
	/**
	 * Gets the pheromone level of an edge
	 *
	 * @param startNode  the start node
	 * @param endNode    the end node
	 * @return the pheromone level of the edge
	 */
	public double getLevel(int startNode, int endNode) {
		return pheromones[startNode-1][endNode-1];
	}

	/**
	 * Gets the pheromones table containing all the edges pheromone level
	 *
	 * @return the pheromones table
	 */
	public double[][] getPheromonesTable(){ 
		return this.pheromones;
	}

	/**
	 * Updates the pheromone level of an edge.
	 *
	 * @param startNode  the start node
	 * @param endNode    the end node
	 * @param value      the value to update the pheromone level by
	 * @return the pheromone level of an edge
	 */
	public double updateLevel(int startNode, int endNode, double value) {
		double saveValue = getLevel(startNode, endNode);
		if (pheromones[startNode-1][endNode-1] + value < 0) {
			pheromones[startNode-1][endNode-1] = 0;
			pheromones[endNode-1][startNode-1] = 0;
		} else {
			pheromones[startNode-1][endNode-1] += value;
			pheromones[endNode-1][startNode-1] += value;
		}
		// Notify the simulator to add an evaporation event to the queue
		if ((value > 0) && (saveValue == 0)) {
			this.notifyObservers(startNode, endNode);
			// return the pheromone level before the update (necessary for EvaporationEvent class)
			return saveValue;
		}

		return pheromones[endNode-1][startNode-1]; // return the pheromone level after the update
	}

	/**
	 * Gets the list of ants in the colony.
	 *
	 * @return the list of ants in the colony
	 */
	public ArrayList<IAnt> getAnts() {
		return ants;
	}

	/**
	 * Adds a Hamiltonian cycle to the colony.
	 *
	 * @param cycle  the Hamiltonian cycle
	 * @param weight the weight of the Hamiltonian cycle
	 */
	public void addHamiltonianCycle(ArrayList<Integer> cycle, int weight) {
		CycleAndWeight hamiltonianCycle = new CycleAndWeight(cycle, weight);
		if(!hamiltonianCycles.contains(hamiltonianCycle))
			hamiltonianCycles.add(hamiltonianCycle);
	}

	/**
	 * Prints the top N Hamiltonian cycles (Excluding the best one) based on their weights.
	 *
	 * @param n the number of top cycles to print
	 */
	public void printTopHamiltonianCycles(int n) {
		// Convert PriorityQueue to ArrayList
		ArrayList<CycleAndWeight> cycles = new ArrayList<>();
		cycles.addAll(hamiltonianCycles);

		// Sort the cycles based on weights
		Collections.sort(cycles);

		if(cycles.size() <= 1) {
			System.out.println("");
		}

		for (int i = 1; i < Math.min(cycles.size(), n); i++) {
			if(i != 1)
				System.out.print("\t\t\t\t\t\t");
			else {
				if(n==1)
					System.out.print("\t");
				else
					System.out.print("\t\t");
			}
			ArrayList<Integer> cycle = cycles.get(i).getHamiltonianCycle();
			int weight = cycles.get(i).getWeight();

			System.out.print("{");
			for (int j = 0; j < cycle.size(); j++) {
				System.out.print(cycle.get(j));
				if(j != cycle.size() - 1)
					System.out.print(",");
			}
			System.out.println("}:" + weight);
		}
	}

	/**
	 * Prints the best Hamiltonian cycle.
	 */
	public void printBestHamiltonianCycle() {
		// Convert PriorityQueue to ArrayList
		ArrayList<CycleAndWeight> cycles = new ArrayList<>();
		cycles.addAll(hamiltonianCycles);
		// Sort the cycles based on weights
		// Collections.sort(cycles);

		if(cycles.size() == 0)
			System.out.println("{}");    
		else {
			System.out.print("\t");

			ArrayList<Integer> cycle = cycles.get(0).getHamiltonianCycle();
			int weight = cycles.get(0).getWeight();

			System.out.print("{");
			for (int j = 0; j < cycle.size(); j++) {
				System.out.print(cycle.get(j));
				if(j != cycle.size() - 1)
					System.out.print(",");
			}
			System.out.println("}:" + weight);
		}
	}

	/**
	 * Gets the priority queue containing the Hamiltonian cycles.
	 *
	 * @return the priority queue of Hamiltonian cycles
	 */
	public PriorityQueue<CycleAndWeight> getHamiltonianCycles() {
		return hamiltonianCycles;
	}

}

