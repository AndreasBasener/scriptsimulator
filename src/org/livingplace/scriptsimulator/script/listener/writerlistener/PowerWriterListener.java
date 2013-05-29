package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.PowerEntry;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class PowerWriterListener extends EntryJsonListener{

	public PowerWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		PowerEntry entry = (PowerEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Power;" + 
					entry.getExecutionTime() + ";" +
					entry.getPowerID() + ";" +
					entry.getPowerState();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
}
