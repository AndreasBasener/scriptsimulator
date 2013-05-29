package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.WindowEntry;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class WindowWriterListener extends EntryJsonListener{

	public WindowWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		WindowEntry entry = (WindowEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Window;" + 
					entry.getExecutionTime() + ";" +
					entry.getWinID() + ";" +
					entry.getWindowAction();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
}
