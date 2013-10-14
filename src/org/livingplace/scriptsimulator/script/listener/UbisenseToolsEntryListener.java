package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.UbisenseToolsEntry;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseToolsEntryListener extends EntryJsonListener
{

	public UbisenseToolsEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.UBISENSE_ENTRY_TOPIC_NAME);

	}

	@Override
	/**
	 * Catch Event, generate JSON String and publish it to ActiveMQ
	 */
	public void entryEvent(EntryEvent event)
	{
		UbisenseToolsEntry entry = (UbisenseToolsEntry) event.getSource();

		String s = null;

		s = gson.toJson(entry.getUbisenseData());

		sendJSONtoActiveMQ(s);
	}

}
