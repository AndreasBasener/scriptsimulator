package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.MoodEntry;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SLMoodEntryConverter implements JsonSerializer<MoodEntry>,
												JsonDeserializer<MoodEntry>{

	@Override
	public MoodEntry deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		int moodid = object.get("moodID").getAsInt();
		String topicName = object.get("topicName").getAsString();

		MoodEntry entry = new MoodEntry(	offset,
											name,
											descr,
											moodid,
											topicName);

		return entry;
	}

	@Override
	public JsonElement serialize(MoodEntry src, Type typeOfSrc,
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
		object.addProperty(	"moodID",
							src.getMoodID());
		object.addProperty(	"topicName",
							src.getTopicName());

		return object;
	}

}
