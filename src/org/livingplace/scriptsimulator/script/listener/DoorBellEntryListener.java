package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class DoorBellEntryListener extends EntryJsonListener
{

	public DoorBellEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.DOORBELL_ENTRY_TOPIC_NAME);

	}
}
