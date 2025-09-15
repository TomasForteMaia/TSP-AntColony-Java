package distribution;

/**
 * The {@code RandomValueGenerator} class represents a context class that uses a distribution strategy
 * to generate random values.
 * 
 * It provides a flexible way to generate random values based on different distribution strategies.
 * The context class delegates the task of generating random values to the selected distribution strategy.
 * 
 * @see DistributionStrategy
 * @see ExponentialDistributionStrategy
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class RandomValueGenerator {
	private DistributionStrategy strategy;

	/**
	 * Constructs a new RandomValueGenerator object with the specified distribution strategy.
	 *
	 * @param strategy the distribution strategy to be used for generating random values
	 */
	public RandomValueGenerator(DistributionStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Sets the distribution strategy used for generating random values.
	 *
	 * @param strategy the distribution strategy to be set
	 */
	public void setStrategy(DistributionStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Generates a random value based on the selected distribution strategy.
	 *
	 * @return a randomly generated value based on the distribution strategy
	 */
	public double generateRandomValue() {
		return strategy.generateRandomValue();
	}

}