package haw.livingplace.scriptSimulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.joda.time.*;

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
			Topic topic = newSession.createTopic(Helper.UBISENSE_TRACKING_TOPIC);

			// Setup a message publisher to send messages to the topic
			subscriber = newSession.createSubscriber(topic);

			PrintWriter writer = new PrintWriter("UbisenseTestData.csv");

			System.out.println("Alles fertig...");

			while (true)
			{
				TextMessage txtMessage = (TextMessage) subscriber.receive();

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

				String txt = txtMessage.getText();

				txt = txt.replaceAll(	"\r\n",
										"");
				txt = txt.replaceAll(	"[ ]+",
										"");

				txt = (dur.getMillis() - offset) + ";" + txt;

				System.out.println(txt);
				writer.println(txt);
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
