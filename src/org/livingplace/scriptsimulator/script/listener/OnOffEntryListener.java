package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class OnOffEntryListener extends EntryJsonListener
{

	public OnOffEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.ONOFF_ENTRY_TOPIC_NAME);

	}
}
