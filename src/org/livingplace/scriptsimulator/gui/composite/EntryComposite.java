package org.livingplace.scriptsimulator.gui.composite;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;

/**
 * The EntryComposite is a wrapper composite for the several different Composites for OnOffEntry,
 * PeriodicEntry, etc.
 * 
 * The Composite features a Combo to select the desired entry type and input fields for the
 * individual option of the entrys.
 * 
 * @author Andreas Basener
 * 
 */
public class EntryComposite extends Composite implements ScriptableFactory
{

	private HashMap<String, Composite>	compositeMap;
	/**
	 * Combo for type-selection
	 */
	private Combo						comboEntryType;

	/**
	 * Layout to stack the several entry composites
	 */
	private StackLayout					stackLayout;
	/**
	 * Composite to stack the several entry composites
	 */
	private Composite					stackComposite;
	/**
	 * Spinner for offset hours
	 */
	private Spinner						spinnerOffsetHours;
	/**
	 * Spinner for offset minutes
	 */
	private Spinner						spinnerOffsetMinutes;
	/**
	 * Spinner for offset seconds
	 */
	private Spinner						spinnerOffsetSeconds;
	/**
	 * Spinner for offset milliseconds
	 */
	private Spinner						spinnerOffsetMilliseconds;

	/**
	 * textfield for entry-description
	 */
	private Text						textDescr;
	/**
	 * textfield for entry-name
	 */
	private Text						textName;

	/**
	 * Constructor for EntryComposite
	 * 
	 * @param parent
	 * @param style
	 */
	public EntryComposite(Composite parent, int style)
	{
		super(parent, style);

		compositeMap = new HashMap<String, Composite>();

		stackLayout = new StackLayout();
		GridData gridData;

		GridLayout gridLayout = new GridLayout(	2,
												false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		this.setLayout(gridLayout);

		/* Entrytype Combo */
		Label typeLabel = new Label(this,
									SWT.NONE);
		typeLabel.setText("Entry Typ");
		gridData = new GridData();
		gridData.widthHint = 120;
		typeLabel.setLayoutData(gridData);

		comboEntryType = new Combo(	this,
									SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		comboEntryType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				setTopComposite(getComboSelection());
			}
		});
		gridData = new GridData();
		comboEntryType.setLayoutData(gridData);

		/* Name and Description */
		Label labelName = new Label(this,
									SWT.NONE);
		labelName.setText("Name");
		gridData = new GridData();
		labelName.setLayoutData(gridData);

		textName = new Text(this,
							SWT.SINGLE | SWT.BORDER);
		textName.setText(Helper.DEFAULT_ENTRY_NAME);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		textName.setLayoutData(gridData);

		Label labelDescr = new Label(	this,
										SWT.NONE);
		labelDescr.setText("Beschreibung");
		gridData = new GridData();
		labelDescr.setLayoutData(gridData);

		textDescr = new Text(	this,
								SWT.SINGLE | SWT.BORDER);
		textDescr.setText(Helper.DEFAULT_ENTRY_DESCRIPTION);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		textDescr.setLayoutData(gridData);

		/* ********** Offset ************* */
		Label labelOffset = new Label(	this,
										SWT.NONE);
		labelOffset.setText("Offset");
		gridData = new GridData();
		labelOffset.setLayoutData(gridData);

		Composite compositeOffset = new Composite(	this,
													SWT.NONE);
		compositeOffset.setLayoutData(new GridData());
		compositeOffset.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label labelOffsetHours = new Label(	compositeOffset,
											SWT.NONE);
		labelOffsetHours.setText("H:");
		labelOffsetHours.setLayoutData(new RowData());

		spinnerOffsetHours = new Spinner(	compositeOffset,
											SWT.NONE);
		spinnerOffsetHours.setSelection(0);
		spinnerOffsetHours.setMaximum(1000);
		spinnerOffsetHours.setLayoutData(new RowData());

		Label labelOffsetMinutes = new Label(	compositeOffset,
												SWT.NONE);
		labelOffsetMinutes.setText("M:");
		labelOffsetMinutes.setLayoutData(new RowData());

		spinnerOffsetMinutes = new Spinner(	compositeOffset,
											SWT.NONE);
		spinnerOffsetMinutes.setSelection(0);
		spinnerOffsetMinutes.setMaximum(59);
		spinnerOffsetMinutes.setLayoutData(new RowData());

		Label labelOffsetSeconds = new Label(	compositeOffset,
												SWT.NONE);
		labelOffsetSeconds.setText("S:");
		labelOffsetSeconds.setLayoutData(new RowData());

		spinnerOffsetSeconds = new Spinner(	compositeOffset,
											SWT.NONE);
		spinnerOffsetSeconds.setSelection(0);
		spinnerOffsetSeconds.setMaximum(59);
		spinnerOffsetSeconds.setLayoutData(new RowData());

		Label labelOffsetMilliseconds = new Label(	compositeOffset,
													SWT.NONE);
		labelOffsetMilliseconds.setText("MS:");
		labelOffsetMilliseconds.setLayoutData(new RowData());

		spinnerOffsetMilliseconds = new Spinner(compositeOffset,
												SWT.NONE);
		spinnerOffsetMilliseconds.setSelection(0);
		spinnerOffsetMilliseconds.setMaximum(999);
		spinnerOffsetMilliseconds.setLayoutData(new RowData());

		/* stackComposite */
		stackComposite = new Composite(	this,
										SWT.NONE);

		stackComposite.setLayout(stackLayout);
		// generateEntryComposites(stackComposite,SWT.NONE);
		// stackLayout.topControl = singleComposite;
		stackLayout.marginWidth = 0;

		gridData = new GridData(SWT.FILL,
								SWT.FILL,
								true,
								true);
		gridData.horizontalSpan = 2;
		gridData.minimumHeight = 50;
		gridData.horizontalAlignment = SWT.FILL;
		stackComposite.setLayoutData(gridData);

	}

	/**
	 * Sets the Combo for the Entrytype selection enabled or disabled
	 * 
	 * @param bool
	 */
	public void setcomboEntryTypeEnabled(Boolean bool)
	{
		comboEntryType.setEnabled(bool);
	}

	/**
	 * sets the composite specified in the param as the top Composite in the Stacklayout.
	 * 
	 * @param compName
	 *            simple classname of the Composite which should be set on top
	 */
	public void setTopComposite(String compName)
	{
		Composite comp = compositeMap.get(compName);

		stackLayout.topControl = comp;

		stackComposite.layout(true);
	}

	/**
	 * returns the current selected entry type
	 * 
	 * @return String
	 */
	public String getComboSelection()
	{
		return comboEntryType.getText();
	}

	/**
	 * Gets the time values from the spinners in milliseconds
	 * 
	 * @return offset
	 */
	public Period getOffset()
	{
		Period offset = new Period(	0,
									0,
									0,
									0,
									spinnerOffsetHours.getSelection(),
									spinnerOffsetMinutes.getSelection(),
									spinnerOffsetSeconds.getSelection(),
									spinnerOffsetMilliseconds.getSelection());

		return offset;
	}

	/**
	 * Sets the Spinners for hour, minute, second and millisecond acording the given param offset
	 * 
	 * @param offset
	 *            the new offset
	 */
	public void setOffset(Period offset)
	{
		spinnerOffsetHours.setSelection(offset.getHours());
		spinnerOffsetMinutes.setSelection(offset.getMinutes());
		spinnerOffsetSeconds.setSelection(offset.getSeconds());
		spinnerOffsetMilliseconds.setSelection(offset.getMillis());
	}

	/**
	 * returns the description
	 * 
	 * @return decription
	 */
	public String getDescription()
	{
		return textDescr.getText();
	}

	/**
	 * sets the entry decription-
	 * 
	 * @param descr
	 *            description
	 */
	public void setdescription(String descr)
	{
		textDescr.setText(descr);
	}

	/**
	 * returns the entry-name
	 * 
	 * @return name
	 */
	public String getEntryName()
	{
		return textName.getText();
	}

	/**
	 * sets the entry-name
	 * 
	 * @param text
	 *            name
	 */
	public void setEntryName(String text)
	{
		textName.setText(text);
	}

	/**
	 * Generates a Scriptable depending on which entry-type is selected in the entry-type Combo
	 * 
	 * @return Scriptable
	 */
	@Override
	public Scriptable generateScriptable()
	{
		Composite comp = compositeMap.get(comboEntryType.getText());

		ScriptableFactory factory = (ScriptableFactory) comp;

		Scriptable entry = factory.generateScriptable(this);

		return entry;
	}

	/**
	 * Sets the StackComposite to the given Scriptable.
	 * 
	 * @param scriptable
	 */
	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		String classname = scriptable.getClass().getSimpleName();

		ScriptableFactory sf = (ScriptableFactory) compositeMap.get(classname);

		// Set Scriptable properties in this Composite
		setOffset(scriptable.getOffset());
		textName.setText(scriptable.getName());
		textDescr.setText(scriptable.getDescription());

		comboEntryType.setText(classname);
		setTopComposite(classname);

		// Set Scriptable properties of the specific sub-Composite
		sf.setToScriptable(scriptable);
	}

	/**
	 * Adds a new <code>Scriptable</code> <code>Composite</code> to the this <code>EntryComposite</code>.
	 * @param type Type of the <code>Composite</code>
	 * @param composite the <code>Composite</code> to add
	 */
	public void addScriptableComposite(String type, Composite composite)
	{
		composite.setParent(stackComposite);
		compositeMap.put(	type,
							composite);
		comboEntryType.add(type);
		comboEntryType.select(0);
	}

	/**
	 * @return the compositeMap
	 */
	public HashMap<String, Composite> getCompositeMap()
	{
		return compositeMap;
	}

	/**
	 * @param compositeMap
	 *            the compositeMap to set
	 */
	public void setCompositeMap(HashMap<String, Composite> compositeMap)
	{
		this.compositeMap = compositeMap;
	}

	/**
	 * @return the stackComposite
	 */
	public Composite getStackComposite()
	{
		return stackComposite;
	}

	/**
	 * @param stackComposite
	 *            the stackComposite to set
	 */
	public void setStackComposite(Composite stackComposite)
	{
		this.stackComposite = stackComposite;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
