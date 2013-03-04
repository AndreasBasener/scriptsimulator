package haw.livingplace.scriptSimulator.script.entry;

import java.text.DecimalFormat;

import haw.livingplace.scriptSimulator.Helper;
import haw.livingplace.scriptSimulator.Point3D;
import haw.livingplace.scriptSimulator.script.listener.UbisenseToolsEntryListener;

import org.joda.time.Period;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseToolsEntry extends ScriptEntry
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3448143368150015285L;

	private static final int	SLEEP_INTERVAL		= 1000;

	public enum UbisenseToolType
	{
		PUNKT, KREIS, LINIE
	};

	private UbisenseToolType	ubiToolType;

	private int					x1, y1, x2, y2;
	private int					radius;
	private int					arc;
	private int					toolDuration;
	private int					toolSpeed;

	private double				elapsedTime;

	private UbisenseMockupData	ubisenseData;

	public UbisenseToolsEntry(	Period offset,
								String name,
								String descr,
								UbisenseToolType type,
								int x1,
								int y1,
								int x2,
								int y2,
								int radius,
								int arc,
								int duration,
								int speed)
	{
		super(offset, name, descr);
		ubiToolType = type;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.radius = radius;
		this.arc = arc;
		this.toolDuration = duration;
		this.toolSpeed = speed;

		if (descr.equals(Helper.DEFAULT_ENTRY_DESCRIPTION))
		{
			this.description = ubiToolType.name();
		}
	}

	/**
	 * Executes the <code>UbisenseToolsEntry</code>. Depending of the type (point, line, circle),
	 * the matching behavior will be invoked. 
	 */
	@Override
	public void run()
	{
		switch (ubiToolType)
		{
		case PUNKT:
			doPoint();
			break;
		case KREIS:
			doCircle();
			break;
		case LINIE:
			doLine();
			break;
		default:
			break;
		}
	}

	/**
	 * This method will simulate a non-moving point on the given coordinates 
	 * (<code>x1</code>, <code>y1</code>) for the given time (<code>toolDuration</code>).
	 */
	private void doPoint()
	{
		ubisenseData = new UbisenseMockupData();

		elapsedTime = 0;
		
		ubisenseData.setTagID(Helper.DEFAULT_TAG_ID);
		ubisenseData.setUnit(Helper.UBISENSE_UNIT);
		ubisenseData.setOntology(Helper.LP_ONTOLOGY_URL);
		ubisenseData.setVersion(Helper.UBISENSE_VERSION);
		ubisenseData.setId("");
		ubisenseData.setSendTime(sendTime);
		ubisenseData.setPosition(new Point3D(	x1/100,
												y1/100,
												1.6));
		long millis = startDate.getMillis();
		millis += offset.toStandardDuration().getMillis();

		while (elapsedTime < toolDuration && !this.terminate)
		{
			ubisenseData.setId(String.valueOf(Helper.getRandomInt()));
			ubisenseData.setTime(millis + (long)(elapsedTime*SLEEP_INTERVAL));
			EntryEvent event = new EntryEvent(this);
			this.notifyListeners(event);
			elapsedTime++;
			try
			{
				if (speed > 0)
					Thread.sleep(SLEEP_INTERVAL/speed);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method will simulate a moving point from the coordinates 
	 * (<code>x1</code>,<code>y1</code>) to the coordinates 
	 * (<code>x2</code>,<code>y2</code>) at the given speed (<code>toolSpeed</code>).
	 */
	private void doLine()
	{
		ubisenseData = new UbisenseMockupData();
		DecimalFormat df = new DecimalFormat("#0,00");

		elapsedTime = 0;
		
		ubisenseData.setTagID(Helper.DEFAULT_TAG_ID);
		ubisenseData.setUnit(Helper.UBISENSE_UNIT);
		ubisenseData.setOntology(Helper.LP_ONTOLOGY_URL);
		ubisenseData.setVersion(Helper.UBISENSE_VERSION);
		ubisenseData.setId("");
		ubisenseData.setSendTime(sendTime);
		
		long millis = startDate.getMillis();
		millis += offset.toStandardDuration().getMillis();
		
		// Steigung m und Achsenabschnitt b berechnen
//		double m = 0.;
//		if ((x2-x1)!=0)
//			m = (y2-y1)/(x2-x1);
//		double b = y1 - (m * x1);
		
		// Abstand zwischen beiden Koordinaten d
		double dsquare = Math.pow((x2-x1),2) + Math.pow((y2-y1),2);
		double d = Math.sqrt(dsquare);

		// Zeit t [s] berechnen, die für die Distanz d unter gegebener Geschwindigkeit benötigt wird.
		double t = d / toolSpeed;
		double steps = Math.ceil(t) +1;
		
		while (steps>0 && !terminate)
		{
			double x,y, currentTime=0;
			if (t != 0)
			currentTime = elapsedTime / t;
			
			x = (x2-x1) * currentTime + x1;
			y = (y2-y1) * currentTime + y1;
			ubisenseData.setPosition(new Point3D(	Double.parseDouble(df.format(x)),
			                                     	Double.parseDouble(df.format(y)),
													1.6));
			ubisenseData.setId(String.valueOf(Helper.getRandomInt()));
			ubisenseData.setTime(millis + (long)(elapsedTime*SLEEP_INTERVAL));
			EntryEvent event = new EntryEvent(this);
			notifyListeners(event);
			elapsedTime++;
			if (elapsedTime > t)
				elapsedTime = t;
			
			steps--;
			try
			{
				if (speed > 0)
					Thread.sleep(SLEEP_INTERVAL/speed);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method will simulate a moving point on the circumfence of the given circle with the
	 * middle coordinates (<code>x1</code>,<code>y1</code>) and the <code>radius</code>. How far 
	 * and fast the point will move, depends on the <code>toolSpeed</code> and <code>arc</code> 
	 * values.
	 */
	private void doCircle()
	{
		ubisenseData = new UbisenseMockupData();
		DecimalFormat df = new DecimalFormat("#0,00");

		elapsedTime = 0;
		
		ubisenseData.setTagID(Helper.DEFAULT_TAG_ID);
		ubisenseData.setUnit(Helper.UBISENSE_UNIT);
		ubisenseData.setOntology(Helper.LP_ONTOLOGY_URL);
		ubisenseData.setVersion(Helper.UBISENSE_VERSION);
		ubisenseData.setId("");
		ubisenseData.setSendTime(sendTime);

		long millis = startDate.getMillis();
		millis += offset.toStandardDuration().getMillis();
		
		//Radianten des Winkels berechnen
		double radiant = Math.toRadians(arc);
		
		// Distanz d berechnen, die auf dem Kreis zurückgelegt werden soll
		double d = Math.PI * radius * (arc / 180);
		
		// Zeit t [s] berechnen, die für die Distanz d unter gegebener Geschwindigkeit benötigt wird.
		double t = d / toolSpeed;
		double steps = Math.ceil(t) +1;

		//Startkoordinaten berechnen
		int startX, startY;
		startX = x1;
		startY = y1 + radius;
		
		while(steps > 0 && !terminate)
		{
			double x,y, currentTime=0, currentArc = 0;
			if (t != 0)
			currentTime = elapsedTime / t;

			currentArc = radiant * currentTime;
			
			x = (startX - x1) * Math.cos(currentArc) - (startY - y1) * Math.sin(currentArc) + x1;
			y = (startX - x1) * Math.sin(currentArc) + (startY - y1) * Math.cos(currentArc) + y1;

			ubisenseData.setPosition(new Point3D(	Double.parseDouble(df.format(x)),
			                                     	Double.parseDouble(df.format(y)),
													1.6));
			ubisenseData.setId(String.valueOf(Helper.getRandomInt()));
			ubisenseData.setTime(millis + (long) (elapsedTime*SLEEP_INTERVAL));
			
			EntryEvent event = new EntryEvent(this);
			notifyListeners(event);
			elapsedTime++;
			if (elapsedTime > t)
				elapsedTime = t;
			
			steps--;
			try
			{
				if (speed > 0)
					Thread.sleep(SLEEP_INTERVAL/speed);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initDefaultListener(String activeMQip, String mongoDBip, Gson gson)
	{
		if (listenerList.getListenerCount() == 0)
			this.addEntryListener(new UbisenseToolsEntryListener(	activeMQip,
																	mongoDBip,
																	gson));
	}

	/**
	 * @return the ubiToolType
	 */
	public UbisenseToolType getUbiToolType()
	{
		return ubiToolType;
	}

	/**
	 * @param ubiToolType
	 *            the ubiToolType to set
	 */
	public void setUbiToolType(UbisenseToolType ubiToolType)
	{
		this.ubiToolType = ubiToolType;
	}

	/**
	 * @return the x1
	 */
	public int getX1()
	{
		return x1;
	}

	/**
	 * @param x1
	 *            the x1 to set
	 */
	public void setX1(int x1)
	{
		this.x1 = x1;
	}

	/**
	 * @return the y1
	 */
	public int getY1()
	{
		return y1;
	}

	/**
	 * @param y1
	 *            the y1 to set
	 */
	public void setY1(int y1)
	{
		this.y1 = y1;
	}

	/**
	 * @return the x2
	 */
	public int getX2()
	{
		return x2;
	}

	/**
	 * @param x2
	 *            the x2 to set
	 */
	public void setX2(int x2)
	{
		this.x2 = x2;
	}

	/**
	 * @return the y2
	 */
	public int getY2()
	{
		return y2;
	}

	/**
	 * @param y2
	 *            the y2 to set
	 */
	public void setY2(int y2)
	{
		this.y2 = y2;
	}

	/**
	 * @return the radius
	 */
	public int getRadius()
	{
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(int radius)
	{
		this.radius = radius;
	}

	/**
	 * @return the arc
	 */
	public int getArc()
	{
		return arc;
	}

	/**
	 * @param arc
	 *            the arc to set
	 */
	public void setArc(int arc)
	{
		this.arc = arc;
	}

	/**
	 * @return the toolDuration
	 */
	public int getToolDuration()
	{
		return toolDuration;
	}

	/**
	 * @param toolDuration
	 *            the toolDuration to set
	 */
	public void setToolDuration(int toolDuration)
	{
		this.toolDuration = toolDuration;
	}

	/**
	 * @return the toolSpeed
	 */
	public int getToolSpeed()
	{
		return toolSpeed;
	}

	/**
	 * @param toolSpeed
	 *            the toolSpeed to set
	 */
	public void setToolSpeed(int toolSpeed)
	{
		this.toolSpeed = toolSpeed;
	}

	/**
	 * @return the ubisenseData
	 */
	public UbisenseMockupData getUbisenseData()
	{
		return ubisenseData;
	}

	/**
	 * @param ubisenseData
	 *            the ubisenseData to set
	 */
	public void setUbisenseData(UbisenseMockupData ubisenseData)
	{
		this.ubisenseData = ubisenseData;
	}

}
