package org.livingplace.scriptsimulator.script.listener;


import java.net.UnknownHostException;

import javax.jms.JMSException;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.Point3D;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupEntry;

import com.google.gson.Gson;
import com.mongodb.MongoException;

import de.hawhamburg.livingplace.messaging.activemq.wrapper.ConnectionSettings;
import de.hawhamburg.livingplace.messaging.activemq.wrapper.LPPublisher;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseMockupEntryListener extends EntryJsonListener
{

	public UbisenseMockupEntryListener(String amqip, String mongoip, Gson gson)
	{
		super(amqip,mongoip,gson);
		ConnectionSettings settings = new ConnectionSettings();
		settings.amq_ip = amqip;
		settings.mongo_ip = mongoip;

		try
		{
			this.lpPublisher = new LPPublisher(	Helper.UBISENSE_ENTRY_TOPIC_NAME,
												settings);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (MongoException e)
		{
			e.printStackTrace();
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	/**
	 * Catch Event, generate JSON String and publish it to ActiveMQ
	 */
	public void entryEvent(EntryEvent event)
	{
		UbisenseMockupEntry entry = (UbisenseMockupEntry) event.getSource();

		String s = gson.toJson(entry.getCurrentMockupData());

		sendJSONtoActiveMQ(s);
	}
	@Override
	/**
	 * Catch Event, generate JSON String and publish it to ActiveMQ
	 */
	public void entryEvent(EntryEvent event, Deviation deviation)
	{
		UbisenseMockupEntry entry = (UbisenseMockupEntry) event.getSource();
		UbisenseMockupData data = entry.getCurrentMockupData();
		
		double fixedDeviation = 0.2;
		
		Point3D position = data.getPosition();
		position.setX(position.getX() + fixedDeviation*deviation.getRandomDeviation());
		position.setY(position.getY() + fixedDeviation*deviation.getRandomDeviation());
		position.setZ(position.getZ() + fixedDeviation*deviation.getRandomDeviation());
		
		data.setPosition(position);
		
//		String s = gson.toJson(entry.getCurrentMockupData());
		String s = gson.toJson(data);
		
		sendJSONtoActiveMQ(s);
	}
}
