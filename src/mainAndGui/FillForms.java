package mainAndGui;

import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import problem.UserProblem;
import problem.Variable;
/**
 * This class objetive is to fill the forms with a problem.
 * @author Diana nr 72898
 **/
public class FillForms {

	private DefaultTableModel dtmDecisionVariables;

	/**
	 * Fills the form with the data from a XML file.
	 **/
	public void fillInicialForm(String filePath, NameDescriptionSection nameDescriptionProblem,
			UserProblem problem, EmailSection email, TimeOptimizationSection timeOptimization,
			JTextField txtFilePathXML) {
		txtFilePathXML.setText(filePath);
		nameDescriptionProblem.getProblemName().setText(problem.getName());
		nameDescriptionProblem.getProblemDescription().setText(problem.getDescription());
		email.getEmail().setText(problem.getEmail());
		timeOptimization.getSpnMaxNumberOfDays().setValue(problem.getMax().getDays());
		timeOptimization.getSpnMaxNumberOfHours().setValue(problem.getMax().getHours());
		timeOptimization.getSpnMaxNumberOfMinutes().setValue(problem.getMax().getMinutes());
		timeOptimization.getSpnIdealNumberOfDays().setValue(problem.getIdeal().getDays());
		timeOptimization.getSpnIdealNumberOfDays().setValue(problem.getIdeal().getHours());
		timeOptimization.getSpnIdealNumberOfDays().setValue(problem.getIdeal().getMinutes());
	}

	/**
	 * Fills the decision variable table with the data from a XML file.
	 **/
	public void fillDecisionVariableForm(DecisionVariablesSection decisionVariables, UserProblem problem) {
		decisionVariables.getTxtNameOfDecisionVariablesGroup().setText(problem.getGroupName());
		decisionVariables.getSpnNumberOfDecisionVariables().setValue(problem.getNumberVariables());

		dtmDecisionVariables = new DefaultTableModel();
		decisionVariables.getTblDecisionVariables().setModel(dtmDecisionVariables);

		dtmDecisionVariables.addColumn("Name");
		dtmDecisionVariables.addColumn("Minimum Value");
		dtmDecisionVariables.addColumn("Maximum Value");
		dtmDecisionVariables.addColumn("Restrictions");
		String[] variableDataTypesXML = { "boolean", "byte", "char", "double", "float", "integer", "long", "real",
				"short", "String" };
		JComboBox<String> cmbVariableDataTypesXML = new JComboBox<>(variableDataTypesXML);

		for (int i = 0; i < problem.getNumberVariables(); i++) {
			List<Variable> variablesList = problem.getVariables();

			if (i < variablesList.size()) {
				dtmDecisionVariables.addRow(new Object[] { variablesList.get(i).getName(), variablesList.get(i).getMin(), variablesList.get(i).getMax(),
						variablesList.get(i).getRestriction() });
			} else {
				dtmDecisionVariables.addRow(new Object[] { "", "", "", "", "" });
			}
			decisionVariables.getTblDecisionVariables().getColumnModel().getColumn(1)
					.setCellEditor(new DefaultCellEditor(cmbVariableDataTypesXML));
		}
	}
}
