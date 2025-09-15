package ao;

import java.util.ArrayList;
import java.util.Random;
import graph.IWeightedGraph;

/**
 * The AntColonyOptimization class implements the AntOptimizationAlgorithm interface
 * and represents the Ant Colony Optimization algorithm.
 * 
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class AntColonyOptimization implements AntOptimizationAlgorithm {
	
    /**
     * Default constructor for the AntColonyOptimization class.
     */
    public AntColonyOptimization() {
        // No initialization logic required for the default constructor
    }


	/**
     * Selects the next edge to be traversed by the ant
     *
     * @param ant           the ant to move
     * @param graphGetters  the weighted graph
     * @param gamma         the parameter controlling the evaporation rate of pheromones
     * @param alfa  the parameter concerning the ant move event
     * @param beta  the parameter concerning the ant move event
     * @param delta the parameter concerning the ant move event
     * @return the time taken by the ant to traverse the selected edge
     */
	public double optimize(IAnt ant, IWeightedGraph graphGetters, double gamma, double alfa, double beta, double delta) {

		ArrayList<Integer> list = graphGetters.getAdjacentNodes(ant.getCurrentNode());

		ArrayList<Integer> unvisitedAdjacent = new ArrayList<>();


		int node;
		int pathWeight = 0;
		double tempo = 0;
		
		// Find unvisited adjacent nodes
		for (int i = 0; i < list.size();  i++) {
			node = list.get(i);
			if (ant.getUnvisitedNodes().contains(node))
				unvisitedAdjacent.add(node);
		}
		// No unvisited adjacent nodes
		if (unvisitedAdjacent.isEmpty()) {
			// No unvisited nodes
			if(ant.getUnvisitedNodes().isEmpty()) {
				// Edge to the nest node
				if(list.contains(ant.getColony().getNestNode())) {
					// Hamiltonian cycle found
					ant.getPath().add(ant.getColony().getNestNode());
					ArrayList <Integer> pathClone = (ArrayList<Integer>) ant.getPath().clone();
					ant.setCurrentNode(ant.getColony().getNestNode());
					pathWeight = ant.updatePheromones(ant.getColony(), gamma);
					pathClone.remove(pathClone.size()-1);
					ant.getColony().addHamiltonianCycle(pathClone, pathWeight);

					tempo = moveTime(ant, graphGetters, delta);
					restartVar(ant, graphGetters);
					return tempo;
				}
				// No edge to the nest node. The ant has to revisit a node
				else {
					tempo = backtrack(ant, graphGetters, list, alfa, beta, delta);
					return tempo;
				}
			}
			// There are still nodes to be visited. The ant has to revisit a node
			else {
				tempo = backtrack(ant, graphGetters, list, alfa, beta, delta);
				return tempo;
			}
		}
		// Available unvisited adjacent nodes
		else {
			// There is only one unvisited adjacent node
			if(unvisitedAdjacent.size() == 1) { 
				ant.setCurrentNode(unvisitedAdjacent.get(0));
				ant.getPath().add(ant.getCurrentNode());
				ant.getUnvisitedNodes().remove(Integer.valueOf(ant.getCurrentNode()));
				// Time to traverse the edge
				return moveTime(ant, graphGetters, delta);
			}
			// There is more than one unvisited adjacent node
			else {
				// Calculate probabilities for selecting unvisited adjacent nodes
				ArrayList<Double> probs = new ArrayList<>(unvisitedAdjacent.size());
				double prob;
				double sumProbs = 0;
				
				for (int i = 0; i < unvisitedAdjacent.size(); i++ ) {
					node = unvisitedAdjacent.get(i);
					prob = ((alfa+ant.getColony().getLevel(ant.getCurrentNode(), node))/(beta + graphGetters.getWeight(ant.getCurrentNode(), node)));
					sumProbs += prob;
					probs.add(prob);
				}
				
				// Normalize probabilities
				for (int i = 0; i < probs.size(); i++) {
					probs.set(i, probs.get(i)/sumProbs);
				}
				
				// Calculate cumulative probabilities
				for (int i = 1; i < probs.size(); i++) {
					probs.set(i, probs.get(i)+probs.get(i-1));
				}

				Random random = new Random();
				double randomNumber = random.nextDouble();

				// Select the node based on the probabilities and update the ant content
				for (int i = 0; i < probs.size(); i++) {
					if (randomNumber <= probs.get(i)) {
						ant.setCurrentNode(unvisitedAdjacent.get(i));
						ant.getPath().add(ant.getCurrentNode());
						ant.getUnvisitedNodes().remove(Integer.valueOf(ant.getCurrentNode()));
						break;
					}		
				}
				// Time to traverse the edge
				return moveTime(ant, graphGetters, delta);
			}	
		}	
	}

    /**
     * Restarts the content of the ant after finding an Hamiltonian cycle.
     *
     * @param ant   the ant to move
     * @param graph the weighted graph
     */
	private void restartVar(IAnt ant, IWeightedGraph graphGetters) {
		int numNodes = graphGetters.getNumNodes();
		ant.getUnvisitedNodes().clear();
		for (int i = 1; i < numNodes+1; i++) {
			if (i != ant.getCurrentNode())
				ant.getUnvisitedNodes().add(i);
		}	
		ant.getPath().clear();
		ant.getPath().add(ant.getCurrentNode());
	}

	/**
     * Closes the cycle and updates the path and unvisitedNodes.
     *
     * @param ant  ant to move
     * @param node the node to backtrack to
     */
	private void updateBacktrack(IAnt ant, int node) {
		int lastNode=0;
		int sizeFor = ant.getPath().size();

		for(int i = sizeFor; i > 0; i--) {
			lastNode = ant.getPath().get(i-1);
			if( lastNode != node) {
				ant.getUnvisitedNodes().add(lastNode);
				ant.getPath().remove(ant.getPath().size()-1);
			}
			else break;
		}
	}

    /**
     * Performs backtracking to select the next node when no unvisited adjacent nodes are available.
     *
     * @param ant    the ant to move
     * @param graph  the weighted graph
     * @param list   the list of adjacent nodes
     * @param alfa  the parameter concerning the ant move event
     * @param beta  the parameter concerning the ant move event
     * @param delta the parameter concerning the ant move event
     * @return the time taken by the ant to complete its path
     */
	private double backtrack(IAnt ant, IWeightedGraph graphGetters, ArrayList<Integer> list, double alfa, double beta, double delta) {

		int node;
		double time = 0;
		// There is only a single adjacent node
		if(list.size() == 1) { 
			ant.setCurrentNode(list.get(0));
			time = moveTime(ant, graphGetters, delta);
			updateBacktrack(ant, ant.getCurrentNode());
		}
		// There is more than one adjacent node
		else {
			ArrayList<Double> probs = new ArrayList<>(list.size());
			double prob;
			double sumProbs = 0;

			for (int i = 0; i < list.size(); i++) {
				node = list.get(i);
				prob = (alfa+ant.getColony().getLevel(ant.getCurrentNode(), node)/(beta+graphGetters.getWeight(ant.getCurrentNode(), node)));
				sumProbs += prob;
				probs.add(prob);
			}
			
			// Normalize probabilities
			for (int i = 0; i < probs.size(); i++) {
				probs.set(i, probs.get(i)/sumProbs);
			}

            // Calculate cumulative probabilities
			for (int i = 1; i < probs.size(); i++) {
				probs.set(i, probs.get(i)+probs.get(i-1));
			}

			Random random = new Random();
			double randomNumber = random.nextDouble();

			// Select the node based on the probabilities
			for (int i = 0; i < probs.size(); i++) {
				if (randomNumber < probs.get(i)) {
					ant.setCurrentNode(list.get(i));
					time = moveTime(ant, graphGetters, delta);
					updateBacktrack(ant, ant.getCurrentNode());
					break;
				}		
			}
		}
		return time;
	}

    /**
     * Calculates the time taken by an ant to move from one node to another
     *
     * @param ant    the ant object representing the moving entity
     * @param graph  the weighted graph on which the ant moves
     * @param delta  the parameter for controlling the importance of pheromone level in backtrack
     * @return the time taken for the ant to move from one node to another
     */
	private double moveTime(IAnt ant, IWeightedGraph graph, double delta) {

		int node1 = ant.getPath().get(ant.getPath().size() - 2);
		int node2 = ant.getPath().get(ant.getPath().size() - 1);
		return delta*graph.getWeight(node1, node2);
	}

}
