package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class BedEntryListener extends EntryJsonListener
{

	public BedEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.BED_ENTRY_TOPIC_NAME);

	}

}
