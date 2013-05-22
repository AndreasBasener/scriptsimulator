package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupEntry;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

import com.google.gson.Gson;

public class UbisenseWriterListener extends EntryJsonListener{

	private MessageFileWriter writer;
	
	public UbisenseWriterListener(String amqip, String mongoip, Gson gson)
	{
		super(amqip, mongoip, gson);
		
		writer = MessageFileWriter.getInstance();
	}
	
	@Override
	public void entryEvent(EntryEvent event)
	{
		UbisenseMockupEntry entry = (UbisenseMockupEntry) event.getSource();
		UbisenseMockupData data = entry.getCurrentMockupData();
		
		String s = "Ubisense;" + 
					entry.getExecutionTime() + ";" +
					data.getPosition().getX() + ";" + 
					data.getPosition().getY();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
	
}
