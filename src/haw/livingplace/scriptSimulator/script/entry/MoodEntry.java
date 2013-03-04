package haw.livingplace.scriptSimulator.script.entry;

import haw.livingplace.scriptSimulator.script.listener.AlarmEntryListener;
import haw.livingplace.scriptSimulator.script.listener.MoodEntryListener;

import com.google.gson.Gson;

public class MoodEntry extends ScriptEntry{

	/**
	 * 
	 */
	private static final long serialVersionUID = 44344001912000494L;

	private int moodID;
	
	public MoodEntry(int moodID)
	{
		this.moodID = moodID;
	}
	
//	@Override
//	public void run()
//	{
//		
//	}
	
	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new MoodEntryListener(activeMQip,
															mongoDBip,
															gson));
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
}
