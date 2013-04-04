package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.listener.PeriodicEntryListener;

import com.google.gson.Gson;

/**
 * The PeriodicEntry class extends the ScriptEntry class. The PeriodicEntry class offers
 * functionalities for periodic Events.
 * 
 * @author Andreas Basener
 * 
 */
public class PeriodicEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8461348796962064579L;

	/**
	 * Time between two messages.
	 */
	protected int				period;
	/**
	 * Indicates how often the <code>PriodicEntry</code> generates a new <code>EntryEvent</code>
	 * .</br> A value of 0 means infinite repetitions.
	 */
	protected int				repeats;

	/**
	 * Indicates how often the <code>PeriodicEntry</code> had already generated a new
	 * <code>EntryEvent</code>.
	 */
	private int					count				= 0;

	/**
	 * Creates a new PeriodicEntry instance with specified offset, name and period
	 * 
	 * @param offset
	 *            offset
	 * @param name
	 *            name
	 * @param period
	 *            period
	 */
	public PeriodicEntry(Period offset, String name, String descr, int period)
	{
		this(offset, name, descr, period, 0);
	}
	/**
	 * Creates a new PeriodicEntry instance with specified offset, name, period and repeats
	 * 
	 * @param offset
	 *            offset
	 * @param name
	 *            name
	 * @param period
	 *            period
	 * @param repeats
	 * 			  repeats
	 */
	public PeriodicEntry(Period offset, String name, String descr, int period, int repeats)
	{
		super(offset, name, descr);
		this.period = period;
		this.repeats = repeats;

		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			this.description = "Period: " + period + " Repeats: " + repeats;
		}
	}

	/**
	 * Creates periodic Events
	 */
	@Override
	public void run()
	{
		terminate = false;

		System.out.println(this.getClass().getSimpleName() + " gestartet");

		while (!terminate && (count < repeats))
		{
			// System.out.println(this.toString());
			EntryEvent event = new EntryEvent(this);
			notifyListeners(event);
			count++;
			try
			{
				if (speed > 0)
					Thread.sleep(period / speed);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		// System.out.println("PeriodicEntry terminiert / fertig");
	}

	@Override
	public String toString()
	{
		return offset + " " + this.description;
	}

	/**
	 * Returns the period in milliseconds at which the events happen. 
	 * @return
	 */
	public int getPeriod()
	{
		return period;
	}

	/**
	 * Sets the period in milliseconds at which the events happen.
	 * @param period
	 */
	public void setPeriod(int period)
	{
		this.period = period;
	}

	/**
	 * @return the repeats
	 */
	public int getRepeats()
	{
		return repeats;
	}

	/**
	 * @param repeats
	 *            the repeats to set
	 */
	public void setRepeats(int repeats)
	{
		this.repeats = repeats;
	}

	/**
	 * @return the count
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count)
	{
		this.count = count;
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new PeriodicEntryListener(activeMQip,
															mongoDBip,
															gson));

	}

}
