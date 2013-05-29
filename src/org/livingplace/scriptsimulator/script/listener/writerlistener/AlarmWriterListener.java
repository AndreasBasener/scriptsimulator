package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.AlarmEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class AlarmWriterListener extends EntryJsonListener{

	public AlarmWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		AlarmEntry entry = (AlarmEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Alarm;" + 
					entry.getExecutionTime() + ";" +
					entry.getName() + ";" +
					entry.getDescription();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
	
}
