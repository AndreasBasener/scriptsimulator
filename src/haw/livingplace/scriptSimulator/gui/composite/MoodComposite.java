package haw.livingplace.scriptSimulator.gui.composite;

import haw.livingplace.scriptSimulator.ScriptableFactory;
import haw.livingplace.scriptSimulator.script.Scriptable;
import haw.livingplace.scriptSimulator.script.entry.MoodEntry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class MoodComposite extends Composite implements ScriptableFactory{

	private Spinner moodIDSpinner;
	
	public MoodComposite(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;
		
		Label moodIDLabel = new Label(this, SWT.NONE);
		moodIDLabel.setText("MoodID");
		moodIDLabel.setLayoutData(new GridData());
		
		moodIDSpinner = new Spinner(this, SWT.BORDER);
		moodIDSpinner.setMinimum(0);
		moodIDSpinner.setMaximum(Integer.MAX_VALUE);
		moodIDSpinner.setLayoutData(new GridData());

		this.setLayout(gridLayout);
	}

	@Override
	public Scriptable generateScriptable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp) {
		return new MoodEntry(moodIDSpinner.getSelection());
	}

	@Override
	public void setToScriptable(Scriptable scriptable) {
		// TODO Auto-generated method stub
		
	}

}
