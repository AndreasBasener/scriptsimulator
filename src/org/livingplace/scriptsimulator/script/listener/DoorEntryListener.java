package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.DoorEntry;
import org.livingplace.scriptsimulator.script.entry.DoorEntry.DoorState;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class DoorEntryListener extends EntryJsonListener
{

	public DoorEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.DOOR_ENTRY_TOPIC_NAME);

	}

	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		DoorEntry entry = (DoorEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			double d = Helper.getRandomDouble();
			if(Helper.getRandomDouble() < devval)
			{
				if(d < 0.5)
				{
					entry.setDoorState(DoorState.CLOSED);
				}
				else
				{
					entry.setDoorState(DoorState.OPEN);
				}
			}
			String s = gson.toJson(entry);
			sendJSONtoActiveMQ(s);
		}
	}
}
