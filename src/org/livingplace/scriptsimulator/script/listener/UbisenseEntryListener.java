package org.livingplace.scriptsimulator.script.listener;

import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.UbisenseEntry;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseEntryListener extends EntryJsonListener
{

	public UbisenseEntryListener(String amqip, String mongoip, Gson gson)
	{

		super(amqip,mongoip,gson,Helper.UBISENSE_ENTRY_TOPIC_NAME);

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
