package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class DoorEntryListener extends EntryJsonListener
{

	public DoorEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.DOOR_ENTRY_TOPIC_NAME);

	}

}
