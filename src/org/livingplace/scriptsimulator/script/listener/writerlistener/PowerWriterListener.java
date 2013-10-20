package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.PowerEntry;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerState;
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
	
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		PowerEntry entry = (PowerEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			double d = Helper.getRandomDouble();
			
			if(Helper.getRandomDouble() < devval)
			{
				if(d < 0.5)
				{
					entry.setPowerState(PowerState.OFF);
				}
				else
				{
					entry.setPowerState(PowerState.ON);
				}
			}
			MessageFileWriter writer = MessageFileWriter.getInstance();
			
			String s = "Power;" + 
						entry.getExecutionTime() + ";" +
						entry.getPowerID() + ";" +
						entry.getPowerState();
			
			writer.bufferString(entry.getExecutionTime(), s);
		}
	}
}
