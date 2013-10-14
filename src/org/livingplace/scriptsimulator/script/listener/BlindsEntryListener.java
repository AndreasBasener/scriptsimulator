package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

public class BlindsEntryListener extends EntryJsonListener
{
	public BlindsEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.BLINDS_ENTRY_TOPIC_NAME);

	}
}
