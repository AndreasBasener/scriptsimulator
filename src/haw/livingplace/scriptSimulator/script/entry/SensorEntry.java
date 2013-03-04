package haw.livingplace.scriptSimulator.script.entry;

import org.joda.time.Period;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.listener.SensorEntryListener;
import com.google.gson.Gson;

/**
 * Parentclass for all sensortypes possible in the wireless sensornetwork of Alexander Pautz.
 * http://livingplace.informatik.haw-hamburg.de/wiki/index.php/Drahtloses_Sensornetzwerk
 * 
 * @author Andreas Basener
 * @see ScriptEntry
 * 
 */
public class SensorEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6687006340736730896L;

	/**
	 * type of the value i for integer s for string b for boolean
	 */
	protected String			valuet;
	/**
	 * integer-representation of the value
	 */
	protected int				valuei;
	/**
	 * boolean-representation of the value
	 */
	protected Boolean			valueb;
	/**
	 * string-representation of the value
	 */
	protected String			values;

	/**
	 * unit of the value e.g. °C for temperature
	 */
	protected String			unit;

	/**
	 * Constructor for SensorEntry
	 * 
	 * @param offset
	 * @param name
	 * @param descr
	 * @param valuet
	 * @param valuei
	 * @param valueb
	 * @param values
	 * @param unit
	 */
	public SensorEntry(	Period offset,
						String name,
						String descr,
						String valuet,
						int valuei,
						Boolean valueb,
						String values,
						String unit)
	{
		super(offset, name, descr);
		this.valuet = valuet;
		this.valuei = valuei;
		this.valueb = valueb;
		this.values = values;
		this.unit = unit;

		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			String s = "";
			if (valuet.equals("s"))
			{
				s = values;
			}
			else if (valuet.equals("i"))
			{
				s = valuei + "";
			}
			else if (valuet.equals("b"))
			{
				s = valueb + "";
			}
			description = s + " " + unit;
		}
	}

	/**
	 * Creates a new SensorEntry instance.
	 * @param entry
	 */
	public SensorEntry(SensorEntry entry)
	{
		this(entry.getOffset(), entry.getName(), entry.getDescription(), entry.getValuet(), entry
				.getValuei(), entry.getValueb(), entry.getValues(), entry.getUnit());
	}

	/**
	 * // * run-method for thread-execution //
	 */
	// @Override
	// public void run()
	// {
	// terminate = false;
	//
	// EntryEvent event = new EntryEvent(this);
	// notifyListeners(event);
	// }

	/**
	 * Gets the type of the SensorEntry
	 * 
	 * @return type of SensorEntry
	 */
	public String getValuet()
	{
		return valuet;
	}

	/**
	 * Sets the type of the SensorEntry
	 * 
	 * @param valuet
	 *            type of SensorEntry
	 */
	public void setValuet(String valuet)
	{
		this.valuet = valuet;
	}

	/**
	 * Gets the integer-representation of the value
	 * 
	 * @return value
	 */
	public int getValuei()
	{
		return valuei;
	}

	/**
	 * Sets the integer-value
	 * 
	 * @param valuei
	 *            value
	 */
	public void setValuei(int valuei)
	{
		this.valuei = valuei;
	}

	/**
	 * Gets the boolean-representation of the value
	 * 
	 * @return value
	 */
	public Boolean getValueb()
	{
		return valueb;
	}

	/**
	 * Sets the boolean-value
	 * 
	 * @param valueb
	 *            value
	 */
	public void setValueb(Boolean valueb)
	{
		this.valueb = valueb;
	}

	/**
	 * Gets the string-representation of the value
	 * 
	 * @return value
	 */
	public String getValues()
	{
		return values;
	}

	/**
	 * Sets the string-value
	 * 
	 * @param values
	 *            value
	 */
	public void setValues(String values)
	{
		this.values = values;
	}

	/**
	 * Gets the unit of the value
	 * 
	 * @return unit of value
	 */
	public String getUnit()
	{
		return unit;
	}

	/**
	 * Sets the unit of the value
	 * 
	 * @param unit
	 */
	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	/**
	 * Sets the integer-value
	 * 
	 * @param value
	 *            value
	 */
	public void setValue(int value)
	{
		this.setValuei(value);
	}

	/**
	 * Sets the string-value
	 * 
	 * @param value
	 *            value
	 */
	public void setValue(String value)
	{
		this.setValues(value);
	}

	/**
	 * Sets the boolean-value
	 * 
	 * @param value
	 *            value
	 */
	public void setValue(Boolean value)
	{
		this.setValueb(value);
	}

	/**
	 * Sets all values according to given integer-value
	 * 
	 * @param value
	 */
	public void setAllValue(int value)
	{
		this.valuei = value;
		this.valueb = value != 0 ? true : false;
		this.values = String.valueOf(value);
	}

	/**
	 * Sets all values according to given string-value
	 * 
	 * @param value
	 */
	public void setAllValue(String value)
	{
		this.values = value;
		this.valuei = Integer.parseInt(value);
		this.valueb = value.equals("") ? true : false;
	}

	/**
	 * Sets all values according to given boolean-value
	 * 
	 * @param value
	 */
	public void setAllValue(Boolean value)
	{
		this.valueb = value;
		this.values = String.valueOf(value);
		this.valuei = value ? 1 : 0;
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new SensorEntryListener(	activeMQip,
															mongoDBip,
															gson));
	}

}
