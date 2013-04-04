package org.livingplace.scriptsimulator.script.json;

import java.lang.reflect.Type;

import org.livingplace.scriptsimulator.Point3D;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class Point3DJsonConverter implements JsonDeserializer<Point3D>,
												JsonSerializer<Point3D>{

	@Override
	public JsonElement serialize(Point3D src, Type typeOfSrc,
									JsonSerializationContext context) 
	{
		JsonObject object = new JsonObject();
		
		object.addProperty("X", src.getX());
		object.addProperty("Y", src.getY());
		object.addProperty("Z", src.getZ());
		
		return object;
	}

	@Override
	public Point3D deserialize(JsonElement json, Type typeOfT,
								JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		double x,y,z;
		
		x = object.get("X").getAsDouble();
		y = object.get("Y").getAsDouble();
		z = object.get("Z").getAsDouble();
		
		Point3D point = new Point3D(x, y, z);
		
		return point;
	}

}
