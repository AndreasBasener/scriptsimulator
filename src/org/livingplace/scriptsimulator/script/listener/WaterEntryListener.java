package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

public class WaterEntryListener extends EntryJsonListener
{
	public WaterEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.WATER_ENTRY_TOPIC_NAME);
	
	}
}
