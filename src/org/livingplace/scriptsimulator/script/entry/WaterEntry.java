package org.livingplace.scriptsimulator.script.entry;


import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.WaterEntryListener;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 *
 */
public class WaterEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5860229802063801585L;
	
	/**
	 * IDs of available faucets in the LivingPlace.
	 * @author Andreas Basener
	 *
	 */
	public enum WaterID
	{
		BATH_SINK_COLD,
		BATH_SINK_HOT,
		BATH_SHOWER_COLD,
		BATH_SHOWER_HOT,
		BATH_TOILET_COLD,
		KITCHEN_SINK_COLD,
		KITCHEN_SINK_HOT
	}
	
	/**
	 * Faucets can either be turned on or off.
	 * @author Andreas Basener
	 *
	 */
	public enum WaterState
	{
		ON,
		OFF
	}
	
	/**
	 * <code>WaterID</code> of this <code>WaterEntry</code>.
	 */
	private WaterID waterID;
	/**
	 * <code>WaterState</code> of this <code>WaterEntry</code>.
	 */
	private WaterState waterState;
	
	public WaterEntry(Period offset, String name, String description,
	                  WaterID id, WaterState state)
	{
		super(offset,name,description);
		this.waterID = id;
		this.waterState = state;
	}
	
	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new WaterEntryListener(activeMQip,
															mongoDBip,
															gson));
	}

	/**
	 * @return the waterID
	 */
	public synchronized WaterID getWaterID()
	{
		return waterID;
	}

	/**
	 * @param waterID the waterID to set
	 */
	public synchronized void setWaterID(WaterID waterID)
	{
		this.waterID = waterID;
	}

	/**
	 * @return the waterState
	 */
	public synchronized WaterState getWaterState()
	{
		return waterState;
	}

	/**
	 * @param waterState the waterState to set
	 */
	public synchronized void setWaterState(WaterState waterState)
	{
		this.waterState = waterState;
	}

}
