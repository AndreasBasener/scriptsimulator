package org.livingplace.scriptsimulator.script.json;

import java.lang.reflect.Type;

import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.SingleEntry;

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
public class SingleEntryJsonConverter implements JsonSerializer<SingleEntry>,
		JsonDeserializer<SingleEntry>
{

	@Override
	public SingleEntry deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement serialize(SingleEntry src, Type type, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();

		if(src.getSendTime()){
			long milli = src.getStartDate().getMillis();
			milli += src.getOffset().toStandardDuration().getMillis();
			object.addProperty("time", milli);
		}
		object.addProperty(	"version",
							src.getJSONVersion());
		object.addProperty(	"id",
							src.getJSONId() + Helper.getRandomInt());
		object.addProperty(	"name",
							src.getName());

		return object;
	}

}
