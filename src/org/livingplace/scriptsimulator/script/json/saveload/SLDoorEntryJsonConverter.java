package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.DoorEntry;
import org.livingplace.scriptsimulator.script.entry.DoorEntry.DoorID;
import org.livingplace.scriptsimulator.script.entry.DoorEntry.DoorState;


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
public class SLDoorEntryJsonConverter implements JsonDeserializer<DoorEntry>,
		JsonSerializer<DoorEntry>
{

	@Override
	public JsonElement serialize(DoorEntry src, Type typeOfSrc, JsonSerializationContext context)
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

		object.addProperty(	"doorID",
							src.getDoorID().name());
		object.addProperty(	"doorState",
							src.getDoorState().name());

		return object;
	}

	@Override
	public DoorEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{

		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		String doorID = object.get("doorID").getAsString();
		String doorState = object.get("doorState").getAsString();

		DoorEntry entry = new DoorEntry(offset,
										name,
										descr,
										DoorID.valueOf(doorID),
										DoorState.valueOf(doorState));

		return entry;
	}

}
