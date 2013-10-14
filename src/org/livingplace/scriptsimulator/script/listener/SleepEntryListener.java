package org.livingplace.scriptsimulator.script.listener;

import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.BedEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.SleepEntry;

import com.google.gson.Gson;

public class SleepEntryListener extends EntryJsonListener{

	public SleepEntryListener(String amqip, String mongoip, Gson gson)
	{
		
		super(amqip,mongoip,gson,Helper.BED_ENTRY_TOPIC_NAME);
		
	}
	
	@Override
	public void entryEvent(EntryEvent event)
	{
		SleepEntry sleep = (SleepEntry) event.getSource();
		BedEntry entry = new BedEntry(sleep.getOffset(), "", "", sleep.getCurrentState());
		
		String s = gson.toJson(entry);
		
		sendJSONtoActiveMQ(s);
	}
}
