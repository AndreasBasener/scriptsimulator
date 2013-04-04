package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.BlindsEntryListener;

import com.google.gson.Gson;

/**
 * 
 * <code>Entry</code>-class for the window blinds.
 * 
 * @author Andreas Basener
 *
 */
public class BlindsEntry extends ScriptEntry
{
	/**
	 * ID for the <code>BlindsEntry</code>.
	 * @author Andreas Basener
	 *
	 */
	public enum BlindsID 
	{
		winDining0,
		winDining1,
		winKitchen0,
		winKitchen1,
		winLounge0,
		winLounge1,
		winLounge2,
		winLounge3,
		winLounge4,
		winBathroom,
		WIN_ALL
	}
	
	/**
	 * State for the <code>BlindEntry</code>.
	 * @author Andreas Basener
	 *
	 */
	public enum BlindsState
	{
		OPEN,
		CLOSED,
		OPENING,
		CLOSING
	}
	
	/**
	 * Action for the <code>BlindsEntry</code>.
	 * @author Andreas Basener
	 *
	 */
	public enum BlindsAction
	{
		OPEN,
		CLOSE
	}
	
	/**
	 * The ID of this <code>BlindsEntry</code>.
	 */
	private BlindsID blindsID;
	
	/**
	 * The state at which this blind should end with.
	 */
	private BlindsState blindsState;
	
	/**
	 * Defines if the blinds should open or close.
	 */
	private BlindsAction blindsAction;
	
	/**
	 * The time in seconds which the blinds need to open or close.
	 */
	private static final int BLINDS_SPEED = 10;
	
	private boolean allBlinds = false;
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7494441261913036083L;
	
	/**
	 * Creates a new <code>BlindsEntry</code> instance.
	 * @param offset
	 * @param name
	 * @param description
	 * @param id
	 * @param action
	 */
	public BlindsEntry(Period offset, String name, String description, BlindsID id,
	                   BlindsAction action)
	{
		super(offset,name,description);
		this.blindsID = id;
		this.blindsAction = action;
	}
	

	/**
	 * Runs this BlindsEntry.
	 */
	@Override
	public void run()
	{
		// set to default false
		terminate = false;
		if(blindsID.equals(BlindsID.WIN_ALL))
			allBlinds = true;
		
		// Set blindState according to blindsAction
		if(blindsAction.equals(BlindsAction.OPEN))
		{
			blindsState = BlindsState.OPENING;
		}
		else if (blindsAction.equals(BlindsAction.CLOSE))
		{
			blindsState = BlindsState.CLOSING;
		}
		
		// If blindsID equals WIN_ALL, send message for all IDs except WIN_ALL
		if(allBlinds)
		{
			for(BlindsID id : BlindsID.values())
			{
				if(!id.equals(BlindsID.WIN_ALL))
				{
					blindsID = id;
					
					EntryEvent event = new EntryEvent(this);
					this.notifyListeners(event);
				}
			}
		}
		// If blindsID is not equal to WIN_ALL, send only one massage
		else
		{
			EntryEvent event = new EntryEvent(this);
			this.notifyListeners(event);
		}
		
		// Sleep for BLINDS_SPEED seconds
		int remaining = BLINDS_SPEED;
		while(!terminate && remaining > 0)
		{
			try
			{
				if(speed != 0)
					Thread.sleep(1000/speed);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			remaining--;
		}

		// Set blindState according to blindsAction
		if(blindsAction.equals(BlindsAction.OPEN))
		{
			blindsState = BlindsState.OPEN;
		}
		else if (blindsAction.equals(BlindsAction.CLOSE))
		{
			blindsState = BlindsState.CLOSED;
		}

		// If blindsID equals WIN_ALL, send message for all IDs except WIN_ALL
		if(allBlinds)
		{
			for(BlindsID id : BlindsID.values())
			{
				if(!id.equals(BlindsID.WIN_ALL))
				{
					blindsID = id;
					
					EntryEvent event = new EntryEvent(this);
					this.notifyListeners(event);
				}
			}
		}
		// If blindsID is not equal to WIN_ALL, send only one massage
		else
		{
			EntryEvent event = new EntryEvent(this);
			this.notifyListeners(event);
		}
	}
	
	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new BlindsEntryListener(activeMQip,
															mongoDBip,
															gson));
	}

	/**
	 * @return the blindsID
	 */
	public synchronized BlindsID getBlindsID()
	{
		return blindsID;
	}

	/**
	 * @param blindsID the blindsID to set
	 */
	public synchronized void setBlindsID(BlindsID blindsID)
	{
		this.blindsID = blindsID;
	}

	/**
	 * @return the blindsState
	 */
	public synchronized BlindsState getBlindsState()
	{
		return blindsState;
	}

	/**
	 * @param blindsState the blindsState to set
	 */
	public synchronized void setBlindsState(BlindsState blindsState)
	{
		this.blindsState = blindsState;
	}


	/**
	 * @return the blindsAction
	 */
	public synchronized BlindsAction getBlindsAction()
	{
		return blindsAction;
	}


	/**
	 * @param blindsAction the blindsAction to set
	 */
	public synchronized void setBlindsAction(BlindsAction blindsAction)
	{
		this.blindsAction = blindsAction;
	}

}
