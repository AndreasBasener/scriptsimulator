package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

public class PowerEntryListener extends EntryJsonListener
{
	public PowerEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.POWER_ENTRY_TOPIC_NAME);
	
	}
}
