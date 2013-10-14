package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class PeriodicEntryListener extends EntryJsonListener
{

	public PeriodicEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.PERIODIC_ENTRY_TOPIC_NAME);
		
	}

}
