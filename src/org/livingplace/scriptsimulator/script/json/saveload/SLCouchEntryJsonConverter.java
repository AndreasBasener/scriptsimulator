package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.CouchEntry;
import org.livingplace.scriptsimulator.script.entry.CouchEntry.CouchID;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SLCouchEntryJsonConverter implements JsonSerializer<CouchEntry>,
JsonDeserializer<CouchEntry>{

	@Override
	public CouchEntry deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();

		Period offset = new Period(object.get("offset").getAsLong());
		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		CouchID id = CouchID.valueOf(object.get("couchid").getAsString());

		CouchEntry entry = new CouchEntry(	offset,
											name,
											descr,
											id);

		return entry;
	}

	@Override
	public JsonElement serialize(CouchEntry src, Type typeOfSrc,
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
		object.addProperty(	"couchid",
							src.getCouchID().name());

		return object;
	}

}
