package org.livingplace.scriptsimulator.script;


import java.io.Serializable;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.script.exceptions.IllegalSpeedValueException;
import org.livingplace.scriptsimulator.script.listener.EntryListener;

import com.google.gson.Gson;

/**
 * The Scriptable interface extends the Runnable interface and defines methods which have to be
 * implemented by an entry for the Script class.
 * 
 * @author Andreas Basener
 * 
 */
public interface Scriptable extends Runnable, Serializable, Comparable<Scriptable>
{

	public void setJSONVersion(String version);

	public String getJSONVersion();

	public void setJSONId(String id);

	public String getJSONId();
	
	public long getExecutionTime();

	/**
	 * Signals the Scripatble Thread to terminate and to disconnect all Listeners.
	 */
	public void terminateEntry();

	/**
	 * Signals the Entry to stop the execution.
	 */
	public void stopEntry();

	/**
	 * Returns the Name of the Scriptable.
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Sets the name of the Scriptable.
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * Returns the description of the Scriptable.
	 * 
	 * @return description
	 */
	public String getDescription();

	/**
	 * Sets the description of the Scriptable.
	 * 
	 * @param description
	 */
	public void setDescription(String description);

	/**
	 * Returns the Offset of the Scriptable relativ to the startdate of the Script.
	 * 
	 * @return offset
	 */
	// public Offset getOffset();
	public Period getOffset();

	/**
	 * Sets the Offset of the Scriptable relative to the startdate of the Script.
	 * 
	 * @param offset
	 */
	// public void setOffset(Offset offset);
	public void setOffset(Period offset);

	public void setParentOffset(Period offset);
	public Period getParentOffset();
	
	/**
	 * Gets the playing speed of the Scriptable
	 * 
	 * @return speed
	 */
	public int getSpeed();

	/**
	 * Sets the playing speed of the Scriptable
	 * 
	 * @param speed
	 * @throws IllegalSpeedValueException
	 */
	public void setSpeed(int speed) throws IllegalSpeedValueException;

	/**
	 * Adds an EntryListener to the listener List.
	 * 
	 * @param listener
	 */
	public void addEntryListener(EntryListener listener);

	/**
	 * Removes the specified Entrylistener from the listener list.
	 * 
	 * @param listener
	 */
	public void removeEntryListener(EntryListener listener);

	/**
	 * Initializes the default listener and adds the listener to the listenerList
	 */
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson);

	public void disconnectListener();

	public void setStartDate(DateTime startDate);

	public void setDeviation(Deviation dev);

	public Deviation getDeviation();
	
	public void setSendTime(boolean bool);
	
	public boolean getSendTime();
}
