package org.livingplace.scriptsimulator.script.entry;


import java.io.FileReader;
import java.util.ArrayList;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.listener.UbisenseEntryListener;

import com.Ostermiller.util.CSVParser;
import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long					serialVersionUID	= 6011299621951250674L;
	/**
	 * Path to the file.
	 */
	private String								fileName			= "";
	// private File file;
	/**
	 * current line of the file.
	 */
	private String								currentLine			= "";
	/**
	 * List of the data.
	 */
	private transient ArrayList<UbisenseData>	dataList;

	/**
	 * Creates a new UbisenseEntry instance.
	 * @param offset
	 * @param name
	 * @param descr
	 * @param fileName
	 */
	public UbisenseEntry(Period offset, String name, String descr, String fileName)
	{
		super(offset, name, descr);
		this.fileName = fileName;

		dataList = new ArrayList<UbisenseData>();

		String lineArr[];

		try
		{
			FileReader filereader = new FileReader(fileName);
			CSVParser parser = new CSVParser(filereader);
			parser.changeDelimiter(';');
			parser.setEscapes(	"nr",
								"\n\r");

			while ((lineArr = parser.getLine()) != null)
			{
				UbisenseData udata = new UbisenseData(	lineArr[0],
														new Boolean(lineArr[1]),
														lineArr[2],
														lineArr[3]);
				dataList.add(udata);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(dataList.size() + " Datensätze gefunden");

		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			this.description = fileName;
		}
	}

	@Override
	public void run()
	{
		// synchronized (terminate)
		// {
		// terminate = false;
		// }
		this.setTerminate(false);

		long lasttime = 0;
		System.out.println(this.getClass().getSimpleName() + " " + this.getClass().getSimpleName()
							+ " gestartet");
		for (UbisenseData udata : dataList)
		{
			if (terminate)
				return;
			long nexttime = udata.getTimestampAsLong();
			long diff = nexttime - lasttime;
			setCurrentLine(udata.getJsonData());
			// System.out.println(diff);
			if (diff >= 0)
			{
				EntryEvent event = new EntryEvent(this);
				notifyListeners(event);
				// System.out.println(udata.getJsonData());
				if (speed > 0)
				{
					try
					{
						Thread.sleep(diff / speed);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
			lasttime = nexttime;
		}
		System.out.println(this.getClass().getSimpleName() + " beendet/terminiert");
	}


	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new UbisenseEntryListener(activeMQip,
															mongoDBip));
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the currentLine
	 */
	public String getCurrentLine() {
		return currentLine;
	}

	/**
	 * @param currentLine the currentLine to set
	 */
	public void setCurrentLine(String currentLine) {
		this.currentLine = currentLine;
	}

	/**
	 * @return the dataList
	 */
	public ArrayList<UbisenseData> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(ArrayList<UbisenseData> dataList) {
		this.dataList = dataList;
	}

}
