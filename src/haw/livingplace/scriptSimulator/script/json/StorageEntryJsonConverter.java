package haw.livingplace.scriptSimulator.script.json;

import java.lang.reflect.Type;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.script.entry.StorageEntry;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StorageEntryJsonConverter implements JsonDeserializer<StorageEntry>,
JsonSerializer<StorageEntry>
{

	@Override
	public JsonElement serialize(StorageEntry src, Type typeOfSrc, JsonSerializationContext context)
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
		object.addProperty(	"storageID",
							src.getStorageID().name());
		object.addProperty(	"action",
							src.getStorageAction().name());

		return object;
	}

	@Override
	public StorageEntry deserialize(JsonElement json,
									Type typeOfT,
									JsonDeserializationContext context) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
