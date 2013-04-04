package org.livingplace.scriptsimulator.gui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.livingplace.scriptsimulator.ScriptableFactory;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.entry.SensorEntry;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class SensorComposite extends Composite implements ScriptableFactory
{

	protected Combo		unitCombo;
	protected Combo		typeCombo;

	protected Spinner	valueIntegerSpinner;
	protected Combo		valueBooleanCombo;
	protected Text		valueStringText;

	public SensorComposite(Composite parent, int style)
	{
		super(parent, style);

		GridLayout gridLayout = new GridLayout(	4,
												false);
		gridLayout.marginWidth = 0;

		this.setLayout(gridLayout);
		GridData griddata;

		Label labelUnit = new Label(this,
									SWT.NONE);
		labelUnit.setText("Einheit");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		labelUnit.setLayoutData(griddata);

		Label labelValueTyp = new Label(this,
										SWT.NONE);
		labelValueTyp.setText("Typ");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		labelValueTyp.setLayoutData(griddata);

		unitCombo = new Combo(	this,
								SWT.NONE);
		unitCombo.add("°C");
		unitCombo.add("Lux");
		unitCombo.add("dB");
		unitCombo.select(0);
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		unitCombo.setLayoutData(griddata);

		typeCombo = new Combo(	this,
								SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		typeCombo.add("i");
		typeCombo.add("b");
		typeCombo.add("s");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		typeCombo.setLayoutData(griddata);
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				switch (typeCombo.getSelectionIndex())
				{
				case 0:
					valueIntegerSpinner.setEnabled(true);
					valueBooleanCombo.setEnabled(false);
					valueStringText.setEnabled(false);
					break;
				case 1:
					valueIntegerSpinner.setEnabled(false);
					valueBooleanCombo.setEnabled(true);
					valueStringText.setEnabled(false);
					break;
				case 2:
					valueIntegerSpinner.setEnabled(false);
					valueBooleanCombo.setEnabled(false);
					valueStringText.setEnabled(true);
					break;
				default:
					break;
				}
			}
		});

		Label labelValueI = new Label(	this,
										SWT.NONE);
		labelValueI.setText("Value Int");
		griddata = new GridData();
		griddata.widthHint = 120;
		labelValueI.setLayoutData(griddata);

		Label labelValueB = new Label(	this,
										SWT.NONE);
		labelValueB.setText("Value Boolean");
		labelValueB.setLayoutData(new GridData());

		Label labelValueS = new Label(	this,
										SWT.NONE);
		labelValueS.setText("Value String");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		labelValueS.setLayoutData(griddata);

		valueIntegerSpinner = new Spinner(	this,
											SWT.BORDER);
		valueIntegerSpinner.setSelection(0);
		valueIntegerSpinner.setMaximum(10000000);
		griddata = new GridData();
		valueIntegerSpinner.setLayoutData(griddata);
		// valueIntegerSpinner.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e)
		// {
		// valueBooleanCombo.select(valueIntegerSpinner.getSelection()!=0 ? 1 : 0);
		// valueStringText.setText(String.valueOf(valueIntegerSpinner.getSelection()));
		// }
		// });
		valueIntegerSpinner.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e)
			{
				if (valueIntegerSpinner.isEnabled())
				{
					valueBooleanCombo.select(valueIntegerSpinner.getSelection() != 0 ? 1 : 0);
					valueStringText.setText(String.valueOf(valueIntegerSpinner.getSelection()));
				}
			}
		});

		valueBooleanCombo = new Combo(	this,
										SWT.NONE);
		valueBooleanCombo.add("false");
		valueBooleanCombo.add("true");
		valueBooleanCombo.select(0);
		valueBooleanCombo.setLayoutData(new GridData());
		// valueBooleanCombo.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e)
		// {
		// valueIntegerSpinner.setSelection(valueBooleanCombo.getSelectionIndex()==0 ? 0 : 1);
		// valueStringText.setText(valueBooleanCombo.getSelectionIndex()==0 ? "false" : "true");
		// }
		// });
		valueBooleanCombo.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e)
			{
				if (valueBooleanCombo.isEnabled())
				{
					valueIntegerSpinner.setSelection(valueBooleanCombo.getSelectionIndex() == 0	? 0
																								: 1);
					valueStringText.setText(valueBooleanCombo.getSelectionIndex() == 0	? "false"
																						: "true");
				}
			}
		});
		valueStringText = new Text(	this,
									SWT.BORDER);
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		valueStringText.setLayoutData(griddata);
		valueStringText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e)
			{
				if (valueStringText.isEnabled())
				{
					valueIntegerSpinner.setSelection(valueStringText.getText().length());
					valueBooleanCombo.select(valueStringText.getText().isEmpty() ? 0 : 1);
				}
			}
		});

		typeCombo.select(0);
		enableInteger();
	}

	public int getValueInteger()
	{
		return valueIntegerSpinner.getSelection();
	}

	public void setValueInteger(int value)
	{
		valueIntegerSpinner.setSelection(value);
	}

	public String getValueString()
	{
		return valueStringText.getText();
	}

	public void setValueString(String value)
	{
		valueStringText.setText(value);
	}

	public Boolean getValueBoolean()
	{
		return new Boolean(valueBooleanCombo.getText());
	}

	public void setValueBoolean(Boolean value)
	{
		valueBooleanCombo.setText(value.toString());
	}

	public String getUnit()
	{
		return unitCombo.getText();
	}

	public void setUnit(String unit)
	{
		unitCombo.setText(unit);
	}

	public String getType()
	{
		return typeCombo.getText();
	}

	public void setType(String type)
	{
		typeCombo.setText(type);
	}

	/**
	 * Sets the SensorComposite to the given type. E.g. if the type is "i" then the typeCombo is set
	 * to "i" and the integer input field is enabled while the other input fields are disabled. If
	 * the given type is not available in the typeCombo nothing happens.
	 * 
	 * @param type
	 */
	public void setToAvailableType(String type)
	{
		if (type.equals("i"))
		{
			typeCombo.select(0);
			enableInteger();
		}
		else if (type.equals("b"))
		{
			typeCombo.select(1);
			enableBoolean();
		}
		else if (type.equals("s"))
		{
			typeCombo.select(2);
			enableString();
		}
	}

	protected void enableInteger()
	{
		valueIntegerSpinner.setEnabled(true);
		valueBooleanCombo.setEnabled(false);
		valueStringText.setEnabled(false);
	}

	protected void enableBoolean()
	{
		valueIntegerSpinner.setEnabled(false);
		valueBooleanCombo.setEnabled(true);
		valueStringText.setEnabled(false);
	}

	protected void enableString()
	{
		valueIntegerSpinner.setEnabled(false);
		valueBooleanCombo.setEnabled(false);
		valueStringText.setEnabled(true);
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		return new SensorEntry(	comp.getOffset(),
								comp.getEntryName(),
								comp.getDescription(),
								getType(),
								getValueInteger(),
								getValueBoolean(),
								getValueString(),
								getUnit());
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

		SensorEntry entry = (SensorEntry) scriptable;

		String type = entry.getValuet();

		this.setToAvailableType(type);

		if (type.equals("i"))
		{
			setValue(entry.getValuei());
		}
		else if (type.equals("b"))
		{
			setValue(entry.getValueb());
		}
		else if (type.equals("s"))
		{
			setValue(entry.getValues());
		}
	}

	protected void setValue(int value)
	{
		valueIntegerSpinner.setSelection(value);
	}

	protected void setValue(Boolean value)
	{
		valueBooleanCombo.select(value ? 1 : 0);
	}

	protected void setValue(String value)
	{
		valueStringText.setText(value);
	}
}
