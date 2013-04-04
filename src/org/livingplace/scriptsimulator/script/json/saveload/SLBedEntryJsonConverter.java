package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.BedEntry;
import org.livingplace.scriptsimulator.script.entry.BedEntry.SleepState;


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
public class SLBedEntryJsonConverter implements JsonSerializer<BedEntry>,
		JsonDeserializer<BedEntry>
{

	@Override
	public BedEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		Period offset = new Period(object.get("offset").getAsLong());
		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		SleepState state = SleepState.valueOf(object.get("sleepState").getAsString());

		BedEntry entry = new BedEntry(	offset,
										name,
										descr,
										state);

		return entry;
	}

	@Override
	public JsonElement serialize(BedEntry src, Type typeOfSrc, JsonSerializationContext context)
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
		object.addProperty(	"sleepState",
							src.getSleepState().name());

		return object;
	}

}
