package ao;

import graph.IWeightedGraph;

/**
 * The IAntFactory interface provides a contract for creating instances of the Ant class.
 * 
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public interface IAntFactory {
	
	/**
	 * Creates a new instance of the Ant class with the specified initial parameters.
	 *
	 * @param nestNode      the initial node where the ant is located
	 * @param graphGetters  the weighted graph
	 * @param colony        the colony that the ant belongs to
	 * @param algorithm     the optimization algorithm used by the ant
	 * @return a new instance of the Ant class
	 */
	IAnt createAntInstance(int nestNode, IWeightedGraph graphGetters, IColony colony, AntOptimizationAlgorithm algorithm);
}
