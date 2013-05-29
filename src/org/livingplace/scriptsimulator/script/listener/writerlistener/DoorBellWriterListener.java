package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.DoorBellEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class DoorBellWriterListener extends EntryJsonListener{

	public DoorBellWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		DoorBellEntry entry = (DoorBellEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "DoorBell;" + 
					entry.getExecutionTime() + ";" +
					entry.getName() + ";" +
					entry.getDescription();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
}
