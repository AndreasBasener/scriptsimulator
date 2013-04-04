package org.livingplace.scriptsimulator.gui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.DoorEntry;
import org.livingplace.scriptsimulator.script.entry.DoorEntry.DoorID;
import org.livingplace.scriptsimulator.script.entry.DoorEntry.DoorState;


/**
 * 
 * @author Andreas Basener
 *
 */
public class DoorComposite extends Composite implements ScriptableFactory
{

	private Combo	comboDoorID;
	private Combo	comboDoorState;

	public DoorComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);
		// GridData griddata;

		Label labelDoorID = new Label(	this,
										SWT.NONE);
		labelDoorID.setText("Tür ID");
		labelDoorID.setLayoutData(new GridData());

		comboDoorID = new Combo(this,
								SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for (DoorID s : DoorEntry.DoorID.values())
		{
			comboDoorID.add(s.name());
		}
		comboDoorID.select(0);
		comboDoorID.setLayoutData(new GridData());

		Label labelDoorState = new Label(	this,
											SWT.NONE);
		labelDoorState.setText("Tür Zustand");
		labelDoorState.setLayoutData(new GridData());

		comboDoorState = new Combo(	this,
									SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for (DoorState ds : DoorState.values())
		{
			comboDoorState.add(ds.name());
		}
		comboDoorState.select(0);
		comboDoorState.setLayoutData(new GridData());
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
		return new DoorEntry(	comp.getOffset(),
								comp.getEntryName(),
								comp.getDescription(),
								DoorID.valueOf(this.comboDoorID.getText()),
								DoorState.valueOf(this.comboDoorState.getText()));
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		DoorEntry entry = (DoorEntry) scriptable;

		this.comboDoorID.setText(entry.getDoorID().name());
		this.comboDoorState.setText(entry.getDoorState().name());

	}

	/**
	 * @return the comboDoorID
	 */
	public Combo getComboDoorID()
	{
		return comboDoorID;
	}

	/**
	 * @param comboDoorID
	 *            the comboDoorID to set
	 */
	public void setComboDoorID(Combo comboDoorID)
	{
		this.comboDoorID = comboDoorID;
	}

	/**
	 * @return the comboDoorState
	 */
	public Combo getComboDoorState()
	{
		return comboDoorState;
	}

	/**
	 * @param comboDoorState
	 *            the comboDoorState to set
	 */
	public void setComboDoorState(Combo comboDoorState)
	{
		this.comboDoorState = comboDoorState;
	}

}
