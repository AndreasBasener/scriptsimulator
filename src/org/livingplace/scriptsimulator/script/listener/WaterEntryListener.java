package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.WaterEntry;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterState;

import com.google.gson.Gson;

public class WaterEntryListener extends EntryJsonListener
{
	public WaterEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.WATER_ENTRY_TOPIC_NAME);
	
	}
	
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		WaterEntry entry = (WaterEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			if(Helper.getRandomDouble() < devval)
			{
				if(Helper.getRandomDouble() < 0.5)
				{
					entry.setWaterState(WaterState.OFF);
				}
				else
				{
					entry.setWaterState(WaterState.ON);
				}
			}
			String s = gson.toJson(entry);
			sendJSONtoActiveMQ(s);
		}
	}
}
