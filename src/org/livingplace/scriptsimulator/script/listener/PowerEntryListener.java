package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.PowerEntry;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerState;

import com.google.gson.Gson;

public class PowerEntryListener extends EntryJsonListener
{
	public PowerEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.POWER_ENTRY_TOPIC_NAME);
	
	}
	
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		PowerEntry entry = (PowerEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			double d = Helper.getRandomDouble();
			
			if(Helper.getRandomDouble() < devval)
			{
				if(d < 0.5)
				{
					entry.setPowerState(PowerState.OFF);
				}
				else
				{
					entry.setPowerState(PowerState.ON);
				}
			}
			String s = gson.toJson(entry);
			sendJSONtoActiveMQ(s);
		}
	}
}
