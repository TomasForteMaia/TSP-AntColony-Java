package eventqueue;

import java.util.PriorityQueue;

/**
 * The {@code PEC} class represents a Priority Event Queue.
 * It manages a collection of events in ascending order of their timestamps.
 * It provides methods to add events, remove the next event, and check if the queue is empty.
 * Events are ordered based on their timestamps.
 * 
 * In this implementation, the PEC class uses a PriorityQueue from the Java Collections framework to 
 * store the events. The events are automatically ordered based on their natural ordering, which is determined by the 
 * compareTo method implemented in the Event class.
 *
 * @see EventQueue
 * @see Event
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class PEC implements EventQueue {
	private PriorityQueue<Event> eventQueue;

	/**
	 * Constructs a new PEC object.
	 */
	public PEC() {
		eventQueue = new PriorityQueue<>();
	}

	/**
	 * Adds an event to the event queue.
	 *
	 * @param event the event to be added
	 */
	@Override
	public void addEvent(Event event) {
		eventQueue.add(event);
	}

	/**
	 * Removes and returns the next event from the event queue.
	 *
	 * @return the next event in the queue
	 */
	@Override
	public Event getNextEvent() {
		return eventQueue.poll();
	}

	/**
	 * Checks if the event queue is empty.
	 *
	 * @return true if the event queue is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return eventQueue.isEmpty();
	}

	/**
	 * Clears the event queue.
	 */
	@Override
	public void clear() {
		eventQueue.clear();
	}
}