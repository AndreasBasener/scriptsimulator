package org.livingplace.scriptsimulator.script.json;

import java.lang.reflect.Type;

import org.joda.time.DateTime;
import org.livingplace.scriptsimulator.script.entry.SensorEntry;


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
public class SensorEntryJsonConverter implements JsonDeserializer<SensorEntry>,
		JsonSerializer<SensorEntry>
{

	@Override
	public JsonElement serialize(SensorEntry src, Type type, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();

		if(src.getSendTime()){
			long milli = src.getStartDate().getMillis();
			milli += src.getOffset().toStandardDuration().getMillis();
			milli += src.getParentOffset().toStandardDuration().getMillis();
			object.addProperty("time", milli);
		}
		DateTime dt = src.getStartDate().plus(src.getOffset());
		object.addProperty(	"date",
							( (dt.getMillis() + src.getOffset().toStandardDuration().getMillis()) / 1000));
		object.addProperty(	"name",
							src.getName());
		object.addProperty(	"valuet",
							src.getValuet());
		object.addProperty(	"valuei",
							src.getValuei());
		object.addProperty(	"valueb",
							src.getValueb());
		object.addProperty(	"values",
							src.getValues());
		object.addProperty(	"unit",
							src.getUnit());
		object.addProperty(	"id",
							"");

		return object;
	}

	@Override
	public SensorEntry deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
