package graph;

import java.util.ArrayList;

/**
 * The {@code IWeightedGraph} interface represents a weighted graph data structure.
 * 
 * Implementations of this interface provide specific implementations of weighted graph data structures and related operations.
 * 
 * The {@code getNumNodes()} method retrieves the number of nodes in the graph.
 * 
 * The {@code getWeight(int startNode, int endNode)} method retrieves the weight of the edge connecting the specified start and end nodes.
 * 
 * The {@code getGraphWeight()} method retrieves the total weight of the graph, which is the sum of all edge weights.
 * 
 * The {@code getAdjacentNodes(int node)} method retrieves a list of adjacent nodes to the specified node.
 * 
 * The {@code printGraph()} method prints the graph representation.
 * 
 * @see IGraph
 * @see UndirectedWeightedGraph
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public interface IWeightedGraph extends IGraph {

	/**
	 * Retrieves the number of nodes in the graph.
	 * 
	 * @return the number of nodes in the graph
	 */
	public int getNumNodes();

	/**
	 * Retrieves the weight of the edge connecting the specified start and end nodes.
	 * 
	 * @param startNode one endpoint of the edge
	 * @param endNode the other endpoint of the edge
	 * @return the weight of the edge
	 */
	public int getWeight(int startNode, int endNode);

	/**
	 * Retrieves the total weight of the graph.
	 * 
	 * @return the total weight of the graph
	 */
	public int getGraphWeight();

	/**
	 * Retrieves a list of adjacent nodes to the specified node.
	 * 
	 * @param node the node for which to retrieve the adjacent nodes
	 * @return a list of adjacent nodes
	 */
	public ArrayList<Integer> getAdjacentNodes(int node);

	/**
	 * Prints the graph representation.
	 */
	public void printGraph();
}