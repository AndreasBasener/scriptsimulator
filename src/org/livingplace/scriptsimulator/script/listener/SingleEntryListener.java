package org.livingplace.scriptsimulator.script.listener;

import org.livingplace.scriptsimulator.Helper;


import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class SingleEntryListener extends EntryJsonListener
{

	public SingleEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.SINGLE_ENTRY_TOPIC_NAME);

	}

}
