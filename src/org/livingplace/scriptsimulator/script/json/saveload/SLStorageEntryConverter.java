package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.StorageEntry;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageAction;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageID;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SLStorageEntryConverter implements JsonSerializer<StorageEntry>,
JsonDeserializer<StorageEntry>
{

	@Override
	public StorageEntry deserialize(JsonElement json,
									Type typeOfT,
									JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());
		StorageID id = StorageID.valueOf(object.get("id").getAsString());
		StorageAction action = StorageAction.valueOf(object.get("action").getAsString());

		StorageEntry entry = new StorageEntry(offset,
												name,
												descr,
												id,
												action);

		return entry;
	}

	@Override
	public JsonElement serialize(StorageEntry src, Type typeOfSrc, JsonSerializationContext context)
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
							src.getStorageID().name());
		object.addProperty(	"action",
							src.getStorageAction().name());

		return object;
	}

}
