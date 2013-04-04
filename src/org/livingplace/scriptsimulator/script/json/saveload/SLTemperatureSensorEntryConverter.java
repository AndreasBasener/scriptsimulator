package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.TemperaturSensorEntry;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class SLTemperatureSensorEntryConverter implements JsonSerializer<TemperaturSensorEntry>,
		JsonDeserializer<TemperaturSensorEntry>
{

	@Override
	public TemperaturSensorEntry deserialize(	JsonElement json,
												Type typeOfT,
												JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		int valuei = object.get("valuei").getAsInt();
		Boolean valueb = object.get("valueb").getAsBoolean();
		String values = object.get("values").getAsString();

		String valuet = object.get("valuet").getAsString();
		String unit = object.get("unit").getAsString();

		TemperaturSensorEntry entry = new TemperaturSensorEntry(offset,
																name,
																descr,
																valuet,
																valuei,
																valueb,
																values,
																unit);
		return entry;
	}

	@Override
	public JsonElement serialize(	TemperaturSensorEntry src,
									Type typeOfSrc,
									JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();

		object.addProperty(	"type",
							src.getClass().getName());
		object.addProperty(	"offset",
							src.getOffset().toStandardDuration().getMillis());
		object.addProperty(	"name",
							src.getName());
		object.addProperty(	"description",
							src.getDescription());

		object.addProperty(	"valuetype",
							src.getValuet());
		object.addProperty(	"unit",
							src.getUnit());
		object.addProperty(	"valuei",
							src.getValuei());
		object.addProperty(	"valueb",
							src.getValueb());
		object.addProperty(	"values",
							src.getValues());

		return object;
	}

}
