package org.livingplace.scriptsimulator;

import org.eclipse.swt.widgets.Composite;
import org.livingplace.scriptsimulator.script.Scriptable;
import org.livingplace.scriptsimulator.script.json.EntryJsonConverter;
import org.livingplace.scriptsimulator.script.listener.EntryJsonListener;


/**
 * 
 * @author Andreas Basener
 *
 * @param <E>
 * @deprecated
 */
public class ScriptableWrapper<E>
{
	private Scriptable scriptable;
	private Composite composite;
	private EntryJsonListener listener;
	private EntryJsonConverter<E> jsonConverter;
	private EntryJsonConverter<E> slConverter;
	
	public ScriptableWrapper(Scriptable scriptable, 
	                         Composite composite,
	                         EntryJsonListener listener,
	                         EntryJsonConverter<E> jsonConv,
	                         EntryJsonConverter<E> slConv)
	{
		this.scriptable = scriptable;
		this.composite = composite;
		this.listener = listener;
		this.jsonConverter = jsonConv;
		this.slConverter = slConv;
	}

	/**
	 * @return the scriptable
	 */
	public Scriptable getScriptable()
	{
		return scriptable;
	}

	/**
	 * @param scriptable the scriptable to set
	 */
	public void setScriptable(Scriptable scriptable)
	{
		this.scriptable = scriptable;
	}

	/**
	 * @return the composite
	 */
	public Composite getComposite()
	{
		return composite;
	}

	/**
	 * @param composite the composite to set
	 */
	public void setComposite(Composite composite)
	{
		this.composite = composite;
	}

	/**
	 * @return the listener
	 */
	public EntryJsonListener getListener()
	{
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(EntryJsonListener listener)
	{
		this.listener = listener;
	}

	/**
	 * @return the jsonConverter
	 */
	public EntryJsonConverter<E> getJsonConverter()
	{
		return jsonConverter;
	}

	/**
	 * @param jsonConverter the jsonConverter to set
	 */
	public void setJsonConverter(EntryJsonConverter<E> jsonConverter)
	{
		this.jsonConverter = jsonConverter;
	}

	/**
	 * @return the slConverter
	 */
	public EntryJsonConverter<E> getSlConverter()
	{
		return slConverter;
	}

	/**
	 * @param slConverter the slConverter to set
	 */
	public void setSlConverter(EntryJsonConverter<E> slConverter)
	{
		this.slConverter = slConverter;
	}

}
