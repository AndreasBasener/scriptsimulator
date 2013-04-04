package org.livingplace.scriptsimulator.script.exceptions;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class IllegalSpeedValueException extends IllegalArgumentException
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2223439868166778594L;

	public IllegalSpeedValueException()
	{
		super("Speed Value must be greater than '0'");
	}

}
