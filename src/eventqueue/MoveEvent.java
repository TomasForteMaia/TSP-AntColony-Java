package eventqueue;

import ao.IAnt;
import distribution.*;
/**
 * The {@code MoveEvent} class represents an event where an ant makes a move.
 * It extends the {@code Event} class and encapsulates the properties and behavior
 * specific to a move event.
 * <p>
 * The {@code MoveEvent} class is responsible for executing the move event,
 * updating the timestamp, and adding the move event back to the event queue
 * if the simulation time has not been reached.
 * </p>
 * <p>
 * The move event is associated with an ant and is executed by invoking the
 * {@code move} method of the ant object. The duration of the move event is
 * obtained from the ant's move method.
 * </p>
 * <p>
 * The move event can occur multiple times, and the number of occurrences can
 * be obtained using the {@code getOccurrences} method.
 * </p>
 * <p>
 * This class is part of the pec package.
 * </p>
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class MoveEvent extends Event {
	private IAnt ant;
	private EventQueue queue;
	private double gamma;
	private double alfa;
	private double beta;
	private double delta;
	private double simulationTime;
	/**
	 * Counter for number of ocurrences of this type of events
	 */
	private static int ocurrences = 0;

	/**
	 * Constructs a new MoveEvent object with the specified timestamp and ant.
	 *
	 * @param timestamp      the timestamp of the event
	 * @param ant            the ant associated with the event
	 * @param gamma          the gamma value for the move event
	 * @param alfa           the alfa value for the move event
	 * @param beta           the beta value for the move event
	 * @param delta          the parameter concerning pheromone level
	 * @param simulationTime the total simulation time
	 * @param queue          the event queue for the move event
	 */
	public MoveEvent(double timestamp, IAnt ant, double gamma, double alfa, 
			double beta, double delta, double simulationTime, EventQueue queue) {
		super(timestamp);
		this.ant = ant;
		this.gamma = gamma;
		this.alfa = alfa;
		this.beta = beta;
		this.delta = delta;
		this.simulationTime = simulationTime;
		this.queue = queue;
	}

	/**
	 * Retrieves the number of occurrences of the MoveEvent.
	 *
	 * @return the number of occurrences of the MoveEvent
	 */
	static public int getOcurrences() {
		return ocurrences;
	}

	/**
	 * Simulates the move event.
	 * <p>
	 * This method executes the move event by invoking the {@code move} method
	 * of the associated ant object. It obtains the duration mean value of the move
	 * event and increments the number of occurrences of the MoveEvent.
	 * The timestamp is then increased by a random value drawn from an exponential
	 * distribution with the duration mean as the parameter.
	 * </p>
	 * <p>
	 * After executing the move event, it checks if the current timestamp is less
	 * than the simulation time. If it is, the move event is added back to the event
	 * queue for future execution. If not, the move event is not added to the event
	 * queue.
	 * </p>
	 */
	@Override
	public void simulateEvent() {

		// Code to execute the move event        
		double increase = ant.move(gamma, alfa, beta, delta); // Execute move and obtain duration mean value
		ocurrences++; // Increment number of MoveEvents Occurrences 

		// Create an instance of the desired distribution strategy
		DistributionStrategy distributionStrategy = new ExponentialDistributionStrategy(increase);

		// Create the RandomValueGenerator with the selected distribution strategy
		RandomValueGenerator randomValueGenerator = new RandomValueGenerator(distributionStrategy);

		// Generate random values using the selected distribution strategy
		double randomValue = randomValueGenerator.generateRandomValue();

		// Increase the timestamp of the event by the generated random exponential value
		this.increaseTimeStamp(randomValue);  

		// Check simulation Time
		if(this.getTimestamp() < simulationTime) {
			queue.addEvent(this); // Add Move event again
		}
		// Else do not add to the queue
	}
}
