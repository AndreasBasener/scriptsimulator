package org.livingplace.scriptsimulator.script.json;

import java.lang.reflect.Type;

import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.OnOffEntry;

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
public class OnOffEntryJsonConverter implements JsonDeserializer<OnOffEntry>,
		JsonSerializer<OnOffEntry>
{

	@Override
	public JsonElement serialize(OnOffEntry src, Type type, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();

		if(src.getSendTime()){
			long milli = src.getStartDate().getMillis();
			milli += src.getOffset().toStandardDuration().getMillis();
			milli += src.isDone() ? src.getDuration() : 0;
			object.addProperty("time", milli);
		}
		object.addProperty(	"version",
							src.getJSONVersion());
		object.addProperty(	"id",
							src.getJSONId() + Helper.getRandomInt());
		object.addProperty(	"name",
							src.getName());
		object.addProperty(	"onoff",
							src.getOnOff());

		return object;
	}

	@Override
	public OnOffEntry deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
