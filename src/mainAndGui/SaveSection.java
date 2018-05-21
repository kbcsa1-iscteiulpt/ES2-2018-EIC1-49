package mainAndGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import problem.Time;
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

	private JButton btnSaveToXML;
	private TypeVarSection type;

	/**
	 * Returns a panel with a JButton that saves the configuration to a XML file.
	 **/
	public JPanel savePanel(NameDescriptionSection nameDescriptionProblem, EmailSection email, UserProblem problem,
			XMLEditor xml, DecisionVariablesSection decisionVariables, TimeOptimizationSection timeOptimization,
			TypeVarSection type) {
		this.type = type;
		JPanel pnlSave = new JPanel();
		saveXMLProblem(nameDescriptionProblem, email, problem, xml, decisionVariables, timeOptimization);
		btnSaveToXML.setToolTipText("Save your problem into a XML file");
		btnSaveToXML.setContentAreaFilled(false);
		pnlSave.add(btnSaveToXML, decisionVariables);
		return pnlSave;
	}

	/**
	 * Saves the problem in a XML file
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
					JFileChooser fchXMLSave = new JFileChooser();
					fchXMLSave.setDialogTitle("Save");
					fchXMLSave.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
					if (fchXMLSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						saveProblem(nameDescriptionProblem, problem, decisionVariables, email, timeOptimization, type);
						String filePath = fchXMLSave.getSelectedFile().getPath();
						if (!filePath.endsWith(".xml")) {
							filePath += ".xml";
						}
						System.out.println(problem);
						xml.write(filePath, problem);
					}
				}
			}
		});
	}

	/**
	 * Saves the problem with the data given by the fields that were filled.
	 **/
	public void saveProblem(NameDescriptionSection nameDescriptionProblem, UserProblem problem,
			DecisionVariablesSection decisionVariables, EmailSection email, TimeOptimizationSection timeOptimization,
			TypeVarSection typeVar) {
		String groupName = groupName(decisionVariables);
		List<Variable> variablesList = createVariableList(decisionVariables);

		setProblem(nameDescriptionProblem, problem, decisionVariables, email, timeOptimization, typeVar, groupName,
				variablesList);
	}

	private String groupName(DecisionVariablesSection decisionVariables) {
		String groupName = "";
		if (decisionVariables.getTxtNameOfDecisionVariablesGroup() != null) {
			groupName = decisionVariables.getTxtNameOfDecisionVariablesGroup().getText();
		}
		return groupName;
	}

	/**
	 * Sets the problem with the data given by the fields
	 **/
	private void setProblem(NameDescriptionSection nameDescriptionProblem, UserProblem problem,
			DecisionVariablesSection decisionVariables, EmailSection email, TimeOptimizationSection timeOptimization,
			TypeVarSection typeVar, String groupName, List<Variable> variablesList)
			throws java.lang.NumberFormatException {
		problem.setName(nameDescriptionProblem.getProblemName().getText());
		problem.setDescription(nameDescriptionProblem.getProblemDescription().getText());
		problem.setEmail(email.getEmail().getText());
		problem.setMax(new Time(Integer.parseInt(timeOptimization.getSpnMaxNumberOfDays().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnMaxNumberOfHours().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnMaxNumberOfMinutes().getValue().toString())));
		problem.setIdeal(new Time(Integer.parseInt(timeOptimization.getSpnIdealNumberOfDays().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnIdealNumberOfHours().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnIdealNumberOfMinutes().getValue().toString())));
		problem.setType(typeVar.getDataType());
		problem.setGroupName(groupName);
		problem.setNumberVariables(
				Integer.parseInt(decisionVariables.getSpnNumberOfDecisionVariables().getValue().toString()));
		problem.setVariables(variablesList);
	}

	/**
	 * Creates a list of the Variables that were filled
	 **/
	public List<Variable> createVariableList(DecisionVariablesSection decisionVariables) {
		List<Variable> variablesList = new ArrayList<Variable>();

		if (decisionVariables.getTblDecisionVariables() != null) {
			DefaultTableModel dtmDecisionVariables = (DefaultTableModel) decisionVariables.getTblDecisionVariables()
					.getModel();
			int numberOfRows = dtmDecisionVariables.getRowCount();
			if (type.getDataType().equals(Type.BINARY)) {
				for (int i = 0; i < numberOfRows; i++) {
					String decisionVariableName = decisionVariableName(dtmDecisionVariables, i);
					String decisionVariableBinaryValue= decisionVariableBinaryValue(dtmDecisionVariables, i);
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

	private String decisionVariableBinaryValue(DefaultTableModel dtmDecisionVariables, int i) {
		String decisionVariableValueBinary= "";
		if (dtmDecisionVariables.getValueAt(i, 1).toString() != null)
			decisionVariableValueBinary= dtmDecisionVariables.getValueAt(i, 1).toString();
		return decisionVariableValueBinary;
	}

	/**
	 * Checks if the decision variable name was filled, if it was, returns the
	 * restriction, else the returns an empty string
	 **/
	private String decisionVariableName(DefaultTableModel dtmDecisionVariables, int i) {
		String decisionVariableName = "";
		if (dtmDecisionVariables.getValueAt(i, 0).toString() != null)
			decisionVariableName = dtmDecisionVariables.getValueAt(i, 0).toString();
		return decisionVariableName;
	}


	/**
	 * Checks if the decision variable minumum value was filled, if it was, returns
	 * the restriction, else the returns an empty string
	 **/
	private String decisionVariableMinValue(DefaultTableModel dtmDecisionVariables, int i) {
		String decisionVariableMinValue = "";
		if (dtmDecisionVariables.getValueAt(i, 1).toString() != null) {
			decisionVariableMinValue = dtmDecisionVariables.getValueAt(i, 1).toString();
		}
		return decisionVariableMinValue;
	}

	/**
	 * Checks if the decision variable maximum value was filled, if it was, returns
	 * the restriction, else the returns an empty string
	 **/
	private String decisionVariableMaxValue(DefaultTableModel dtmDecisionVariables, int i) {
		String decisionVariableMaxValue = "";
		if (dtmDecisionVariables.getValueAt(i, 2).toString() != null) {
			decisionVariableMaxValue = dtmDecisionVariables.getValueAt(i, 2).toString();
		}
		return decisionVariableMaxValue;
	}

	/**
	 * Checks if the decision variable restriction was filled, if it was, returns
	 * the restriction, else the returns an empty string
	 **/
	private String decisionVariableRestriction(DefaultTableModel dtmDecisionVariables, int i) {
		String decisionVariableRestriction = "";
		if (dtmDecisionVariables.getValueAt(i, 3).toString() != null) {
			decisionVariableRestriction = dtmDecisionVariables.getValueAt(i, 3).toString();
		}
		return decisionVariableRestriction;
	}

	public JButton getBtnSaveToXML() {
		return btnSaveToXML;
	}
	
}
