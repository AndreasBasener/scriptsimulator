package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.MediaEntry;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SLMediaEntryConverter implements JsonSerializer<MediaEntry>,
												JsonDeserializer<MediaEntry>{

	@Override
	public MediaEntry deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		String url = object.get("url").getAsString();

		MediaEntry entry = new MediaEntry(	offset,
											name,
											descr,
											url);

		return entry;
	}

	@Override
	public JsonElement serialize(MediaEntry src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject object = new JsonObject();

		object.addProperty(	"type",
							src.getClass().getName());
		object.addProperty(	"offset",
							src.getOffset().toStandardDuration().getMillis());
		object.addProperty(	"name",
							src.getName());
		object.addProperty(	"description",
							src.getDescription());
		object.addProperty(	"url",
							src.getUrl());

		return object;
	}

}
