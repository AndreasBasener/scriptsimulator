package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.WaterEntry;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterID;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterState;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SLWaterEntryConverter implements JsonSerializer<WaterEntry>,
JsonDeserializer<WaterEntry>
{

	@Override
	public WaterEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());
		WaterID id = WaterID.valueOf(object.get("id").getAsString());
		WaterState state = WaterState.valueOf(object.get("state").getAsString());

		WaterEntry entry = new WaterEntry(offset,
												name,
												descr,
												id,
												state);

		return entry;
	}

	@Override
	public JsonElement serialize(WaterEntry src, Type typeOfSrc, JsonSerializationContext context)
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
		object.addProperty(	"id",
							src.getWaterID().name());
		object.addProperty(	"state",
							src.getWaterState().name());

		return object;
	}

}
