package org.livingplace.scriptsimulator.script.entry;

import java.io.FileReader;
import java.util.ArrayList;

import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.Point3D;
import org.livingplace.scriptsimulator.script.json.saveload.SLPoint3D;
import org.livingplace.scriptsimulator.script.json.saveload.SLUbisenseMockupDataConverter;
import org.livingplace.scriptsimulator.script.listener.UbisenseMockupEntryListener;

import com.Ostermiller.util.CSVParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseMockupEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long					serialVersionUID	= 3121138455114984024L;

	/**
	 * Path to the file.
	 */
	private String								fileName;
	/**
	 * String of the current line in file.
	 */
	private String								currentLine			= "";
	/**
	 * current Psition
	 */
	private Point3D								currentPosition;
	/**
	 * current UbisenseMockupData
	 */
	private UbisenseMockupData					currentMockupData;
	/**
	 * List of all UbisenseData.
	 */
	private transient ArrayList<UbisenseData>	dataList;

	/**
	 * Creates a new UbisenseMockupEntry.
	 * @param offset
	 * @param name
	 * @param descr
	 * @param filename
	 */
	public UbisenseMockupEntry(Period offset, String name, String descr, String filename)
	{

		super(offset, name, descr);
		this.fileName = filename;

		dataList = new ArrayList<UbisenseData>();

		String lineArr[];

		try
		{
			FileReader filereader = new FileReader(fileName);
			CSVParser parser = new CSVParser(filereader);
			parser.changeDelimiter(';');
			// parser.setEscapes("nr", "\n\r");

			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(UbisenseMockupData.class,
										new SLUbisenseMockupDataConverter());
			builder.registerTypeAdapter(Point3D.class,
										new SLPoint3D());

			Gson gson = builder.create();

			while ((lineArr = parser.getLine()) != null)
			{
				UbisenseData udata = new UbisenseData(	lineArr[0],
														false,
														"UbisenseTracking",
														lineArr[1]);
				udata.setMockupData(gson.fromJson(	lineArr[1],
													UbisenseMockupData.class));
//				System.out.println(udata.getMockupData().getId());
				dataList.add(udata);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(this.getClass().getSimpleName() + " " + dataList.size()
							+ " Datensätze gefunden");

		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			this.description = filename;
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
//		System.out.println(this.getClass().getSimpleName() + " gestartet");
		for (UbisenseData udata : dataList)
		{
			if (terminate)
				break;
			long nexttime = udata.getTimestampAsLong();
			long diff = nexttime - lasttime;
			setCurrentLine(udata.getJsonData());
			setCurrentPosition(udata.getMockupData().getPosition());
			setCurrentMockupData(udata.getMockupData());
			long millis = startDate.getMillis();
			millis += offset.toStandardDuration().getMillis();
			millis += nexttime;
			currentMockupData.setTime(millis);
			currentMockupData.setSendTime(this.sendTime);
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
//		System.out.println(this.getClass().getSimpleName() + " beendet/terminiert");
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getCurrentLine()
	{
		return currentLine;
	}

	public void setCurrentLine(String currentLine)
	{
		this.currentLine = currentLine;
	}

	/**
	 * @return the currentPosition
	 */
	public Point3D getCurrentPosition()
	{
		return currentPosition;
	}

	/**
	 * @param currentPosition
	 *            the currentPosition to set
	 */
	public void setCurrentPosition(Point3D currentPosition)
	{
		this.currentPosition = currentPosition;
	}

	/**
	 * @return the currentMockupData
	 */
	public UbisenseMockupData getCurrentMockupData() {
		return currentMockupData;
	}

	/**
	 * @param currentMockupData the currentMockupData to set
	 */
	public void setCurrentMockupData(UbisenseMockupData currentMockupData) {
		this.currentMockupData = currentMockupData;
	}

	public ArrayList<UbisenseData> getDataList()
	{
		return dataList;
	}

	public void setDataList(ArrayList<UbisenseData> dataList)
	{
		this.dataList = dataList;
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new UbisenseMockupEntryListener(	activeMQip,
																	mongoDBip,
																	gson));
	}
}
