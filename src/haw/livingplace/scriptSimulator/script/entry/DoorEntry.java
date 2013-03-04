package haw.livingplace.scriptSimulator.script.entry;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.listener.DoorEntryListener;
import org.joda.time.Period;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class DoorEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2480783694543153091L;

	/**
	 * State of the door.
	 * @author Andreas Basener
	 *
	 */
	public enum DoorState
	{
		OPEN, CLOSED
	};

	/**
	 * ID of the Door.
	 * @author Andreas Basener
	 *
	 */
	public enum DoorID
	{
		DOOR_ENTRANCE, DOOR_BATH
	}

	/**
	 * Current <code>DoorState</code> of the door.
	 */
	private DoorState	doorState;

	/**
	 * the ID of the door.
	 */
	private DoorID		doorID;

	/**
	 * Creates a new <code>DoorEntry</code> instance.
	 * @param offset
	 * @param name
	 * @param descr
	 * @param doorID
	 * @param state
	 */
	public DoorEntry(Period offset, String name, String descr, DoorID doorID, DoorState state)
	{
		super(offset, name, descr);
		this.doorState = state;
		this.doorID = doorID;

		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			this.description = doorID.name() + " - " + doorState.name();
		}
	}

	// @Override
	// public void run()
	// {
	//
	// }

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new DoorEntryListener(activeMQip,
														mongoDBip,
														gson));
	}

	/**
	 * @return the state
	 */
	public DoorState getDoorState()
	{
		return doorState;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setDoorState(DoorState state)
	{
		this.doorState = state;
	}

	/**
	 * @return the doorID
	 */
	public DoorID getDoorID()
	{
		return doorID;
	}

	/**
	 * @param doorID
	 *            the doorID to set
	 */
	public void setDoorID(DoorID doorID)
	{
		this.doorID = doorID;
	}

}
