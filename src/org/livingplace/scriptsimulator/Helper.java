package org.livingplace.scriptsimulator;

import java.util.Random;

/**
 * This class serves the sole purpose to offer a central place for constant declarations.
 * 
 * @author Andreas Basener
 * 
 */
public class Helper
{
	/**
	 * global <code>Random</code> instance
	 */
	private static Random		globalRandom;
	
	public static double MAX_DISTANCE_DEVIATION = 0.2; // in Meter
	public static int MAX_TIME_DEVIATION = 300000; // in Millisekunden

	/**
	 * Default url for ActiveMQ
	 */
	public static final String	URL							= "tcp://127.0.0.1:61616";
	/**
	 * Default IP for ActiveMQ
	 */
	public static final String	ACTIVE_MQ_IP				= "127.0.0.1";
	/**
	 * Default IP for mongodb
	 */
	public static final String	MONGO_DB_IP					= "127.0.0.1";
	/**
	 * default client id for JSON-messages
	 */
	public static final String	DEFAULT_CLIENT_ID			= "ScriptSimulator:"
																+ System.getProperty("user.name");

	/**
	 * Default name for ActiveMQ topic
	 */
	public static final String	TOPIC_NAME					= "testTopic";
	/**
	 * Default name for the global test-<code>Topic</code>
	 */
	public static final String	DEFAULT_TEST_TOPIC			= "defaultTestTopic";

	/**
	 * Default <code>Topic</code>-name for UbisenseTracking
	 */
	public static final String	UBISENSE_TRACKING_TOPIC		= "UbisenseTracking";

	/**
	 * Default <code>Topic</code>-name for GlobalInfo
	 */
	public static final String	GLOBAL_INFO_TOPIC			= "GlobalInfoTopic";
	/**
	 * Default <code>Topic</code>-name for GlobalControl
	 */
	public static final String	GLOBAL_CONTROL_TOPIC		= "GlobalControlTopic";

	/**
	 * Default <code>Topic</code>-name for <code>AlarmEntry</code>
	 */
	public static final String	ALARM_ENTRY_TOPIC_NAME		= "alarmEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>BedEntry</code>
	 */
	public static final String	BED_ENTRY_TOPIC_NAME		= "bedEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>BlindsEntry</code>
	 */
	public static final String	BLINDS_ENTRY_TOPIC_NAME		= "blindsEntryTopic";
	public static final String  COUCH_ENTRY_TOPC_NAME       = "couchEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>DoorBellEntry</code>
	 */
	public static final String	DOORBELL_ENTRY_TOPIC_NAME	= "doorBellEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>DoorEntry</code>
	 */
	public static final String	DOOR_ENTRY_TOPIC_NAME		= "doorEntryTopic";
	public static final String MEDIA_ENTRY_TOPIC_NAME		= "mediaEntryTopic";
	public static final String MOOD_ENTRY_TOPIC_NAME		= "moodEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>OnOffEntry</code>
	 */
	public static final String	ONOFF_ENTRY_TOPIC_NAME		= "onOffEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>PeriodicEntry</code>
	 */
	public static final String	PERIODIC_ENTRY_TOPIC_NAME	= "periodicEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>PowerEntry</code>
	 */
	public static final String	POWER_ENTRY_TOPIC_NAME		= "powerEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>SensorEntry</code>
	 */
	public static final String	SENSOR_ENTRY_TOPIC_NAME		= "sensorEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>SingleEntry</code>
	 */
	public static final String	SINGLE_ENTRY_TOPIC_NAME		= "singleEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>StorageEntry</code>
	 */
	public static final String	STORAGE_ENTRY_TOPIC_NAME		= "storageEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>UbisenseEntry</code>
	 */
	public static final String	UBISENSE_ENTRY_TOPIC_NAME	= "ubisenseEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>WaterEntry</code>
	 */
	public static final String	WATER_ENTRY_TOPIC_NAME		= "waterEntryTopic";
	/**
	 * Default <code>Topic</code>-name for <code>WindowEntry</code>
	 */
	public static final String	WINDOW_ENTRY_TOPIC_NAME		= "windowsInfoEntryTopic";
	

	/**
	 * Default format for date output
	 */
	public static final String	DEFAULT_DATE_FORMAT			= "HH:mm:ss.S";

	/**
	 * url for LivingPlace ontology
	 */
	public static final String	LP_ONTOLOGY_URL				= "http://livingplace.informatik.haw-hamburg.de/wiki/index.php/Ontologie";
	/**
	 * default Ubisense tag id
	 */
	public static final String	DEFAULT_TAG_ID				= "839-452-37-094";
	/**
	 * default measurement unit of Ubisense
	 */
	public static final String	UBISENSE_UNIT				= "meter";
	/**
	 * version of Ubisense JSON messages
	 */
	public static final String	UBISENSE_VERSION			= "0.6";
	//
	// public static final String icon16 = "pics\\LivingPlace16.png";
	// public static final String icon32 = "pics\\LivingPlace32.png";
	// public static final String icon48 = "pics\\LivingPlace48.png";
	// public static final String icon64 = "pics\\LivingPlace64.png";
	// public static final String icon128 = "pics\\LivingPlace128.png";
	// public static final String icon256 = "pics\\LivingPlace256.png";
	// public static final String icon512 = "pics\\LivingPlace512.png";

	public static final String	ICON16						= "/LivingPlace16.png";
	public static final String	ICON32						= "/LivingPlace32.png";
	public static final String	ICON48						= "/LivingPlace48.png";
	public static final String	ICON64						= "/LivingPlace64.png";
	public static final String	ICON128						= "/LivingPlace128.png";
	public static final String	ICON256						= "/LivingPlace256.png";
	public static final String	ICON512						= "/LivingPlace512.png";

	/**
	 * default <code>ScriptEntry</code> name
	 */
	public static final String	DEFAULT_ENTRY_NAME			= "Name";
	/**
	 * default <code>ScriptEntry</code> description
	 */
	public static final String	DEFAULT_ENTRY_DESCRIPTION	= "Beschreibung";

	public static final String MESSAGEFILEWRITER_FILENAME = "output.csv";
	
	/**
	 * Generates and returns a new Random integer.
	 * 
	 * @return random integer
	 */
	public static int getRandomInt()
	{
		if (globalRandom == null)
			globalRandom = new Random();

		return globalRandom.nextInt();
	}
	public static int getRandomInt(int number)
	{
		if (globalRandom == null)
			globalRandom = new Random();

		return globalRandom.nextInt(number);
	}
	
	public static double getRandomDeviation()
	{
		if (globalRandom == null)
			globalRandom = new Random();
		
		return globalRandom.nextGaussian();
	}
	
	public static Random getGlobalRandom()
	{
		if (globalRandom == null)
			globalRandom = new Random();
		
		return globalRandom;
	}
}
