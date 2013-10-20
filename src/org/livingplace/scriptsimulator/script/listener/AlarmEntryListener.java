package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.AlarmEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import com.google.gson.Gson;

public class AlarmEntryListener extends EntryJsonListener
{
	public AlarmEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.ALARM_ENTRY_TOPIC_NAME);

	}
	
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		AlarmEntry entry = (AlarmEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			String s = gson.toJson(entry);
			sendJSONtoActiveMQ(s);
		}
	}
}
