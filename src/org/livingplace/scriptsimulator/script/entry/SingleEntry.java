package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.SingleEntryListener;


import com.google.gson.Gson;

/**
 * The SingleEntry class is an empty wrapper for the ScriptEntry class
 * 
 * @author Andreas Basener
 * 
 */
public class SingleEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1625958900516155649L;

	/**
	 * Creates an new SingleEntry instance with specified offset and name.
	 * 
	 * @param offset
	 *            offset
	 * @param name
	 *            name
	 */
	public SingleEntry(Period offset, String name, String descr)
	{
		super(offset, name, descr);
	}

	@Override
	public synchronized void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() > 0)
			return;
			
		this.addEntryListener(new SingleEntryListener(	activeMQip,
															mongoDBip,
															gson));

	}
}
