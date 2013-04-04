package org.livingplace.scriptsimulator.script.json.saveload;

import java.lang.reflect.Type;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.Script;
import org.livingplace.scriptsimulator.script.Scriptable;

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
public class SLScriptConverter implements JsonSerializer<Script>, JsonDeserializer<Script>
{

	private Gson	activeGson;

	private String	activeMQip	= Helper.ACTIVE_MQ_IP;
	private String	mongoDBip	= Helper.MONGO_DB_IP;

	//
	// public SaveLoadScriptConverter(Gson gson)
	// {
	// super();
	// this.gson = gson;
	// }

	@Override
	public Script deserialize(JsonElement element, Type typ, JsonDeserializationContext context) throws JsonParseException
	{
		Script script = null;

		JsonObject object = element.getAsJsonObject().getAsJsonObject();
		String type = object.get("type").getAsString();
		if (type.equals(Script.class.getName()))
		{
			script = new Script();
			script.setName(object.get("name").getAsString());
			script.setDescription(object.get("description").getAsString());
			long offset = object.get("offset").getAsLong();
			script.setOffset(new Period(offset));

			if (object.get("entrylist").isJsonArray())
			{
				JsonArray array = object.get("entrylist").getAsJsonArray();
				for (JsonElement e : array)
				{
					JsonObject o = (JsonObject) e;

					Scriptable scriptable = null;

					String entryType = o.get("type").getAsString();

					try
					{
						scriptable = (Scriptable) context.deserialize(o, Class.forName(entryType));
					}
					catch (ClassNotFoundException e1)
					{
						e1.printStackTrace();
					}

					if (scriptable != null)
					{
						scriptable.initDefaultListener(	activeMQip,
														mongoDBip,
														activeGson);
						scriptable.setJSONId(Helper.DEFAULT_CLIENT_ID);
						script.setJSONId(Helper.DEFAULT_CLIENT_ID);
						script.add(scriptable);
					}
					// System.out.println(scriptable);
				}
			}
		}

		return script;
	}

	@Override
	public JsonElement serialize(Script src, Type type, JsonSerializationContext context)
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

		object.add(	"entrylist",
					context.serialize(src.getEntryList()));

		return object;
	}

	public Gson getActiveGson()
	{
		return activeGson;
	}

	public void setActiveGson(Gson activeGson)
	{
		this.activeGson = activeGson;
	}

	public String getActiveMQip()
	{
		return activeMQip;
	}

	public void setActiveMQip(String activeMQip)
	{
		this.activeMQip = activeMQip;
	}

	public String getMongoDBip()
	{
		return mongoDBip;
	}

	public void setMongoDBip(String mongoDBip)
	{
		this.mongoDBip = mongoDBip;
	}

}
