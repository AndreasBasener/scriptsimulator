package haw.livingplace.scriptSimulator.script.entry;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.listener.BedEntryListener;
import org.joda.time.Period;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class BedEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6146610456037029344L;

	/**
	 * Possible Sleepstates
	 * @author Andreas Basener
	 *
	 */
	public enum SleepState
	{
		WACH, REM, NREM, N1, N2, N3, N4
	};

	/**
	 * The <code>SleepState</code> of this <code>ScriptEntry</code>.
	 */
	private SleepState	sleepState;

	/**
	 * Creates a new <code>BedEntry</code> instance.
	 * @param offset
	 * @param name
	 * @param descr
	 * @param state
	 */
	public BedEntry(Period offset, String name, String descr, SleepState state)
	{
		super(offset, name, descr);
		this.sleepState = state;
		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			this.description = sleepState.name();
		}
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new BedEntryListener(	activeMQip,
														mongoDBip,
														gson));
	}

	/**
	 * @return the sleepState
	 */
	public SleepState getSleepState()
	{
		return sleepState;
	}

	/**
	 * @param sleepState
	 *            the sleepState to set
	 */
	public void setSleepState(SleepState sleepState)
	{
		this.sleepState = sleepState;
	}

	@Override
	/**
	 * Returns the name and the state of this <code>SleepState</code>.
	 */
	public String toString()
	{
		return sleepState.name() + " " + sleepState;
	}
}
