package org.livingplace.scriptsimulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.joda.time.*;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;
import org.livingplace.scriptsimulator.script.json.Point3DJsonConverter;
import org.livingplace.scriptsimulator.script.json.UbisenseMockupDataJsonConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Simple <code>MessageReceiver</code>
 * 
 * @author Andreas Basener
 * 
 */
public class MessageReceiver
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(UbisenseMockupData.class, new UbisenseMockupDataJsonConverter());
		builder.registerTypeAdapter(Point3D.class, new Point3DJsonConverter());
		Gson gson = builder.create();
		
		DateTime start = null;

		Long offset = null;

		boolean transacted = false;
		TopicSubscriber subscriber;

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Helper.URL);
		TopicConnection connection;

		try
		{
			connection = connectionFactory.createTopicConnection();
			connection.start();

			TopicSession newSession = connection.createTopicSession(transacted,
																	Session.AUTO_ACKNOWLEDGE);
			Topic topic = newSession.createTopic(Helper.UBISENSE_ENTRY_TOPIC_NAME);

			// Setup a message publisher to send messages to the topic
			subscriber = newSession.createSubscriber(topic);

			@SuppressWarnings("resource")
			PrintWriter writer = new PrintWriter("UbisenseTestData.csv");

			System.out.println("Alles fertig...");

			while (true)
			{
				TextMessage txtMessage = (TextMessage) subscriber.receive();

				UbisenseMockupData data = gson.fromJson(txtMessage.getText(), UbisenseMockupData.class);
				
				String csvString =  data.getTime() + ";" + 
									data.getPosition().getX() + ";" + 
									data.getPosition().getY();
				if (start == null)
				{
					start = new DateTime();
				}
				Duration dur = new Duration(start,
											new DateTime());

				if (offset == null)
				{
					offset = dur.getMillis();
				}

				String jsontxt = txtMessage.getText();

				jsontxt = jsontxt.replaceAll(	"\r\n",
										"");
				jsontxt = jsontxt.replaceAll(	"[ ]+",
										"");

				jsontxt = (dur.getMillis() - offset) + ";" + jsontxt;

//				System.out.println(jsontxt);
//				writer.println(jsontxt);
				

				System.out.println(csvString);
				writer.println(csvString);
				writer.flush();
			}
		}
		catch (JMSException e)
		{
			// Handle the exception appropriately
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
