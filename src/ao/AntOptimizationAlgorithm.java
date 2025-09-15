package ao;

import graph.IWeightedGraph;

/**
 * The AntOptimizationAlgorithm interface represents an optimization algorithm for ants in the Ant Colony Optimization (ACO) algorithm.
 * It defines the method for optimizing the ant's path based on the given graph and parameters.
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public interface AntOptimizationAlgorithm {
	
	/**
     * Selects the next edge to be traversed by the ant
     *
     * @param ant   the ant to optimize
     * @param graph the weighted graph
     * @param gamma the parameter controlling the evaporation rate of pheromones
     * @param alfa  the parameter concerning the ant move event
     * @param beta  the parameter concerning the ant move event
     * @param delta the parameter concerning the ant move event
     * @return the time taken by the ant to traverse the selected edge
     */
	public double optimize(IAnt ant, IWeightedGraph graph, double gamma, double alfa, double beta, double delta);
}