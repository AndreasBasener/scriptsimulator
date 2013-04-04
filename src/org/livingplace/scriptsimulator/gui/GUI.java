package org.livingplace.scriptsimulator.gui;


import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.MultiJsonReceiver;
import org.livingplace.scriptsimulator.gui.composite.EntryComposite;
import org.livingplace.scriptsimulator.script.Script;
import org.livingplace.scriptsimulator.script.Scriptable;

/**
 * <code>GUI</code> for <code>ScriptSimulator</code>
 * 
 * @author Andreas Basener
 * 
 */
public class GUI extends ApplicationWindow
{

	/**
	 * the <code>Script</code> displayed in this <code>GUI</code>.
	 */
	private Script			script;

	/**
	 * <code>TableViewer</code> for the <code>Script</code>
	 */
	private TableViewer		tableviewer;
	/**
	 * displays text and everything that goes to StdOut
	 */
	private StyledText		styledTextConsole;

	/**
	 * wraps the <code>Entry</code> <code>Composites</code>
	 */
	private EntryComposite	entrycomp;

	/**
	 * This <code>Composite</code> contains everything that is necessary for the <code>Entry</code>
	 */
	private Composite		entryTabWrapper;
	/**
	 * This <code>Composite</code> contains everything that is necessary for the options
	 */
	private Composite		optionsTabWrapper;
	/**
	 * Wraps <code>entryWrapper</code> and <code>optionsWrapper</code>.
	 */
	private Composite		globalWrapper;
	
	private Menu menu;

	/**
	 * <code>Spinner</code> for year selection
	 */
	private Spinner			yearSpinner;
	/**
	 * <code>Spinner</code> for month selection
	 */
	private Spinner			monthSpinner;
	/**
	 * <code>Spinner</code> for day selection
	 */
	private Spinner			daySpinner;
	/**
	 * <code>Spinner</code> for hour selection
	 */
	private Spinner			hourSpinner;
	/**
	 * <code>Spinner</code> for minute selection
	 */
	private Spinner			minuteSpinner;
	/**
	 * <code>Spinner</code> for second selection
	 */
	private Spinner			secondSpinner;

	/**
	 * name of the <code>Script</code>.
	 */
	private Text			scriptNameText;
	/**
	 * Description of the <code>Script</code>.
	 */
	private Text			scriptDescriptionText;

	/**
	 * <code>Spinner</code> for year <code>Script</code> playback speed
	 */
	private Spinner			scriptSpeedSpinner;
	/**
	 * <code>Spinner</code> for normal distribution of the <code>Entry</code> output
	 */
	private Spinner			deviationSpinner;

	/**
	 * this <code>Array</code> contains <code>RadioButtons</code> to select either automatic or
	 * manual time selection.
	 */
	private Button[]		timeChoiceButtons	= new Button[2];

	/**
	 * <code>Button</code> to generate a new <code>Scriptable</code>
	 */
	private Button			newEntryButton;
	/**
	 * <code>Button</code> to delete a selected <code>Scriptable</code>
	 */
	private Button			deleteEntryButton;
	/**
	 * <code>Button</code> to edit a selected <code>Scriptable</code>
	 */
	private Button			editEntryButton;
	/**
	 * <code>Button</code> to commit changes of a selected <code>Scriptable</code>
	 */
	private Button			commitEditButton;

	/**
	 * <code>Button</code> to play the <code>Script</code>
	 */
	private Button			playButton;

	/**
	 * <code>Button</code> to stop playing the <code>Script</code>
	 */
	private Button			stopButton;

	/**
	 * <code>Button</code> to save the <code>Script</code>
	 */
	private Button			saveButton;

	/**
	 * <code>Button</code> to load a <code>Script</code>
	 */
	private Button			loadButton;

	/**
	 * <code>Button</code> to throw the current <code>Script</code> away and create a new empty
	 * <code>Script</code>
	 */
	private Button			newScriptButton;
	
	/**
	 * Checkbox to indicate, whether the current time should be includen in JSON messages or not.
	 */
	private Button			sendTimeButton;

	/**
	 * Creates a new <code>GUI</code> instance.
	 * 
	 * @param parentShell
	 *            parent <code>Shell</code>
	 */
	public GUI(Shell parentShell)
	{
		super(parentShell);
	}

	/**
	 * Creates a new <code>GUI</code> instance.
	 * 
	 * @param parent
	 *            parent <code>Shell</code>
	 * @param script
	 *            main <code>Script</code>
	 */
	public GUI(Shell parent, Script script)
	{
		super(parent);
		this.script = script;

	}

	/**
	 * Creates and initializes all <code>Controls</code> of this <code>GUI</code>.
	 */
	@Override
	protected Control createContents(Composite parent)
	{
		GridData griddata;
		
		// set images for the Icon
		Image icons[] = { new Image(Display.getDefault(),
									getClass().getResourceAsStream(Helper.ICON16)),
				new Image(	Display.getDefault(),
							getClass().getResourceAsStream(Helper.ICON32)),
				new Image(	Display.getDefault(),
							getClass().getResourceAsStream(Helper.ICON48)),
				new Image(	Display.getDefault(),
							getClass().getResourceAsStream(Helper.ICON64)),
				new Image(	Display.getDefault(),
							getClass().getResourceAsStream(Helper.ICON128)),
				new Image(	Display.getDefault(),
							getClass().getResourceAsStream(Helper.ICON256)),
				new Image(	Display.getDefault(),
							getClass().getResourceAsStream(Helper.ICON512)) };
		getShell().setImages(icons);

		getShell().setText("Scriptsimulator");

		parent.setLayout(new FillLayout(SWT.VERTICAL));
		Control[] children = parent.getChildren();
		for (Control c : children)
		{
			// Aus irgendeinem Grund befindet sich in parent ein leeres Label,
			// welches da nicht hingehoehrt. Daher wird hier der parent geleert.
			c.dispose();
		}
		
		// ******** Menu          *****************
		menu = new Menu(getShell(), SWT.BAR);
		getShell().setMenuBar(menu);

		MenuItem fileMenuItem = new MenuItem(menu, SWT.CASCADE);
		fileMenuItem.setText("&Datei");
		
		Menu fileMenu = new Menu(getShell(), SWT.DROP_DOWN);
		fileMenuItem.setMenu(fileMenu);
		
		MenuItem multijsonItem = new MenuItem(fileMenu, SWT.PUSH);
		multijsonItem.setText("starte &MultiJsonReceiver");
		multijsonItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Display.getDefault().asyncExec(new Runnable() {
					
					@Override
					public void run() {

						MultiJsonReceiver receiver = new MultiJsonReceiver();
						receiver.setBlockOnOpen(true);
						receiver.open();
//						Display.getCurrent().dispose();
					}
				});
			}
		});
		
		
		
		// ******** globalWrapper *****************
		globalWrapper = new Composite(	parent,
										SWT.NONE);
		RowLayout wrapperRowLayout = new RowLayout(SWT.HORIZONTAL);
		wrapperRowLayout.fill = true;
		wrapperRowLayout.pack = false;
		wrapperRowLayout.wrap = false;
		globalWrapper.setLayout(wrapperRowLayout);

		// ******** entryWrapper ******************
		entryTabWrapper = new Composite(globalWrapper,
										SWT.NONE);
		entryTabWrapper.setLayout(new GridLayout(	1,
													false));
		entryTabWrapper.setSize(200,
								400);
		entryTabWrapper.setLayoutData(new RowData());

		// ******** optionsWrapper ****************
		optionsTabWrapper = new Composite(	globalWrapper,
											SWT.NONE);
		optionsTabWrapper.setLayout(new GridLayout(	1,
													false));
		optionsTabWrapper.setLayoutData(new RowData());

		// ******** Einstellungen *****************
		Group configComposite = new Group(	optionsTabWrapper,
											SWT.NONE);
		configComposite.setText("Einstellungen");

		griddata = new GridData(SWT.FILL,
								SWT.FILL,
								true,
								false);
		configComposite.setLayoutData(griddata);

		GridLayout configGridLayout = new GridLayout(	8,
														false);
		configComposite.setLayout(configGridLayout);

		Label aMQlabel = new Label(	configComposite,
									SWT.NONE);
		aMQlabel.setText("ActiveMQ IP");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		aMQlabel.setLayoutData(griddata);

		Combo aMQcombo = new Combo(	configComposite,
									SWT.DROP_DOWN);
		aMQcombo.add(Helper.ACTIVE_MQ_IP);
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		aMQcombo.setLayoutData(griddata);
		aMQcombo.select(0);

		Label mongolabel = new Label(	configComposite,
										SWT.NONE);
		mongolabel.setText("MongoDB IP");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		mongolabel.setLayoutData(griddata);

		Combo mongoQcombo = new Combo(	configComposite,
										SWT.DROP_DOWN);
		mongoQcombo.add(Helper.MONGO_DB_IP);
		// mongoQcombo.setEnabled(false);
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		mongoQcombo.setLayoutData(griddata);
		mongoQcombo.select(0);

		timeChoiceButtons[0] = new Button(	configComposite,
											SWT.RADIO);
		timeChoiceButtons[0].setText("aktuelle Zeit");
		timeChoiceButtons[0].setSelection(true);
		griddata = new GridData();
		griddata.horizontalSpan = 4;
		timeChoiceButtons[0].setLayoutData(griddata);

		timeChoiceButtons[1] = new Button(	configComposite,
											SWT.RADIO);
		timeChoiceButtons[1].setText("eigene Zeit");
		griddata = new GridData();
		griddata.horizontalSpan = 4;
		timeChoiceButtons[1].setLayoutData(griddata);

		sendTimeButton = new Button(configComposite, SWT.CHECK);
		sendTimeButton.setText("Zeit mitsenden");
		griddata= new GridData();
		griddata.horizontalSpan = 8;
		sendTimeButton.setLayoutData(griddata);
		sendTimeButton.setToolTipText("Wenn aktiviert, wird die aktuelle Zeit, zum Zeitpunkt des Events,\n"+
									  " als eigenes Feld in der JSON Nachricht übertragen.");
		
		Label startDatelabel = new Label(	configComposite,
											SWT.NONE);
		startDatelabel.setText("Datum");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		startDatelabel.setLayoutData(griddata);

		Label dayLabel = new Label(	configComposite,
									SWT.NONE);
		dayLabel.setText("D:");
		dayLabel.setLayoutData(new GridData());
		daySpinner = new Spinner(	configComposite,
									SWT.NONE);
		daySpinner.setMinimum(1);
		daySpinner.setMaximum(31);
		daySpinner.setSelection(script.getStartDate().getDayOfMonth());
		daySpinner.setLayoutData(new GridData());
		Label monthLabel = new Label(	configComposite,
										SWT.NONE);
		monthLabel.setText("M:");
		monthLabel.setLayoutData(new GridData());
		monthSpinner = new Spinner(	configComposite,
									SWT.NONE);
		monthSpinner.setMinimum(1);
		monthSpinner.setMaximum(12);
		monthSpinner.setSelection(script.getStartDate().getMonthOfYear());
		monthSpinner.setLayoutData(new GridData());
		Label yearLabel = new Label(configComposite,
									SWT.NONE);
		yearLabel.setText("Y:");
		yearLabel.setLayoutData(new GridData());
		yearSpinner = new Spinner(	configComposite,
									SWT.NONE);
		yearSpinner.setMinimum(1970);
		yearSpinner.setMaximum(9999);
		yearSpinner.setSelection(script.getStartDate().getYear());
		yearSpinner.setLayoutData(new GridData());

		Label startTimelabel = new Label(	configComposite,
											SWT.NONE);
		startTimelabel.setText("Zeit");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		startTimelabel.setLayoutData(griddata);
		Label hourLabel = new Label(configComposite,
									SWT.NONE);
		hourLabel.setText("h:");
		hourLabel.setLayoutData(new GridData());
		hourSpinner = new Spinner(	configComposite,
									SWT.NONE);
		hourSpinner.setMinimum(0);
		hourSpinner.setMaximum(23);
		hourSpinner.setSelection(script.getStartDate().getHourOfDay());
		hourSpinner.setLayoutData(new GridData());
		Label minuteLabel = new Label(	configComposite,
										SWT.NONE);
		minuteLabel.setText("m:");
		minuteLabel.setLayoutData(new GridData());
		minuteSpinner = new Spinner(configComposite,
									SWT.NONE);
		minuteSpinner.setMinimum(0);
		minuteSpinner.setMaximum(59);
		minuteSpinner.setSelection(script.getStartDate().getMinuteOfHour());
		minuteSpinner.setLayoutData(new GridData());
		Label secondLabel = new Label(	configComposite,
										SWT.NONE);
		secondLabel.setText("s:");
		secondLabel.setLayoutData(new GridData());
		secondSpinner = new Spinner(configComposite,
									SWT.NONE);
		secondSpinner.setMinimum(0);
		secondSpinner.setMaximum(59);
		secondSpinner.setSelection(script.getStartDate().getSecondOfMinute());
		secondSpinner.setLayoutData(new GridData());

		this.setTimeSpinnerenabled(false);

		Label scriptNameLabel = new Label(	configComposite,
											SWT.NONE);
		scriptNameLabel.setText("Skriptname");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		scriptNameLabel.setLayoutData(griddata);

		scriptNameText = new Text(	configComposite,
									SWT.SINGLE | SWT.BORDER);
		scriptNameText.setText(script.getName());
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		scriptNameText.setLayoutData(griddata);

		Label scriptDescriptionLabel = new Label(	configComposite,
													SWT.NONE);
		scriptDescriptionLabel.setText("Skriptbeschreibung");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		scriptDescriptionLabel.setLayoutData(griddata);

		scriptDescriptionText = new Text(	configComposite,
											SWT.SINGLE | SWT.BORDER);
		scriptDescriptionText.setText(script.getDescription());
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		scriptDescriptionText.setLayoutData(griddata);

		Label speedLabel = new Label(	configComposite,
										SWT.NONE);
		speedLabel.setText("Geschwindigkeit");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		speedLabel.setLayoutData(griddata);

		scriptSpeedSpinner = new Spinner(	configComposite,
											SWT.BORDER);
		scriptSpeedSpinner.setSelection(1);
		scriptSpeedSpinner.setMinimum(0);
		scriptSpeedSpinner.setMaximum(100);
		scriptSpeedSpinner
				.setToolTipText("Ist der Wert gleich 0, werden alle Eintraege sofort ausgefuehrt.\n"
								+ "Offset, und Andere, werden ignoriert.\n\n"
								+ "Ist der Wert groesser als 0, werden der Offset, und Andere, durch "
								+ "diesen Wert geteilt.");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		scriptSpeedSpinner.setLayoutData(griddata);

		Label distributionLabel = new Label(configComposite,
											SWT.NONE);
		distributionLabel.setText("Abweichungsfaktor [%]");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		distributionLabel.setLayoutData(griddata);

		deviationSpinner = new Spinner(	configComposite,
										SWT.NONE);
		deviationSpinner.setMinimum(0);
		deviationSpinner.setMaximum(100);
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		deviationSpinner.setLayoutData(griddata);

		// ************* Console ********************

		styledTextConsole = new StyledText(	optionsTabWrapper,
											SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		styledTextConsole.setLayoutData(new GridData(	SWT.FILL,
														SWT.FILL,
														true,
														true));
		styledTextConsole.setFont(new Font(	Display.getDefault(),
											"Courier New",
											10,
											SWT.NONE));

		styledTextConsole.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e)
			{
				styledTextConsole.setTopIndex(styledTextConsole.getLineCount() - 1);

			}
		});

		// ******** new Script Entry **************
		Group entryGroup = new Group(	entryTabWrapper,
										SWT.NONE);
		entryGroup.setText("Skripteintrag");
		entryGroup.setLayout(new GridLayout(1,
											false));

		entrycomp = new EntryComposite(	entryGroup,
										SWT.NONE);
		griddata = new GridData();
		griddata.horizontalAlignment = SWT.FILL;
		griddata.grabExcessHorizontalSpace = true;
		entrycomp.setLayoutData(griddata);

		griddata = new GridData(SWT.FILL,
								SWT.FILL,
								true,
								false);
		entryGroup.setLayoutData(griddata);

		// ******** Entry Buttons *****************
		Composite entryButtonsComposite = new Composite(entryTabWrapper,
														SWT.BORDER);
		RowLayout entryButtonLayout = new RowLayout(SWT.HORIZONTAL);
		entryButtonsComposite.setLayout(entryButtonLayout);
		griddata = new GridData(SWT.FILL,
								SWT.FILL,
								true,
								false);
		entryButtonsComposite.setLayoutData(griddata);

		newEntryButton = new Button(entryButtonsComposite,
									SWT.PUSH);
		newEntryButton.setText("Eintrag hinzufuegen");
		newEntryButton.setLayoutData(new RowData());

		deleteEntryButton = new Button(	entryButtonsComposite,
										SWT.PUSH);
		deleteEntryButton.setText("Eintrag loeschen");
		deleteEntryButton.setLayoutData(new RowData());

		editEntryButton = new Button(	entryButtonsComposite,
										SWT.PUSH);
		editEntryButton.setText("Eintrag editieren");
		editEntryButton.setLayoutData(new RowData());

		commitEditButton = new Button(	entryButtonsComposite,
										SWT.PUSH);
		commitEditButton.setText("Aenderung uebernehmen");
		commitEditButton.setEnabled(false);
		editEntryButton.setLayoutData(new RowData());

		// ******** Button Row ********************
		Composite playComposite = new Composite(entryTabWrapper,
												SWT.BORDER);
		RowLayout playrowLayot = new RowLayout(SWT.HORIZONTAL);
		playrowLayot.fill = true;
		playrowLayot.pack = false;
		playComposite.setLayout(playrowLayot);
		griddata = new GridData(SWT.FILL,
								SWT.FILL,
								true,
								false);
		playComposite.setLayoutData(griddata);

		playButton = new Button(playComposite,
								SWT.PUSH);
		playButton.setText("Play");
		playButton.setLayoutData(new RowData());

		stopButton = new Button(playComposite,
								SWT.NONE);
		stopButton.setText("Stop");
		stopButton.setLayoutData(new RowData());

		saveButton = new Button(playComposite,
								SWT.NONE);
		saveButton.setText("Save");
		saveButton.setLayoutData(new RowData());

		loadButton = new Button(playComposite,
								SWT.NONE);
		loadButton.setText("Load");
		loadButton.setLayoutData(new RowData());

		newScriptButton = new Button(	playComposite,
										SWT.NONE);
		newScriptButton.setText("New");
		newScriptButton.setLayoutData(new RowData());

		// ******** Script Table ******************
		Composite tableGroup = new Composite(	entryTabWrapper,
												SWT.NONE);
		TableColumnLayout tableLayout = new TableColumnLayout();

		tableGroup.setLayout(tableLayout);

		tableviewer = new TableViewer(tableGroup);
		tableviewer.getTable().setLinesVisible(true);
		tableviewer.getTable().setHeaderVisible(true);

		TableViewerColumn columnType = new TableViewerColumn(	tableviewer,
																SWT.NONE);
		columnType.getColumn().setText("Typ");
		columnType.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element)
			{
				Scriptable entry = (Scriptable) element;
				return entry.getClass().getSimpleName();
			}
		});
		tableLayout.setColumnData(	columnType.getColumn(),
									new ColumnPixelData(100));

		TableViewerColumn columnName = new TableViewerColumn(	tableviewer,
																SWT.NONE);
		columnName.getColumn().setText("Name");
		columnName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element)
			{
				Scriptable entry = (Scriptable) element;
				return entry.getName();
			}
		});
		tableLayout.setColumnData(	columnName.getColumn(),
									new ColumnPixelData(100));

		TableViewerColumn columndescr = new TableViewerColumn(	tableviewer,
																SWT.NONE);
		columndescr.getColumn().setText("Description");
		columndescr.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element)
			{
				Scriptable entry = (Scriptable) element;
				return entry.getDescription();
			}
		});
		tableLayout.setColumnData(	columndescr.getColumn(),
									new ColumnPixelData(200));

		TableViewerColumn columnOffset = new TableViewerColumn(	tableviewer,
																SWT.NONE);
		columnOffset.getColumn().setText("Offset");
		columnOffset.getColumn().setAlignment(SWT.RIGHT);
		columnOffset.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element)
			{
				Scriptable entry = (Scriptable) element;
				Period period = entry.getOffset();
				return String.format(	"%02d:%02d:%02d.%03d",
										period.getHours(),
										period.getMinutes(),
										period.getSeconds(),
										period.getMillis());
			}
		});
		tableLayout.setColumnData(	columnOffset.getColumn(),
									new ColumnPixelData(100));

		tableviewer.setContentProvider(new ArrayContentProvider());
		tableviewer.setInput(script.getEntryList());

		griddata = new GridData(SWT.FILL,
								SWT.FILL,
								true,
								true);
		griddata.minimumHeight = 200;
		tableGroup.setLayoutData(griddata);

		return globalWrapper;
	}

	/**
	 * Refreshes the <code>TableViewer</code> of this <code>GUI</code>.
	 */
	public void refreshGUI()
	{
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run()
			{
				tableviewer.refresh();
				// wrapper.layout(true);
				// wrapper.getParent().layout(true);
				// wrapper.getParent().pack();
			}
		});
	}

	/**
	 * Resizes the <code>GUI</code> so that all children are displayed correctly.
	 */
	public void resizeGUI()
	{
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run()
			{
				tableviewer.refresh();
				globalWrapper.getParent().layout(true);
				globalWrapper.getParent().pack();
			}
		});
	}

	/**
	 * Adds a new <code>Scriptable</code>-type with its own Composite to the
	 * <code>entryComposite</code>.
	 * 
	 * @param type
	 *            <code>String</code>-representation of the type
	 * @param composite
	 *            <code>Composite</code> to be displayed for this type
	 */
	public void addScriptableType(String type, Composite composite)
	{
		entrycomp.addScriptableComposite(	type,
											composite);
	}
	
	public boolean sendTime()
	{
		return sendTimeButton.getSelection();
	}

	/**
	 * Return the <code>EntryComposite</code>
	 * 
	 * @return <code>EntryComposite</code>
	 */
	public EntryComposite getEntryComposite()
	{
		return this.entrycomp;
	}

	/**
	 * Returns the main <code>Script</code>.
	 * 
	 * @return <code>Script</code>
	 */
	public Script getScript()
	{
		return script;
	}

	/**
	 * Sets the main <code>Script</code>.
	 * 
	 * @param script
	 */
	public void setScript(Script script)
	{
		this.script = script;

		this.scriptNameText.setText(script.getName());
		this.scriptDescriptionText.setText(script.getDescription());

		if (script.getEntryList() != null && tableviewer != null)
		{
			tableviewer.setInput(script.getEntryList());
		}
	}

	/**
	 * @return the scriptSpeed
	 */
	public Spinner getscriptSpeedSpinner()
	{
		return scriptSpeedSpinner;
	}

	/**
	 * @param spinner
	 *            the scriptSpeed to set
	 */
	public void setscriptSpeedSpinner(Spinner spinner)
	{
		this.scriptSpeedSpinner = spinner;
	}

	/**
	 * @return the deviationSpinner
	 */
	public Spinner getDeviationSpinner()
	{
		return deviationSpinner;
	}

	/**
	 * @param deviationSpinner
	 *            the deviationSpinner to set
	 */
	public void setDeviationSpinner(Spinner deviationSpinner)
	{
		this.deviationSpinner = deviationSpinner;
	}

	/**
	 * returns the commit <code>Button</code>.
	 * @return
	 */
	public Button getCommitEditButton()
	{
		return commitEditButton;
	}

	/**
	 * Returns the <code>TableViewer</code>
	 * @return
	 */
	public TableViewer getTableViewer()
	{
		return this.tableviewer;
	}

	/**
	 * Returns the hour <code>Spinner</code>
	 * @return
	 */
	public Spinner getHourSpinner()
	{
		return hourSpinner;
	}
	/**
	 * Sets the hour <code>Spinner</code>.
	 * @param hourSpinner
	 */
	public void setHourSpinner(Spinner hourSpinner)
	{
		this.hourSpinner = hourSpinner;
	}
	/**
	 * Returns the minute <code>Spinner</code>.
	 * @return
	 */
	public Spinner getMinuteSpinner()
	{
		return minuteSpinner;
	}
	/**
	 * Sets the minute <code>Spinner</code>.
	 * @param minuteSpinner
	 */
	public void setMinuteSpinner(Spinner minuteSpinner)
	{
		this.minuteSpinner = minuteSpinner;
	}
	/**
	 * Returns the second <code>Spinner</code>.
	 * @return
	 */
	public Spinner getSecondSpinner()
	{
		return secondSpinner;
	}
	/**
	 * Sets the second <code>Spinner</code>.
	 * @param secondSpinner
	 */
	public void setSecondSpinner(Spinner secondSpinner)
	{
		this.secondSpinner = secondSpinner;
	}
	/**
	 * Returns the script name <code>Text</code>.
	 * @return
	 */
	public Text getScriptNameText()
	{
		return scriptNameText;
	}
	/**
	 * Sets the script name <code>Text</code>.
	 * @param scriptNameText
	 */
	public void setScriptNameText(Text scriptNameText)
	{
		this.scriptNameText = scriptNameText;
	}
	/**
	 * Returns the script description <code>Text</code>.
	 * @return
	 */
	public Text getScriptDescriptionText()
	{
		return scriptDescriptionText;
	}
	/**
	 * Sets the script description <code>Text</code>.
	 * @param scriptDescriptionText
	 */
	public void setScriptDescriptionText(Text scriptDescriptionText)
	{
		this.scriptDescriptionText = scriptDescriptionText;
	}
	/**
	 * Returns the <code>StyledTextConsole</code>.
	 * @return
	 */
	public StyledText getStyledTextConsole()
	{
		return styledTextConsole;
	}
	/**
	 * Sets the <code>StyledTextConsole</code>.
	 * @param styledTextConsole
	 */
	public void setStyledTextConsole(StyledText styledTextConsole)
	{
		this.styledTextConsole = styledTextConsole;
	}

	/**
	 * Returns the time choice <code>Buttons</code>.
	 * @return
	 */
	public Button[] getTimeChoiceButtons()
	{
		return timeChoiceButtons;
	}
	/**
	 * Sets the time choice <code>Buttons</code>.
	 * @param timeChoiceButtons
	 */
	public void setTimeChoiceButtons(Button[] timeChoiceButtons)
	{
		this.timeChoiceButtons = timeChoiceButtons;
	}
	/**
	 * Enables or disables the time <code>Spinners</code>.
	 * @param bool
	 */
	public void setTimeSpinnerenabled(Boolean bool)
	{
		this.yearSpinner.setEnabled(bool);
		this.monthSpinner.setEnabled(bool);
		this.daySpinner.setEnabled(bool);
		this.hourSpinner.setEnabled(bool);
		this.minuteSpinner.setEnabled(bool);
		this.secondSpinner.setEnabled(bool);
	}

	/**
	 * Returns a <code>DateTime</code> generated from the time <code>Spinners</code>.
	 * 
	 * @return <code>DateTime</code>
	 */
	public org.joda.time.DateTime getDateTime()
	{
		DateTime dt = new DateTime(
				this.yearSpinner.getSelection(),
				this.monthSpinner.getSelection(),
				this. daySpinner.getSelection(),
				this. hourSpinner.getSelection(),
				this. minuteSpinner.getSelection(),
				this.secondSpinner.getSelection(),
				0);
		return dt;
	}

	/**
	 * @return the newEntryButton
	 */
	public Button getNewEntryButton()
	{
		return newEntryButton;
	}

	/**
	 * @param newEntryButton
	 *            the newEntryButton to set
	 */
	public void setNewEntryButton(Button newEntryButton)
	{
		this.newEntryButton = newEntryButton;
	}

	/**
	 * @return the deleteEntryButton
	 */
	public Button getDeleteEntryButton()
	{
		return deleteEntryButton;
	}

	/**
	 * @param deleteEntryButton
	 *            the deleteEntryButton to set
	 */
	public void setDeleteEntryButton(Button deleteEntryButton)
	{
		this.deleteEntryButton = deleteEntryButton;
	}

	/**
	 * @return the editEntryButton
	 */
	public Button getEditEntryButton()
	{
		return editEntryButton;
	}

	/**
	 * @param editEntryButton
	 *            the editEntryButton to set
	 */
	public void setEditEntryButton(Button editEntryButton)
	{
		this.editEntryButton = editEntryButton;
	}

	/**
	 * @return the playButton
	 */
	public Button getPlayButton()
	{
		return playButton;
	}

	/**
	 * @param playButton
	 *            the playButton to set
	 */
	public void setPlayButton(Button playButton)
	{
		this.playButton = playButton;
	}

	/**
	 * @return the stopButton
	 */
	public Button getStopButton()
	{
		return stopButton;
	}

	/**
	 * @param stopButton
	 *            the stopButton to set
	 */
	public void setStopButton(Button stopButton)
	{
		this.stopButton = stopButton;
	}

	/**
	 * @return the saveButton
	 */
	public Button getSaveButton()
	{
		return saveButton;
	}

	/**
	 * @param saveButton
	 *            the saveButton to set
	 */
	public void setSaveButton(Button saveButton)
	{
		this.saveButton = saveButton;
	}

	/**
	 * @return the loadButton
	 */
	public Button getLoadButton()
	{
		return loadButton;
	}

	/**
	 * @param loadButton
	 *            the loadButton to set
	 */
	public void setLoadButton(Button loadButton)
	{
		this.loadButton = loadButton;
	}

	/**
	 * @return the newButton
	 */
	public Button getNewScriptButton()
	{
		return newScriptButton;
	}

	/**
	 * @param newButton
	 *            the newButton to set
	 */
	public void setNewScriptButton(Button newButton)
	{
		this.newScriptButton = newButton;
	}

	/**
	 * @param commitEditButton
	 *            the commitEditButton to set
	 */
	public void setCommitEditButton(Button commitEditButton)
	{
		this.commitEditButton = commitEditButton;
	}

	/**
	 * Sets the time <code>Spinners</code> to the given <code>DateTime</code>.
	 * 
	 * @param dateTime
	 */
	public void setDateTime(org.joda.time.DateTime dateTime)
	{
		this.yearSpinner.setSelection(dateTime.getYear());
		this.monthSpinner.setSelection(dateTime.getMonthOfYear());
		this.daySpinner.setSelection(dateTime.getDayOfMonth());
		this.hourSpinner.setSelection(dateTime.getHourOfDay());
		this.minuteSpinner.setSelection(dateTime.getMinuteOfDay());
		this.secondSpinner.setSelection(dateTime.getSecondOfMinute());
	}
}
