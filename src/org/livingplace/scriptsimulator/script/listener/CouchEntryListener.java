package org.livingplace.scriptsimulator.script.listener;

import org.livingplace.scriptsimulator.Helper;

import com.google.gson.Gson;

public class CouchEntryListener extends EntryJsonListener{

	public CouchEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.COUCH_ENTRY_TOPC_NAME);
		
	}
	
}
