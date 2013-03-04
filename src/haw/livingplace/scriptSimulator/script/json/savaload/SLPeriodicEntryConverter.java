package haw.livingplace.scriptSimulator.script.json.savaload;

import java.lang.reflect.Type;

import org.joda.time.Period;

import haw.livingplace.scriptSimulator.script.entry.PeriodicEntry;
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
public class SLPeriodicEntryConverter implements JsonSerializer<PeriodicEntry>,
		JsonDeserializer<PeriodicEntry>
{

	@Override
	public PeriodicEntry deserialize(	JsonElement json,
										Type typeOfT,
										JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		int period = object.get("period").getAsInt();
		int repeats = object.get("repeats").getAsInt();

		PeriodicEntry entry = new PeriodicEntry(offset,
												name,
												descr,
												period,
												repeats);

		return entry;
	}

	@Override
	public JsonElement serialize(PeriodicEntry src, Type typeOfSrc, JsonSerializationContext context)
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
		object.addProperty(	"period",
							src.getPeriod());
		object.addProperty(	"repeats",
							src.getRepeats());

		return object;
	}

}
