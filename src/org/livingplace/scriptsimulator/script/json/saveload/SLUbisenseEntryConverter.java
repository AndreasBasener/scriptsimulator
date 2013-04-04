package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.UbisenseData;
import org.livingplace.scriptsimulator.script.entry.UbisenseEntry;


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
public class SLUbisenseEntryConverter implements JsonSerializer<UbisenseEntry>,
		JsonDeserializer<UbisenseEntry>
{

	@Override
	public UbisenseEntry deserialize(	JsonElement json,
										Type typeOfT,
										JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		String fileName = object.get("filename").getAsString();

		UbisenseEntry entry = new UbisenseEntry(offset,
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
	public JsonElement serialize(UbisenseEntry src, Type typeOfSrc, JsonSerializationContext context)
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
