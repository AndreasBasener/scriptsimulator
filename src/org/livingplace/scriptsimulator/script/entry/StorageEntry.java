package org.livingplace.scriptsimulator.script.entry;


import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.StorageEntryListener;

import com.google.gson.Gson;

public class StorageEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2134936199400568290L;
	
	/**
	 * ID of the storage.
	 * @author Andreas Basener
	 *
	 */
	public enum StorageID
	{
		BATH_SHELVE,
		SLEEPING_ROOM_WARDROBE,
		BATH_TOWELRACK,
		KITCHEN_SHELF,
		KITCHEN_FRIDGE,
		KITCHEN_DISHWASHER,
		KITCHEN_GARBAGE
	}
	
	/**
	 * Action of the storage.
	 * @author Andreas Basener
	 *
	 */
	public enum StorageAction
	{
		OPENED,
		CLOSED,
		OBJECT_TAKEN,
		OBJECT_PUT
	}

	/**
	 * ID of the storage.
	 */
	private StorageID storageID;
	/**
	 * Action of the storage.
	 */
	private StorageAction storageAction;
	
	/**
	 * Creates a new <code>StorageEntry</code> instance.
	 * @param offset
	 * @param name
	 * @param description
	 * @param id
	 * @param action
	 */
	public StorageEntry(Period offset, String name, String description, 
	                    StorageID id,
	                    StorageAction action)
	{
		super(offset,name,description);
		this.storageID = id;
		this.storageAction = action;
		
	}
	
	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new StorageEntryListener(activeMQip,
															mongoDBip,
															gson));
	}
	/**
	 * @return the storageID
	 */
	public synchronized StorageID getStorageID()
	{
		return storageID;
	}

	/**
	 * @param storageID the storageID to set
	 */
	public synchronized void setStorageID(StorageID storageID)
	{
		this.storageID = storageID;
	}

	/**
	 * @return the storageAction
	 */
	public synchronized StorageAction getStorageAction()
	{
		return storageAction;
	}

	/**
	 * @param storageAction the storageAction to set
	 */
	public synchronized void setStorageAction(StorageAction storageAction)
	{
		this.storageAction = storageAction;
	}
}
