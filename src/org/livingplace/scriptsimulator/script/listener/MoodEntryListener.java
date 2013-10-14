package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

public class MoodEntryListener extends EntryJsonListener{
	public MoodEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.MOOD_ENTRY_TOPIC_NAME);

	}
	
	public MoodEntryListener(String amqip, String mongoip, Gson gson, String topicName)
	{
		super(amqip,mongoip,gson, topicName);
	}

}
