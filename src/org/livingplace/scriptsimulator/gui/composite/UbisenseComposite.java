package org.livingplace.scriptsimulator.gui.composite;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.UbisenseEntry;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseComposite extends Composite implements ScriptableFactory
{

	protected Text	filename;

	public UbisenseComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);

		GridData griddata;

		Button openButton = new Button(	this,
										SWT.PUSH);
		openButton.setText("Open csv");
		openButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog fd = new FileDialog(	Display.getDefault().getActiveShell(),
												SWT.OPEN);
				fd.setText("Open");
				String[] filter = { "*.csv", "*.txt" };
				fd.setFilterExtensions(filter);
				String file = fd.open();
				if (file != null)
					filename.setText(file);

			}
		});
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		openButton.setLayoutData(griddata);

		Label fileLabel = new Label(this,
									SWT.NONE);
		fileLabel.setText("Filename");
		griddata = new GridData();
		fileLabel.setLayoutData(griddata);

		filename = new Text(this,
							SWT.SINGLE | SWT.BORDER);
		filename.setText("na");
		filename.setEditable(false);
		griddata = new GridData();
		griddata.grabExcessHorizontalSpace = true;
		griddata.horizontalAlignment = SWT.FILL;
		filename.setLayoutData(griddata);
	}

	public String getFileName()
	{
		return filename.getText();
	}

	public void setFilename(String file)
	{
		filename.setText(file);
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		return new UbisenseEntry(	comp.getOffset(),
									comp.getEntryName(),
									comp.getDescription(),
									this.getFileName());
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
		UbisenseEntry entry = (UbisenseEntry) scriptable;
		this.filename.setText(entry.getFileName());
	}

}
