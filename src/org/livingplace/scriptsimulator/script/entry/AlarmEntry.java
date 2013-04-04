package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.AlarmEntryListener;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 *
 */
public class AlarmEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3925057817656952578L;

	public AlarmEntry(Period offset, String name, String description)
	{
		super(offset,name,description);
	}
	
	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new AlarmEntryListener(activeMQip,
															mongoDBip,
															gson));
	}
	
}
