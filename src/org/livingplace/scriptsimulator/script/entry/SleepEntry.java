package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.BedEntry.SleepState;
import org.livingplace.scriptsimulator.script.listener.SleepEntryListener;

import com.google.gson.Gson;

public class SleepEntry extends ScriptEntry{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2658063094734554515L;
	
	private int duration;
	
	private SleepState currentState;

	public SleepEntry(Period offset, String name, String descr, int duration)
	{
		this.duration = duration;
		currentState = SleepState.WACH;
	}
	
	public void run()
	{
		int sleeptime = 0;
		int sleepleft = duration * 60000;
		SleepState next = null;
		
		int phase = 0;
		
		terminate = false;
		while(!terminate)
		{
			switch (phase) {
			case 0:
				next = SleepState.N1;
				sleeptime = 10 + Helper.getRandomInt(10);
				phase = 1;
				break;
			case 1:
				next = SleepState.N2;
				sleeptime = 10 + Helper.getRandomInt(5);
				phase = 2;
				break;
			case 2:
				next = SleepState.N3;
				sleeptime = 10 + Helper.getRandomInt(5);
				phase = 3;
				break;
			case 3:
				next = SleepState.N4;
				sleeptime = 10 + Helper.getRandomInt(10);
				phase = 4;
				break;
			case 4:
				next = SleepState.N3;
				sleeptime = 5 + Helper.getRandomInt(5);
				phase = 5;
				break;
			case 5:
				next = SleepState.N2;
				sleeptime = 5 + Helper.getRandomInt(5);
				phase = 6;
				break;
			case 6:
				next = SleepState.REM;
				sleeptime = 10 + Helper.getRandomInt(10);
				phase = 1;
				break;

			default:
				break;
			}
			
			sleepleft -= sleeptime * 60000;
			if(sleepleft > 0)
			{
				currentState = next;
				try {
					if(speed > 0)
						Thread.sleep((sleeptime * 60000) / speed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else
			{
				currentState = SleepState.WACH;
				terminate = true;
			}
			notifyListeners(new EntryEvent(this));
		}
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() > 0)
			return;
			
		this.addEntryListener(new SleepEntryListener(	activeMQip,
															mongoDBip,
															gson));

	}
	
	/**
	 * @return the currentState
	 */
	public SleepState getCurrentState() {
		return currentState;
	}

	/**
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(SleepState currentState) {
		this.currentState = currentState;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
