package haw.livingplace.scriptSimulator.gui.composite;

import haw.livingplace.scriptSimulator.ScriptableFactory;
import haw.livingplace.scriptSimulator.script.Scriptable;
import haw.livingplace.scriptSimulator.script.entry.UbisenseMockupEntry;

import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseMockupComposite extends UbisenseComposite implements ScriptableFactory
{

	public UbisenseMockupComposite(Composite parent, int style)
	{
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		return new UbisenseMockupEntry(	comp.getOffset(),
										comp.getEntryName(),
										comp.getDescription(),
										this.getFileName());
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

		this.filename.setText("N/A");
	}

}
