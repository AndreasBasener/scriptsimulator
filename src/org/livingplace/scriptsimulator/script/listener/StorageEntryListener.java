package org.livingplace.scriptsimulator.script.listener;


import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

public class StorageEntryListener extends EntryJsonListener
{
	public StorageEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.STORAGE_ENTRY_TOPIC_NAME);
	
	}

}
