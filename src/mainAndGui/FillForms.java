package mainAndGui;

import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import problem.Type;
import problem.UserProblem;
import problem.Variable;

/**
 * This class fills the forms with a problem read from a XML file.
 * 
 * @author Diana nr 72898
 **/
public class FillForms {

	private DefaultTableModel dtmDecisionVariables;
	private JTable tblDecisionVariables;

	/**
	 * Fills the form with the data from a XML file.
	 **/
	public void fillInicialForm(String filePath, NameDescriptionSection nameDescriptionProblem, UserProblem problem,
			EmailSection email, TypeVarSection typeVar, TimeOptimizationSection timeOptimization,
			JTextField txtFilePathXML) {
		txtFilePathXML.setText(filePath);
		nameDescriptionProblem.getProblemName().setText(problem.getName());
		nameDescriptionProblem.getProblemDescription().setText(problem.getDescription());
		email.getEmail().setText(problem.getEmail()); 
		typeVar.getCmbVariableDataTypes().setSelectedIndex(problem.getType().ordinal());
		timeOptimization.getSpnMaxNumberOfDays().setValue(problem.getMax().getDays());
		timeOptimization.getSpnMaxNumberOfHours().setValue(problem.getMax().getHours());
		timeOptimization.getSpnMaxNumberOfMinutes().setValue(problem.getMax().getMinutes());
		timeOptimization.getSpnIdealNumberOfDays().setValue(problem.getIdeal().getDays());
		timeOptimization.getSpnIdealNumberOfHours().setValue(problem.getIdeal().getHours());
		timeOptimization.getSpnIdealNumberOfMinutes().setValue(problem.getIdeal().getMinutes());
	}

	/**
	 * Fills the decision variable table with the data from a XML file.
	 **/
	public void fillDecisionVariableForm(DecisionVariablesSection decisionVariables, UserProblem problem) {
		if(!problem.getType().equals(Type.BINARY)){
			decisionVariables.getSpnNumberOfDecisionVariables().setValue(problem.getNumberVariables());
		}else {
			decisionVariables.getSpnNumberOfDecisionVariables().setValue(1);
		}
			decisionVariables.getTxtNameOfDecisionVariablesGroup().setText(problem.getGroupName());
		dtmDecisionVariables = decisionVariables.getDtmDecisionVariables();
		dtmDecisionVariables.setColumnCount(0);
		tblDecisionVariables = decisionVariables.getTblDecisionVariables();
		tblDecisionVariables.setModel(dtmDecisionVariables);
		dtmDecisionVariables.addColumn("Name");
		if (problem.getType().equals(Type.BINARY)) {
			dtmDecisionVariables.addColumn("Value");
		} else {
			dtmDecisionVariables.addColumn("Minimum Value");
			dtmDecisionVariables.addColumn("Maximum Value");
			dtmDecisionVariables.addColumn("Restrictions");
		}

		List<Variable> variablesList = problem.getVariables();
		
		if (!problem.getType().equals(Type.BINARY)) {
			for (int i = 0; i < problem.getNumberVariables(); i++) {
				if (i < variablesList.size()) {
					tblDecisionVariables.setValueAt(variablesList.get(i).getName(), i, 0);
					tblDecisionVariables.setValueAt(variablesList.get(i).getMinRange(), i, 1);
					tblDecisionVariables.setValueAt(variablesList.get(i).getMaxRange(), i, 2);
					tblDecisionVariables.setValueAt(variablesList.get(i).getRestriction(), i, 3);
				}
			}
		} else {
			System.out.println(problem.getNumberVariables()+"+++++");
			for (int i = 0; i < problem.getNumberVariables(); i++) {
				if (i < variablesList.size()) {
					tblDecisionVariables.setValueAt(variablesList.get(i).getName(), i, 0);
					tblDecisionVariables.setValueAt(variablesList.get(i).getBinaryValue(), i, 1);
					}
			}
		}
		decisionVariables.setFilled(true);
	}
}
