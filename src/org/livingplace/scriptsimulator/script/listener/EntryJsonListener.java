package org.livingplace.scriptsimulator.script.listener;

import java.net.UnknownHostException;


import javax.jms.JMSException;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.ScriptEntry;

import com.google.gson.Gson;
import com.mongodb.MongoException;

import de.hawhamburg.livingplace.messaging.activemq.wrapper.ConnectionSettings;
import de.hawhamburg.livingplace.messaging.activemq.wrapper.LPPublisher;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class EntryJsonListener implements EntryListener
{

	protected LPPublisher	lpPublisher;

	Gson					gson;

	protected EntryJsonListener()
	{
	}

	public EntryJsonListener(String amqip, String mongoip, Gson gson)
	{
		this.gson = gson;

		ConnectionSettings settings = new ConnectionSettings();
		settings.amq_ip = amqip;
		settings.mongo_ip = mongoip;

		try
		{
			this.lpPublisher = new LPPublisher(	Helper.DEFAULT_TEST_TOPIC,
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
	
	public EntryJsonListener(String amqip, String mongoip, Gson gson, String topicName)
	{
		this.gson = gson;

		ConnectionSettings settings = new ConnectionSettings();
		settings.amq_ip = amqip;
		settings.mongo_ip = mongoip;

		try
		{
			this.lpPublisher = new LPPublisher(	topicName,
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
		ScriptEntry entry = (ScriptEntry) event.getSource();
		String s = gson.toJson(entry);

		sendJSONtoActiveMQ(s);
	}

	@Override
	public void entryEvent(EntryEvent event, Deviation deviation) {
		entryEvent(event);
	}
	
	/**
	 * Sends a JSON String to ActiveMQ
	 * 
	 * @param jsonString
	 *            the JSON String to be send
	 */
	protected void sendJSONtoActiveMQ(String jsonString)
	{
		lpPublisher.publish(jsonString);
	}

	@Override
	public void disconnect()
	{
		if (lpPublisher != null)
			lpPublisher.disconnect();

	}

}
