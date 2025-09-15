package eventqueue;

import ao.IColony;
import distribution.*;

/**
 * The {@code EvaporationEvent} class represents an evaporation event for pheromones on an edge.
 * It extends the {@code Event} class and encapsulates the properties and behavior specific to an evaporation event.
 * Evaporation events are scheduled after each evaporation on an edge.
 * Each evaporation event is associated with a specific edge, and its timestamp represents the time of the evaporation.
 * The evaporation event triggers the corresponding actions for pheromone evaporation.
 * 
 * The number of occurrences of evaporation events is tracked through the static variable 'ocurrences'.
 * 
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class EvaporationEvent extends Event {
	private int startNode;
	private int endNode; 
	private IColony colony;
	private double rho;
	private double eta;
	private EventQueue queue;
	/**
	 * Counter for number of ocurrences 
	 */
	private static int ocurrences = 0;


	/**
	 * Constructs a new EvaporationEvent object with the specified parameters.
	 *
	 * @param startNode the starting node of the edge
	 * @param endNode the ending node of the edge
	 * @param eta the mean time between evaporations
	 * @param rho the evaporation rate
	 * @param timestamp the timestamp of the evaporation event
	 * @param colony the colony instance
	 * @param queue the event queue
	 */
	public EvaporationEvent(int startNode, int endNode, double eta, double rho, double timestamp, IColony colony, EventQueue queue) {
		super(timestamp);     
		this.startNode = startNode;
		this.endNode = endNode;
		this.rho = rho;
		this.eta = eta;
		this.queue = queue;
		this.colony = colony;
	}

	/**
	 * Returns the number of occurrences of evaporation events.
	 *
	 * @return the number of occurrences
	 */
	static public int getOcurrences() {
		return ocurrences;
	}

	/**
	 * Performs the actions triggered by the evaporation event.
	 * It handles the evaporation of pheromones on the edge and any related updates.
	 */
	@Override
	public void simulateEvent() {
		// Perform the evaporation of pheromones on the edge
		// Handle any additional actions related to the evaporation

		// Use updateLevel to decrease the level of pheromones on the edge
		double value = 0;  	
		value = colony.updateLevel(this.startNode, this.endNode, -rho);  	
		ocurrences++; // Increment number of EvaporationEvente Occurrences

		// Create an instance of the desired distribution strategy
		DistributionStrategy distributionStrategy = new ExponentialDistributionStrategy(eta);

		// Create the RandomValueGenerator with the selected distribution strategy
		RandomValueGenerator randomValueGenerator = new RandomValueGenerator(distributionStrategy);

		// Generate random values using the selected distribution strategy
		double randomValue = randomValueGenerator.generateRandomValue();

		// Increase the timestamp of the event by the generated random exponential value
		this.increaseTimeStamp(randomValue);  

		// Add the same evaporation event to the pec while the pheromones are still positive
		if (value > 0)
			queue.addEvent(this);

	}
}