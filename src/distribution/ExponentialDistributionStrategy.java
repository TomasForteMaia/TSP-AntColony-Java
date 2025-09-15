package distribution;

import java.util.Random;

/**
 * The {@code ExponentialDistributionStrategy} class implements the {@link DistributionStrategy} interface
 * and represents a distribution strategy for generating random values from an exponential distribution.
 * It uses a specified lambda parameter to control the distribution behavior.
 * 
 * The exponential distribution strategy generates random values based on the exponential distribution logic.
 * The generated random values follow the exponential distribution pattern determined by the lambda parameter.
 * 
 * @see DistributionStrategy
 * @author Diogo Miranda
 * @author João Santos
 * @author Tomás Maia
 * @version 1.0
 */
public class ExponentialDistributionStrategy implements DistributionStrategy {
	private double lambda;

	/**
	 * Constructs a new {@code ExponentialDistributionStrategy} object with the specified lambda parameter.
	 *
	 * @param lambda the lambda parameter for the exponential distribution. It is the mean of the exponential distribution.
	 */
	public ExponentialDistributionStrategy(double lambda) {
		this.lambda = lambda;
	}

	/**
	 * Generates a random value from the exponential distribution.
	 * The generated random value follows the exponential distribution pattern
	 * determined by the lambda parameter.
	 *
	 * @return a randomly generated value from the exponential distribution
	 */
	public double generateRandomValue() {
		Random random = new Random();
		double next = random.nextDouble();
		return -Math.log(1.0 - next) * lambda;
	}
}