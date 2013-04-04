package org.livingplace.scriptsimulator.script.entry;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseData
{
	/**
	 * Timestamp of the data in milliseconds.
	 */
	private String				timestamp;
	/**
	 * no idea what this is used for. Default: <code>false</code>
	 */
	private Boolean				bool = false;
	/**
	 * Type of the data.
	 */
	private String				type;
	/**
	 * The JSON data as a <code>String</code>.
	 */
	private String				jsonData;
	/**
	 * The data as <code>UbisenseMockupData</code>.
	 */
	private UbisenseMockupData	mockupData;

	/**
	 * Creates a new <code>UbisenseData</code> instance.
	 * @param timestamp
	 * @param bool
	 * @param type
	 * @param json
	 */
	public UbisenseData(String timestamp, Boolean bool, String type, String json)
	{
		this.timestamp = timestamp;
		this.bool = bool;
		this.type = type;
		this.jsonData = json;
	}

	/**
	 * Returns the timestamp.
	 * @return
	 */
	public String getTimestamp()
	{
		return timestamp;
	}

	/**
	 * Returns the timestamp as a <code>Long</code>.
	 * @return
	 */
	public long getTimestampAsLong()
	{
		return Long.parseLong(timestamp);
	}

	/**
	 * Sets the timestamp.
	 * @param timestamp
	 */
	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	/**
	 * Returns the bool.
	 * @return
	 */
	public Boolean getBool()
	{
		return bool;
	}

	/**
	 * Sets the bool.
	 * @param bool
	 */
	public void setBool(Boolean bool)
	{
		this.bool = bool;
	}

	/**
	 * Returns the message type.
	 * @return
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the message type.
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * returns the JSON data as a <code>String</code>.
	 * @return
	 */
	public String getJsonData()
	{
		return jsonData;
	}

	/**
	 * Sets the JSON data as a <code>String</code>.
	 * @param jsonData
	 */
	public void setJsonData(String jsonData)
	{
		this.jsonData = jsonData;
	}

	/**
	 * @return the mockupData
	 */
	public UbisenseMockupData getMockupData()
	{
		return mockupData;
	}

	/**
	 * @param mockupData
	 *            the mockupData to set
	 */
	public void setMockupData(UbisenseMockupData mockupData)
	{
		this.mockupData = mockupData;
	}

	@Override
	public String toString()
	{
		return this.timestamp + " - " + this.jsonData;
	}
}
