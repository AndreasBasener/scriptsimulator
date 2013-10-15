package org.livingplace.scriptsimulator.script;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.exceptions.IllegalSpeedValueException;
import org.livingplace.scriptsimulator.script.listener.EntryListener;

import com.google.gson.Gson;

/**
 * The <code>Script</code> class contains a list of <code>ScriptEntrys</code>
 * 
 * @author Andreas Basener
 * 
 */
public class Script implements Scriptable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3733179285467132893L;

	/**
	 * Message version for ActiveMQ data format.
	 */
	protected String			version				= "0.1";
	/**
	 * ID for ActiveMQ data format.
	 */
	protected String			id					= "";

	/**
	 * start date of this <code>Script</code>
	 */
	private DateTime			startDate;

	/**
	 * stores the <code>ScriptEntrys</code>
	 */
	private List<Scriptable>	entryList;

	/**
	 * Playback speed of this <code>Script</code>, should be positive
	 */
	private int					speed				= 1;

	private Deviation			deviation;

	/**
	 * Name of this <code>Script</code>
	 */
	private String				name				= Helper.DEFAULT_ENTRY_NAME;

	/**
	 * Description of this <code>Script</code>
	 */
	private String				description			= Helper.DEFAULT_ENTRY_DESCRIPTION;

	/**
	 * Offset of this <code>Script</code> relative to a parent <code>Script</code> if available. If
	 * no parent <code>Script</code> is available, then this field is not used.
	 */
	private Period				offset;
	
	private Period				parentOffset;

	/**
	 * Flag to signal this <code>Script</code> to stop thread-execution.
	 */
	private Boolean				terminate			= false;
	
	private Boolean				sendTime			= false;

	/**
	 * <code>List</code> of <code>EventListeners</code>
	 */
	protected EventListenerList	listenerList;

	/**
	 * Creates a new <code>Script</code> instance.
	 */
	public Script()
	{
		this(new DateTime(), "defaultName", "defaultDescription");
	}

	/**
	 * Creates a new <code>Script</code> instance with specified start date.
	 * 
	 * @param start
	 */
	public Script(DateTime start)
	{
		this(start, "defaultName", "defaultDescription");
	}

	/**
	 * Creates a new <code>Script</code> instance with specified start date and name
	 * 
	 * @param start
	 *            start date for Script
	 * @param name
	 *            scriptname
	 */
	public Script(DateTime start, String name)
	{
		this(start, name, "defaultDescription");
	}

	/**
	 * Creates a new <code>Script</code> instance with specified start date, name and description.
	 * 
	 * @param start
	 * @param name
	 * @param description
	 */
	public Script(DateTime start, String name, String description)
	{
		this(start, name, description, new Deviation(0));
	}

	/**
	 * Creates a new <code>Script</code> instance with specified start date, name, description an
	 * deviation
	 * 
	 * @param start
	 * @param name
	 * @param description
	 * @param deviation
	 */
	public Script(DateTime start, String name, String description, Deviation deviation)
	{
		this.startDate = start;
		this.name = name;
		this.description = description;
		this.deviation = deviation;

		this.entryList = new ArrayList<Scriptable>();
		this.offset = new Period();
	}

	/**
	 * Adds a new <code>ScriptEntry</code> to the entryList and reorders the list.
	 * 
	 * @param entry
	 *            new ScriptEntry to add
	 */
	public synchronized void add(Scriptable entry)
	{
		if (entry == null)
			return;

		entry.setStartDate(this.startDate);
		entry.setSpeed(this.speed);
		this.entryList.add(entry);
		this.reorderList();
	}

	/**
	 * Removes the passed <code>Scriptable</code> from the entryList.
	 * 
	 * @param entry
	 *            the <code>Scriptable</code> to be removed
	 */
	public synchronized void remove(Scriptable entry)
	{
		entry.terminateEntry();
		this.entryList.remove(entry);
	}

	/**
	 * Plays the Script<br />
	 * Starts a new <code>Thread</code> for every ScriptEntry in the entryList. Waits the specified
	 * offset in milliseconds until the <code>ScriptEntry</code> starts.<br />
	 * <code>playScript()</code> doesn't wait for the <code>ScriptEntrys</code> to terminate.
	 */
	public void playScript()
	{
		terminate = false;

		ThreadGroup threatGroup = new ThreadGroup("Entry ThreadGroup");

		DateTime startDT = new DateTime();

		System.out.println(startDate.plus(offset).toString("dd.MM.YYYY HH:mm:ss.SSS") + " Script \""+ name +"\" gestartet. Geschwindigkeit: "
							+ speed);
		for (int i = 0; i < entryList.size(); i++)
		{
			if (terminate)
			{
				System.out.println("Skript gestoppt.");
				return;
			}
			long diff = 0;
			Scriptable entry = entryList.get(i);
			entry.setStartDate(startDate.plus(offset));
			entry.setSpeed(this.speed);
			entry.setDeviation(this.deviation);
			entry.setSendTime(this.sendTime);
			entry.setParentOffset(offset);

			Thread t = new Thread(	threatGroup,
									entry,
									entry.getName());

			t.start();

			if (i + 1 < entryList.size() && speed > 0)
			{
				diff = entryList.get(i + 1).getOffset().toStandardDuration().getMillis()
						- entry.getOffset().toStandardDuration().getMillis();
				diff /= speed;
			}
			if (diff > 0)
			{
				try
				{
					Thread.sleep(diff);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

		}
		System.out.println("Alle ScriptEntrys wurden gestartet.");

		synchronized (threatGroup)
		{

			while (threatGroup.activeCount() > 0)
				try
				{
					threatGroup.wait(10);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
		}
		System.out.println("Alle Entrys sind fertig.");
		DateTime stopDT = new DateTime();

		Duration dur = new Duration(startDT,
									stopDT);
		System.out.println("Skript \""+ name +"\" beendet. Ausführungszeit: " + dur);
		
//		MessageFileWriter.flushBuffer();
	}

	/**
	 * Gets the entryList of this <code>Script</code>.
	 * 
	 * @return entryList
	 */
	public List<Scriptable> getEntryList()
	{
		return entryList;
	}

	/**
	 * Gets the start <code>DateTime</code> of this <code>Script</code>.
	 * 
	 * @return startDate
	 */
	public synchronized DateTime getStartDate()
	{
		return startDate;
	}

	/**
	 * Sets the <code>DateTime</code> of this <code>Script</code>.
	 * 
	 * @param startDate
	 *            <code>DateTime</code>
	 */
	@Override
	public synchronized void setStartDate(DateTime startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * Gets the speed at which this <code>Script</code> plays.
	 * 
	 * @return speed
	 */
	@Override
	public synchronized int getSpeed()
	{
		return speed;
	}

	/**
	 * Sets the speed at which the <code>Script</code> plays.
	 * 
	 * @param speed
	 */
	@Override
	public synchronized void setSpeed(int speed) throws IllegalSpeedValueException
	{
		if (speed < 0)
			throw new IllegalSpeedValueException();

		this.speed = speed;

		// for(Scriptable s: entryList)
		// {
		// s.setSpeed(speed);
		// }
	}

	/**
	 * @return the deviation
	 */
	@Override
	public Deviation getDeviation()
	{
		return deviation;
	}

	/**
	 * @param deviation
	 *            the deviation to set
	 */
	@Override
	public void setDeviation(Deviation deviation)
	{
		this.deviation = deviation;
	}

	/**
	 * Gets the name of this <code>Script</code>.
	 * 
	 * @return name
	 */
	@Override
	public synchronized String getName()
	{

		return name;
	}

	/**
	 * Sets the name of this <code>Script</code>.
	 * 
	 * @param name
	 */
	@Override
	public synchronized void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Clears the entryList.
	 */
	public void clear()
	{
		entryList.clear();
	}

	/**
	 * Forces the entryList to reorder itself.
	 */
	public void reorderList()
	{
		Collections.sort(entryList);
	}

	/**
	 * Plays the <code>Script</code>.
	 */
	@Override
	public void run()
	{
		playScript();
	}

	/**
	 * Defines how the <code>Script</code> is ordered in a <code>List</code>.<br />
	 * If the offset is smaller than the passed <code>Scriptable</code> this <code>Script</code> is
	 * considered greater.<br />
	 * If the offset is equal than the passed <code>Scriptable</code> this <code>Script</code> is
	 * considered smaller.<br />
	 * Else this <code>Script</code> is equal to to passed <code>Scriptable</code>.
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

	// /**
	// * Iterates through entryList and invokes <code>terminateEntry()</code> for every
	// * <code>ScriptEntry</code> in list.
	// */
	// public void stopScript()
	// {
	//
	// }

	@Override
	public void terminateEntry()
	{
		// stopScript();
		this.terminate = true;

		for (Scriptable e : entryList)
		{
			e.terminateEntry();
		}
	}

	@Override
	public synchronized void stopEntry()
	{
		this.terminate = true;
		for (Scriptable e : entryList)
		{
			e.stopEntry();
		}
	}

	@Override
	public synchronized String getDescription()
	{
		return this.description;
	}

	@Override
	public synchronized void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public synchronized Period getOffset()
	{
		return this.offset;
	}

	@Override
	public synchronized void setOffset(Period offset)
	{
		this.offset = offset;
	}

	@Override
	public void addEntryListener(EntryListener listener)
	{
		if (this.listenerList == null)
			this.listenerList = new EventListenerList();
		this.listenerList.add(	EntryListener.class,
								listener);
	}

	@Override
	public void removeEntryListener(EntryListener listener)
	{
		this.listenerList.remove(	EntryListener.class,
									listener);
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		for (Scriptable s : entryList)
		{
			s.initDefaultListener(	activeMQip,
									mongoDBip,
									gson);
		}
	}

	@Override
	public void disconnectListener()
	{
		for (Scriptable s : entryList)
		{
			s.disconnectListener();
		}
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
	public void setSendTime(boolean bool) {
		this.sendTime = bool;
		
	}

	@Override
	public boolean getSendTime() {
		return this.sendTime;
	}

	@Override
	public long getExecutionTime() {
		long milli = startDate.getMillis();
		milli += offset.toStandardDuration().getMillis();
		//milli += parentOffset.toStandardDuration().getMillis();
		
		return milli;
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
