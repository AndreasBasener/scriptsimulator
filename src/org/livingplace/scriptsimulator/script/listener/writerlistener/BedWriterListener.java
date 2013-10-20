package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.BedEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.BedEntry.SleepState;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

public class BedWriterListener extends EntryJsonListener{

	public BedWriterListener()
	{
		
	}
	
	public void entryEvent(EntryEvent event)
	{
		BedEntry entry = (BedEntry) event.getSource();

		MessageFileWriter writer = MessageFileWriter.getInstance();
		
		String s = "Bed;" + 
					entry.getExecutionTime() + ";" +
					entry.getSleepState().toString();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}

	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		BedEntry entry = (BedEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			double d = Helper.getRandomDouble();
			if(Helper.getRandomDouble() < devval)
			{
				if(d < 0.143)
				{
					entry.setSleepState(SleepState.N1);
				}
				else if( d < 0.286)
				{
					entry.setSleepState(SleepState.N2);
				}
				else if( d < 0.429)
				{
					entry.setSleepState(SleepState.N3);
				}
				else if( d < 0.572)
				{
					entry.setSleepState(SleepState.N4);
				}
				else if( d < 0.715)
				{
					entry.setSleepState(SleepState.NREM);
				}
				else if( d < 0.858)
				{
					entry.setSleepState(SleepState.REM);
				}
				else
				{
					entry.setSleepState(SleepState.WACH);
				}
			}

			MessageFileWriter writer = MessageFileWriter.getInstance();
			
			String s = "Bed;" + 
						entry.getExecutionTime() + ";" +
						entry.getSleepState().toString();
			
			writer.bufferString(entry.getExecutionTime(), s);
		}
	}
}
