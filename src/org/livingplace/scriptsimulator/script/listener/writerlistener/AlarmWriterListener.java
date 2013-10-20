package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
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
	
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		AlarmEntry entry = (AlarmEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			MessageFileWriter writer = MessageFileWriter.getInstance();
			
			String s = "Alarm;" + 
						entry.getExecutionTime() + ";" +
						entry.getName() + ";" +
						entry.getDescription();
			
			writer.bufferString(entry.getExecutionTime(), s);
		}
	}
	
}
