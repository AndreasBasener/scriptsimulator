package org.livingplace.scriptsimulator.gui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.PeriodicEntry;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class PeriodicComposite extends Composite implements ScriptableFactory
{

	private Spinner	periodSpinner;
	private Spinner	repeatSpinner;

	public PeriodicComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);

		GridData griddata;

		Label label = new Label(this,
								SWT.NONE);
		label.setText("Periodenlänge [ms]");
		griddata = new GridData();
		griddata.widthHint = 120;
		label.setLayoutData(griddata);

		periodSpinner = new Spinner(this,
									SWT.NONE);
		periodSpinner.setSelection(1000);
		periodSpinner.setMaximum(10000000);
		periodSpinner.setMinimum(0);
		griddata = new GridData();
		periodSpinner.setLayoutData(griddata);

		Label repeatLabel = new Label(	this,
										SWT.NONE);
		repeatLabel.setText("Wiederholungen");
		repeatLabel.setLayoutData(new GridData());

		repeatSpinner = new Spinner(this,
									style);
		repeatSpinner.setMinimum(0);
		repeatSpinner.setMaximum(10000000);
		repeatSpinner.setSelection(0);
		periodSpinner.setLayoutData(new GridData());

	}

	public int getPeriodValue()
	{
		return periodSpinner.getSelection();
	}

	public void setPeriodValue(int period)
	{
		periodSpinner.setSelection(period);
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		return new PeriodicEntry(	comp.getOffset(),
									comp.getEntryName(),
									comp.getDescription(),
									this.getPeriodValue(),
									this.repeatSpinner.getSelection());
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

		PeriodicEntry entry = (PeriodicEntry) scriptable;
		this.setPeriodValue(entry.getPeriod());
	}

}
