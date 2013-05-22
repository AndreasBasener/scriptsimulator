package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.UbisenseToolsEntry;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;

import com.google.gson.Gson;

public class UbisenseToolsWriterListener extends EntryJsonListener{

	private MessageFileWriter writer;
	
	public UbisenseToolsWriterListener(String amqip, String mongoip, Gson gson)
	{
		super(amqip,mongoip,gson);
		writer = MessageFileWriter.getInstance();
	}
	
	@Override
	public void entryEvent(EntryEvent event)
	{
		UbisenseToolsEntry entry = (UbisenseToolsEntry) event.getSource();

		String s = "Ubisense;" + 
					entry.getExecutionTime() + ";" +
					entry.getUbisenseData().getPosition().getX() + ";" +
					entry.getUbisenseData().getPosition().getY();
		
		writer.bufferString(entry.getExecutionTime(), s);
	}
}
