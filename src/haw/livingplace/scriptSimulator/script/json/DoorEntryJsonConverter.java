package haw.livingplace.scriptSimulator.script.json;

import java.lang.reflect.Type;
import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.entry.DoorEntry;
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
public class DoorEntryJsonConverter implements JsonDeserializer<DoorEntry>,
		JsonSerializer<DoorEntry>
{

	@Override
	public JsonElement serialize(DoorEntry src, Type typeOfSrc, JsonSerializationContext context)
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

		object.addProperty(	"doorID",
							src.getDoorID().name());
		object.addProperty(	"doorState",
							src.getDoorState().name());

		return object;
	}

	@Override
	public DoorEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
