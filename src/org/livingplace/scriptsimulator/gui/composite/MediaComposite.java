package org.livingplace.scriptsimulator.gui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.MediaEntry;

public class MediaComposite extends Composite implements ScriptableFactory{

	private Text urlText;
	
	public MediaComposite(Composite arg0, int arg1) {
		super(arg0, arg1);
		GridLayout gridLayout = new GridLayout(	2,
				false);
		gridLayout.marginWidth = 0;
		
		Label topicLabel = new Label(this, SWT.NONE);
		topicLabel.setText("URL");
		topicLabel.setLayoutData(new GridData());
		
		urlText = new Text(this, SWT.NONE);
		urlText.setText(Helper.MEDIA_ENTRY_TOPIC_NAME);
		urlText.setLayoutData(new GridData());
		
		this.setLayout(gridLayout);
	}

	@Override
	public Scriptable generateScriptable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp) {
		return new MediaEntry(comp.getOffset(),
								comp.getEntryName(),
								comp.getDescription(),
								urlText.getText());
	}

	@Override
	public void setToScriptable(Scriptable scriptable) {
		MediaEntry entry = (MediaEntry) scriptable;
		
		this.urlText.setText(entry.getUrl());
		
	}

	
	
}
