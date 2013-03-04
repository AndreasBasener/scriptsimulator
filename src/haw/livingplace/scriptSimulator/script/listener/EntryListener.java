package haw.livingplace.scriptSimulator.script.listener;

import haw.livingplace.scriptSimulator.Deviation;
import haw.livingplace.scriptSimulator.script.entry.EntryEvent;

import java.util.EventListener;

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
