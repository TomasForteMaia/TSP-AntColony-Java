package graph;

/**
 * The {@code Edge} class represents a weighted edge in a Weighted Graph and is
 * a class to store edges of the weighted graph.
 *
 * The Edge has properties such as source node, destination node and weigth.
 * Each edge consists of two integers (naming the two vertices) and a real-value weight. 
 * The data type provides methods for accessing the two endpoints of the edge and
 * the weight.
 * 
 *
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */

public class Edge {

	private final int startNode;
	private final int endNode;
	private final int weight;

	/**
	 * Initializes an edge between nodes {@code src} and {@code dest} of
	 * the given {@code weight}.
	 *
	 * @param  startNode a node
	 * @param  endNode the other node
	 * @param  weight the weight of the edge
	 * @throws IllegalArgumentException if either {@code src} or {@code dest}
	 *         is a negative integer
	 */
	public Edge(int startNode, int endNode, int weight) {
		if (startNode < 0) throw new IllegalArgumentException("Node index must be a non-negative integer");
		if (endNode < 0) throw new IllegalArgumentException("Node index must be a non-negative integer");
		if(weight < 0) throw new IllegalArgumentException("Node index must be a non-negative integer");

		this.startNode = startNode;
		this.endNode = endNode;
		this.weight = weight;
	}

	/**
	 * Returns one endpoint of the edge.
	 *
	 * @return one endpoint of the edge
	 */
	public int getStartNode() {
		return this.startNode;
	}

	/**
	 * Returns the other endpoint of the edge.
	 *
	 * @return the other endpoint of the edge
	 */
	public int getEndNode() {
		return this.endNode;
	}

	/**
	 * Returns the weight of the edge.
	 *
	 * @return the weight of the edge
	 */
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Returns a string representation of this edge.
	 *
	 * @return a string representation of this edge
	 */
	public String toString() {
		return String.format("Edge:%d-%d | Weight: %d", startNode, endNode, weight);
	}

}

