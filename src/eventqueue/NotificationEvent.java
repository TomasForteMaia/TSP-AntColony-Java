package eventqueue;

import ao.IColony;
/**
 * The {@code NotificationEvent} class represents a notification event during the simulation.
 * It extends the {@link Event} class and encapsulates the properties and behavior specific to notification events.
 * Notification events are responsible for printing system observations at specific time intervals.
 * <p>
 * This class provides methods to access the observation details, such as the observation number, number of move events,
 * number of evaporation events, top candidate cycles, and the best Hamiltonian cycle.
 * </p>
 * <p>
 * The specific behavior of a notification event is defined in the {@link #simulateEvent()} method.
 * </p>
 * <p>
 * The observation number is incremented for each notification event.
 * </p>
 * <p>
 * Example observation format:
 * 
 * Observation number: 1
 * Present instant: instant
 * Number of move events: mevents
 * Number of evaporation events: eevents
 * Top candidate cycles: cycles
 * Best Hamiltonian cycle: best
 * </p>
 * <p>
 * Note: The observation number is automatically incremented by 1 for each notification event.
 * </p>
 * This class requires an instance of the {@link IColony} interface to access information about the system.
 *
 * This class is part of the pec package.
 * 
 * 
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class NotificationEvent extends Event {
	/**
	 * Counter for observation numbers
	 */
	private static int observationNumberCounter = 1;

	/**
	 * The observation number
	 */
	private int observationNumber;


	private IColony colony;

	/**
	 * Constructs a new NotificationEvent object with the specified timestamp and colony.
	 *
	 * @param timestamp the timestamp of the event
	 * @param colony    the colony instance
	 */
	public NotificationEvent(double timestamp, IColony colony) {
		super(timestamp);
		this.colony = colony;
		this.observationNumber = observationNumberCounter;
		observationNumberCounter++;
	}


	/**
	 * Simulates the notification event by printing the observation details.
	 * <p>
	 * This method prints the observation number, present instant, number of move events,
	 * number of evaporation events, top candidate cycles (up to 5 cycles), and the best Hamiltonian cycle.
	 * </p>
	 */
	@Override
	public void simulateEvent() {
		System.out.println("Observation " + observationNumber + ":");
		System.out.println("\t\tPresent instant: \t\t" + getTimestamp());
		System.out.println("\t\tNumber of move events: \t\t" + MoveEvent.getOcurrences());
		System.out.println("\t\tNumber of evaporation events:   " + EvaporationEvent.getOcurrences());

		// Print top 5 cycles (or less if 5 cycles haven't been found)
		System.out.print("\t\tTop candidate cycles: ");  
		colony.printTopHamiltonianCycles(5);         

		// Print best cycle
		System.out.print("\t\tBest Hamiltonian cycle: "); 
		colony.printBestHamiltonianCycle();
		System.out.println();
		// Further formatting adjustments can be made to the print statements based on the desired output format
	}
}