package haw.livingplace.scriptSimulator.gui.composite;

import haw.livingplace.scriptSimulator.ScriptableFactory;
import haw.livingplace.scriptSimulator.script.Scriptable;
import haw.livingplace.scriptSimulator.script.entry.TemperaturSensorEntry;

import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class TemperatureSensorComposite extends SensorComposite implements ScriptableFactory
{

	public TemperatureSensorComposite(Composite parent, int style)
	{
		super(parent, style);

		this.typeCombo.select(0);
		this.typeCombo.setEnabled(false);
		this.enableInteger();

		this.unitCombo.removeAll();
		this.unitCombo.add("°C");
		this.unitCombo.add("°F");
		this.unitCombo.add("K");
		this.unitCombo.select(0);

	}

	/**
	 * Overrides the Method from the Base SensorComposite class. Since the type of the
	 * TemperatureSensor is already set fix to "i" nothing should happen if this method is called.
	 * 
	 * @param type
	 *            is ignored
	 */
	@Override
	public void setToAvailableType(String type)
	{
		// do nothing
	}

	// @Override
	// public void setToScriptable(Scriptable scriptable) {
	// EntryComposite comp = (EntryComposite)this.getParent().getParent();
	//
	// comp.setTopComposite(scriptable.getClass().getSimpleName());
	// comp.setOffset(scriptable.getOffset());
	// comp.setEntryName(scriptable.getName());
	// comp.setdescription(scriptable.getDescription());
	//
	// }

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		return new TemperaturSensorEntry(	comp.getOffset(),
											comp.getEntryName(),
											comp.getDescription(),
											getType(),
											getValueInteger(),
											getValueBoolean(),
											getValueString(),
											getUnit());
	}

	// protected void setValue(int value)
	// {
	// valueIntegerSpinner.setSelection(value);
	// }
	@Override
	protected void setValue(Boolean value)
	{
		// do nothing
	}

	@Override
	protected void setValue(String value)
	{
		// do nothing
	}

}
