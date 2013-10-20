package org.livingplace.scriptsimulator.script.listener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;
import org.livingplace.scriptsimulator.script.entry.ScriptEntry;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class EntryJsonListener implements EntryListener
{

	//protected LPPublisher	lpPublisher;
	private TopicConnection topicConnection;
    private String topicName;
    private TopicSession topicSession;
    private Topic topic;
    private TopicPublisher publisher;
    private ActiveMQConnectionFactory connectionFactory;

	protected Gson			gson;

	protected EntryJsonListener()
	{
	}

	public EntryJsonListener(String amqip, String mongoip, Gson gson)
	{
		this(amqip,mongoip,gson,Helper.DEFAULT_TEST_TOPIC);

	}
	
	public EntryJsonListener(String amqip, String mongoip, Gson gson, String topicName)
	{
		this.gson = gson;
		
		configure(amqip, topicName);

//		ConnectionSettings settings = new ConnectionSettings();
//		settings.amq_ip = amqip;
//		settings.mongo_ip = mongoip;

		
		
//		try
//		{
//			this.lpPublisher = new LPPublisher(	topicName,
//												settings);
//		}
//		catch (UnknownHostException e)
//		{
//			e.printStackTrace();
//		}
//		catch (MongoException e)
//		{
//			e.printStackTrace();
//		}
//		catch (JMSException e)
//		{
//			e.printStackTrace();
//		}
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
		ScriptEntry entry = (ScriptEntry) event.getSource();
		
		Deviation dev = entry.getDeviation();
		double devval = dev.getDeviationWeight();
		
//		if(deviation.getDeviationWeight()>0)
//		{
//			long time = entry.getOffset().toStandardDuration().getMillis();
//			time = time + (long) (Helper.MAX_TIME_DEVIATION * deviation.getRandomDeviation());
//			entry.setOffset(new Period(time));
//		}

		String s = gson.toJson(entry);

		if(Helper.getRandomDouble() > devval)
		{
			sendJSONtoActiveMQ(s);
		}
	}
	
	/**
	 * Sends a JSON String to ActiveMQ
	 * 
	 * @param jsonString
	 *            the JSON String to be send
	 */
	protected void sendJSONtoActiveMQ(String jsonString)
	{
//		lpPublisher.publish(jsonString);
		try {
            TextMessage t = this.topicSession.createTextMessage(jsonString);
            this.publisher.send(topic, t);

        } catch (JMSException ex) {
        }
	}

//	@Override
//	public void disconnect()
//	{
//		if (lpPublisher != null){
//			lpPublisher.disconnect();
////			System.out.println(this.getClass().getSimpleName() + ": disconnected");
//		}
//		else
//		{
////			System.out.println(this.getClass().getSimpleName() + ": not disconnected");
//		}
//	}

    private void configure(String amqip, String topicName)
    {
        this.topicName = topicName;
        String address = "tcp://" + amqip + ":61616";

        this.connectionFactory = new ActiveMQConnectionFactory(address);

        try {
            this.topicConnection = this.connectionFactory.createTopicConnection();
            this.topicConnection.start();

            this.topicSession = this.topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            this.topic = this.topicSession.createTopic(this.topicName);

            this.publisher = this.topicSession.createPublisher(this.topic);


        } catch (JMSException ex) {

            System.err.println("FATAL: \t|We can't connect to the ActiveMQ");
            System.err.println("FATAL: \t|Printing Stack:\n");
            ex.getStackTrace();
        }
    }

    public void disconnect() {
        try {

            if (publisher != null) {
                this.publisher.close();
            }

            if (topicSession != null) {
                this.topicSession.close();
            }

            if (topicConnection != null) {
                this.topicConnection.close();
            }
            
        } catch (JMSException ex) {

        }
    }
	
}
