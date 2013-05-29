package org.livingplace.scriptsimulator.gui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Script;
import org.livingplace.scriptsimulator.script.Scriptable;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class ScriptComposite extends Composite implements ScriptableFactory
{

	private Script	script;

	private Text	filename;

	private Button	openButton;

	public ScriptComposite(Composite parent, int style)
	{
		super(parent, style);
		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);

		GridData griddata;

		openButton = new Button(this,
								SWT.PUSH);
		openButton.setText("Open Script");
		// openButton.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e)
		// {
		// FileDialog fd = new FileDialog(Display.getDefault().getActiveShell(),SWT.OPEN);
		// fd.setText("Open");
		// String[] filter = {"*.script"};
		// fd.setFilterExtensions(filter);
		// String file = fd.open();
		// if (file != null)
		// filename.setText(file);
		//
		// }
		// });
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

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
//		System.out.println("Hier" + script.getName());
		script.setOffset(comp.getOffset());
		return script;
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

		this.script = (Script) scriptable;
		this.filename.setText("N/A");
	}

	/**
	 * @return the filename
	 */
	public Text getFilename()
	{
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(Text filename)
	{
		this.filename = filename;
	}

	/**
	 * @return the script
	 */
	public Script getScript()
	{
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 */
	public void setScript(Script script)
	{
		this.script = script;
	}

	/**
	 * @return the openButton
	 */
	public Button getOpenButton()
	{
		return openButton;
	}

	/**
	 * @param openButton
	 *            the openButton to set
	 */
	public void setOpenButton(Button openButton)
	{
		this.openButton = openButton;
	}

}
