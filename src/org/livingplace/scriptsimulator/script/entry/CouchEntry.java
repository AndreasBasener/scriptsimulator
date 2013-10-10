package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.listener.CouchEntryListener;
import org.livingplace.scriptsimulator.script.listener.writerlistener.CouchWriterListener;

import com.google.gson.Gson;

public class CouchEntry extends ScriptEntry{

	public enum CouchID
	{
		c00,c01,
		c10,c11,
		c20,c21,
		c30,c31,
		c40,c41,c42,c43,
		c50,c51,c52,c53,
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1931055037302822287L;

	private CouchID couchID;
	
	public CouchEntry(Period offset, String name, String description, CouchID id)
	{
		super(offset,name,description);
		this.couchID = id;
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() > 0)
			return;
			
		this.addEntryListener(new CouchEntryListener(activeMQip,
														mongoDBip,
														gson));
		this.addEntryListener(new CouchWriterListener());
	}
	
	/**
	 * @return the couchID
	 */
	public CouchID getCouchID() {
		return couchID;
	}

	/**
	 * @param couchID the couchID to set
	 */
	public void setCouchID(CouchID couchID) {
		this.couchID = couchID;
	}
	
}
