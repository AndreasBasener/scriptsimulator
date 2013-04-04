package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.WaterEntry;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterID;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterState;

/**
 * 
 * @author Andreas Basener
 *
 */
public class WaterComposite extends Composite implements ScriptableFactory
{
	
	private Combo idCombo;
	private Combo stateCombo;

	public WaterComposite(Composite parent, int style)
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
		for(WaterID id : WaterID.values())
		{
			idCombo.add(id.name());
		}
		idCombo.select(0);
		idCombo.setLayoutData(new GridData());
		
		Label stateLabel = new Label(this, SWT.NONE);
		stateLabel.setText("State");
		stateLabel.setLayoutData(new GridData());

		stateCombo = new Combo(this,SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for(WaterState state : WaterState.values())
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
		return new WaterEntry(comp.getOffset(),
		                      comp.getEntryName(),
		                      comp.getDescription(),
		                      WaterID.valueOf(idCombo.getText()),
		                      WaterState.valueOf(stateCombo.getText()));
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		WaterEntry entry = (WaterEntry) scriptable;
		idCombo.setText(entry.getWaterID().name());
		stateCombo.setText(entry.getWaterState().name());
		
	}

}
