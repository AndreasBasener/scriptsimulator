package org.livingplace.scriptsimulator.script.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Deprecated
public interface EntryJsonConverter<E> extends JsonDeserializer<E>,
JsonSerializer<E>
{
	public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context);
	public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException;
}
