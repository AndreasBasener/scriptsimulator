package org.livingplace.scriptsimulator.script.entry;


import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.PowerEntryListener;
import org.livingplace.scriptsimulator.script.listener.writerlistener.PowerWriterListener;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 *
 */
public class PowerEntry extends ScriptEntry
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8363006666865551914L;
	
	/**
	 * ID of the Device
	 * @author Andreas Basener
	 *
	 */
	public enum PowerID
	{
		RASIERER,
		KAFFEMASCHINE,
		HERD,
		BACKOFEN,
		WORKING_PC,
		TELEVISION,
		WATER_BOILER,
		KITCHEN_DISHWASHER
	}
	
	/**
	 * Power state.
	 * @author Andreas Basener
	 *
	 */
	public enum PowerState
	{
		ON,
		OFF
	}
	
	/**
	 * ID of this device.
	 */
	private PowerID powerID;
	/**
	 * Power state.
	 */
	private PowerState powerState;

	/**
	 * Creates a new PowerEntry instance.
	 * @param offset
	 * @param name
	 * @param description
	 * @param id
	 * @param state
	 */
	public PowerEntry(Period offset, String name, String description,
	                  PowerID id,
	                  PowerState state)
	{
		super(offset,name,description);
		this.powerID = id;
		this.powerState = state;
	}
	
	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() > 0)
			return;
			
		this.addEntryListener(new PowerEntryListener(activeMQip,
															mongoDBip,
															gson));
		this.addEntryListener( new PowerWriterListener());
	}
	
//	@Override
//	public void run()
//	{
//		
//	}

	/**
	 * @return the powerID
	 */
	public synchronized PowerID getPowerID()
	{
		return powerID;
	}

	/**
	 * @param powerID the powerID to set
	 */
	public synchronized void setPowerID(PowerID powerID)
	{
		this.powerID = powerID;
	}

	/**
	 * @return the powerState
	 */
	public synchronized PowerState getPowerState()
	{
		return powerState;
	}

	/**
	 * @param powerState the powerState to set
	 */
	public synchronized void setPowerState(PowerState powerState)
	{
		this.powerState = powerState;
	}
}
