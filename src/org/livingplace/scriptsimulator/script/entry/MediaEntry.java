package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;
import com.google.gson.Gson;

public class MediaEntry extends ScriptEntry{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5610732348187827137L;

	private String url;
	
	public MediaEntry(String url)
	{
		this.setUrl(url);
	}
	public MediaEntry(Period offset, String name, String descr, String url)
	{
		super(offset,name,descr);
		this.url = url;
	}
	
	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new EntryJsonListener(	activeMQip,
															mongoDBip,
															gson,
															Helper.MEDIA_ENTRY_TOPIC_NAME));

	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
}
