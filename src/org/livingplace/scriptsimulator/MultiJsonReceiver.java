package org.livingplace.scriptsimulator;

import java.util.ArrayList;
import java.util.List;


import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
// import org.apache.activemq.broker.region.Topic;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.livingplace.scriptsimulator.script.entry.*;

/**
 * Hilfsprogramm, um die verschiedenen JSON Nachrichtenströme über ActiveMQ zu empfangen.
 * 
 * @author Andreas Basener
 * 
 */
public class MultiJsonReceiver extends ApplicationWindow
{
	/**
	 * Stores the different <code>TabItems</code>.
	 */
	private TabFolder			tabFolder;

	// private List<Topic> topicList;

	/**
	 * Displays the received messages.
	 */
	private List<StyledText>	styledTextList;
	
//	private Shell shell;

	/**
	 * Creates a new <code>MultiJsonReceiver</code> instance.
	 */
	public MultiJsonReceiver()
	{
		super(null);
//		Display.getCurrent().asyncExec(new Runnable() {
//			
//			@Override
//			public void run() {
//				shell = new Shell();
//			}
//		});
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createContents(Composite parent)
	{

		// set images for the Icon
		Image icons[] = { new Image(Display.getDefault(),
									getClass().getResourceAsStream("/envelope442.png"))};
		getShell().setImages(icons);
		
		getShell().setText("MultiJsonReceiver");
		parent.setSize(	1200,
						600);

		// topicList = new ArrayList<Topic>();
		styledTextList = new ArrayList<StyledText>();

		Menu menu = new Menu(	getShell(),
								SWT.BAR);
		this.getShell().setMenuBar(menu);

		MenuItem clearItem = new MenuItem(	menu,
											SWT.PUSH);
		clearItem.setText("clear");
		clearItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run()
					{
						for (StyledText st : styledTextList)
						{
							st.setText("");
						}
					}
				});
			}

		});

		tabFolder = new TabFolder(	parent,
									SWT.BORDER);
		addTab(	tabFolder,
				AlarmEntry.class.getSimpleName(),
				Helper.ALARM_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				BlindsEntry.class.getSimpleName(),
				Helper.BLINDS_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				BedEntry.class.getSimpleName(),
				Helper.BED_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				DoorBellEntry.class.getSimpleName(),
				Helper.DOORBELL_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				DoorEntry.class.getSimpleName(),
				Helper.DOOR_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				MoodEntry.class.getSimpleName(),
				Helper.MOOD_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				MediaEntry.class.getSimpleName(),
				Helper.MEDIA_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				OnOffEntry.class.getSimpleName(),
				Helper.ONOFF_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				PeriodicEntry.class.getSimpleName(),
				Helper.PERIODIC_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				PowerEntry.class.getSimpleName(),
				Helper.POWER_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				SensorEntry.class.getSimpleName(),
				Helper.SENSOR_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				SingleEntry.class.getSimpleName(),
				Helper.SINGLE_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				StorageEntry.class.getSimpleName(),
				Helper.STORAGE_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				"UbisenseTracking",
				Helper.UBISENSE_TRACKING_TOPIC);
		addTab(	tabFolder,
				UbisenseMockupEntry.class.getSimpleName(),
				Helper.UBISENSE_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				WindowEntry.class.getSimpleName(),
				Helper.WINDOW_ENTRY_TOPIC_NAME);
		addTab(	tabFolder,
				WaterEntry.class.getSimpleName(),
				Helper.WATER_ENTRY_TOPIC_NAME);

		return parent;
	}

	public static void main(String[] args)
	{
		MultiJsonReceiver receiver = new MultiJsonReceiver();
		receiver.setBlockOnOpen(true);
		receiver.open();
		Display.getCurrent().dispose();
	}

	/**
	 * Adds a new <code>TabItem</code> to the <code>TabFolder</code>.
	 * @param folder <code>TabFolder</code>
	 * @param text Text at top of <code>TabItem</code>.
	 * @param topicName Name of ActiveMQ topic
	 */
	private void addTab(TabFolder folder, String text, final String topicName)
	{
		TabItem item = new TabItem(	folder,
									SWT.NONE);
		item.setText(text);

		final StyledText styledText = new StyledText(	folder,
														SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
																| SWT.H_SCROLL);
		styledText.setText(topicName + "\n");
		item.setControl(styledText);

		styledTextList.add(styledText);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run()
			{

				boolean transacted = false;
				TopicSubscriber subscriber;

				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Helper.URL);
				TopicConnection connection;

				try
				{
					connection = connectionFactory.createTopicConnection();
					connection.start();

					TopicSession newSession = connection
							.createTopicSession(transacted,
												Session.AUTO_ACKNOWLEDGE);
					Topic topic = newSession.createTopic(topicName);

					// Setup a message publisher to send messages to the topic
					subscriber = newSession.createSubscriber(topic);

					while (true)
					{
						final TextMessage message = (TextMessage) subscriber.receive();
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run()
							{
								try
								{
									styledText.append(message.getText() + "\n");
								}
								catch (JMSException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		thread.start();

	}

}
