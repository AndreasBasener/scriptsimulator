package org.livingplace.scriptsimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;

import org.apache.kahadb.util.ByteArrayOutputStream;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.joda.time.DateTime;
import org.livingplace.scriptsimulator.gui.GUI;
import org.livingplace.scriptsimulator.gui.composite.*;
import org.livingplace.scriptsimulator.script.*;
import org.livingplace.scriptsimulator.script.entry.*;
import org.livingplace.scriptsimulator.script.json.*;
import org.livingplace.scriptsimulator.script.json.saveload.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Main class for the <code>Scriptsimulator</code>. The <code>ScriptSimulator</code> will initialize
 * everything that is needed for proper execution end will open the <code>GUI</code>.
 * 
 * The <code>Scriptsimulator</code> is tools to create an execute scripts. These scripts can represent anything 
 * from a simple action with only one entry, to a whole day and more.   
 * 
 * @author Andreas Basener
 * 
 */
public class ScriptSimulator
{

	/**
	 * the <code>Script</code> which will be executed by the <code>ScriptSimulator</code>
	 */
	private Script			script;
	/**
	 * the <code>GUI</code>
	 */
	private GUI				gui;

	/**
	 * <code>GsonBuilder</code> for custom <code>TypeAdapter</code> of the different
	 * <code>ScriptEntrys</code>
	 */
	private GsonBuilder		gsonBuilder;
	/**
	 * global Gson instance
	 */
	private Gson			gson;

	/**
	 * <code>Gson</code> instance to save and load the <code>Script</code> to or from a file on the
	 * disk.
	 */
	private Gson			saveLoadScriptGson;
	/**
	 * <code>Gson</code> instance to save and load <code>ScriptEntries</code> to or from a file on
	 * the disk.
	 */
//	private Gson			saveLoadEntryGson;

	/**
	 * represents which entry is selected for editing in the <code>GUI</code> table
	 */
	private int				editIndex;

	/**
	 * <code>ScriptComposite</code> of the <code>GUI</code>
	 */
	private ScriptComposite	scriptComp;

	private static Logger logger = Logger.getRootLogger();
	
	private PrintStream backupOutStream;
	
	/**
	 * Creates new <code>ScriptSimulator</code> instance
	 */
	public ScriptSimulator()
	{
		// random = new Random();
	}

	/**
	 * Main method for <code>ScriptSimulator</code> <br />
	 * The main method creates an instance of <code>ScriptSimulator</code>, initiates, runs and
	 * waits for termination of <code>ScriptSimulator</code>.
	 * 
	 * @param args
	 *            will be ignored
	 */
	public static void main(String[] args)
	{
		try {
		      SimpleLayout layout = new SimpleLayout();
		      ConsoleAppender consoleAppender = new ConsoleAppender( layout );
		      logger.addAppender( consoleAppender );
		      FileAppender fileAppender = new FileAppender( layout, "logs/MeineLogDatei.log", false );
			  logger.addAppender( fileAppender );
			  // ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
			  logger.setLevel( Level.WARN );
		} catch( Exception ex ) {
		  System.out.println( ex );
		}
		logger.info("Scriptsimulator gestartet");
		ScriptSimulator simulator = new ScriptSimulator();
		simulator.init();
		simulator.stopScriptSimulator();
		logger.info("Scriptsimulator beendet");
	}

	/**
	 * Initiates <code>ScriptSimulator</code>.
	 * 
	 * <ol>
	 * <li>all custom <code>TypeAdapter</code> are registered to the <code>GsonBuilder</code>
	 * <li>create <code>Gson</code> instance
	 * <li>initialize <code>Script</code>
	 * <li>initialize <code>GUI</code>
	 * <li>Set StdOut to textfield in <code>GUI</code>
	 * </ol>
	 */
	public void init()
	{
		/* ActiveMQ Gson */
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(AlarmEntry.class,
										new AlarmEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(BedEntry.class,
										new BedEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(BlindsEntry.class,
										new BlindsEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(CouchEntry.class,
										new CouchEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(DoorEntry.class,
										new DoorEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(DoorBellEntry.class,
										new DoorBellEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(MediaEntry.class,
										new MediaEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(MoodEntry.class,
										new MoodEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(OnOffEntry.class,
										new OnOffEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(PeriodicEntry.class,
										new PeriodicEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(Point3D.class,
										new Point3DJsonConverter());
		gsonBuilder.registerTypeAdapter(PowerEntry.class,
										new PowerEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(ScriptEntry.class,
										new ScriptEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(SensorEntry.class,
										new SensorEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(SingleEntry.class,
										new SingleEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(StorageEntry.class,
										new StorageEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(UbisenseMockupData.class,
										new UbisenseMockupDataJsonConverter());
		gsonBuilder.registerTypeAdapter(WindowEntry.class,
										new WindowEntryJsonConverter());
		gsonBuilder.registerTypeAdapter(WaterEntry.class,
										new WaterEntryJsonConverter());

		// gson = gsonBuilder.setPrettyPrinting().create();
		gson = gsonBuilder.create();

		/* SL Script Gson */
		GsonBuilder saveloadScriptBuilder = new GsonBuilder();

		SLScriptConverter slsc = new SLScriptConverter();
		saveloadScriptBuilder.registerTypeAdapter(	Script.class,
													slsc);

		saveloadScriptBuilder.registerTypeAdapter(	AlarmEntry.class,
													new SLAlarmEntryJsonConverter());
		saveloadScriptBuilder.registerTypeAdapter(	BedEntry.class,
													new SLBedEntryJsonConverter());
		saveloadScriptBuilder.registerTypeAdapter(	BlindsEntry.class,
													new SLBlindsEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	CouchEntry.class,
													new SLCouchEntryJsonConverter());
		saveloadScriptBuilder.registerTypeAdapter(	DoorBellEntry.class,
													new SLDoorBellEntryJsonConverter());
		saveloadScriptBuilder.registerTypeAdapter(	DoorEntry.class,
													new SLDoorEntryJsonConverter());
		saveloadScriptBuilder.registerTypeAdapter(	MediaEntry.class,
													new SLMediaEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	MoodEntry.class,
													new SLMoodEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	OnOffEntry.class,
													new SLOnOffEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	PeriodicEntry.class,
													new SLPeriodicEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	Point3D.class,
													new SLPoint3D());
		saveloadScriptBuilder.registerTypeAdapter(	PowerEntry.class,
													new SLPowerEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	SensorEntry.class,
													new SLSensorEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	SingleEntry.class,
													new SLSingleEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	SleepEntry.class,
													new SLSleepEntryJsonConverter());
		saveloadScriptBuilder.registerTypeAdapter(	StorageEntry.class,
													new SLStorageEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	TemperaturSensorEntry.class,
													new SLTemperatureSensorEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	UbisenseEntry.class,
													new SLUbisenseEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	UbisenseMockupEntry.class,
													new SLUbisenseMockupEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	UbisenseToolsEntry.class,
													new SLUbisenseToolsEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	WindowEntry.class,
													new SLWindowEntryConverter());
		saveloadScriptBuilder.registerTypeAdapter(	WaterEntry.class,
													new SLWaterEntryConverter());

		saveloadScriptBuilder.setPrettyPrinting();

		saveLoadScriptGson = saveloadScriptBuilder.create();

		slsc.setActiveGson(gson);
		slsc.setActiveMQip(Helper.ACTIVE_MQ_IP);
		slsc.setMongoDBip(Helper.MONGO_DB_IP);

		// initActiveMQ();
		initScript();
		initGUI();

		gui.setBlockOnOpen(true);
		gui.open();
	}

	/**
	 * Initiates default <code>Script</code> of <code>ScriptSimulator</code>
	 */
	private void initScript()
	{
		script = new Script();
		script.setSpeed(1);
		script.setJSONId(Helper.DEFAULT_CLIENT_ID);
	}

	/**
	 * Initiates the <code>GUI</code> of <code>ScriptSimulator</code> creates new GUI instance and
	 * registers all needed listeners
	 */
	private void initGUI()
	{
		Display display = Display.getDefault();
		
		gui = new GUI(	null,
						script);

		display.asyncExec(new Runnable() {
			@Override
			public void run()
			{
				backupOutStream = System.out;
				PrintStream ps = new PrintStream(new TextOutputStream(	new ByteArrayOutputStream(),
																		gui.getStyledTextConsole()));

				System.setOut(ps);
				// System.setErr(ps);
				
				int style = SWT.NONE;
				Composite comp = gui.getEntryComposite().getStackComposite();
				gui.addScriptableType(	AlarmEntry.class.getSimpleName(),
										new AlarmComposite(	comp,
															style));
				gui.addScriptableType(	BedEntry.class.getSimpleName(),
										new BedComposite(	comp,
															style));
				gui.addScriptableType(	BlindsEntry.class.getSimpleName(),
										new BlindsComposite(	comp,
															style));
				gui.addScriptableType(	CouchEntry.class.getSimpleName(),
										new CouchComposite(	comp,
															style));
				gui.addScriptableType(	DoorBellEntry.class.getSimpleName(),
										new DoorBellComposite(	comp,
																style));
				gui.addScriptableType(	DoorEntry.class.getSimpleName(),
										new DoorComposite(	comp,
															style));
				gui.addScriptableType(	MediaEntry.class.getSimpleName(),
										new MediaComposite(	comp,
															style));
				gui.addScriptableType(	MoodEntry.class.getSimpleName(),
										new MoodComposite(	comp,
															style));
				gui.addScriptableType(	OnOffEntry.class.getSimpleName(),
										new OnOffComposite(	comp,
															style));
				gui.addScriptableType(	PeriodicEntry.class.getSimpleName(),
										new PeriodicComposite(	comp,
																style));
				gui.addScriptableType(	PowerEntry.class.getSimpleName(),
										new PowerComposite(	comp,
															style));
				gui.addScriptableType(	SingleEntry.class.getSimpleName(),
										new EmptyEntryComposite(comp,
																style));
				gui.addScriptableType(	SensorEntry.class.getSimpleName(),
										new SensorComposite(comp,
															style));
				gui.addScriptableType(	SleepEntry.class.getSimpleName(),
										new SleepComposite(comp,
											style));
				gui.addScriptableType(	StorageEntry.class.getSimpleName(),
										new StorageComposite(comp,
															style));
				gui.addScriptableType(	TemperaturSensorEntry.class.getSimpleName(),
										new TemperatureSensorComposite(	comp,
																		style));
				gui.addScriptableType(	UbisenseEntry.class.getSimpleName(),
										new UbisenseComposite(	comp,
																style));
				gui.addScriptableType(	UbisenseMockupEntry.class.getSimpleName(),
										new UbisenseMockupComposite(comp,
																	style));
				gui.addScriptableType(	UbisenseToolsEntry.class.getSimpleName(),
										new UbisenseToolsComposite(	comp,
																	style));
				gui.addScriptableType(	WaterEntry.class.getSimpleName(),
										new WaterComposite(	comp,
															style));
				gui.addScriptableType(	WindowEntry.class.getSimpleName(),
										new WindowComposite(comp,
															style));

				scriptComp = new ScriptComposite(	comp,
													style);
				//Skript wird in Entryliste hinzugefügt
				scriptComp.getOpenButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						FileDialog fd = new FileDialog(	Display.getDefault().getActiveShell(),
														SWT.OPEN);
						fd.setText("Open");
						String[] filter = { "*.script" };
						fd.setFilterExtensions(filter);
						String file = fd.open();

						if (file != null)
						{
							scriptComp.getFilename().setText(file);

							Script s = loadScriptFromPath(file);
							scriptComp.setScript(s);
						}
					}
				});
				gui.addScriptableType(	Script.class.getSimpleName(),
										scriptComp);

				// Listener for "new Entry" Button pressed
				gui.getNewEntryButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						Scriptable entry = null;
						EntryComposite entryComp = gui.getEntryComposite();
						entry = entryComp.generateScriptable();
						if (entry != null)
						{
							entry.initDefaultListener(	Helper.ACTIVE_MQ_IP,
														Helper.MONGO_DB_IP,
														gson);
							entry.setJSONId(Helper.DEFAULT_CLIENT_ID);
							script.add(entry);
							// script.reorderList();
							gui.refreshGUI();
						}
					}
				});

				gui.getDeleteEntryButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						int ti = gui.getTableViewer().getTable().getSelectionIndex();
						if (ti >= 0)
						{
							// script.getEntryList().remove(ti);
							Scriptable entry = script.getEntryList().get(ti);
							script.remove(entry);
							gui.refreshGUI();
						}
					}
				});

				gui.getEditEntryButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						editIndex = gui.getTableViewer().getTable().getSelectionIndex();
						if (editIndex >= 0)
						{
							Scriptable entry = script.getEntryList().get(editIndex);
							gui.getEntryComposite().setToScriptable(entry);

							gui.getCommitEditButton().setEnabled(true);
							gui.getEntryComposite().setcomboEntryTypeEnabled(false);
						}
					}
				});
				gui.getCommitEditButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						if (editIndex >= 0)
						{
							Scriptable entry = script.getEntryList().get(editIndex);

							EntryComposite ecomp = gui.getEntryComposite();

							gui.getCommitEditButton().setEnabled(false);
							ecomp.setcomboEntryTypeEnabled(true);

							script.remove(entry);
							entry = ecomp.generateScriptable();
							entry.initDefaultListener(	Helper.ACTIVE_MQ_IP,
														Helper.MONGO_DB_IP,
														gson);
							entry.setJSONId(Helper.DEFAULT_CLIENT_ID);
							script.add(entry);

							gui.refreshGUI();
						}
					}
				});

				gui.getPlayButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						MessageFileWriter.clear();
						
						script.setSpeed(gui.getscriptSpeedSpinner().getSelection());
						script.setDeviation(new Deviation(gui.getDeviationSpinner().getSelection() / 100.));
						script.setSendTime(gui.sendTime());
						
						if (gui.getTimeChoiceButtons()[0].getSelection())
						{
							script.setStartDate(new DateTime());
						}
						else if (gui.getTimeChoiceButtons()[1].getSelection())
						{
							script.setStartDate(gui.getDateTime());
						}
						Thread t = new Thread(	script,
												"Script: main-thread");
						t.start();
					}
				});

				gui.getStopButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						// script.stopScript();
						script.stopEntry();
					}
				});

				gui.getSaveButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						saveScript();
					}
				});
				//Laden eines Skriptes zum abspielen
				gui.getLoadButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						loadScript();
						
						//Geladenes Skript wird in scriptout.csv als Übersicht gedumpt
						File file = new File("scriptout.csv");
						try
						{
							FileWriter writer = new FileWriter(file);
							for(Scriptable s: gui.getScript().getEntryList())
							{
								writer.write(s.getClass().getSimpleName() + ";" + s.getName() + ";" + s.getDescription() + "\n");
							}
							
							writer.close();
						}
						catch(Exception ex)
						{
							
						}
					}
				});

				gui.getNewScriptButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						// Random rand = new Random();
						// Deviation dev = new
						// Deviation(gui.getDeviationSpinner().getSelection()/100.);
						// for (int i =0;i<100;i++)
						// System.out.println(dev.getRandomDeviation());
						//
						// System.out.println("schnipp...");

						MessageBox mb = new MessageBox(	Display.getDefault().getActiveShell(),
														SWT.ICON_WARNING | SWT.YES | SWT.NO
																| SWT.CANCEL);
						mb.setText("Achtung!");
						mb.setMessage("Altes Skript speichern?");
						int response = mb.open();
						switch (response)
						{
						case SWT.YES:
							saveScript();
							script.clear();
							gui.refreshGUI();
							break;
						case SWT.NO: // System.out.println("no");
							script.clear();
							gui.refreshGUI();
							break;
						case SWT.CANCEL:
							break;
						default:
							System.out.println("Da passt was nicht!");
						}
					}
				});
				gui.getFlushBufferButton().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						MessageFileWriter.flushBuffer();
					}
				});
				gui.getHourSpinner().addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e)
					{
						script.setStartDate(script.getStartDate().hourOfDay()
								.setCopy(gui.getHourSpinner().getSelection()));
					}
				});
				gui.getMinuteSpinner().addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e)
					{

						script.setStartDate(script.getStartDate().minuteOfDay()
								.setCopy(gui.getHourSpinner().getSelection()));

					}
				});
				gui.getSecondSpinner().addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e)
					{

						script.setStartDate(script.getStartDate().secondOfDay()
								.setCopy(gui.getHourSpinner().getSelection()));
					}
				});
				gui.getTimeChoiceButtons()[0].addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						gui.setTimeSpinnerenabled(false);
					}
				});
				gui.getTimeChoiceButtons()[1].addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e)
					{
						gui.setTimeSpinnerenabled(true);
					}
				});
				gui.resizeGUI();

			}
		});
	}

	/**
	 * saves the <code>Script</code> a <code>FileDialog</code> will pop up an let the user choose
	 * how to save the <code>Script</code><br />
	 * the <code>Script</code> is saved via <code>ObjectOutputStream</code>
	 */
	private void saveScript()
	{
		this.script.setName(gui.getScriptNameText().getText());
		this.script.setDescription(gui.getScriptDescriptionText().getText());

		FileWriter writer;
		File file;
		try
		{
			FileDialog fd = new FileDialog(	Display.getDefault().getActiveShell(),
											SWT.SAVE);
			fd.setText("Save Skript");
			String[] filter = { "*.script" };
			fd.setFilterExtensions(filter);

			String path = fd.open();

			file = new File(path);

			writer = new FileWriter(file);
			writer.write(saveLoadScriptGson.toJson(script));
			writer.flush();

			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * loads a saved <code>Script</code> from <code>File</code> that is determined by a
	 * <code>FileDialog</code>
	 */
	private void loadScript()
	{
		File file;
		FileReader reader;

		try
		{
			FileDialog fd = new FileDialog(	Display.getDefault().getActiveShell(),
											SWT.OPEN);
			fd.setText("Open Skript");
			String[] filter = { "*.script" };
			fd.setFilterExtensions(filter);

			String path = fd.open();

			if(path == null)
				return;
			
			file = new File(path);
			reader = new FileReader(file);
			BufferedReader breader = new BufferedReader(reader);
			StringBuffer strbuff = new StringBuffer();
			String s;
			boolean firstline = true;
			while ((s = breader.readLine()) != null)
			{
				if(!firstline)
					strbuff.append("\n");
				else
					firstline = false;
				strbuff.append(s);
			}

			Script newScript = saveLoadScriptGson.fromJson(	strbuff.toString(),
															Script.class);

			this.script = newScript;
			gui.setScript(newScript);
			
			breader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Loads a saved <code>Script</code> specified by the given path.
	 * @param path Filename of the saved <code>Script</code>
	 * @return new <code>Script</code> instance generated out of the saved <code>Script</code>
	 */
	private Script loadScriptFromPath(String path)
	{
		Script scr = null;
		try
		{
			File file = new File(path);
			FileReader reader = new FileReader(file);
			BufferedReader breader = new BufferedReader(reader);
			StringBuffer strbuff = new StringBuffer();
			String s;
			Boolean firstline = true;
			while ((s = breader.readLine()) != null)
			{
				if(!firstline)
					strbuff.append("\n");
				else
					firstline = false;
				strbuff.append(s);
			}
			scr = saveLoadScriptGson.fromJson(	strbuff.toString(),
												Script.class);
			
			breader.close();

		}
		catch (Exception e)
		{

		}
		return scr;
	}

	/**
	 * Signals the <code>Script</code> to stop playing and closes all connection to ActiveMQ
	 */
	public void stopScriptSimulator()
	{
		System.setOut(backupOutStream);

		script.terminateEntry();
		MessageFileWriter.close();
//		System.out.println("alles gestoppt");
	}
}
