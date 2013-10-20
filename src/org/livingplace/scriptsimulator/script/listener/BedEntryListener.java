package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.BedEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.BedEntry.SleepState;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class BedEntryListener extends EntryJsonListener
{

	public BedEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.BED_ENTRY_TOPIC_NAME);

	}
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		BedEntry entry = (BedEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			double d = Helper.getRandomDouble();
			if(Helper.getRandomDouble() < devval)
			{
				if(d < 0.143)
				{
					entry.setSleepState(SleepState.N1);
				}
				else if( d < 0.286)
				{
					entry.setSleepState(SleepState.N2);
				}
				else if( d < 0.429)
				{
					entry.setSleepState(SleepState.N3);
				}
				else if( d < 0.572)
				{
					entry.setSleepState(SleepState.N4);
				}
				else if( d < 0.715)
				{
					entry.setSleepState(SleepState.NREM);
				}
				else if( d < 0.858)
				{
					entry.setSleepState(SleepState.REM);
				}
				else
				{
					entry.setSleepState(SleepState.WACH);
				}
			}
			String s = gson.toJson(entry);
			sendJSONtoActiveMQ(s);
		}
	}

}
