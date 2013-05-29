package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.MoodEntryListener;

import com.google.gson.Gson;

public class MoodEntry extends ScriptEntry{

	/**
	 * 
	 */
	private static final long serialVersionUID = 44344001912000494L;

	private int moodID;
	private String topicName;
	
	public MoodEntry(int moodID, String topicName)
	{
		this.moodID = moodID;
		if(topicName.equals("")||topicName == null)
			topicName = "defaultMoodTopic";
		this.topicName = topicName;
	}
	
	public MoodEntry(Period offset, String name, String descr, int moodID, String topicName)
	{
		super(offset,name,descr);
		this.moodID = moodID;
		if(topicName.equals("")||topicName == null)
			topicName = "defaultMoodTopic";
		this.topicName = topicName;
	}
	
//	@Override
//	public void run()
//	{
//		
//	}
	
	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() > 0)
			return;
			
		this.addEntryListener(new MoodEntryListener(activeMQip,
															mongoDBip,
															gson,
															topicName));
	}

	/**
	 * @return the moodID
	 */
	public int getMoodID() {
		return moodID;
	}

	/**
	 * @param moodID the moodID to set
	 */
	public void setMoodID(int moodID) {
		this.moodID = moodID;
	}

	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
