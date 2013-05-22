package org.livingplace.scriptsimulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageFileWriter {

	private static MessageFileWriter instance;
	
	private static PrintWriter writer;
	
	private static List<WriterBufferEntry> buffer;
	
	private MessageFileWriter()
	{
		
	}
	
	public static synchronized MessageFileWriter getInstance()
	{
		if(instance == null)
			instance = new MessageFileWriter();
		
		instantiatePrinterWriter();
		instantiateBuffer();
		
		return instance;
	}
	
	private static synchronized void instantiatePrinterWriter()
	{
		try {
			if(writer == null)
				writer = new PrintWriter(Helper.MESSAGEFILEWRITER_FILENAME);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static synchronized void instantiateBuffer()
	{
		if(buffer == null)
			buffer = new ArrayList<WriterBufferEntry>();
	}
	
	public synchronized void println(String string)
	{
		if(writer == null)
			return;
		
		writer.println(string);
		writer.flush();
	}
	
	public synchronized void bufferString(long key, String value)
	{
		buffer.add(new WriterBufferEntry(key, value));
	}
	
	public static void flushBuffer()
	{
		Collections.sort(buffer);
		for(WriterBufferEntry w: buffer)
		{
			writer.println(w.getValue());
		}
		writer.flush();
	}
	
	public static void close()
	{
		if(writer != null)
			writer.close();
	}

	public synchronized static void clear()
	{
		if(writer == null)
			return;
		
		buffer.clear();
		
		writer.close();
		try {
			writer = new PrintWriter(Helper.MESSAGEFILEWRITER_FILENAME);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the writer
	 */
	public static PrintWriter getWriter() {
		return writer;
	}

	/**
	 * @param writer the writer to set
	 */
	public static void setWriter(PrintWriter writer) {
		MessageFileWriter.writer = writer;
	}
}
