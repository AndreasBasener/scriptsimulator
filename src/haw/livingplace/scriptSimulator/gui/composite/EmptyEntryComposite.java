package haw.livingplace.scriptSimulator.gui.composite;

import haw.livingplace.scriptSimulator.ScriptableFactory;
import haw.livingplace.scriptSimulator.script.Scriptable;
import haw.livingplace.scriptSimulator.script.entry.SingleEntry;

import org.eclipse.swt.widgets.Composite;

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
