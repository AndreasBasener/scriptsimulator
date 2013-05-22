package org.livingplace.scriptsimulator;

public class WriterBufferEntry implements Comparable<WriterBufferEntry>{

	private long key;
	private String value;
	
	public WriterBufferEntry(long key, String value)
	{
		this.value = value;
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public long getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(long key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(WriterBufferEntry o) {
		if(this.key < o.getKey())
			return -1;
		else if(this.key > o.getKey())
			return 1;
		
		return 0;

	}
	
}
