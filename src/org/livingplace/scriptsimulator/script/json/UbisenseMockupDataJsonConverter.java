package org.livingplace.scriptsimulator.script.json;

import java.lang.reflect.Type;

import org.livingplace.scriptsimulator.Point3D;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UbisenseMockupDataJsonConverter implements JsonDeserializer<UbisenseMockupData>,
														JsonSerializer<UbisenseMockupData>
{

	@Override
	public JsonElement serialize(UbisenseMockupData src, Type typeOfSrc,
									JsonSerializationContext context) 
	{
		JsonObject object = new JsonObject();
		
		if(src.getSendTime()){
			long milli = src.getTime();
			object.addProperty("time", milli);
		}
		object.addProperty("UbisenseTagId", src.getTagID());
		object.addProperty("Unit", src.getUnit());
		object.add("NewPosition", context.serialize(src.getPosition()));
		object.addProperty("Version", src.getVersion());
		object.addProperty("id", src.getId());
		object.addProperty("Ontology", src.getOntology());
		
		return object;
	}

	@Override
	public UbisenseMockupData deserialize(JsonElement json, Type typeOfT,
											JsonDeserializationContext context) throws JsonParseException 
	{
		UbisenseMockupData data = new UbisenseMockupData();
		JsonObject object = json.getAsJsonObject();
		
		data.setTime(object.get("time").getAsLong());
		data.setTagID(object.get("UbisenseTagId").getAsString());
		data.setUnit(object.get("Unit").getAsString());
		data.setPosition((Point3D)context.deserialize(object.get("NewPosition"),Point3D.class));
		data.setVersion(object.get("Version").getAsString());
		data.setId(object.get("id").getAsString());
		data.setOntology(object.get("Ontology").getAsString());
		
		return data;
	}

}
