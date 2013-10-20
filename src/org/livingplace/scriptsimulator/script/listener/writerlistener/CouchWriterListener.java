package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.CouchEntry;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.CouchEntry.CouchID;
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
	
	public void entryEvent(EntryEvent event, Deviation deviation) {
		CouchEntry entry = (CouchEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();


		if(Helper.getRandomDouble() > devval)
		{
			double d = Helper.getRandomDouble();
			if(Helper.getRandomDouble() < devval)
			{
				if(d < 0.0625)
				{
					entry.setCouchID(CouchID.c00);
				}
				else if(d < 0.125)
				{
					entry.setCouchID(CouchID.c01);
				}
				else if(d < 0.1875)
				{
					entry.setCouchID(CouchID.c10);
				}
				else if(d < 0.25)
				{
					entry.setCouchID(CouchID.c11);
				}
				else if(d < 0.3125)
				{
					entry.setCouchID(CouchID.c20);
				}
				else if(d < 0.375)
				{
					entry.setCouchID(CouchID.c21);
				}
				else if(d < 0.4375)
				{
					entry.setCouchID(CouchID.c30);
				}
				else if(d < 0.5)
				{
					entry.setCouchID(CouchID.c31);
				}
				else if(d < 0.5625)
				{
					entry.setCouchID(CouchID.c40);
				}
				else if(d < 0.625)
				{
					entry.setCouchID(CouchID.c41);
				}
				else if(d < 0.6875)
				{
					entry.setCouchID(CouchID.c42);
				}
				else if(d < 0.75)
				{
					entry.setCouchID(CouchID.c43);
				}
				else if(d < 0.8125)
				{
					entry.setCouchID(CouchID.c50);
				}
				else if(d < 0.875)
				{
					entry.setCouchID(CouchID.c51);
				}
				else if(d < 0.9375)
				{
					entry.setCouchID(CouchID.c52);
				}
				else
				{
					entry.setCouchID(CouchID.c53);
				}
			}

			MessageFileWriter writer = MessageFileWriter.getInstance();
			
			String s = "Couch;" + 
						entry.getExecutionTime() + ";" +
						entry.getCouchID().toString();
			
			writer.bufferString(entry.getExecutionTime(), s);
		}
	}
}
