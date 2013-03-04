package haw.livingplace.scriptSimulator.script.json.savaload;

import java.lang.reflect.Type;

import org.joda.time.Period;

import haw.livingplace.scriptSimulator.script.entry.OnOffEntry;
import haw.livingplace.scriptSimulator.script.entry.OnOffEntry.OnOffAction;

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
public class SLOnOffEntryConverter implements JsonSerializer<OnOffEntry>,
		JsonDeserializer<OnOffEntry>
{

	@Override
	public OnOffEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		int duration = object.get("duration").getAsInt();
		String action = object.get("action").getAsString();

		OnOffAction onoffaction = OnOffAction.valueOf(action);

		OnOffEntry entry = new OnOffEntry(	offset,
											name,
											descr,
											duration,
											onoffaction);

		return entry;
	}

	@Override
	public JsonElement serialize(OnOffEntry src, Type typeOfSrc, JsonSerializationContext context)
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
		object.addProperty(	"duration",
							src.getDuration());
		object.addProperty(	"action",
							src.getAction().name());

		return object;
	}

}
