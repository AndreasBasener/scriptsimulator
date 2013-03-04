package haw.livingplace.scriptSimulator.script.json;

import java.lang.reflect.Type;
import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.entry.ScriptEntry;

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
public class ScriptEntryJsonConverter implements JsonSerializer<ScriptEntry>,
		JsonDeserializer<ScriptEntry>
{

	@Override
	public ScriptEntry deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement serialize(ScriptEntry src, Type type, JsonSerializationContext context)
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
