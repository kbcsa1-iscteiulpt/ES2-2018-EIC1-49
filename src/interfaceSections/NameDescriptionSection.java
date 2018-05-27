package interfaceSections;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class represents the section of the problem name and description.
 * @author Diana nr 72898
 **/
public class NameDescriptionSection {
	private JTextField txtProblemName;
	private JTextArea txaProblemDescription;

	/**
	 * Returns a panel with a JTextField for user to fill with the problem's name.
	 * @return pnlProblemName
	 **/
	public JPanel problemNamePanel() {
		JPanel pnlProblemName = new JPanel();
		JLabel lblProblemNameMandatory = new JLabel("*");
		lblProblemNameMandatory.setForeground(Color.red);
		JLabel lblProblemName = new JLabel("Problem's Name:");
		txtProblemName = new JTextField();
		txtProblemName.setToolTipText("Enter the problem's name here.");
		txtProblemName.setColumns(20);
		pnlProblemName.add(lblProblemNameMandatory);
		pnlProblemName.add(lblProblemName);
		pnlProblemName.add(txtProblemName);
		return pnlProblemName;
	}

	/**
	 * Returns a panel with a JTextField for user to fill with the problem's
	 * description.
	 * @return pnlProblemDescription
	 **/
	public JPanel problemDescriptionPanel() {
		JPanel pnlProblemDescription = new JPanel(new FlowLayout());
		JLabel lblProblemDescriptionMandatory = new JLabel("*");
		lblProblemDescriptionMandatory.setForeground(Color.red);
		JLabel lblProblemDescription = new JLabel("Problem's Description:");
		txaProblemDescription = new JTextArea(4, 60);
		txaProblemDescription.setToolTipText("Describe your problem here.");
		JScrollPane scrProblemDescription = new JScrollPane(txaProblemDescription);
		txaProblemDescription.setLineWrap(true);
		pnlProblemDescription.add(lblProblemDescriptionMandatory);
		pnlProblemDescription.add(lblProblemDescription);
		pnlProblemDescription.add(scrProblemDescription);
		return pnlProblemDescription;
	}
	
	public JTextField getProblemName() {
		return txtProblemName;
	}
	
	public JTextArea getProblemDescription() {
		return txaProblemDescription;
	}
}
