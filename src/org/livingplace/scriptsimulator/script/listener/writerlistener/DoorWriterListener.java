package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.DoorEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.DoorEntry.DoorState;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class DoorWriterListener extends EntryJsonListener{

	public DoorWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		DoorEntry entry = (DoorEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Door;" + 
					entry.getExecutionTime() + ";" +
					entry.getName() + ";" +
					entry.getDescription();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
	
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		DoorEntry entry = (DoorEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			double d = Helper.getRandomDouble();
			if(Helper.getRandomDouble() < devval)
			{
				if(d < 0.5)
				{
					entry.setDoorState(DoorState.CLOSED);
				}
				else
				{
					entry.setDoorState(DoorState.OPEN);
				}
			}

			MessageFileWriter writer = MessageFileWriter.getInstance();
			
			String s = "Door;" + 
						entry.getExecutionTime() + ";" +
						entry.getName() + ";" +
						entry.getDescription();
			
			writer.bufferString(entry.getExecutionTime(), s);
		}
	}
}
