package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.WaterEntry;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterState;
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
	
	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		WaterEntry entry = (WaterEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			if(Helper.getRandomDouble() < devval)
			{
				if(Helper.getRandomDouble() < 0.5)
				{
					entry.setWaterState(WaterState.OFF);
				}
				else
				{
					entry.setWaterState(WaterState.ON);
				}
			}
			MessageFileWriter writer = MessageFileWriter.getInstance();
			
			String s = "Water;" + 
						entry.getExecutionTime() + ";" +
						entry.getWaterID() + ";" +
						entry.getWaterState();
			
			writer.bufferString(entry.getExecutionTime(), s);
		}
	}
}
