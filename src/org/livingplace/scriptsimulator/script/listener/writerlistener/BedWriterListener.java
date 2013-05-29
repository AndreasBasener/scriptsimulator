package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.BedEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class BedWriterListener extends EntryJsonListener{

	public BedWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		BedEntry entry = (BedEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Bed;" + 
					entry.getExecutionTime() + ";" +
					entry.getSleepState().toString();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
}
