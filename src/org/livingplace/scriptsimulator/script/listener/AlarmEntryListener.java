package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

public class AlarmEntryListener extends EntryJsonListener
{
	public AlarmEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.ALARM_ENTRY_TOPIC_NAME);

	}
}
