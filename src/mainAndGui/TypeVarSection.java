package mainAndGui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import problem.Type;

public class TypeVarSection {
	private Type dataType;
	public JPanel setVarType(DecisionVariablesSection decisionVariables) {

		JPanel pnlVarType = new JPanel(new FlowLayout());
		JLabel lblVarType = new JLabel("Problem variable's type: ");
		String[] variableDataTypes = {"Binary", "Double", "Integer" };
		JComboBox<String> cmbVariableDataTypes = new JComboBox<>(variableDataTypes);
		cmbVariableDataTypes.setSelectedIndex(-1);
		cmbVariableDataTypes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String data= (String) cmbVariableDataTypes.getSelectedItem();
					dataType = Type.valueOf(data.toUpperCase());
				if (decisionVariables.getTblDecisionVariables().getRowCount() == 0) {
				} else {
					while (decisionVariables.getDtmDecisionVariables().getRowCount() > 0) {
						decisionVariables.getDtmDecisionVariables().removeRow(0);
					}
					decisionVariables.getSpnNumberOfDecisionVariables().setValue(0);
				}
			}
		});
		pnlVarType.add(lblVarType);
		pnlVarType.add(cmbVariableDataTypes);
		return pnlVarType;
	}
	public Type getDataType() {
		return dataType;
	}
	
}
