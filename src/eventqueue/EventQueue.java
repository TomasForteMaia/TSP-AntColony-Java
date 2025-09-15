package eventqueue;

/**
 * The {@code EventQueue} interface represents a queue of events.
 * It manages a collection of events and provides methods to add and remove events.
 * Events can be of any subclass of the {@link Event} class.
 * <p>
 * This interface allows for flexibility in accepting different subclasses of {@link Event}
 * as method arguments.
 * </p>
 * <p>
 * Implementations of this interface provide specific implementations for adding and removing events.
 * </p>
 * <p>
 * This interface is part of the pec package.
 * </p>
 * 
 * @see Event
 * @see PEC
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public interface EventQueue {
	/**
	 * Adds an event to the event queue.
	 *
	 * @param event the event to be added
	 */
	void addEvent(Event event);

	/**
	 * Removes and returns the next event from the event queue.
	 *
	 * @return the next event in the queue
	 */
	Event getNextEvent();

	/**
	 * Checks if the event queue is empty.
	 *
	 * @return {@code true} if the event queue is empty, {@code false} otherwise
	 */
	boolean isEmpty();

	/**
	 * Clears the event queue.
	 */
	void clear();
}