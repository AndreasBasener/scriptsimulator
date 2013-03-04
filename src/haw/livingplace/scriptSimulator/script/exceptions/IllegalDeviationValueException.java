package haw.livingplace.scriptSimulator.script.exceptions;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class IllegalDeviationValueException extends IllegalArgumentException
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3791170020437829891L;

	public IllegalDeviationValueException()
	{
		super("deviation value must be between 0.0 and 1.0");
	}

}
