package graph;

/**
 * The {@code IGraph} interface represents a graph data structure.
 * 
 * Implementations of this interface provide specific implementations
 * of graph data structures and related operations.
 * 
 * The {@code getNumNodes()} method retrieves the number of nodes in the graph.
 * 
 * @see IWeightedGraph
 * @see UndirectedWeightedGraph
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public interface IGraph {

	/**
	 * Retrieves the number of nodes in the graph.
	 * 
	 * @return the number of nodes in the graph
	 */
	public int getNumNodes();
}