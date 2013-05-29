package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.DoorBellEntryListener;
import org.livingplace.scriptsimulator.script.listener.writerlistener.DoorBellWriterListener;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class DoorBellEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2847866654983213961L;

	/**
	 * Creates a new <code>DoorBellEntry</code>.
	 * @param offset
	 * @param name
	 * @param descr
	 */
	public DoorBellEntry(Period offset, String name, String descr)
	{
		super(offset, name, descr);
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() > 0)
			return;
			
		this.addEntryListener(new DoorBellEntryListener(activeMQip,
															mongoDBip,
															gson));
		this.addEntryListener(new DoorBellWriterListener());
	}

}
