package haw.livingplace.scriptSimulator.gui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import haw.livingplace.scriptSimulator.ScriptableFactory;
import haw.livingplace.scriptSimulator.script.Scriptable;
import haw.livingplace.scriptSimulator.script.entry.BedEntry;
import haw.livingplace.scriptSimulator.script.entry.BedEntry.SleepState;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class BedComposite extends Composite implements ScriptableFactory
{

	private Combo	stateCombo;

	public BedComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);

		Label stateLabel = new Label(	this,
										SWT.NONE);
		stateLabel.setText("Schlafzustand");
		stateLabel.setLayoutData(new GridData());

		stateCombo = new Combo(	this,
								SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for (SleepState s : SleepState.values())
		{
			stateCombo.add(s.name());
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
		String selection = this.stateCombo.getText();
		BedEntry entry = new BedEntry(	comp.getOffset(),
										comp.getEntryName(),
										comp.getDescription(),
										SleepState.valueOf(selection));
		return entry;
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		// TODO Auto-generated method stub

	}

}
