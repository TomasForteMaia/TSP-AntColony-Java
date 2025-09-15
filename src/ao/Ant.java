package ao;

import java.util.ArrayList;
import graph.IWeightedGraph;


/**
 * The Ant class represents an ant in the Ant Optimization (AO) algorithm.
 * It implements the IAnt and IAntFactory interfaces.
 *
 * <p>
 * This class represents an ant that traverses a weighted graph to find a Hamiltonian cycle.
 * It keeps track of its current node, the unvisited nodes, and the path it has traversed so far.
 * The ant uses an optimization algorithm to make movement decisions and interacts with the colony it belongs to.
 * </p>
 *
 * <p>
 * This class also implements the IAntFactory interface, allowing it to create new instances of Ant objects.
 * </p>
 *
 * @see IAnt
 * @see IAntFactory
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class Ant implements IAnt, IAntFactory{

	private int currentNode;
	private ArrayList<Integer> unvisitedNodes;
	private ArrayList<Integer> path;
	static private IWeightedGraph graphGetters;
	private IColony colony; 
	private AntOptimizationAlgorithm algorithm;

	/**
	 * Constructs an Ant object with the specified nest node, weighted graph, colony, and optimization algorithm.
	 *
	 * @param nestNode   the node where the ant starts its path
	 * @param graph      the weighted graph representing the problem
	 * @param colony     the colony the ant belongs to
	 * @param algorithm  the optimization algorithm used by the ant
	 */
	public Ant(int nestNode, IWeightedGraph graph, IColony colony, AntOptimizationAlgorithm algorithm){
		this.currentNode = nestNode;
		this.algorithm = algorithm;
		graphGetters = graph;
		int numNodes = graphGetters.getNumNodes();
		this.unvisitedNodes = new ArrayList<>(numNodes-1);
		for (int i = 1; i < numNodes+1; i++) {
			if (i != currentNode)
				unvisitedNodes.add(i);
		}	
		path = new ArrayList<>(numNodes+1);
		path.add(currentNode);
		this.colony = colony;
	}

	/**
	 * Creates a new instance of the Ant class.
	 *
	 * @param nestNode     the node where the ant starts its path
	 * @param graphGetters        the weighted graph
	 * @param colony       the colony that all ants belongs to
	 * @param algorithm    the optimization algorithm used by the ant
	 * @return a new instance of the Ant class
	 */
	public Ant createAntInstance(int nestNode, IWeightedGraph graphGetters, IColony colony, AntOptimizationAlgorithm algorithm) {
		return new Ant(nestNode, graphGetters, colony, algorithm);
	}

	/**
	 * Retrieves the current node of the ant.
	 *
	 * @return the current node
	 */
	public int getCurrentNode() {
		return currentNode;
	}
	
	/*
	 * Sets the current node of the ant.
	 *
	 * @param node the current node to set
	 */
	public void setCurrentNode(int node) {
		this.currentNode = node;
	}


	/**
	 * Retrieves the list of unvisited nodes.
	 *
	 * @return the list of unvisited nodes
	 */
	public ArrayList<Integer> getUnvisitedNodes(){
		return unvisitedNodes;
	}

	/**
	 * Retrieves the path traversed by the ant since the nest node.
	 *
	 * @return the path traversed
	 */
	public ArrayList<Integer> getPath(){
		return path;
	}

	/**
	 * Retrieves the colony that the ant belongs to.
	 *
	 * @return the colony that the ant belongs to
	 */
	public IColony getColony() {
		return colony;
	}

	/**
	 * Moves the ant to the next node based on the given parameters.
	 *
	 * @param gamma the parameter concerning the pheromone level
	 * @param alpha  the parameter for the ant's movement
	 * @param beta  the parameter for the ant's movement
	 * @param delta the parameter for the ant's movement
	 * @return the time it takes to traverse the edge
	 */
	public double move(double gamma, double alpha, double beta, double delta) {
		return algorithm.optimize(this, graphGetters, gamma, alpha, beta, delta);
	}

	/**
	 * Updates the pheromone levels of the colony based on the ant's path.
	 *
	 * @param colony the colony that the ant belongs to
	 * @param gamma  the parameter for updating the pheromone levels
	 * @return the weight of the Hamiltonian cycle
	 */
	public int updatePheromones (IColony colony, double gamma) { 	
		int pathWeight=0;
		for (int i = 1; i < path.size(); i++) {
			pathWeight += graphGetters.getWeight(path.get(i), path.get(i-1));
		}
		int W = graphGetters.getGraphWeight();
		for(int i = 1; i < path.size(); i++) {			
			colony.updateLevel(path.get(i), path.get(i-1), gamma*W/pathWeight);
		}
		return pathWeight;
	}

}
