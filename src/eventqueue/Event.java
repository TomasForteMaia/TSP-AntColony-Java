package eventqueue;

/**
 * The {@code Event} class represents a generic event.
 * It encapsulates the properties and behavior common to all events.
 * Events are comparable based on their timestamps for ordering in the PEC.
 * 
 * The specific behavior of each event type is defined in the subclasses.
 * 
 * This class can be extended to create different types of events.
 * 
 * 
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public abstract class Event implements Comparable<Event> {
	private double timestamp;

	/**
	 * Constructs a new Event object with the specified timestamp.
	 *
	 * @param timestamp the timestamp of the event
	 */
	public Event(double timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the timestamp of the event.
	 *
	 * @return the timestamp of the event
	 */
	public double getTimestamp() {
		return timestamp;
	}

	/**
	 * Increases the timestamp of the event by the specified value.
	 *
	 * @param value the value by which the timestamp should be increased
	 */
	public void increaseTimeStamp(double value) {
		this.timestamp += value;
	}


	/**
	 * Compares this event with another event based on their timestamps.
	 *
	 * @param other the other event to compare
	 * @return a negative integer if this event's timestamp is less than the other event's timestamp,
	 *         zero if both events have the same timestamp, a positive integer otherwise
	 */
	@Override
	public int compareTo(Event other) {
		return Double.compare(this.timestamp, other.timestamp);
	}

	/**
	 * Simulates the event.
	 *
	 * This method should be implemented by subclasses to define the specific behavior of the event.
	 */
	public abstract void simulateEvent();
}