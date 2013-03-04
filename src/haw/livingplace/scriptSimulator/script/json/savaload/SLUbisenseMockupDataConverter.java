package haw.livingplace.scriptSimulator.script.json.savaload;

import java.lang.reflect.Type;

import haw.livingplace.scriptSimulator.Point3D;
import haw.livingplace.scriptSimulator.script.entry.UbisenseMockupData;

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
public class SLUbisenseMockupDataConverter implements JsonSerializer<UbisenseMockupData>,
		JsonDeserializer<UbisenseMockupData>
{

	@Override
	public UbisenseMockupData deserialize(	JsonElement json,
											Type typeOfT,
											JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();

		String tagID = object.get("UbisenseTagId").getAsString();
		String unit = object.get("Unit").getAsString();
		Point3D position = context.deserialize(	object.get("NewPosition"),
												Point3D.class);
		String version = object.get("Version").getAsString();
		String id = object.get("Id").getAsString();
		String ontology = object.get("Ontology").getAsString();

		UbisenseMockupData data = new UbisenseMockupData();
		data.setTagID(tagID);
		data.setUnit(unit);
		data.setPosition(position);
		data.setVersion(version);
		data.setId(id);
		data.setOntology(ontology);

		return data;
	}

	@Override
	public JsonElement serialize(	UbisenseMockupData src,
									Type typeOfSrc,
									JsonSerializationContext context)
	{

		JsonObject object = new JsonObject();

		object.addProperty(	"UbisenseTagId",
							src.getTagID());
		object.addProperty(	"Unit",
							src.getUnit());
		object.add(	"NewPosition",
					context.serialize(src.getPosition()));
		object.addProperty(	"Version",
							src.getVersion());
		object.addProperty(	"Id",
							src.getId());
		object.addProperty(	"Ontology",
							src.getOntology());

		return object;
	}

}
