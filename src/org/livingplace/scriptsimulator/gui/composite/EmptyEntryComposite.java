package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.widgets.Composite;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.SingleEntry;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class EmptyEntryComposite extends Composite implements ScriptableFactory
{

	public EmptyEntryComposite(Composite parent, int style)
	{
		super(parent, style);
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		return new SingleEntry(	comp.getOffset(),
								comp.getEntryName(),
								comp.getDescription());
	}

	@Override
	public Scriptable generateScriptable()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		// EntryComposite comp = (EntryComposite)this.getParent().getParent();
		//
		// comp.setTopComposite(scriptable.getClass().getSimpleName());
		// comp.setOffset(scriptable.getOffset());
		// comp.setEntryName(scriptable.getName());
		// comp.setdescription(scriptable.getDescription());
	}

}
