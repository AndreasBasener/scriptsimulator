package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.widgets.Composite;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupEntry;

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
		UbisenseMockupEntry entry = (UbisenseMockupEntry) scriptable;
		this.filename.setText(entry.getFileName());
	}

}
