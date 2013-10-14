package org.livingplace.scriptsimulator.script.entry;


import javax.swing.event.EventListenerList;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.exceptions.IllegalSpeedValueException;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;
import org.livingplace.scriptsimulator.script.listener.EntryListener;

import com.google.gson.Gson;

/**
 * The class ScriptEntry is the default implementation of the Scriptable interface. All more
 * specific implementations should extend this class.
 * 
 * @author Andreas Basener
 * 
 */
public class ScriptEntry implements Scriptable
{

	/**
	 * UID for serialization
	 */
	private static final long	serialVersionUID	= -347808914513665926L;

	/**
	 * global counter of all ScriptEntry instances.
	 */
	private static int			instanceCount		= 0;

	/**
	 * Message version for ActiveMQ data format.
	 */
	protected String			version				= "0.1";
	/**
	 * ID for ActiveMQ data format.
	 */
	protected String			id					= "";
	/**
	 * Offset of the ScriptEntry relative to the startdate of the Script.
	 */
	protected Period			offset;
	
	protected Period			parentOffset;
	
	/**
	 * Name of the ScriptEntry
	 */
	protected String			name;
	/**
	 * Description of the ScriptEntry
	 */
	protected String			description;
	/**
	 * Absolute startdate of the ScriptEntry.
	 */
	protected DateTime			startDate;
	/**
	 * Playback speed of the ScriptEntry.
	 */
	protected int				speed;
	/**
	 * List of EventListeners
	 */
	protected EventListenerList	listenerList;
	/**
	 * Indicates if the ScriptEntryshould terminate.
	 */
	protected Boolean			terminate			= false;
	protected Boolean			sendTime			= false;
	/**
	 * Deviation of the ScriptEntry.
	 */
	protected Deviation			deviation;

	/**
	 * Creates a new ScriptEntry instance with offset set to 0 and name and description set to "".
	 */
	public ScriptEntry()
	{
		this(new Period(), Helper.DEFAULT_ENTRY_NAME, Helper.DEFAULT_ENTRY_DESCRIPTION);
	}

	/**
	 * Creates a new ScriptEntry instance with specified offset, name and description
	 * 
	 * @param offset
	 *            offset in milliseconds
	 * @param name
	 *            name of ScriptEntry
	 * @param descr
	 *            description of the ScriptEntry
	 */
	public ScriptEntry(Period offset, String name, String descr)
	{
		this.offset = offset;
		if (name.equals(Helper.DEFAULT_ENTRY_NAME))
		{
			this.name = this.getClass().getSimpleName() + getNextInstanceCount();
		}
		else
		{
			this.name = name;
		}
		this.description = descr;
		this.listenerList = new EventListenerList();
	}

	/**
	 * Runs this ScriptEntry.
	 * 
	 * In default implementation the Script generate a single EntryEvent and terminates.
	 */
	@Override
	public void run()
	{
		terminate = false;
		EntryEvent event = new EntryEvent(this);
		this.notifyListeners(event);
	}

	/**
	 * Simple string representation of the ScriptEntry. "name - description"
	 */
	@Override
	public String toString()
	{

		return name + " - " + this.description;
	}

	/**
	 * Returns the description of this ScriptEntry
	 * 
	 * @return description
	 */
	@Override
	public synchronized String getDescription()
	{
		return description;
	}

	/**
	 * Sets the Description of this ScriptEntry
	 * 
	 * @param description
	 *            description
	 */
	@Override
	public synchronized void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Returns the offset of this ScriptEntry
	 * 
	 * @return offset
	 */
	@Override
	public synchronized Period getOffset()
	{
		return offset;
	}

	/**
	 * Sets the offset of this ScriptEntry
	 * 
	 * @param offset
	 *            offset
	 */
	@Override
	public synchronized void setOffset(Period offset)
	{
		this.offset = offset;
	}

	/**
	 * Returns the start date of this ScriptEntry
	 * 
	 * @return startDate
	 */
	public synchronized DateTime getStartDate()
	{
		return startDate;
	}

	/**
	 * Sets the start date of this ScriptEntry
	 * 
	 * @param startDate
	 *            start date
	 */
	@Override
	public synchronized void setStartDate(DateTime startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * Returns the speed of this ScriptEntry
	 * 
	 * @return speed
	 */
	@Override
	public synchronized int getSpeed()
	{
		return speed;
	}

	/**
	 * Sets the speed of this ScriptEntry
	 * 
	 * @param speed
	 */
	@Override
	public synchronized void setSpeed(int speed) throws IllegalSpeedValueException
	{
		if (speed < 0)
			throw new IllegalSpeedValueException();

		this.speed = speed;
	}

	/**
	 * Signals the thread of this ScriptEntry to terminate.
	 */
	@Override
	public void terminateEntry()
	{
		this.terminate = true;
		this.disconnectListener();
	}

	@Override
	public synchronized void stopEntry()
	{
		this.terminate = true;

	}

	/**
	 * Returns the name of this ScriptEntry
	 * 
	 * @return name
	 */
	@Override
	public synchronized String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of this ScriptEntry
	 * 
	 * @param name
	 *            name
	 */
	@Override
	public synchronized void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Adds a new EntryListener to this ScriptEntry
	 * 
	 * @param listener
	 *            listener
	 */
	@Override
	public void addEntryListener(EntryListener listener)
	{
		if (this.listenerList == null)
			this.listenerList = new EventListenerList();
		this.listenerList.add(	EntryListener.class,
								listener);
	}

	/**
	 * Removes the EntryListener from this ScriptEntry
	 * 
	 * @param listener
	 *            listener
	 */
	@Override
	public void removeEntryListener(EntryListener listener)
	{
		this.listenerList.remove(	EntryListener.class,
									listener);
	}

	/**
	 * Notifies all subscribed listeners of a new Event
	 * 
	 * @param event
	 */
	protected synchronized void notifyListeners(EntryEvent event)
	{
		for (EntryListener l : listenerList.getListeners(EntryListener.class))
		{
			if(deviation.getDeviationWeight()>0)
				l.entryEvent(event, deviation);
			else
				l.entryEvent(event);
		}
	}

	/**
	 * Compares this ScriptEntry with a given Scriptable. If this.offset in milliseconds is greater
	 * then return 1 If this.offset in milliseconds is equal then return 0 If this.offset in
	 * milliseconds is less then return -1
	 * 
	 * @param o
	 *            Scriptable to compare with
	 * @return 1,0 or -1
	 */
	@Override
	public int compareTo(Scriptable o)
	{
		Period off = o.getOffset();
		Duration dur1 = off.toStandardDuration();
		Duration dur2 = this.offset.toStandardDuration();
		if (dur2.getMillis() > dur1.getMillis())
		{
			return 1;
		}
		else if (dur2.getMillis() == dur1.getMillis())
		{
			return 0;
		}
		else if (dur2.getMillis() < dur1.getMillis())
		{
			return -1;
		}
		return 0;
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() > 0)
			return;
			
		this.addEntryListener(new EntryJsonListener(activeMQip,
														mongoDBip,
														gson));

	}

	@Override
	public synchronized void disconnectListener()
	{
		for (EntryListener listener : listenerList.getListeners(EntryListener.class))
		{
			listener.disconnect();
		}
	}

	/**
	 * Returns the number of <code>ScriptEntry</code> instances and increases it by one.
	 * @return
	 */
	private synchronized int getNextInstanceCount()
	{
		return instanceCount++;
	}


	@Override
	public long getExecutionTime() {
		long milli = startDate.getMillis();
		milli += offset.toStandardDuration().getMillis();
		milli += parentOffset.toStandardDuration().getMillis();
		
		return milli;
	}
	
	@Override
	public synchronized void setJSONVersion(String version)
	{
		this.version = version;
	}

	@Override
	public synchronized String getJSONVersion()
	{
		return this.version;
	}

	@Override
	public synchronized void setJSONId(String id)
	{
		this.id = id;
	}

	@Override
	public synchronized String getJSONId()
	{
		return this.id;
	}

	@Override
	public void setDeviation(Deviation dev)
	{
		this.deviation = dev;

	}

	@Override
	public Deviation getDeviation()
	{
		return this.deviation;
	}

	/**
	 * @return the terminate
	 */
	protected synchronized Boolean getTerminate()
	{
		return terminate;
	}

	/**
	 * @param terminate
	 *            the terminate to set
	 */
	protected synchronized void setTerminate(Boolean terminate)
	{
		this.terminate = terminate;
	}

	@Override
	public void setSendTime(boolean bool) {
		this.sendTime = bool;
	}

	@Override
	public boolean getSendTime() {
		return this.sendTime;
	}

	@Override
	public void setParentOffset(Period offset) {
		this.parentOffset = offset;
	}

	@Override
	public Period getParentOffset() {
		return parentOffset;
	}


}
