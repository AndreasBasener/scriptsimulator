package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.listener.OnOffEntryListener;

import com.google.gson.Gson;

/**
 * The OnOffEntry class extends the ScriptEntry class. The OnOffEntry class generates an "on" event
 * at the start and an "off" event when the time runs out.
 * 
 * @author Andreas Basener
 * 
 */
public class OnOffEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5444604025513906242L;
	/**
	 * Default on message.
	 */
	private static final String	on					= "on";
	/**
	 * Default off message.
	 */
	private static final String	off					= "off";

	/**
	 * Time between on an off event.
	 */
	protected int				duration;

	/**
	 * Current message state
	 */
	private String				onOff				= "on";

	/**
	 * What action should be performed.
	 */
	private OnOffAction			action;
	
	private Boolean				done = false;

	/**
	 * Action of the <code>OnOffEntry</code>.
	 * @author Andreas Basener
	 *
	 */
	public enum OnOffAction
	{
		ON, OFF, ON_TO_OFF, OFF_TO_ON
	};

	/**
	 * Creates a new <code>OnOffEntry</code> instance with specified offset, name and duration.
	 * 
	 * @param offset
	 *            offset
	 * @param name
	 *            name
	 * @param duration
	 *            duration
	 */
	public OnOffEntry(Period offset, String name, String descr, int duration)
	{
		this(offset, name, descr, duration, null);
	}
	/**
	 * Creates a new <code>OnOffEntry</code> instance with specified offset, name, duration and action.
	 * 
	 * @param offset
	 *            offset
	 * @param name
	 *            name
	 * @param duration
	 *            duration
	 * @param action
	 * 			  action
	 */
	public OnOffEntry(Period offset, String name, String descr, int duration, OnOffAction action)
	{
		super(offset, name, descr);
		this.duration = duration;
		this.action = action;

		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			this.description = action.name();
		}
	}

	/**
	 * Sends an "on" event at start and an "off" event after a delay specified in {@link #duration}.
	 */
	@Override
	public void run()
	{
		if (this.action == null)
			return;

		Boolean toggle = false;

		switch (action)
		{
		case ON:
			this.onOff = on;
			break;
		case OFF:
			this.onOff = off;
			break;
		case ON_TO_OFF:
			this.onOff = on;
			toggle = true;
			break;
		case OFF_TO_ON:
			this.onOff = off;
			toggle = true;
			break;
		}
		// setOnOff("on");
		// System.out.println(this.getClass().getSimpleName() + " " + this.getOnOff());
		this.done = false;
		EntryEvent event = new EntryEvent(this);
		notifyListeners(event);

		if (!toggle)
			return;

		int remaining = 0;
		if (speed > 0)
			remaining = duration / speed;

		int step = 10;

		while (remaining > 0 && terminate == false)
		{
			try
			{
				Thread.sleep(step);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			remaining -= step;
		}
		// setOnOff("off");
		switch (action)
		{
		case ON_TO_OFF:
			this.onOff = off;
			break;
		case OFF_TO_ON:
			this.onOff = on;
			break;
		default: break;
		}
		// System.out.println(this.getClass().getSimpleName() + " " + this.getOnOff());
		this.done = true;
		event = new EntryEvent(this);
		notifyListeners(event);
	}

	/**
	 * <code>String</code>-representation of this OnOffEntry. <br />
	 * <code>offset</code> <code>description</code> duration: <code>duration</code>
	 */
	@Override
	public String toString()
	{
		return offset + " " + this.description + " duration: " + duration;
	}

	/**
	 * Returns current OnOff message.
	 * @return
	 */
	public String getOnOff()
	{
		return onOff;
	}

	/**
	 * Sets current OnOff message.
	 * @param onOff
	 */
	public void setOnOff(String onOff)
	{
		this.onOff = onOff;
	}

	/**
	 * Returns the time between the on and off event in milliseconds.
	 * @return
	 */
	public int getDuration()
	{
		return duration;
	}

	/**
	 * Sets the time between the on and off event in milliseconds.
	 * @param duration
	 */
	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	/**
	 * @return the action
	 */
	public OnOffAction getAction()
	{
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(OnOffAction action)
	{
		this.action = action;
	}
	
	public boolean isDone()
	{
		return this.done;
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new OnOffEntryListener(	activeMQip,
															mongoDBip,
															gson));

	}
}
