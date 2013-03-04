package haw.livingplace.scriptSimulator.script.json.savaload;

import java.lang.reflect.Type;

import haw.livingplace.scriptSimulator.Point3D;
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
public class SLPoint3D implements JsonSerializer<Point3D>, JsonDeserializer<Point3D>
{

	@Override
	public Point3D deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{

		JsonObject object = json.getAsJsonObject();

		double x = object.get("X").getAsDouble();
		double y = object.get("Y").getAsDouble();
		double z = object.get("Z").getAsDouble();

		return new Point3D(	x,
							y,
							z);
	}

	@Override
	public JsonElement serialize(Point3D src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();

		object.addProperty(	"X",
							src.getX());
		object.addProperty(	"Y",
							src.getY());
		object.addProperty(	"Z",
							src.getZ());

		return object;
	}

}
