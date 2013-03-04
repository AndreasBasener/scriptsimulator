package haw.livingplace.scriptSimulator.script.json.savaload;

import java.lang.reflect.Type;

import org.joda.time.Period;

import haw.livingplace.scriptSimulator.script.entry.BlindsEntry;
import haw.livingplace.scriptSimulator.script.entry.BlindsEntry.BlindsAction;
import haw.livingplace.scriptSimulator.script.entry.BlindsEntry.BlindsID;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SLBlindsEntryConverter implements JsonSerializer<BlindsEntry>,
JsonDeserializer<BlindsEntry>
{

	@Override
	public BlindsEntry deserialize(	JsonElement json,
									Type typeOfT,
									JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());
		BlindsID id = BlindsID.valueOf(object.get("id").getAsString());
		BlindsAction action = BlindsAction.valueOf(object.get("action").getAsString());

		BlindsEntry entry = new BlindsEntry(offset,
												name,
												descr,
												id,
												action);

		return entry;
	}

	@Override
	public JsonElement serialize(BlindsEntry src, Type typeOfSrc, JsonSerializationContext context)
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
							src.getBlindsID().name());
		object.addProperty(	"action",
							src.getBlindsAction().name());

		return object;
	}

}
