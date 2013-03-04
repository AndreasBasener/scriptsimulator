package haw.livingplace.scriptSimulator;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

/**
 * Class to set the StdOut to a <code>StyledText</code> in a <code>GUI</code>
 * 
 * @author Andreas Basener
 * 
 */
public class TextOutputStream extends FilterOutputStream
{

	/**
	 * <code>StyledText</code> which shows the text
	 */
	StyledText	text;

	/**
	 * Constructor for <code>TextOutputStream</code>.
	 * 
	 * @param out
	 *            <code>OutputStream</code>
	 */
	public TextOutputStream(OutputStream out)
	{
		super(out);
	}

	/**
	 * Constructor for <code>TextOutputStream</code>.
	 * 
	 * @param out
	 *            <code>OutputStream</code>
	 * @param text
	 *            <code>StyledText</code>
	 */
	public TextOutputStream(OutputStream out, StyledText text)
	{
		super(out);
		this.text = text;
	}

	@Override
	public void write(byte b[]) throws IOException
	{
		final String aString = new String(b);
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run()
			{
				text.append(aString);

			}
		});
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException
	{
		final String aString = new String(	b,
											off,
											len);
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run()
			{
				text.append(aString);

			}
		});
	}

	/**
	 * Sets the <code>StyledText</code>
	 * 
	 * @return <code>StyledText</code>
	 */
	public StyledText getText()
	{
		return text;
	}

	/**
	 * Sets the <code>StyledText</code>
	 * 
	 * @param text
	 *            <code>StyledText</code>
	 */
	public void setText(StyledText text)
	{
		this.text = text;
	}
}
