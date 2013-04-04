package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.PowerEntry;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerID;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerState;

/**
 * 
 * @author Andreas Basener
 *
 */
public class PowerComposite extends Composite implements ScriptableFactory
{

	private Combo idCombo;
	private Combo stateCombo;

	public PowerComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);
		
		Label idLabel = new Label(this, SWT.NONE);
		idLabel.setText("ID");
		idLabel.setLayoutData(new GridData());
		
		idCombo = new Combo(this,SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for(PowerID id : PowerID.values())
		{
			idCombo.add(id.name());
		}
		idCombo.select(0);
		idCombo.setLayoutData(new GridData());
		
		Label stateLabel = new Label(this, SWT.NONE);
		stateLabel.setText("State");
		stateLabel.setLayoutData(new GridData());

		stateCombo = new Combo(this,SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for(PowerState state : PowerState.values())
		{
			stateCombo.add(state.name());
		}
		stateCombo.select(0);
		stateCombo.setLayoutData(new GridData());
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
		return new PowerEntry(comp.getOffset(),
		                      comp.getEntryName(),
		                      comp.getDescription(),
		                      PowerID.valueOf(idCombo.getText()),
		                      PowerState.valueOf(stateCombo.getText()));
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		PowerEntry entry = (PowerEntry) scriptable;
		idCombo.setText(entry.getPowerID().name());
		stateCombo.setText(entry.getPowerState().name());
		
	}

}
