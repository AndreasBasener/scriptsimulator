package haw.livingplace.scriptSimulator.script.json.savaload;

import java.lang.reflect.Type;

import org.joda.time.Period;

import haw.livingplace.scriptSimulator.script.entry.SingleEntry;

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
public class SLSingleEntryConverter implements JsonSerializer<SingleEntry>,
		JsonDeserializer<SingleEntry>
{

	@Override
	public SingleEntry deserialize(	JsonElement element,
									Type type,
									JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = element.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		return new SingleEntry(	offset,
								name,
								descr);
	}

	@Override
	public JsonElement serialize(SingleEntry src, Type type, JsonSerializationContext context)
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

		return object;
	}

}
