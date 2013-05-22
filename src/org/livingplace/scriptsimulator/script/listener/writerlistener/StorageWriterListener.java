package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.StorageEntry;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

import com.google.gson.Gson;

public class StorageWriterListener extends EntryJsonListener{

	private MessageFileWriter writer;
	
	public StorageWriterListener(String amqip, String mongoip, Gson gson)
	{
		super(amqip,mongoip,gson);
		writer = MessageFileWriter.getInstance();
	}
	
	@Override
	public void entryEvent(EntryEvent event)
	{
		StorageEntry entry = (StorageEntry) event.getSource();

		String s = "Storage;" + 
					entry.getExecutionTime() + ";" +
					entry.getStorageID() + ";" +
					entry.getStorageAction();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
	
}
