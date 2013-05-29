package org.livingplace.scriptsimulator.gui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.SleepEntry;

public class SleepComposite  extends Composite implements ScriptableFactory
{
	
	private Spinner durationSpinner;

	public SleepComposite(Composite arg0, int arg1) {
		super(arg0, arg1);


		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);
		
		Label durationlabel = new Label(this, SWT.NONE);
		durationlabel.setText("Schlafdauer [min]");
		durationlabel.setLayoutData(new GridData());
		
		durationSpinner = new Spinner(this, SWT.NONE);
		durationSpinner.setMinimum(0);
		durationSpinner.setMaximum(1440);
		durationSpinner.setLayoutData(new GridData());
	}

	@Override
	public Scriptable generateScriptable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp) {
		SleepEntry entry = new SleepEntry(	comp.getOffset(),
							comp.getEntryName(),
							comp.getDescription(),
							durationSpinner.getSelection());
		return entry;
	}

	@Override
	public void setToScriptable(Scriptable scriptable) {
		if(!(scriptable instanceof SleepEntry))
			return;
		
		durationSpinner.setSelection(((SleepEntry)scriptable).getDuration());
		
	}

}
