package haw.livingplace.scriptSimulator.script.listener;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.entry.EntryEvent;
import haw.livingplace.scriptSimulator.script.entry.SensorEntry;
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
public class SensorEntryListener extends EntryJsonListener
{

	public SensorEntryListener(String amqip, String mongoip, Gson gson)
	{

		this.gson = gson;

		ConnectionSettings settings = new ConnectionSettings();
		settings.amq_ip = amqip;
		settings.mongo_ip = mongoip;

		try
		{
			this.lpPublisher = new LPPublisher(	Helper.SENSOR_ENTRY_TOPIC_NAME,
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
	public void entryEvent(EntryEvent event)
	{
		SensorEntry entry = (SensorEntry) event.getSource();
		String s = "";
		if (entry.getValuet().equals("i"))
		{
			SensorEntry dummy = new SensorEntry(entry);
			dummy.setStartDate(entry.getStartDate());

			double tmp = entry.getValuei() * entry.getDeviation().getRandomDeviation();

			dummy.setAllValue(entry.getValuei() + (int) tmp);

			s = gson.toJson(dummy);
		}
		else
		{
			s = gson.toJson(entry);
		}
		// System.out.println(s);
		sendJSONtoActiveMQ(s);
	}

}
