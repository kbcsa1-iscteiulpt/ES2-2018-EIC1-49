package mainAndGui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * This class represents the ideal and max time of the problem selection
 * section.
 * 
 * @author Diana nr 72898
 **/
public class TimeOptimizationSection {

	private JSpinner spnMaxNumberOfDays;
	private JSpinner spnMaxNumberOfHours;
	private JSpinner spnMaxNumberOfMinutes;
	private JSpinner spnIdealNumberOfDays;
	private JSpinner spnIdealNumberOfHours;
	private JSpinner spnIdealNumberOfMinutes;

	/**
	 * Returns a panel with a JSpinner for user to select the maximum time to wait
	 * for the optimization.
	 * @return pnlMaxTime
	 **/
	public JPanel maxTimePanel() {
		JPanel pnlMaxTime = new JPanel(new FlowLayout());
		JLabel lblMaxTime = new JLabel("Maximum time for optimization:");

		JLabel lblNumberofDays = new JLabel("Days");
		JLabel lblNumberofHours = new JLabel("Hours");
		JLabel lblNumberofMinutes = new JLabel("Minutes");

		spnMaxNumberOfDays = new JSpinner(new SpinnerNumberModel(0, 0, 31, 1));
		spnMaxNumberOfHours = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
		spnMaxNumberOfMinutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		pnlMaxTime.add(lblMaxTime);
		pnlMaxTime.add(spnMaxNumberOfDays);
		pnlMaxTime.add(lblNumberofDays);
		pnlMaxTime.add(spnMaxNumberOfHours);
		pnlMaxTime.add(lblNumberofHours);
		pnlMaxTime.add(spnMaxNumberOfMinutes);
		pnlMaxTime.add(lblNumberofMinutes);
		return pnlMaxTime;
	}

	/**
	 * Returns a panel with a JSpinner for user to select the ideal time to wait for
	 * the optimization.
	 * @return pnlIdealTime
	 **/
	public JPanel idealTimePanel() {
		JPanel pnlIdealTime = new JPanel(new FlowLayout());
		JLabel lblIdealTime = new JLabel("Ideal time for optimization:");

		JLabel lblIdealNumberofDays = new JLabel("Days");
		JLabel lblIdealNumberofHours = new JLabel("Hours");
		JLabel lblIdealNumberofMinutes = new JLabel("Minutes");

		spnIdealNumberOfDays = new JSpinner(new SpinnerNumberModel(0, 0, 31, 1));
		spnIdealNumberOfHours = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
		spnIdealNumberOfMinutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		pnlIdealTime.add(lblIdealTime);
		pnlIdealTime.add(spnIdealNumberOfDays);
		pnlIdealTime.add(lblIdealNumberofDays);
		pnlIdealTime.add(spnIdealNumberOfHours);
		pnlIdealTime.add(lblIdealNumberofHours);
		pnlIdealTime.add(spnIdealNumberOfMinutes);
		pnlIdealTime.add(lblIdealNumberofMinutes);
		return pnlIdealTime;
	}

	public JSpinner getSpnMaxNumberOfDays() {
		return spnMaxNumberOfDays;
	}

	public JSpinner getSpnMaxNumberOfHours() {
		return spnMaxNumberOfHours;
	}

	public JSpinner getSpnMaxNumberOfMinutes() {
		return spnMaxNumberOfMinutes;
	}

	public JSpinner getSpnIdealNumberOfDays() {
		return spnIdealNumberOfDays;
	}

	public JSpinner getSpnIdealNumberOfHours() {
		return spnIdealNumberOfHours;
	}

	public JSpinner getSpnIdealNumberOfMinutes() {
		return spnIdealNumberOfMinutes;
	}

}
