package haw.livingplace.scriptSimulator.script.entry;

import java.util.EventObject;

/**
 * Basic <code>Event</code> class for <code>ScriptEntrys</code>.
 * 
 * @author Andreas Basener
 * 
 */
public class EntryEvent extends EventObject
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8167106950755387592L;

	/**
	 * Constructor for <code>EntryEvent</code>.
	 * 
	 * @param arg0
	 */
	public EntryEvent(Object arg0)
	{
		super(arg0);
	}

}
