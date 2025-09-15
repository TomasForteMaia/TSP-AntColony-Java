package ao;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The CycleAndWeight class represents a Hamiltonian cycle and its weight.
 * It provides methods for accessing the cycle and weight, and implements
 * the Comparable interface for comparing Hamiltonian cycles based on their weights.
 *
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class CycleAndWeight implements Comparable<CycleAndWeight> {

	private ArrayList<Integer> cycle;
	private int weight;

	/**
     * Constructs a CycleAndWeight object with the specified cycle and weight.
     *
     * @param cycle  the Hamiltonian cycle
     * @param weight the weight of the Hamiltonian cycle
     */
	public CycleAndWeight(ArrayList<Integer> cycle, int weight){
		this.cycle = cycle;
		this.weight = weight;
	}

    /**
     * Returns the weight of the Hamiltonian cycle.
     *
     * @return the weight of the Hamiltonian cycle
     */
	public int getWeight() {
		return this.weight;
	}

    /**
     * Returns the Hamiltonian cycle
     *
     * @return the Hamiltonian cycle
     */
	public ArrayList<Integer> getHamiltonianCycle(){
		return this.cycle;
	}

    /**
     * Compares this CycleAndWeight object with another based on their weights.
     * This method is used for sorting cycles in ascending order of their weights.
     *
     * @param other the other CycleAndWeight object to compare
     * @return a negative integer, zero, or a positive integer if this cycle's weight
     *         is less than, equal to, or greater than the other cycle's weight, respectively.
     */
    @Override
	public int compareTo(CycleAndWeight other) {
		return Integer.compare(this.weight, other.weight);
	}

    /**
     * Checks if this CycleAndWeight object is equal to another object.
     * Two CycleAndWeight objects are considered equal if their cycles are equal.
     *
     * @param obj the object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
	public boolean equals(Object obj) {
		if (this.cycle == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		CycleAndWeight other = (CycleAndWeight) obj;
		return Objects.equals(cycle, other.cycle);
	}
	
	/**
	 * Returns a hash code value for the CycleAndWeight object based on the cycle property.
	 *
	 * @return the hash code value for the object
	 */
	@Override
	public int hashCode() {
	    return Objects.hashCode(cycle);
	}
}
