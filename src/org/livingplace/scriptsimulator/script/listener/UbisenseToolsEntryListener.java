package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.Point3D;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;
import org.livingplace.scriptsimulator.script.entry.UbisenseToolsEntry;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseToolsEntryListener extends EntryJsonListener
{

	public UbisenseToolsEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.UBISENSE_ENTRY_TOPIC_NAME);

	}

	@Override
	/**
	 * Catch Event, generate JSON String and publish it to ActiveMQ
	 */
	public void entryEvent(EntryEvent event)
	{
		UbisenseToolsEntry entry = (UbisenseToolsEntry) event.getSource();

		String s = null;

		s = gson.toJson(entry.getUbisenseData());

		sendJSONtoActiveMQ(s);
	}

	@Override
	/**
	 * Catch Event, generate JSON String and publish it to ActiveMQ
	 */
	public void entryEvent(EntryEvent event, Deviation deviation)
	{
		UbisenseToolsEntry entry = (UbisenseToolsEntry) event.getSource();
		UbisenseMockupData data = entry.getUbisenseData();
		
		double devval = entry.getDeviation().getDeviationWeight();
		if(Helper.getRandomDouble() > devval)
		{
			double fixedDeviation = 0.2;
			
			Point3D position = data.getPosition();
			position.setX(position.getX() + fixedDeviation*deviation.getRandomDeviation());
			position.setY(position.getY() + fixedDeviation*deviation.getRandomDeviation());
			position.setZ(position.getZ() + fixedDeviation*deviation.getRandomDeviation());
			
			data.setPosition(position);
			
			String s = gson.toJson(data);
			
			sendJSONtoActiveMQ(s);
		}
	}
}
