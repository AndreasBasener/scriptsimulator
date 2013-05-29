package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.WaterEntry;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class WaterWriterListener extends EntryJsonListener{

	public WaterWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		WaterEntry entry = (WaterEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Water;" + 
					entry.getExecutionTime() + ";" +
					entry.getWaterID() + ";" +
					entry.getWaterState();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
}
