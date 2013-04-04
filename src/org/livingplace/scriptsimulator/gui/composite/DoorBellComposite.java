package org.livingplace.scriptsimulator.gui.composite;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.DoorBellEntry;


/**
 * 
 * @author Andreas Basener
 * 
 */
public class DoorBellComposite extends Composite implements ScriptableFactory
{

	public DoorBellComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);
	}

	@Override
	public Scriptable generateScriptable()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		DoorBellEntry entry = new DoorBellEntry(comp.getOffset(),
												comp.getEntryName(),
												comp.getDescription());
		return entry;
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		// TODO Auto-generated method stub

	}

}
