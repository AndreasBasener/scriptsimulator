package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.CouchEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class CouchWriterListener extends EntryJsonListener{

	public CouchWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		CouchEntry entry = (CouchEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Couch;" + 
					entry.getExecutionTime() + ";" +
					entry.getCouchID().toString();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
}
