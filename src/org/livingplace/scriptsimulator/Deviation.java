package org.livingplace.scriptsimulator;

import java.util.Random;

/**
 * Simple class to store deviation information.
 * 
 * 
 * @author Andreas Basener
 * 
 */
public class Deviation
{
	/**
	 * Weight of the deviation. [0.,1.]
	 */
	private double	deviationWeight;

	/**
	 * Random instance to create random deviation value.
	 */
	private static Random	random;

	/**
	 * Creates a new <code>Deviation</code> instance.
	 * @param deviation Deviation weight [0,1]
	 */
	public Deviation(double deviationWeight)
	{
		if (deviationWeight >= 0)
		{
			this.deviationWeight = deviationWeight;
		}
		else
		{
			this.deviationWeight = 0;
		}
		
		if (random == null)
			random = new Random();
	}

	/**
	 * Returns a new random deviation. 
	 * @return
	 */
	public double getRandomDeviation()
	{
		double gauss = random.nextGaussian();

		if (gauss > 2)
			gauss = 2;
		if (gauss < -2)
			gauss = -2;

		gauss /= 2;

		double diff = gauss * deviationWeight;

		return diff;
	}

	/**
	 * @return the deviation
	 */
	public double getDeviationWeight()
	{
		return deviationWeight;
	}

	/**
	 * @param deviation
	 *            the deviation to set
	 */
	public void setDeviationWeight(double deviationWeight)
	{
		this.deviationWeight = deviationWeight;
	}

}
