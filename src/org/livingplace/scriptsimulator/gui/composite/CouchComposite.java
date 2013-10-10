package org.livingplace.scriptsimulator.gui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.CouchEntry;
import org.livingplace.scriptsimulator.script.entry.CouchEntry.CouchID;

public class CouchComposite extends Composite implements ScriptableFactory{

	private Combo comboID;
	
	public CouchComposite(Composite parent, int style) {

		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);

		Label stateLabel = new Label(	this,
										SWT.NONE);
		stateLabel.setText("Couch ID");
		stateLabel.setLayoutData(new GridData());

		comboID = new Combo(	this,
								SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		
		for(CouchID id: CouchID.values())
		{
			comboID.add(id.name());
		}
		comboID.select(0);
		comboID.setLayoutData(new GridData());
	}

	@Override
	public Scriptable generateScriptable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp) {
		String selection = comboID.getText();
		CouchEntry entry = new CouchEntry(comp.getOffset(), 
										  comp.getEntryName(), 
										  comp.getDescription(), 
										  CouchID.valueOf(selection));
		return entry;
	}

	@Override
	public void setToScriptable(Scriptable scriptable) {
		if(!(scriptable instanceof CouchEntry))
			return;
		
		CouchEntry entry = (CouchEntry) scriptable;
		
		comboID.setText(entry.getCouchID().toString());
	}


}
