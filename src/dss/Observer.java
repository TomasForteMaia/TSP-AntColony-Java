package dss;

/**
 * The Observer interface represents an observer that receives updates from a subject.
 * It provides a method for updating the observer with information about a start node and an end node.
 * Classes implementing this interface can act as observers in a discrete stochastic simulation.
 */
public interface Observer {
    /**
     * Updates the observer with information about a start node and an end node.
     *
     * @param startNode the start node of the observed event
     * @param endNode   the end node of the observed event
     */
    void update(int startNode, int endNode);
}