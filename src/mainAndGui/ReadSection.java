package mainAndGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import problem.UserProblem;
import support.XML_Editor;

public class ReadSection {
	private JButton btnReadXML;
	private JTextField txtFilePathXML;
	private FillForms fillForms = new FillForms();

	/**
	 * Returns the panel with a button that reads a problem from a XML file and writes the respective path.
	 * When clicked, reads a XML file and fills the form the data from a XML file
	 **/
	public JPanel readPanel(NameDescriptionSection nameDescriptionProblem, EmailSection email,
			UserProblem problem, XML_Editor xml, DecisionVariablesSection decisionVariables,
			TimeOptimizationSection timeOptimization) {
		JPanel pnlRead = new JPanel();
		btnReadXML = new JButton("Read from a XML File:");
		btnReadXML.setToolTipText("Read a XML file. This action will replace the fields already filled");
		txtFilePathXML = new JTextField();
		txtFilePathXML.setEditable(false);
		readFromFile(nameDescriptionProblem, email, problem, xml, decisionVariables, timeOptimization, pnlRead);
		pnlRead.add(btnReadXML);
		pnlRead.add(txtFilePathXML);
		return pnlRead;
	}

	/**
	 * Reads a XML file and fills the from with the data from a XML file 
	 **/
	private void readFromFile(NameDescriptionSection nameDescriptionProblem, EmailSection email, UserProblem problem,
			XML_Editor xml, DecisionVariablesSection decisionVariables, TimeOptimizationSection timeOptimization,
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
							timeOptimization, txtFilePathXML);
					fillForms.fillDecisionVariableForm(decisionVariables, problem);
				}
			}
		});
	}

	public JTextField getFilePathXML() {
		return txtFilePathXML;
	}

}