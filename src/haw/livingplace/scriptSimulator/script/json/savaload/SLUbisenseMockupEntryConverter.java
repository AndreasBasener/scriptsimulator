package haw.livingplace.scriptSimulator.script.json.savaload;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.joda.time.Period;

import haw.livingplace.scriptSimulator.script.entry.UbisenseData;
import haw.livingplace.scriptSimulator.script.entry.UbisenseMockupEntry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
public class SLUbisenseMockupEntryConverter implements JsonSerializer<UbisenseMockupEntry>,
		JsonDeserializer<UbisenseMockupEntry>
{

	@Override
	public UbisenseMockupEntry deserialize(	JsonElement json,
											Type typeOfT,
											JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		String fileName = object.get("filename").getAsString();

		UbisenseMockupEntry entry = new UbisenseMockupEntry(offset,
															name,
															descr,
															fileName);

		ArrayList<UbisenseData> data = new ArrayList<UbisenseData>();
		Gson gson = new Gson();
		if (object.get("ubisensdata").isJsonArray())
		{
			JsonArray array = object.get("ubisensdata").getAsJsonArray();
			for (JsonElement e : array)
			{
				UbisenseData ubidata = gson.fromJson(	e,
														UbisenseData.class);
				data.add(ubidata);
			}
		}
		entry.setDataList(data);

		return entry;
	}

	@Override
	public JsonElement serialize(	UbisenseMockupEntry src,
									Type typeOfSrc,
									JsonSerializationContext context)
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
		object.addProperty(	"filename",
							src.getFileName());
		Gson gson = new Gson();
		object.add(	"ubisensdata",
					gson.toJsonTree(src.getDataList()));

		return object;
	}

}
