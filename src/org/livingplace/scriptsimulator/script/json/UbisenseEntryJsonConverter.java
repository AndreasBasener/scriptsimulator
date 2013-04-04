package org.livingplace.scriptsimulator.script.json;

import java.lang.reflect.Type;

import org.livingplace.scriptsimulator.script.entry.UbisenseEntry;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Deprecated
/**
 * 
 * @author Andreas Basener
 *
 */
public class UbisenseEntryJsonConverter implements JsonDeserializer<UbisenseEntry>,
		JsonSerializer<UbisenseEntry>
{

	@Override
	public JsonElement serialize(UbisenseEntry src, Type type, JsonSerializationContext context)
	{

		JsonPrimitive primitive = new JsonPrimitive(src.getCurrentLine());

		return primitive;
	}

	@Override
	public UbisenseEntry deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
