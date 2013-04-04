package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.WindowEntry;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowAction;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowID;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowSpeed;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class WindowComposite extends Composite implements ScriptableFactory
{

	protected Combo		comboID;
	protected Combo		comboWindowSpeed;
	protected Combo		comboAction;
	protected Spinner	spinnerPosition;
	protected Spinner	spinnerEndPosition;

	public WindowComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);

		GridData griddata;

		Label labelID = new Label(	this,
									SWT.NONE);
		labelID.setText("Window ID");
		griddata = new GridData();
		labelID.setLayoutData(griddata);

		comboID = new Combo(this,
							SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for (WindowID s : WindowEntry.WindowID.values())
		{
			comboID.add(s.name());
		}
		comboID.select(0);
		griddata = new GridData();
		comboID.setLayoutData(griddata);

		Label labelSpeed = new Label(	this,
										SWT.NONE);
		labelSpeed.setText("Speed");
		griddata = new GridData();
		labelSpeed.setLayoutData(griddata);

		comboWindowSpeed = new Combo(	this,
										SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for (WindowSpeed s : WindowEntry.WindowSpeed.values())
		{
			comboWindowSpeed.add(s.name());
		}
		griddata = new GridData();
		comboWindowSpeed.select(0);
		comboWindowSpeed.setLayoutData(griddata);


		Label labelAction = new Label(	this,
										SWT.NONE);
		labelAction.setText("Action");
		griddata = new GridData();
		labelAction.setLayoutData(griddata);

		comboAction = new Combo(	this,
										SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for (WindowAction a : WindowAction.values())
		{
			comboAction.add(a.name());
		}
		griddata = new GridData();
		comboAction.select(0);
		comboAction.setLayoutData(griddata);
		
//		Label labelPosition = new Label(this,
//										SWT.NONE);
//		labelPosition.setText("Position");
//		griddata = new GridData();
//		labelPosition.setLayoutData(griddata);
//
//		spinnerPosition = new Spinner(	this,
//										SWT.NONE);
//		spinnerPosition.setMinimum(0);
//		spinnerPosition.setMaximum(20);
//		griddata = new GridData();
//		spinnerPosition.setLayoutData(griddata);
//
//		Label labelEndPosition = new Label(	this,
//											SWT.NONE);
//		labelEndPosition.setText("Endposition");
//		griddata = new GridData();
//		labelEndPosition.setLayoutData(griddata);
//
//		spinnerEndPosition = new Spinner(	this,
//											SWT.NONE);
//		spinnerEndPosition.setMinimum(0);
//		spinnerEndPosition.setMaximum(20);
//		griddata = new GridData();
//		spinnerEndPosition.setLayoutData(griddata);
	}

	@Override
	public Scriptable generateScriptable()
	{
		return null;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		return new WindowEntry(	comp.getOffset(),
								comp.getEntryName(),
								comp.getDescription(),
								WindowID.valueOf(this.comboID.getText()),
								WindowSpeed.valueOf(this.comboWindowSpeed.getText()),
								WindowAction.valueOf(this.comboAction.getText()));
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		WindowEntry entry = (WindowEntry) scriptable;

		this.comboID.setText(entry.getWinID().name());
		this.comboWindowSpeed.setText(entry.getWindowSpeed().name());
//		this.spinnerPosition.setSelection(entry.getPosition());
//		this.spinnerEndPosition.setSelection(entry.getEndPosition());
		comboAction.setText(entry.getWindowAction().name());

	}

}
