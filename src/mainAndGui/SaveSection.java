package mainAndGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import problem.Type;
import problem.UserProblem;
import problem.Variable;
import support.XMLEditor;

/**
 * This class represents the save file section.
 * 
 * @author Diana nr 72898
 **/
public class SaveSection {

	private SaveProcess saveProcess = new SaveProcess();
	private JButton btnSaveToXML;
	private TypeSection type;
	private String fileName;

	/**
	 * Returns a panel with a JButton that saves the configuration to a XML file.
	 * 
	 * @param nameDescriptionProblem
	 * @param email
	 * @param problem
	 * @param xml
	 * @param decisionVariables
	 * @param timeOptimization
	 * @param type
	 * @return pnlSave
	 **/
	public JPanel savePanel(NameDescriptionSection nameDescriptionProblem, EmailSection email, UserProblem problem,
			XMLEditor xml, DecisionVariablesSection decisionVariables, TimeOptimizationSection timeOptimization,
			TypeSection type) {
		this.type = type;
		JPanel pnlSave = new JPanel();
		saveXMLProblem(nameDescriptionProblem, email, problem, xml, decisionVariables, timeOptimization);
		btnSaveToXML.setToolTipText("Save your problem into a XML file");
		btnSaveToXML.setContentAreaFilled(false);
		pnlSave.add(btnSaveToXML, decisionVariables);
		return pnlSave;
	}

	/**
	 * Saves the problem in a XML file when clicking to save problem
	 * 
	 * @param nameDescriptionProblem
	 * @param email
	 * @param problem
	 * @param xml
	 * @param decisionVariables
	 * @param timeOptimization
	 **/
	private void saveXMLProblem(NameDescriptionSection nameDescriptionProblem, EmailSection email, UserProblem problem,
			XMLEditor xml, DecisionVariablesSection decisionVariables, TimeOptimizationSection timeOptimization)
			throws java.awt.HeadlessException {
		btnSaveToXML = new JButton("Save XML File");
		btnSaveToXML.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (nameDescriptionProblem.getProblemName().getText().isEmpty()
						|| nameDescriptionProblem.getProblemDescription().getText().isEmpty()
						|| !email.getBtnWriteEmail().isEnabled()) {
					JOptionPane.showMessageDialog(null, "Please fill all the mandatory fields", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					chooseFileToSave(nameDescriptionProblem, email, problem, xml, decisionVariables, timeOptimization);
				}
			}
		});
	}

	/**
	 * Saves file with default name
	 * 
	 * @param nameDescriptionProblem
	 * @param email
	 * @param problem
	 * @param xml
	 * @param decisionVariables
	 * @param timeOptimization
	 **/
	private void chooseFileToSave(NameDescriptionSection nameDescriptionProblem, EmailSection email,
			UserProblem problem, XMLEditor xml, DecisionVariablesSection decisionVariables,
			TimeOptimizationSection timeOptimization) {
		JFileChooser fchXMLSave = new JFileChooser();
		fchXMLSave.setDialogTitle("Save");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		fileName = nameDescriptionProblem.getProblemName().getText() + " " + dateFormat.format(date) + ".xml";
		fchXMLSave.setSelectedFile(new File(fileName));
		fchXMLSave.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
		if (fchXMLSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			saveProcess.saveProblem(nameDescriptionProblem, problem, decisionVariables, email, timeOptimization, type);
			String filePath = fchXMLSave.getSelectedFile().getPath();
			if (!filePath.endsWith(".xml")) {
				filePath += ".xml";
			}
			xml.write(filePath, problem);
		}
	}

	/**
	 * Saves the problem with the data given by the fields that were filled.
	 * @param nameDescriptionProblem
	 * @param problem
	 * @param decisionVariables
	 * @param email
	 * @param timeOptimization
	 * @param type
	 **/
	public void saveProblem(NameDescriptionSection nameDescriptionProblem, UserProblem problem,
			DecisionVariablesSection decisionVariables, EmailSection email, TimeOptimizationSection timeOptimization,
			TypeSection type) {
		saveProcess.saveProblem(nameDescriptionProblem, problem, decisionVariables, email, timeOptimization, type);
	}

	/**
	 * Creates a list of the Variables that were filled
	 * @param decisionVariables
	 * @return variablesList
	 **/
	public List<Variable> createVariableList(DecisionVariablesSection decisionVariables) {
		List<Variable> variablesList = new ArrayList<Variable>();

		if (decisionVariables.getTblDecisionVariables() != null) {
			DefaultTableModel dtmDecisionVariables = (DefaultTableModel) decisionVariables.getTblDecisionVariables()
					.getModel();
			int numberOfRows = dtmDecisionVariables.getRowCount();
			if (type.getType().equals(Type.BINARY)) {
				for (int i = 0; i < numberOfRows; i++) {
					String decisionVariableName = decisionVariableName(dtmDecisionVariables, i);
					String decisionVariableBinaryValue = decisionVariableBinaryValue(dtmDecisionVariables, i);
					variablesList.add(new Variable(decisionVariableName, decisionVariableBinaryValue));
				}
			} else {
				for (int i = 0; i < numberOfRows; i++) {
					String decisionVariableName = decisionVariableName(dtmDecisionVariables, i);
					String decisionVariableMinValue = decisionVariableMinValue(dtmDecisionVariables, i);
					String decisionVariableMaxValue = decisionVariableMaxValue(dtmDecisionVariables, i);
					String decisionVariableRestriction = decisionVariableRestriction(dtmDecisionVariables, i);
					variablesList.add(new Variable(decisionVariableName, decisionVariableMinValue,
							decisionVariableMaxValue, decisionVariableRestriction));
				}
			}
		}
		return variablesList;
	}

	/**
	 * Checks if the decision variable binary value was filled, if it was, returns
	 * the binary value, else the returns an empty string
	 * @param dtmDecisionVariables
	 * @param row
	 * @return decisionVariableValueBinary
	 **/
	private String decisionVariableBinaryValue(DefaultTableModel dtmDecisionVariables, int row) {
		String decisionVariableValueBinary = "";
		if (dtmDecisionVariables.getValueAt(row, 1).toString() != null)
			decisionVariableValueBinary = dtmDecisionVariables.getValueAt(row, 1).toString();
		return decisionVariableValueBinary;
	}

	/**
	 * Checks if the decision variable name was filled, if it was, returns the
	 * restriction, else the returns an empty string
	 * @param dtmDecisionVariables
	 * @param row
	 * @param decisionVariableName
	 **/
	private String decisionVariableName(DefaultTableModel dtmDecisionVariables, int row) {
		String decisionVariableName = "";
		if (dtmDecisionVariables.getValueAt(row, 0).toString() != null)
			decisionVariableName = dtmDecisionVariables.getValueAt(row, 0).toString();
		return decisionVariableName;
	}

	/**
	 * Checks if the decision variable minimum value was filled, if it was, returns
	 * the restriction, else the returns an empty string
	 * @param dtmDecisionVariables
	 * @param row
	 * @return decisionVariableMinValue
	 **/
	private String decisionVariableMinValue(DefaultTableModel dtmDecisionVariables, int row) {
		String decisionVariableMinValue = "";
		if (dtmDecisionVariables.getValueAt(row, 1).toString() != null) {
			decisionVariableMinValue = dtmDecisionVariables.getValueAt(row, 1).toString();
		}
		return decisionVariableMinValue;
	}

	/**
	 * Checks if the decision variable maximum value was filled, if it was, returns
	 * the restriction, else the returns an empty string
	 * 
	 * @param dtmDecisionVariables
	 * @param row
	 * @return decisionVariableMaxValue
	 **/
	private String decisionVariableMaxValue(DefaultTableModel dtmDecisionVariables, int row) {
		String decisionVariableMaxValue = "";
		if (dtmDecisionVariables.getValueAt(row, 2).toString() != null) {
			decisionVariableMaxValue = dtmDecisionVariables.getValueAt(row, 2).toString();
		}
		return decisionVariableMaxValue;
	}

	/**
	 * Checks if the decision variable restriction was filled, if it was, returns
	 * the restriction, else the returns an empty string
	 * @param dtmDecisionVariables
	 * @param row
	 * @return decisionVariableRestriction
	 **/
	private String decisionVariableRestriction(DefaultTableModel dtmDecisionVariables, int row) {
		String decisionVariableRestriction = "";
		if (dtmDecisionVariables.getValueAt(row, 3).toString() != null) {
			decisionVariableRestriction = dtmDecisionVariables.getValueAt(row, 3).toString();
		}
		return decisionVariableRestriction;
	}

	public JButton getBtnSaveToXML() {
		return btnSaveToXML;
	}

}
