package org.livingplace.scriptsimulator.script.entry;

import org.joda.time.Period;

/**
 * Class for temperature-sensors
 * 
 * @author Andreas Basener
 * @see SensorEntry
 * 
 */
public class TemperaturSensorEntry extends SensorEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -607703875644451317L;

	/**
	 * Constructor for <code>TemperaturSensorEntry</code>
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
	public TemperaturSensorEntry(	Period offset,
									String name,
									String descr,
									String valuet,
									int valuei,
									Boolean valueb,
									String values,
									String unit)
	{
		super(offset, name, descr, valuet, valuei, valueb, values, unit);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for <code>TemperaturSensorEntry</code>. Sets the type to "i" and the unit to "°C"
	 * 
	 * @param offset
	 * @param name
	 * @param descr
	 * @param value
	 */
	public TemperaturSensorEntry(Period offset, String name, String descr, int value)
	{
		super(	offset,
				name,
				descr,
				"i",
				value,
				value != 0 ? true : false,
				String.valueOf(value),
				"°C");
	}

	/**
	 * Creates a new <code>TemperaturSensorEntry</code> instance.
	 * @param entry
	 */
	public TemperaturSensorEntry(TemperaturSensorEntry entry)
	{
		super(	entry.getOffset(),
				entry.getName(),
				entry.getDescription(),
				"i",
				entry.getValuei(),
				entry.getValueb(),
				entry.getValues(),
				"°C");
	}

}
