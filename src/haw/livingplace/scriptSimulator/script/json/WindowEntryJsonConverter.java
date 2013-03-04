package haw.livingplace.scriptSimulator.script.json;

import java.lang.reflect.Type;

import haw.livingplace.scriptSimulator.script.entry.WindowEntry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class WindowEntryJsonConverter implements JsonDeserializer<WindowEntry>,
		JsonSerializer<WindowEntry>
{

	@Override
	public JsonElement serialize(WindowEntry src, Type type, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();

		if(src.getSendTime()){
			long milli = src.getStartDate().getMillis();
			milli += src.getOffset().toStandardDuration().getMillis();
			object.addProperty("time", milli);
		}
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(String.valueOf(src.getPosition())));
		array.add(new JsonPrimitive(src.getWindowSpeed().name()));
		array.add(new JsonPrimitive(String.valueOf(src.getEndPosition())));
		object.add(	src.getWinID().name(),
					array);

		return object;
	}

	@Override
	public WindowEntry deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
