package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.MoodEntry;

public class MoodComposite extends Composite implements ScriptableFactory{

	private Spinner moodIDSpinner;
	private Text topicText;
	
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

		Label topicLabel = new Label(this, SWT.NONE);
		topicLabel.setText("Topicname");
		topicLabel.setLayoutData(new GridData());
		
		topicText = new Text(this, SWT.NONE);
		topicText.setText("DefaultTopicName");
		topicText.setLayoutData(new GridData());
		
		this.setLayout(gridLayout);
	}

	@Override
	public Scriptable generateScriptable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp) {
		return new MoodEntry(comp.getOffset(),
								comp.getEntryName(),
								comp.getDescription(),
								moodIDSpinner.getSelection(), 
								topicText.getText());
	}

	@Override
	public void setToScriptable(Scriptable scriptable) {
		// TODO Auto-generated method stub
		
	}

}
