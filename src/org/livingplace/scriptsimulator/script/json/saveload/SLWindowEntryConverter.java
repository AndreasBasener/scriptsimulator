package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.WindowEntry;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowAction;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowID;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowSpeed;


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
public class SLWindowEntryConverter implements JsonSerializer<WindowEntry>,
		JsonDeserializer<WindowEntry>
{

	@Override
	public WindowEntry deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		String winID = object.get("winID").getAsString();
		String windowSpeed = object.get("windowSpeed").getAsString();
//		int position = object.get("position").getAsInt();
//		int endPosition = object.get("endPosition").getAsInt();
		String action = object.get("windowAction").getAsString();

		return new WindowEntry(	offset,
								name,
								descr,
								WindowID.valueOf(winID),
								WindowSpeed.valueOf(windowSpeed),
								WindowAction.valueOf(action));
	}

	@Override
	public JsonElement serialize(WindowEntry src, Type type, JsonSerializationContext context)
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

		object.addProperty(	"winID",
							src.getWinID().name());
		object.addProperty(	"windowSpeed",
							src.getWindowSpeed().name());
//		object.addProperty(	"position",
//							src.getPosition());
//		object.addProperty(	"endPosition",
//							src.getEndPosition());
		object.addProperty(	"windowAction",
							src.getWindowAction().name());

		return object;
	}

}
