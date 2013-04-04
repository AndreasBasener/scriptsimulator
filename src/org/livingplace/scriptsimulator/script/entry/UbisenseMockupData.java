package org.livingplace.scriptsimulator.script.entry;

import org.livingplace.scriptsimulator.Point3D;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseMockupData
{
	/**
	 * ID of the Ubisense tag.
	 */
	private String	tagID;
	/**
	 * Unit of the distance, e.g "Meter"
	 */
	private String	unit;
	/**
	 * position of the tag.
	 */
	private Point3D	position;
	/**
	 * version of this data format
	 */
	private String	version;
	/**
	 * unique id of this message
	 */
	private String	id;
	/**
	 * Ontology of this message.
	 */
	private String	ontology;
	
	private long time;
	private boolean sendTime;

	/**
	 * Creates a new UbisenseMockupData instance.
	 */
	public UbisenseMockupData()
	{

	}

	/**
	 * Creates a new UbisenseMockupData instance.
	 * @param tagID
	 * @param unit
	 * @param position
	 * @param version
	 * @param id
	 * @param ontology
	 */
	public UbisenseMockupData(	String tagID,
								String unit,
								Point3D position,
								String version,
								String id,
								String ontology)
	{
		this.tagID = tagID;
		this.unit = unit;
		this.position = position;
		this.version = version;
		this.id = id;
		this.ontology = ontology;
	}
	
	/**
	 * Returns this UbisenseMockupData as a valid JSON message.
	 * @return
	 */
	public String toJson()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"UbisenseTagId\":\"");
		builder.append(tagID);
		builder.append("\",\"Unit\":\"");
		builder.append(unit);
		builder.append("\",\"NewPosition\":{\"X\":");
		builder.append(position.getX());
		builder.append(",\"Y\":");
		builder.append(position.getY());
		builder.append(",\"Z\":");
		builder.append(position.getZ());
		builder.append("},\"Version\":\"");
		builder.append(version);
		builder.append("\",\"id\":\"");
		builder.append(id);
		builder.append("\",\"Ontology\":\"");
		builder.append(ontology);
		builder.append("\"}");
		
		return builder.toString();
	}

	/**
	 * @return the tagID
	 */
	public String getTagID()
	{
		return tagID;
	}

	/**
	 * @param tagID
	 *            the tagID to set
	 */
	public void setTagID(String tagID)
	{
		this.tagID = tagID;
	}

	/**
	 * @return the unit
	 */
	public String getUnit()
	{
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	/**
	 * @return the position
	 */
	public Point3D getPosition()
	{
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Point3D position)
	{
		this.position = position;
	}

	/**
	 * @return the version
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the ontology
	 */
	public String getOntology()
	{
		return ontology;
	}

	/**
	 * @param ontology
	 *            the ontology to set
	 */
	public void setOntology(String ontology)
	{
		this.ontology = ontology;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the sendTime
	 */
	public boolean getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(boolean sendTime) {
		this.sendTime = sendTime;
	}

}
