package org.livingplace.scriptsimulator.script.entry;


import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.listener.WindowEntryListener;
import org.livingplace.scriptsimulator.script.listener.writerlistener.WindowWriterListener;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class WindowEntry extends ScriptEntry
{
	/**
	 * Possible states for windows.
	 * 
	 * @author Andreas Basener
	 * 
	 */
	public enum WindowState
	{
		OPEN, CLOSED, OPENING, CLOSING
	}
	
	/**
	 * Window action
	 * @author Andreas Basener
	 *
	 */
	public enum WindowAction
	{
		OPEN,
		CLOSE
	}

	/**
	 * Possible speeds for windows.
	 * 
	 * @author Andreas Basener
	 * 
	 */
	public enum WindowSpeed
	{
		SLOW, FAST, STOP, NSTP
	}

	/**
	 * Available IDs for windows in the LivingPlace.
	 * 
	 * @author Andreas Basener
	 * 
	 */
	public enum WindowID
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
	 * windows ID
	 */
	private WindowID			winID;

	/**
	 * windows state
	 */
	private WindowState			state;
	
	/**
	 * window action
	 */
	private WindowAction windowAction;

	/**
	 * current window position
	 */
	private int					position			= 0;

	/**
	 * end position of window
	 */
	private int					endPosition			= 0;

	/**
	 * speed of window
	 */
	private WindowSpeed			windowSpeed;
	
	private boolean allWindows = false;

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4359840563152465865L;

	public WindowEntry(Period offset, String name, String descr, WindowID winID,
						WindowSpeed speed,
						WindowAction action)
	{
		super(offset, name, descr);
		this.setName(name);
		this.setDescription(descr);
		this.setWinID(winID);
		this.setWindowSpeed(speed);
//		this.setPosition(position);
//		this.setEndPosition(endPosition);
		this.windowAction = action;

		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			this.description = winID.name();
		}
	}

	@Override
	public void run()
	{
		 // Set to default
		this.terminate = false;
		
		//Check if WIN_ALL is selected
		if (winID.equals(WindowID.WIN_ALL))
			allWindows = true;
		
		// Set windowState according to windowAction
		if(windowAction.equals(WindowAction.OPEN))
		{
			state = WindowState.OPENING;
			position = 0;
			endPosition = 20;
		}
		else if (windowAction.equals(WindowAction.CLOSE))
		{
			state = WindowState.CLOSING;
			position = 20;
			endPosition = 0;
		}
		 
		if(allWindows)
		{
			for (WindowID id : WindowID.values())
			{
				if (!id.equals(WindowID.WIN_ALL))
				{
					winID = id;
		
					 EntryEvent event = new EntryEvent(this);
					notifyListeners(event);
				}
			}
		}
		else{
			EntryEvent event = new EntryEvent(this);
			notifyListeners(event);
		}
//		
//		// Set sleeptime
//		int remaining = 0;
//		if(windowSpeed.equals(WindowSpeed.SLOW))
//			remaining = 30;
//		else if (windowSpeed.equals(WindowSpeed.FAST))
//			remaining = 10;
//		while(!terminate && remaining > 0)
//		{
//			try
//			{
//				if(speed != 0)
//					Thread.sleep(1000/speed);
//			}
//			catch (InterruptedException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			remaining--;
//		}
//		
//		if(allWindows)
//		{
//			for (WindowID id : WindowID.values())
//			{
//				if (!id.equals(WindowID.WIN_ALL))
//				{
//					winID = id;
//		
//					 EntryEvent event = new EntryEvent(this);
//					notifyListeners(event);
//				}
//			}
//		}
//		else{
//			EntryEvent event = new EntryEvent(this);
//			notifyListeners(event);
//		}
//		 
//		// Set windowState according to windowAction
//		if(windowAction.equals(WindowAction.OPEN))
//		{
//			state = WindowState.OPEN;
//		}
//		else if (windowAction.equals(WindowAction.CLOSE))
//		{
//			state = WindowState.CLOSED;
//		}
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() > 0)
			return;
			
		this.addEntryListener(new WindowEntryListener(	activeMQip,
															mongoDBip,
															gson));
		this.addEntryListener(new WindowWriterListener());
	}

	/**
	 * @return the winID
	 */
	public WindowID getWinID()
	{
		return winID;
	}

	/**
	 * @param winID
	 *            the winID to set
	 */
	public void setWinID(WindowID winID)
	{
		this.winID = winID;
	}

	/**
	 * @return the state
	 */
	public WindowState getState()
	{
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(WindowState state)
	{
		this.state = state;
	}

	/**
	 * @return the position
	 */
	public int getPosition()
	{
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position)
	{
		this.position = position;
	}

	/**
	 * @return the endPosition
	 */
	public int getEndPosition()
	{
		return endPosition;
	}

	/**
	 * @param endPosition
	 *            the endPosition to set
	 */
	public void setEndPosition(int endPosition)
	{
		this.endPosition = endPosition;
	}

	/**
	 * @return the windowSpeed
	 */
	public WindowSpeed getWindowSpeed()
	{
		return windowSpeed;
	}

	/**
	 * @param windowSpeed
	 *            the windowSpeed to set
	 */
	public void setWindowSpeed(WindowSpeed windowSpeed)
	{
		this.windowSpeed = windowSpeed;
	}

	/**
	 * @return the windowAction
	 */
	public synchronized WindowAction getWindowAction()
	{
		return windowAction;
	}

	/**
	 * @param windowAction the windowAction to set
	 */
	public synchronized void setWindowAction(WindowAction windowAction)
	{
		this.windowAction = windowAction;
	}
}
