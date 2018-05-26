package mainAndGui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import problem.Type;
import problem.UserProblem;
/**
 * This class represents the section of the problem type.
 * @author Diana nr 72898
 **/
public class TypeVarSection {
	private Type dataType;
	private JComboBox<String> cmbVariableDataTypes;
	
	/**
	 * Returns a panel with a combo box for the user to select the problem type
	 **/
	public JPanel setVarType(DecisionVariablesSection decisionVariables, UserProblem problem, AlgorithmSelectionSection algorithmSelection) {

		JPanel pnlVarType = new JPanel(new FlowLayout());
		JLabel lblTypeMandatory = new JLabel("*");
		lblTypeMandatory.setForeground(Color.red);
		JLabel lblVarType = new JLabel("Problem variable's type: ");
		String[] variableDataTypes = {"Binary", "Double", "Integer" };
		cmbVariableDataTypes = new JComboBox<>(variableDataTypes);
		cmbVariableDataTypes.setSelectedIndex(-1);
		setCmbVariableDataTypesAction(decisionVariables, problem, algorithmSelection);
		pnlVarType.add(lblTypeMandatory);
		pnlVarType.add(lblVarType);
		pnlVarType.add(cmbVariableDataTypes);
		return pnlVarType;
	}
	private void setCmbVariableDataTypesAction(DecisionVariablesSection decisionVariables, UserProblem problem,
			AlgorithmSelectionSection algorithmSelection) {
		cmbVariableDataTypes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String data= (String) cmbVariableDataTypes.getSelectedItem();
					dataType = Type.valueOf(data.toUpperCase());
					decisionVariables.setDataType(dataType);
					algorithmSelection.setDataType(dataType);
					problem.setType(dataType);
				if (decisionVariables.getTblDecisionVariables().getRowCount() == 0) {
				} else {
					while (decisionVariables.getDtmDecisionVariables().getRowCount() > 0) {
						decisionVariables.getDtmDecisionVariables().removeRow(0);
					}
					decisionVariables.getSpnNumberOfDecisionVariables().setValue(0);
				}
			}
		});
	}
	public Type getDataType() {
		return dataType;
	}
	public JComboBox<String> getCmbVariableDataTypes() {
		return cmbVariableDataTypes;
	}
	
	public void setDataType(int index) {
		cmbVariableDataTypes.setSelectedIndex(index);
	}
	
}
