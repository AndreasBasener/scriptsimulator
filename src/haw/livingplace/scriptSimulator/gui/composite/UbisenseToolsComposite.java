package haw.livingplace.scriptSimulator.gui.composite;

import haw.livingplace.scriptSimulator.ScriptableFactory;
import haw.livingplace.scriptSimulator.script.Scriptable;
import haw.livingplace.scriptSimulator.script.entry.UbisenseToolsEntry;
import haw.livingplace.scriptSimulator.script.entry.UbisenseToolsEntry.UbisenseToolType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * 
 * @author Andreas Basener
 * 
 */
public class UbisenseToolsComposite extends Composite implements ScriptableFactory
{
	private Combo	typeCombo;

	private Spinner	x1Spinner;
	private Spinner	y1Spinner;
	private Spinner	x2Spinner;
	private Spinner	y2Spinner;

	private Spinner	radiusSpinner;
	private Spinner	arcSpinner;

	private Spinner	durationSpinner;
	private Spinner	speedSpinner;

	public UbisenseToolsComposite(Composite parent, int style)
	{
		super(parent, style);

		GridData griddata;
		GridLayout gridLayout = new GridLayout(	4,
												false);
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);

		Label typeLabel = new Label(this,
									SWT.NONE);
		typeLabel.setText("Typ");
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		typeLabel.setLayoutData(griddata);

		typeCombo = new Combo(	this,
								SWT.READ_ONLY | SWT.MULTI | SWT.DROP_DOWN);
		for (UbisenseToolType t : UbisenseToolType.values())
		{
			typeCombo.add(t.name());
		}
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				String selection = typeCombo.getText();
				if (selection.equals(UbisenseToolType.PUNKT.name()))
				{
					x1Spinner.setEnabled(true);
					y1Spinner.setEnabled(true);
					x2Spinner.setEnabled(false);
					y2Spinner.setEnabled(false);
					arcSpinner.setEnabled(false);
					radiusSpinner.setEnabled(false);
					durationSpinner.setEnabled(true);
					speedSpinner.setEnabled(false);
				}
				else if (selection.equals(UbisenseToolType.LINIE.name()))
				{
					x1Spinner.setEnabled(true);
					y1Spinner.setEnabled(true);
					x2Spinner.setEnabled(true);
					y2Spinner.setEnabled(true);
					arcSpinner.setEnabled(false);
					radiusSpinner.setEnabled(false);
					durationSpinner.setEnabled(false);
					speedSpinner.setEnabled(true);
				}
				else if (selection.equals(UbisenseToolType.KREIS.name()))
				{
					x1Spinner.setEnabled(true);
					y1Spinner.setEnabled(true);
					x2Spinner.setEnabled(false);
					y2Spinner.setEnabled(false);
					arcSpinner.setEnabled(true);
					radiusSpinner.setEnabled(true);
					durationSpinner.setEnabled(false);
					speedSpinner.setEnabled(true);
				}
			}
		});
		typeCombo.select(0);
		griddata = new GridData();
		griddata.horizontalSpan = 2;
		typeCombo.setLayoutData(griddata);

		Label x1Label = new Label(	this,
									SWT.NONE);
		x1Label.setText("X1 [cm]");
		x1Label.setLayoutData(new GridData());

		x1Spinner = new Spinner(this,
								SWT.NONE);
		x1Spinner.setMaximum(1200);
		x1Spinner.setLayoutData(new GridData());

		Label y1Label = new Label(	this,
									SWT.NONE);
		y1Label.setText("Y1 [cm]");
		y1Label.setLayoutData(new GridData());

		y1Spinner = new Spinner(this,
								SWT.NONE);
		y1Spinner.setMaximum(1700);
		y1Spinner.setLayoutData(new GridData());

		Label x2Label = new Label(	this,
									SWT.NONE);
		x2Label.setText("X2 [cm]");
		x2Label.setLayoutData(new GridData());

		x2Spinner = new Spinner(this,
								SWT.NONE);
		x2Spinner.setMaximum(1200);
		x2Spinner.setLayoutData(new GridData());

		Label y2Label = new Label(	this,
									SWT.NONE);
		y2Label.setText("Y2 [cm]");
		y2Label.setLayoutData(new GridData());

		y2Spinner = new Spinner(this,
								SWT.NONE);
		y2Spinner.setMaximum(1700);
		y2Spinner.setLayoutData(new GridData());

		Label radiusLabel = new Label(	this,
										SWT.NONE);
		radiusLabel.setText("Radius [cm]");
		radiusLabel.setLayoutData(new GridData());

		radiusSpinner = new Spinner(this,
									SWT.NONE);
		radiusSpinner.setMaximum(800);
		radiusLabel.setLayoutData(new GridData());

		Label arcLabel = new Label(	this,
									SWT.NONE);
		arcLabel.setText("Kreisbogen [°]");
		arcLabel.setLayoutData(new GridData());

		arcSpinner = new Spinner(	this,
									SWT.NONE);
		arcSpinner.setMaximum(360);
		arcSpinner.setLayoutData(new GridData());

		Label durationLabel = new Label(this,
										SWT.NONE);
		durationLabel.setText("Dauer [s]");
		durationLabel.setLayoutData(new GridData());

		durationSpinner = new Spinner(	this,
										SWT.NONE);
		durationSpinner.setMaximum(86400);
		durationSpinner.setLayoutData(new GridData());

		Label speedLabel = new Label(	this,
										SWT.NONE);
		speedLabel.setText("Geschw. [cm/s]");
		speedLabel.setLayoutData(new GridData());

		speedSpinner = new Spinner(	this,
									SWT.NONE);
		speedSpinner.setMinimum(1);
		speedSpinner.setMaximum(1250);
		speedSpinner.setSelection(140);
		speedSpinner.setLayoutData(new GridData());
	}

	@Override
	public Scriptable generateScriptable()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scriptable generateScriptable(EntryComposite comp)
	{
		return new UbisenseToolsEntry(	comp.getOffset(),
										comp.getEntryName(),
										comp.getDescription(),
										UbisenseToolType.valueOf(typeCombo.getText()),
										x1Spinner.getSelection(),
										y1Spinner.getSelection(),
										x2Spinner.getSelection(),
										y2Spinner.getSelection(),
										radiusSpinner.getSelection(),
										arcSpinner.getSelection(),
										durationSpinner.getSelection(),
										speedSpinner.getSelection());
	}

	@Override
	public void setToScriptable(Scriptable scriptable)
	{
		UbisenseToolsEntry entry = (UbisenseToolsEntry) scriptable;
		typeCombo.setText(entry.getUbiToolType().name());
		x1Spinner.setSelection(entry.getX1());
		y1Spinner.setSelection(entry.getY1());
		x2Spinner.setSelection(entry.getX2());
		y2Spinner.setSelection(entry.getY2());
		radiusSpinner.setSelection(entry.getRadius());
		arcSpinner.setSelection(entry.getArc());
		durationSpinner.setSelection(entry.getToolDuration());
		speedSpinner.setSelection(entry.getToolSpeed());

	}

}
