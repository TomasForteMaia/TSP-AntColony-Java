package dss;

import ao.IAnt;
import ao.IColony;
import distribution.*;
import eventqueue.*;

/**
 * The ColonySimulator class is responsible for simulating the behavior of a colony of ants
 * to find Hamiltonian cycles in a graph.
 * It implements the Observer interface to receive updates from the simulation.
 * 
 * This class manages the simulation time, events, graph, colony, and the queue of events.
 * It provides methods for constructing and running the simulation.
 * 
 * This implementation uses a specified distribution strategy for generating random values.
 * 
 * The simulation behavior is defined by the parameters passed in the constructor.
 * 
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class ColonySimulator implements Observer {

	private static double TheAlpha;
	private static double TheBeta;
	private static double TheDelta;
	private static double TheEta;
	private static double TheRho;
	private static double TheGamma;
	private static IColony TheColony;
	private static EventQueue TheQueue;

	private static double TheSimulationTime;
	private static double currentTime = 0;
	private Event currentEvent;
	private static DistributionStrategy TheDistributionStrategy;

	private int nNotification = 1;

	/**
	 * Constructs a ColonySimulator object with the specified parameters.
	 *
	 * @param alpha          the value of alpha for the simulation (concerning the move event)
	 * @param beta           the value of beta for the simulation (concerning the move event)
	 * @param delta          the value of delta for the simulation (concerning the move event)
	 * @param eta            the value of eta for the simulation (concerning the pheromone evaporation event)
	 * @param rho            the value of rho for the simulation (concerning the pheromone evaporation event)
	 * @param gamma          the value of gamma for the simulation (concerning pheromone level)
	 * @param simulationTime the total simulation time
	 * @param colony         the colony
	 * @param queue          the event queue for Discrete Stochastic Simulation
	 * @param distribution   the distribution strategy for generating random values
	 */
	public ColonySimulator(double alpha, double beta, double delta, double eta, double rho, double gamma, double simulationTime, IColony colony, EventQueue queue, DistributionStrategy distribution) { // IPEC pec
		TheAlpha = alpha;
		TheBeta = beta;
		TheDelta = delta; // Mean value parameter
		TheEta = eta; // Mean time between evaporation events
		TheRho = rho;
		TheGamma = gamma;
		TheSimulationTime = simulationTime;
		TheColony = colony;
		TheQueue = queue;
		TheDistributionStrategy = distribution;
	}

    /**
     * Receives an update from the observed event, indicating a start node and an end node.
     * It creates and adds an evaporation event to the event queue based on the received information.
     *
     * @param startNode the start node of the observed event
     * @param endNode   the end node of the observed event
     */
	public void update(int startNode, int endNode) {

		EvaporationEvent evaporationEvent = new EvaporationEvent(startNode, endNode, TheEta, TheRho, currentTime, TheColony, TheQueue);

		// Create the RandomValueGenerator with the selected distribution strategy
		RandomValueGenerator randomValueGenerator = new RandomValueGenerator(TheDistributionStrategy);

		// Generate random values using the selected distribution strategy
		double randomValue = randomValueGenerator.generateRandomValue();

		evaporationEvent.increaseTimeStamp(randomValue);  

		TheQueue.addEvent(evaporationEvent);

	}

	/**
	 * Simulates the behavior of the colony using an Ant Optimization Algorithm.
	 * The simulation progresses through discrete time steps until the specified simulation time is reached.
	 * During each time step, events are processed based on their scheduled timestamps in the event queue.
	 * The simulation involves ants making moves, evaporation of pheromones, and periodic notifications.
	 * 
	 * The simulation follows the steps below:
	 * 1. Initialize the event queue with initial move events for each ant in the colony.
	 * 2. Add a notification event at the beginning to schedule periodic notifications.
	 * 3. Enter the simulation loop until the current time reaches the simulation time:
	 *    - Get the next event from the event queue.
	 *    - Update the current time to the timestamp of the current event.
	 *    - Simulate the current event.
	 *    - Check if it is time for a periodic notification and add a notification event if necessary.
	 * 4. Once the simulation time is reached, the simulation ends.
	 * 
	 * Note: The behavior of ants, pheromone evaporation, and notifications are determined by the
	 * parameters and configurations provided during the initialization of the ColonySimulator object.
	 */ 
	public void simulate() {

		// Create the RandomValueGenerator with the selected distribution strategy
		RandomValueGenerator randomValueGenerator = new RandomValueGenerator(TheDistributionStrategy);

		// Add initial move events for each ant in the colony
		for (IAnt ant : TheColony.getAnts()) {
			// Generate random values using the selected distribution strategy
			double randomValue = randomValueGenerator.generateRandomValue();
			Event moveEvent = new MoveEvent(currentTime + randomValue, ant, TheGamma, TheAlpha, TheBeta, TheDelta, TheSimulationTime, TheQueue);
			TheQueue.addEvent(moveEvent);
		}

		// Add First Notification Event
		Event notification = new NotificationEvent(TheSimulationTime/20, TheColony);
		TheQueue.addEvent(notification);
		nNotification++;

		// Simulation cycle
		while (currentTime < TheSimulationTime) {
			this.currentEvent = TheQueue.getNextEvent();
			currentTime = this.currentEvent.getTimestamp();        	
			this.currentEvent.simulateEvent();
			// Check Notification Time
			if((currentTime == (nNotification-1)*TheSimulationTime/20)) {
				// Add Next Notification Event
				notification = new NotificationEvent(nNotification*TheSimulationTime/20, TheColony);
				TheQueue.addEvent(notification);
				nNotification++;
			}  	           
		}
	}

}