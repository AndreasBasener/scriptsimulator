package haw.livingplace.scriptSimulator.script.entry;

import haw.livingplace.scriptSimulator.script.listener.DoorBellEntryListener;
import org.joda.time.Period;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class DoorBellEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2847866654983213961L;

	/**
	 * Creates a new <code>DoorBellEntry</code>.
	 * @param offset
	 * @param name
	 * @param descr
	 */
	public DoorBellEntry(Period offset, String name, String descr)
	{
		super(offset, name, descr);
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new DoorBellEntryListener(activeMQip,
															mongoDBip,
															gson));
	}

}
