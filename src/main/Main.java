package main;

import graph.*;
import dss.*;
import eventqueue.*;
import distribution.*;

import java.io.File;
import java.util.Scanner;

import ao.*;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 * The main class that runs the program.
 *
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class Main {
	
    /**
     * Default constructor for the Main class.
     */
    public Main() {
        // No initialization logic required for the default constructor
    }

    /**
     * The main entry point of the program.
     *
     * @param args the command-line arguments
     */
	public static void main(String[] args) {

        // Read and validate the command
		int command = readCommand(args);
		
		// Invalid command
		if(command == -1)
			return; // Exit if the command is invalid
			
		else if(command == 0) {
			// Perform the -r command
			try {
				// Parse the input parameters
				int numNodes = Integer.parseInt(args[1]);
				int maxWeight = Integer.parseInt(args[2]);
				int nestNode = Integer.parseInt(args[3]);
				double alpha = Double.parseDouble(args[4]);
				double beta = Double.parseDouble(args[5]);
				double delta = Double.parseDouble(args[6]);
				double eta = Double.parseDouble(args[7]);
				double rho = Double.parseDouble(args[8]);
				double gamma = Double.parseDouble(args[9]);
				int colonySize = Integer.parseInt(args[10]);
				double tau = Double.parseDouble(args[11]);
				 
				// Select the graph
				UndirectedWeightedGraph graph = UndirectedWeightedGraph.getInstance(numNodes, maxWeight);
				
				// Print the input parameters    
				printInput(numNodes, nestNode, alpha, beta, delta, eta, rho, gamma, colonySize, tau, graph);
				
				// Select the optimization algorithm
				AntOptimizationAlgorithm algorithm = new AntColonyOptimization();
				
				// Select ant structure
				IAntFactory ant = new Ant(nestNode, graph, null, null);
				
				// Select the colony structure
				IColony colony = new Colony(colonySize, nestNode, numNodes, ant, graph, algorithm);
				
				// Select the queue structure
				EventQueue pec = new PEC();
				
				// Select the distribution strategy
				DistributionStrategy distributionStrategy = new ExponentialDistributionStrategy(delta);
				
				// Initialize simulator  
				ColonySimulator simulator = new ColonySimulator(alpha, beta, delta, eta, rho, gamma, tau, colony, pec, distributionStrategy);  
				colony.addObserver(simulator);
				// Run simulation
				simulator.simulate();      
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid parameters for -r command. " 
						+ "Please verify that the command structure is correct.");
			}
			return;
		}
		else {
			// Perform the -f command
			String inputFile = args[1];
			Scanner scanner = null;
			try {
				File file = new File(inputFile);
				scanner = new Scanner(file);
				
				// Read the first line containing the simulation parameters
				String parametersLine = scanner.nextLine();
				String[] parameters = parametersLine.split("\\s+");

				int numNodes = Integer.parseInt(parameters[0]);
				int nestNode = Integer.parseInt(parameters[1]);
				double alpha = Double.parseDouble(parameters[2]);
				double beta = Double.parseDouble(parameters[3]);
				double delta = Double.parseDouble(parameters[4]);
				double eta = Double.parseDouble(parameters[5]);
				double rho = Double.parseDouble(parameters[6]);
				double gamma = Double.parseDouble(parameters[7]);
				int colonySize = Integer.parseInt(parameters[8]);
				double tau = Double.parseDouble(parameters[9]);  	

				// Initialization of local variables
				int[][] matrix = new int[numNodes][numNodes];
				int weight = 0;

				// Read the weight matrix containing the graph structure and store it in matrix
				for (int i = 0; i < numNodes; i++) {
					String[] weights = scanner.nextLine().trim().split("\\s+");
					if (weights.length != numNodes)
						throw new IllegalArgumentException("The number of nodes must be concordant with the graph structure");

					for (int j = 0; j < numNodes; j++) {
						weight = Integer.parseInt(weights[j]);
						if(weight < 0) 
							throw new IllegalArgumentException("The graph must not have negative weights. Please correct edge (" + i + "," + j + ")");
						matrix[i][j] = weight;
					}
				}

				// Select the graph
				UndirectedWeightedGraph graph = UndirectedWeightedGraph.getInstance(numNodes, matrix);
				
				// Print the input parameters
				printInput(numNodes, nestNode, alpha, beta, delta, eta, rho, gamma, colonySize, tau, graph);
				
				// Select the optimization algorithm        
				AntOptimizationAlgorithm algorithm = new AntColonyOptimization(); 

				// Select the ant structure
				IAntFactory ant = new Ant(nestNode, graph, null, null); 
				
				// Create the colony structure
				IColony colony = new Colony(colonySize, nestNode, numNodes, ant, graph, algorithm);
				
				 // Select the queue structure      
				EventQueue pec = new PEC();     
				
				// Select the distribution strategy
				DistributionStrategy distributionStrategy = new ExponentialDistributionStrategy(delta);
				
				// Initialize the simulator        
				ColonySimulator simulator = new ColonySimulator(alpha, beta, delta, eta, rho, gamma, tau, colony, pec, distributionStrategy); 
				colony.addObserver(simulator);
				// Run the simulation
				simulator.simulate();
			}
			catch (FileNotFoundException e) {
				System.out.println("Input file not found: " + inputFile);
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid parameters for -f command. " 
						+ "Verify that the file has the correct data types for the parameters.");
			}
			catch (IllegalArgumentException e) {
				System.out.println("Invalid graph structure. " + e.getMessage());
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Insufficient parameters for -f command. " 
						+ "Verify that the file has the correct number of parameters.");
			}
			catch (NoSuchElementException e) {
				System.out.println("Insufficient parameters for -f command. " 
						+ "Verify that the file is not empty.");
			}
			finally {
				if (scanner != null)
					scanner.close();
			}
		}
	}

    /**
     * Reads the command-line arguments and determines the command to be executed.
     *
     * @param args the command-line arguments
     * @return the command code (-1 for invalid command, 0 for -r command, 1 for -f command)
     */
	private static int readCommand(String[] args) {	
		if (args.length == 0) {
			System.out.println("No command found. Please provide a valid command to run the program.");
			return -1;
		}
		else if (args[0].equals("-r")) {
			if (args.length != 12) {
				System.out.println("Invalid command structure for -r. Please provide the necessary parameters.");
				return -1;
			}
			else 
				return 0;
		}
		else if (args[0].equals("-f")) {
			if(args.length != 2) {
				System.out.println("Invalid command structure for -f. Please provide the necessary parameters.");
				return -1;
			}
			else 
				return 1;
		}
		else { 
			System.out.println("Unrecognized command. Please provide a valid command to run the program.");
			return -1;
		}
	}

	/**
     * Prints the input parameters and the graph structure.
     *
     * @param n     the number of nodes in the graph
     * @param n1    the nest node
     * @param alpha alpha, ant move event
     * @param beta  beta, ant move event
     * @param delta delta, ant move event
     * @param eta   eta, pheromone evaporation event
     * @param rho   rho, pheromone evaporation event
     * @param gamma pheromone level
     * @param nu    ant colony size
     * @param tau   final instant
     * @param graph the graph structure
     */
	static private void printInput(int n, int n1, double alpha, double beta, double delta, double eta, double rho, double gamma, int nu, double tau, IWeightedGraph graph) {
		System.out.println("Input parameters:");
		System.out.println("\t\t  " + n + "\t: number of nodes in the graph");
		System.out.println("\t\t  " + n1 + "\t: the nest node");
		System.out.println("\t\t  " + alpha + "\t: alpha, ant move event");
		System.out.println("\t\t  " + beta + "\t: beta, ant move event");
		System.out.println("\t\t  " + delta + "\t: delta, ant move event");
		System.out.println("\t\t  " + eta + "\t: eta, pheromone evaporation event");
		System.out.println("\t\t  " + rho + "\t: rho, pheromone evaporation event");
		System.out.println("\t\t  " + gamma + "\t: pheromone level");
		System.out.println("\t\t  " + nu + "\t: ant colony size");
		System.out.println("\t\t  " + tau + "\t: final instant");
		System.out.println();
		System.out.println("\t with graph:");		
		graph.printGraph();
	}

}

