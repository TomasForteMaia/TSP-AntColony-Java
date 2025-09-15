package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The {@code UndirectedWeightedGraph} class represents an undirected graph of vertices named 1 through V.
 * It provides operations to add edges to the graph, iterate over adjacent vertices, and retrieve graph information.
 * This implementation uses an adjacency-lists representation.
 *
 * @see IGraph
 * @see IWeightedGraph
 *
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */

public class UndirectedWeightedGraph implements IWeightedGraph {

	private int nodes;
	private int totalWeight;
	private ArrayList<Edge>[] adjacencyLists;

	private static UndirectedWeightedGraph instance;

	/**
	 * Constructs a new {@code UndirectedWeightedGraph} object with the specified number of nodes and maximum weight.
	 *
	 * @param nodes the number of nodes in the graph
	 * @param maxWeight the maximum weight of an edge
	 */
	private UndirectedWeightedGraph(int nodes, int maxWeight) {
		this.nodes = nodes;
		this.totalWeight = 0;
		this.adjacencyLists = (ArrayList<Edge>[]) new ArrayList<?>[nodes];
		for (int i = 0; i < nodes; i++) {
			this.adjacencyLists[i] = new ArrayList<>();
		}
		this.buildGraph(nodes, maxWeight);
	}

	/**
	 * Constructs a new {@code UndirectedWeightedGraph} object with the specified number of nodes and adjacency matrix.
	 *
	 * @param nodes the number of nodes in the graph
	 * @param matrix the adjacency matrix representing the graph
	 */
	private UndirectedWeightedGraph(int nodes, int[][] matrix) {
		this.nodes = nodes;
		this.totalWeight = 0;
		this.adjacencyLists = (ArrayList<Edge>[]) new ArrayList<?>[nodes];
		for (int i = 0; i < nodes; i++) {
			adjacencyLists[i] = new ArrayList<>();
		}
		this.buildGraph(nodes, matrix);
	}

	/**
	 * Returns an instance of {@code UndirectedWeightedGraph} with the specified number of nodes and maximum weight.
	 *
	 * @param nodes the number of nodes in the graph
	 * @param maxWeight the maximum weight of an edge
	 * @return an instance of {@code UndirectedWeightedGraph}
	 */
	public static UndirectedWeightedGraph getInstance(int nodes, int maxWeight) {
		if (instance == null) {
			instance = new UndirectedWeightedGraph(nodes, maxWeight);
		}
		return instance;
	}

	/**
	 * Returns an instance of {@code UndirectedWeightedGraph} with the specified number of nodes and adjacency matrix.
	 *
	 * @param nodes the number of nodes in the graph
	 * @param matrix the adjacency matrix representing the graph
	 * @return an instance of {@code UndirectedWeightedGraph}
	 */
	public static UndirectedWeightedGraph getInstance(int nodes, int[][] matrix) {
		if (instance == null) {
			instance = new UndirectedWeightedGraph(nodes, matrix);
		}
		return instance;
	}

	/**
	 * Returns the number of nodes in the graph.
	 *
	 * @return the number of nodes
	 */
	public int getNumNodes() {
		return nodes;
	}


	/**
	 * Returns the total weight of the graph.
	 *
	 * @return the total weight of the graph
	 */
	public int getGraphWeight() {
		return totalWeight;
	} 

	/**
	 * Returns the weight of the edge connecting the specified start and end nodes.
	 *
	 * @param startNode the starting node of the edge
	 * @param endNode the ending node of the edge
	 * @return the weight of the edge
	 */
	public int getWeight(int startNode, int endNode) {
		Edge edge;
		edge = getEdge(startNode, endNode);
		if (edge == null)
			return 0;
		else return edge.getWeight();
	}  


	/**
	 * Returns a list of adjacent nodes to the specified node.
	 *
	 * @param node the node for which to retrieve the adjacent nodes
	 * @return a list of adjacent nodes
	 */
	public ArrayList<Integer> getAdjacentNodes(int node){

		ArrayList<Edge> nodeList = adjacencyLists[node-1];
		ArrayList<Integer> adjacentNodes = new ArrayList<>();

		for(Edge edge : nodeList) {
			adjacentNodes.add(edge.getEndNode());
		}

		return adjacentNodes;
	}

	/**
	 * Prints the graph representation.
	 */ 
	public void printGraph() {		     
		int j = 1;
		int find = 0;
		for (int i = 0 ; i < this.nodes; i++) {
			j = 1;
			System.out.print("\t\t     ");
			for(j = 1; j < this.nodes + 1; j++) {
				find = 0;
				for(Edge edge : this.adjacencyLists[i]){
					if(edge.getEndNode() == j) {
						System.out.print(edge.getWeight() + " ");
						find = 1;
						break;
					}
				}
				if (find == 0)
					System.out.print("0 ");
			}
			System.out.println();
		}
	}

	/**
	 * Adds an edge between two vertices with a given weight.
	 *
	 * @param source the source vertex
	 * @param destination the destination vertex
	 * @param weight the weight of the edge
	 */
	private void addEdge(int startNode, int endNode, int weight) {
		adjacencyLists[startNode-1].add(new Edge(startNode, endNode, weight));
		adjacencyLists[endNode-1].add(new Edge(endNode, startNode, weight));
		this.totalWeight+=weight;
	}

	// return the edge
	private Edge getEdge(int startNode, int endNode) {
		for (Edge edge : adjacencyLists[startNode-1]) {
			if (edge.getEndNode() == endNode)
				return edge;
		}
		return null;
	}  

	/**
	 * Builds the graph with the specified number of nodes and maximum weight.
	 *
	 * @param numNodes the number of nodes in the graph
	 * @param maxWeight the maximum weight of an edge
	 */
	private void buildGraph(int numNodes, int maxWeight) {

		ArrayList<Integer> hamiltonianCycle = new ArrayList<>(numNodes+1); 

		for(int i = 1; i < numNodes+1; i++) {
			hamiltonianCycle.add(i);
		}

		Collections.shuffle(hamiltonianCycle);
		hamiltonianCycle.add(hamiltonianCycle.get(0)); // add the first element of the list to the end to create a cycle

		int node1;
		int node2;
		Random random = new Random();
		int weight;

		// add the hamiltonian cycle to the graph structure
		for(int i = 1; i < hamiltonianCycle.size(); i++) {
			node1 = hamiltonianCycle.get(i-1);
			node2 = hamiltonianCycle.get(i);
			weight = random.nextInt(maxWeight)+1;
			this.addEdge(node1, node2, weight);
		}

		int numEdges = numNodes + random.nextInt((numNodes*(numNodes-1)/2) - numNodes + 1); // random number of edges of the graph
		int viable = 0;

		for(int i = 0; i < numEdges-numNodes; i++) {

			node1 = random.nextInt(numNodes)+1;
			node2 = random.nextInt(numNodes)+1;
			weight = random.nextInt(maxWeight)+1;

			while(viable != 1) {
				// checks if node1 is equal to node2
				if(node1 == node2) {
					node1 = random.nextInt(numNodes)+1;
					continue;
				}

				for(Edge edge : adjacencyLists[node1-1]) {
					// checks if the edge is already in the graph
					if(edge.getEndNode() == node2) {
						if(adjacencyLists[node1-1].size() > adjacencyLists[node2-1].size()) {
							viable=0;
							node1 = random.nextInt(numNodes)+1;
							break;
						}	 
						else if(adjacencyLists[node1-1].size() == adjacencyLists[node2-1].size()) {
							if(adjacencyLists[node1-1].size() == numNodes-1) {
								viable = 0;
								node1 = random.nextInt(numNodes)+1;
								node2 = random.nextInt(numNodes)+1;
								break;
							}
							else {
								viable = 0;
								node1 = random.nextInt(numNodes)+1;
								break;
							}

						}
						else {
							viable = 0;
							node2 = random.nextInt(numNodes)+1;
							break;
						}

					}
					// it is viable to add an edge
					else viable = 1;
				}		    			    	 		    	 
			}
			// add the edge to the graph
			this.addEdge(node1, node2, weight); 	    	 
		}

	}

	/**
	 * Builds the graph with the specified number of nodes and adjacency matrix.
	 *
	 * @param numNodes the number of nodes in the graph
	 * @param matrix the adjacency matrix representing the graph
	 */
	private void buildGraph(int numNodes, int[][] matrix) {		     
		int weight;	 
		// Create the adjacency lists
		for (int i = 0; i < matrix.length; i++) { 	
			for (int j = i+1; j < matrix.length; j++) {
				weight = matrix[i][j];
				if (weight != 0)
					this.addEdge(i+1, j+1, weight);
			}
		}
	}

}