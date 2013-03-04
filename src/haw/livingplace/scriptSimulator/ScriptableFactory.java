package haw.livingplace.scriptSimulator;

import haw.livingplace.scriptSimulator.gui.composite.EntryComposite;
import haw.livingplace.scriptSimulator.script.Scriptable;

/**
 * Interface to define methods to create and return a new <code>Scriptable</code> instance or to
 * receive a <code>Scriptable</code> instance.
 * 
 * @author Andreas Basener
 * @see Scriptable
 * @see EntryComposite
 * 
 */
public interface ScriptableFactory
{

	/**
	 * generate and return a new <code>Scriptable</code> object. </br> For use in
	 * <code>EntryComposite</code> class.
	 * 
	 * @return new <code>Scriptable</code> object
	 */
	public Scriptable generateScriptable();

	/**
	 * Generates and return a new <code>Scriptable</code> object.</br> For use in in specific
	 * <code>ScriptEntry</code> <code>Composites</code>.
	 * 
	 * @param comp
	 * @return new <code>Scriptable</code> object.
	 */
	public Scriptable generateScriptable(EntryComposite comp);

	/**
	 * pass a <code>Scriptable</code>
	 * 
	 * @param scriptable
	 */
	public void setToScriptable(Scriptable scriptable);

}
