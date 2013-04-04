package org.livingplace.scriptsimulator.script.listener;


import java.util.EventListener;

import org.livingplace.scriptsimulator.Deviation;
import org.livingplace.scriptsimulator.script.entry.EntryEvent;

/**
 * 
 * @author Andreas Basener
 * 
 */
public interface EntryListener extends EventListener
{

	public void entryEvent(EntryEvent event);
	public void entryEvent(EntryEvent event, Deviation deviation);

	public void disconnect();

}
