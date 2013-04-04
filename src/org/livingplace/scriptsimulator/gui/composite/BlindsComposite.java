package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.BlindsEntry;
import org.livingplace.scriptsimulator.script.entry.BlindsEntry.BlindsAction;
import org.livingplace.scriptsimulator.script.entry.BlindsEntry.BlindsID;

/**
 * Composite for <code>BlindsEntry</code>
 * @author Andreas Basener
 *
 */
public class BlindsComposite extends Composite implements ScriptableFactory
{
	private Combo idCombo;
	private Combo actionCombo;

	public BlindsComposite(Composite parent, int style)
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
		for(BlindsID id : BlindsID.values())
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

		for(BlindsAction action : BlindsAction.values())
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
		return new BlindsEntry(comp.getOffset(), comp.getEntryName(), comp.getDescription(),
		                       BlindsID.valueOf(idCombo.getText()),
		                       BlindsAction.valueOf(actionCombo.getText()));
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		BlindsEntry entry = (BlindsEntry) scriptable;
		idCombo.setText(entry.getBlindsID().name());
		actionCombo.setText(entry.getBlindsAction().name());
		
	}

}
