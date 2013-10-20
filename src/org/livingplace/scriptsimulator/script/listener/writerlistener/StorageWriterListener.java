package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.StorageEntry;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageAction;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class StorageWriterListener extends EntryJsonListener{
	
	public StorageWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		StorageEntry entry = (StorageEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Storage;" + 
					entry.getExecutionTime() + ";" +
					entry.getStorageID() + ";" +
					entry.getStorageAction();
		
		writer.bufferString(entry.getExecutionTime(), s);
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

			MessageFileWriter writer = MessageFileWriter.getInstance();
			
			String s = "Storage;" + 
						entry.getExecutionTime() + ";" +
						entry.getStorageID() + ";" +
						entry.getStorageAction();
			
			writer.bufferString(entry.getExecutionTime(), s);
		}
	}
	
}
