package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class WindowEntryListener extends EntryJsonListener
{

	public WindowEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.WINDOW_ENTRY_TOPIC_NAME);
		
	}

}
