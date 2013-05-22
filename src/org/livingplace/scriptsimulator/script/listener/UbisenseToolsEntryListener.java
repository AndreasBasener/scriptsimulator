package org.livingplace.scriptsimulator.script.listener;


import java.net.UnknownHostException;

import javax.jms.JMSException;

import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.UbisenseToolsEntry;

import com.google.gson.Gson;
import com.mongodb.MongoException;

import de.hawhamburg.livingplace.messaging.activemq.wrapper.ConnectionSettings;
import de.hawhamburg.livingplace.messaging.activemq.wrapper.LPPublisher;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseToolsEntryListener extends EntryJsonListener
{

	public UbisenseToolsEntryListener(String amqip, String mongoip, Gson gson)
	{

		ConnectionSettings settings = new ConnectionSettings();
		settings.amq_ip = amqip;
		settings.mongo_ip = mongoip;
		this.gson = gson;

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
		UbisenseToolsEntry entry = (UbisenseToolsEntry) event.getSource();

		String s = null;

		s = gson.toJson(entry.getUbisenseData());

		sendJSONtoActiveMQ(s);
	}

}
