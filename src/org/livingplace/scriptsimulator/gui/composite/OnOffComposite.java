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
import org.livingplace.scriptsimulator.script.entry.OnOffEntry;
import org.livingplace.scriptsimulator.script.entry.OnOffEntry.OnOffAction;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class OnOffComposite extends Composite implements ScriptableFactory
{

	private Spinner	spinner;

	private Combo	actionCombo;

	public OnOffComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);
		GridData griddata;

		Label label = new Label(this,
								SWT.NONE);
		label.setText("Dauer [ms]");
		griddata = new GridData();
		griddata.widthHint = 120;
		label.setLayoutData(griddata);

		spinner = new Spinner(	this,
								SWT.NONE);
		spinner.setSelection(0);
		spinner.setMaximum(10000000);
		griddata = new GridData();
		spinner.setLayoutData(griddata);

		Label actionLabel = new Label(	this,
										SWT.NONE);
		actionLabel.setText("Aktion");
		actionLabel.setLayoutData(new GridData());

		actionCombo = new Combo(this,
								SWT.DROP_DOWN | SWT.SINGLE | SWT.READ_ONLY);
		for (OnOffAction a : OnOffAction.values())
		{
			actionCombo.add(a.name());
		}
		actionCombo.select(0);
		actionCombo.setLayoutData(new GridData());
	}

	public int getDurationValue()
	{
		return spinner.getSelection();
	}

	public void setDurationValue(int duration)
	{
		spinner.setSelection(duration);
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		String action = this.actionCombo.getText();
		OnOffAction onoffaction = OnOffAction.valueOf(action);
		return new OnOffEntry(	comp.getOffset(),
								comp.getEntryName(),
								comp.getDescription(),
								this.getDurationValue(),
								onoffaction);
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

		OnOffEntry entry = (OnOffEntry) scriptable;
		this.setDurationValue(entry.getDuration());
	}

}
