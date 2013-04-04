package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.PowerEntry;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerID;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerState;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SLPowerEntryConverter implements JsonSerializer<PowerEntry>,
JsonDeserializer<PowerEntry>
{

	@Override
	public PowerEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());
		PowerID id = PowerID.valueOf(object.get("id").getAsString());
		PowerState state = PowerState.valueOf(object.get("state").getAsString());

		PowerEntry entry = new PowerEntry(offset,
												name,
												descr,
												id,
												state);

		return entry;
	}

	@Override
	public JsonElement serialize(PowerEntry src, Type typeOfSrc, JsonSerializationContext context)
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
							src.getPowerID().name());
		object.addProperty(	"state",
							src.getPowerState().name());

		return object;
	}

}
