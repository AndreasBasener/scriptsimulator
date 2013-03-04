package haw.livingplace.scriptSimulator.script.listener;

import java.net.UnknownHostException;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.entry.EntryEvent;
import haw.livingplace.scriptSimulator.script.entry.UbisenseEntry;

import javax.jms.JMSException;
import com.mongodb.MongoException;

import de.hawhamburg.livingplace.messaging.activemq.wrapper.ConnectionSettings;
import de.hawhamburg.livingplace.messaging.activemq.wrapper.LPPublisher;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseEntryListener extends EntryJsonListener
{

	public UbisenseEntryListener(String amqip, String mongoip)
	{

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
		UbisenseEntry entry = (UbisenseEntry) event.getSource();
		String s = entry.getCurrentLine();
		s = s.replaceAll(	"\r",
							"");
		s = s.replaceAll(	"\n",
							"");
		s = s.replaceAll(	"[ ]+",
							"");

		// System.out.println(s);
		sendJSONtoActiveMQ(s);
	}

	/**
	 * // * Sends a JSON String to ActiveMQ // * // * @param jsonString // * the JSON String to be
	 * send //
	 */
	// private void sendJSONtoActiveMQ(String jsonString)
	// {
	// lpPublisher.publish(jsonString);
	// }
	//
	// @Override
	// public void disconnect()
	// {
	// if (lpPublisher != null)
	// lpPublisher.disconnect();
	//
	// }
}
