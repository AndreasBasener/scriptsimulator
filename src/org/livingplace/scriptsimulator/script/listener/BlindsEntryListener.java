package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.BlindsEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;

import com.google.gson.Gson;

public class BlindsEntryListener extends EntryJsonListener
{
	public BlindsEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.BLINDS_ENTRY_TOPIC_NAME);

	}
	
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		BlindsEntry entry = (BlindsEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();

		if(Helper.getRandomDouble() > devval)
		{
			
			
			String s = gson.toJson(entry);
			sendJSONtoActiveMQ(s);
		}
	}
}
