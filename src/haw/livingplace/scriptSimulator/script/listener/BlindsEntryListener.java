package haw.livingplace.scriptSimulator.script.listener;

import haw.livingplace.scriptSimulator.Helper;

import java.net.UnknownHostException;

import javax.jms.JMSException;

import com.google.gson.Gson;
import com.mongodb.MongoException;

import de.hawhamburg.livingplace.messaging.activemq.wrapper.ConnectionSettings;
import de.hawhamburg.livingplace.messaging.activemq.wrapper.LPPublisher;

public class BlindsEntryListener extends EntryJsonListener
{
	public BlindsEntryListener(String amqip, String mongoip, Gson gson)
	{

		this.gson = gson;

		ConnectionSettings settings = new ConnectionSettings();
		settings.amq_ip = amqip;
		settings.mongo_ip = mongoip;

		try
		{
			this.lpPublisher = new LPPublisher(	Helper.BLINDS_ENTRY_TOPIC_NAME,
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
}
