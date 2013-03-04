package haw.livingplace.scriptSimulator.script.json;

import java.lang.reflect.Type;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.entry.AlarmEntry;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AlarmEntryJsonConverter implements JsonSerializer<AlarmEntry>,
JsonDeserializer<AlarmEntry>
{

	@Override
	public AlarmEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement serialize(AlarmEntry src, Type typeOfSrc, JsonSerializationContext context)
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
		object.addProperty(	"description",
							src.getDescription());

		return object;
	}

}
