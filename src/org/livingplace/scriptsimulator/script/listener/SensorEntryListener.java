package org.livingplace.scriptsimulator.script.listener;

import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.SensorEntry;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class SensorEntryListener extends EntryJsonListener
{

	public SensorEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.SENSOR_ENTRY_TOPIC_NAME);

	}

	@Override
	public void entryEvent(EntryEvent event)
	{
		SensorEntry entry = (SensorEntry) event.getSource();
		String s = "";
		if (entry.getValuet().equals("i"))
		{
			SensorEntry dummy = new SensorEntry(entry);
			dummy.setStartDate(entry.getStartDate());

			double tmp = entry.getValuei() * entry.getDeviation().getRandomDeviation();

			dummy.setAllValue(entry.getValuei() + (int) tmp);

			s = gson.toJson(dummy);
		}
		else
		{
			s = gson.toJson(entry);
		}
		// System.out.println(s);
		sendJSONtoActiveMQ(s);
	}

}
