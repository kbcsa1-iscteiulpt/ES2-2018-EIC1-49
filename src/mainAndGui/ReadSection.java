package mainAndGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import problem.UserProblem;
import support.XMLEditor;

/**
 * This class represents the read file section.
 * @author Diana nr 72898
 **/
public class ReadSection {
	private JButton btnReadXML;
	private JTextField txtFilePathXML;
	private FillForms fillForms = new FillForms();

	/**
	 * Returns the panel with a button that reads a problem from a XML file and writes the respective path.
	 * When clicked, reads a XML file and fills the form the data from a XML file
	 * @param problemDataTypeSection 
	 **/
	public JPanel readPanel(NameDescriptionSection nameDescriptionProblem, EmailSection email,
			UserProblem problem, XMLEditor xml, TypeVarSection problemDataTypeSection, DecisionVariablesSection decisionVariables,
			TimeOptimizationSection timeOptimization) {
		JPanel pnlRead = new JPanel();
		btnReadXML = new JButton("Read from a XML File:");
		btnReadXML.setContentAreaFilled(false);
		btnReadXML.setToolTipText("Read a XML file. This action will replace the fields already filled");
		txtFilePathXML = new JTextField();
		txtFilePathXML.setEditable(false);
		readFromFile(nameDescriptionProblem, email, problem, xml, problemDataTypeSection, decisionVariables, timeOptimization, pnlRead);
		pnlRead.add(btnReadXML);
		pnlRead.add(txtFilePathXML);
		return pnlRead;
	}

	/**
	 * Reads a XML file and fills the from with the data from a XML file 
	 **/
	private void readFromFile(NameDescriptionSection nameDescriptionProblem, EmailSection email, UserProblem problem,
			XMLEditor xml, TypeVarSection problemDataTypeSection, DecisionVariablesSection decisionVariables, TimeOptimizationSection timeOptimization,
			JPanel pnlRead) {
		btnReadXML.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchReadXML = new JFileChooser();
				if (fchReadXML.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String filePath = fchReadXML.getSelectedFile().getPath();
					pnlRead.add(txtFilePathXML);
					pnlRead.revalidate();
					fillForms.fillInicialForm(filePath, nameDescriptionProblem, xml.read(filePath), email,
							problemDataTypeSection, timeOptimization, txtFilePathXML);
					fillForms.fillDecisionVariableForm(decisionVariables, xml.read(filePath));
				}
			}
		});
	}

	public JTextField getFilePathXML() {
		return txtFilePathXML;
	}

	public JButton getBtnReadXML() {
		return btnReadXML;
	}
	

}
