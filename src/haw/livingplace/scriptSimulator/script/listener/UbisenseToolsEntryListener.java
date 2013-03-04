package haw.livingplace.scriptSimulator.script.listener;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.entry.EntryEvent;
import haw.livingplace.scriptSimulator.script.entry.UbisenseToolsEntry;

import java.net.UnknownHostException;

import javax.jms.JMSException;

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
		
//		switch (entry.getUbiToolType())
//		{
//		case PUNKT:
//			s = punktCase(entry);
//			break;
//		case LINIE:
//			s = linieCase(entry);
//			break;
//		case KREIS:
//			s = kreisCase(entry);
//			break;
//		default:
//			break;
//		}

		sendJSONtoActiveMQ(s);
	}

	private String punktCase(UbisenseToolsEntry entry)
	{
//		return gson.toJson(entry.getUbisenseData());
		return entry.getUbisenseData().toJson();
	}
	private String linieCase(UbisenseToolsEntry entry)
	{
		return entry.getUbisenseData().toJson();
	}
	private String kreisCase(UbisenseToolsEntry entry)
	{
		return entry.getUbisenseData().toJson();
	}

}
