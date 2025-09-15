package distribution;

/**
 * The {@code DistributionStrategy} interface represents a distribution strategy
 * for generating random values.
 * 
 * Implementations of this interface provide specific algorithms for generating
 * random values based on different distribution strategies.
 * 
 * The {@code generateRandomValue()} method is responsible for generating a random
 * value according to the specific distribution strategy.
 * 
 * @see ExponentialDistributionStrategy
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public interface DistributionStrategy {   
	/**
	 * Generates a random value based on the distribution strategy.
	 *
	 * @return a randomly generated value
	 */
	public double generateRandomValue();
}