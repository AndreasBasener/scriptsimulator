package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.script.entry.UbisenseToolsEntry;
import org.livingplace.scriptsimulator.script.entry.UbisenseToolsEntry.UbisenseToolType;


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
public class SLUbisenseToolsEntryConverter implements JsonSerializer<UbisenseToolsEntry>,
		JsonDeserializer<UbisenseToolsEntry>
{

	@Override
	public UbisenseToolsEntry deserialize(	JsonElement json,
											Type typeOfT,
											JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		Period offset = new Period(object.get("offset").getAsLong());

		UbisenseToolType type = UbisenseToolType.valueOf(object.get("toolType").getAsString());
		int x1 = object.get("x1").getAsInt();
		int y1 = object.get("y1").getAsInt();
		int x2 = object.get("x2").getAsInt();
		int y2 = object.get("y2").getAsInt();
		int radius = object.get("radius").getAsInt();
		int arc = object.get("arc").getAsInt();
		int duration = object.get("toolDuration").getAsInt();
		int speed = object.get("toolSpeed").getAsInt();
		return new UbisenseToolsEntry(	offset,
										name,
										descr,
										type,
										x1,
										y1,
										x2,
										y2,
										radius,
										arc,
										duration,
										speed);
	}

	@Override
	public JsonElement serialize(	UbisenseToolsEntry src,
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
		object.addProperty(	"toolType",
							src.getUbiToolType().name());
		object.addProperty(	"x1",
							src.getX1());
		object.addProperty(	"y1",
							src.getY1());
		object.addProperty(	"x2",
							src.getX2());
		object.addProperty(	"y2",
							src.getY2());
		object.addProperty(	"radius",
							src.getRadius());
		object.addProperty(	"arc",
							src.getArc());
		object.addProperty(	"toolDuration",
							src.getToolDuration());
		object.addProperty(	"toolSpeed",
							src.getToolSpeed());
		return object;
	}

}
