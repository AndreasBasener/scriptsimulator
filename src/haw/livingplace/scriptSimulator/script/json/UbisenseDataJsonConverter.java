package haw.livingplace.scriptSimulator.script.json;

import java.lang.reflect.Type;

import haw.livingplace.scriptSimulator.script.entry.UbisenseData;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Deprecated, use SLUbisenseDataConverter
 * 
 * @author Andreas Basener
 * 
 */
@Deprecated
public class UbisenseDataJsonConverter implements JsonDeserializer<UbisenseData>,
		JsonSerializer<UbisenseData>
{

	@Override
	public JsonElement serialize(UbisenseData src, Type type, JsonSerializationContext context)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UbisenseData deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
