package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.StorageEntry;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageAction;

import com.google.gson.Gson;

public class StorageEntryListener extends EntryJsonListener
{
	public StorageEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.STORAGE_ENTRY_TOPIC_NAME);
	
	}

	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		StorageEntry entry = (StorageEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			if(Helper.getRandomDouble() < devval)
			{
				double d = Helper.getRandomDouble();
				if(d < 0.25)
				{
					entry.setStorageAction(StorageAction.CLOSED);
				}
				else if(d < 0.5)
				{
					entry.setStorageAction(StorageAction.OBJECT_PUT);
				}
				else if(d < 0.75)
				{
					entry.setStorageAction(StorageAction.OBJECT_TAKEN);
				}
				else
				{
					entry.setStorageAction(StorageAction.OPENED);
				}
			}
			String s = gson.toJson(entry);
			sendJSONtoActiveMQ(s);
		}
	}
}
