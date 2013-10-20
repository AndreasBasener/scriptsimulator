package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.Point3D;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;
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
	
	public void entryEvent(EntryEvent event, Deviation deviation)
	{
		UbisenseToolsEntry entry = (UbisenseToolsEntry) event.getSource();
		UbisenseMockupData data = entry.getUbisenseData();
		
		double devval = entry.getDeviation().getDeviationWeight();
		if(Helper.getRandomDouble() > devval)
		{
			double fixedDeviation = 0.2;
			
			Point3D position = data.getPosition();
			position.setX(position.getX() + fixedDeviation*deviation.getRandomDeviation());
			position.setY(position.getY() + fixedDeviation*deviation.getRandomDeviation());
			position.setZ(position.getZ() + fixedDeviation*deviation.getRandomDeviation());
			
			data.setPosition(position);
			
			String s = "Ubisense;" + 
					entry.getExecutionTime() + ";" +
					entry.getUbisenseData().getPosition().getX() + ";" +
					entry.getUbisenseData().getPosition().getY();
		
			writer.bufferString(entry.getExecutionTime(), s);
		}
	}
}
