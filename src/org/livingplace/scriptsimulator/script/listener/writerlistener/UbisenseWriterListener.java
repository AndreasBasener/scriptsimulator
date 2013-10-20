package org.livingplace.scriptsimulator.script.listener.writerlistener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MessageFileWriter;
import org.livingplace.scriptsimulator.Point3D;
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
	
	@Override
	/**
	 * Catch Event, generate JSON String and publish it to ActiveMQ
	 */
	public void entryEvent(EntryEvent event, Deviation deviation)
	{
		UbisenseMockupEntry entry = (UbisenseMockupEntry) event.getSource();
		UbisenseMockupData data = entry.getCurrentMockupData();
		
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
					data.getPosition().getX() + ";" + 
					data.getPosition().getY();
		
		writer.bufferString(entry.getExecutionTime(), s);
		}
	}
}
