package haw.livingplace.scriptSimulator;

import java.awt.Point;
import java.io.Serializable;

/**
 * Simple class to store 3D coordinates. Uses <code>double</code> to store coordinate values.
 * 
 * @author Andreas Basener
 * 
 */
public class Point3D implements Serializable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3518396971383017564L;

	/**
	 * X-coordinate
	 */
	private double				x;
	/**
	 * Y-coordinate
	 */
	private double				y;
	/**
	 * Z-coordinate
	 */
	private double				z;

	/**
	 * Creates a new <code>Point3D</code> instance with given coordinates.
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param z Z-coordinate
	 */
	public Point3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the x
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(double x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y)
	{
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public double getZ()
	{
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(double z)
	{
		this.z = z;
	}

	@Override
	public String toString()
	{
		return "X:" + x + " Y:" + y + " Z:" + z;
	}
	
	/**
	 * Calculates and returns the euclidian distance between this Point and the given one.
	 * @param point <code>Point3D</code>
	 * @return distance
	 */
	public double distance(Point3D point)
	{
		double dist = Math.sqrt(Math.pow((this.x-point.x),2)+
								Math.pow((this.y-point.y),2)+
								Math.pow((this.z-point.z),2));
		return dist;
	}
	
	/**
	 * Returns a new <code>Point</code> instance with only the x and y coordinates.
	 * @return <code>Point</code>
	 */
	public Point getPoint()
	{
		return new Point((int)this.x, (int) this.y);
	}
	
	/**
	 * Returns the values of the coordinate as a double array. <br>
	 * double[3]{x,y,z}
	 * 
	 * @return double[3]
	 */
	public double[] getAsArray()
	{
		return new double[]{this.x, this.y, this.z};
	}

}
