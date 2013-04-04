package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.StorageEntry;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageAction;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageID;

/**
 * 
 * @author Andreas Basener
 *
 */
public class StorageComposite extends Composite implements ScriptableFactory
{
	private Combo idCombo;
	private Combo actionCombo;

	public StorageComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);

		Label idLabel = new Label(this, SWT.NONE);
		idLabel.setText("ID");
		idLabel.setLayoutData(new GridData());
		

		idCombo = new Combo(this,
								SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for(StorageID id : StorageID.values())
		{
			idCombo.add(id.name());
		}
		idCombo.select(0);
		idCombo.setLayoutData(new GridData());
		
		Label actionLabel = new Label(this, SWT.NONE);
		actionLabel.setText("Action");
		actionLabel.setLayoutData(new GridData());
		
		actionCombo = new Combo(this,
								SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);

		for(StorageAction action : StorageAction.values())
		{
			actionCombo.add(action.name());
		}
		actionCombo.select(0);
		actionCombo.setLayoutData(new GridData());
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
		return new StorageEntry(comp.getOffset(), comp.getEntryName(), comp.getDescription(),
		                       StorageID.valueOf(idCombo.getText()),
		                       StorageAction.valueOf(actionCombo.getText()));
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		StorageEntry entry = (StorageEntry) scriptable;
		idCombo.setText(entry.getStorageID().name());
		actionCombo.setText(entry.getStorageAction().name());
		
	}

}
