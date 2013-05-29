package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.BlindsEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class BlindsWriterListener extends EntryJsonListener{

	public BlindsWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		BlindsEntry entry = (BlindsEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Blinds;" + 
					entry.getExecutionTime() + ";" +
					entry.getBlindsID().toString() + ";" +
					entry.getBlindsState().toString();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
}
