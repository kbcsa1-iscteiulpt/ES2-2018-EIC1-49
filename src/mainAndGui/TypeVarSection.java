package mainAndGui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TypeVarSection {
	private String dataType;
	public JPanel setVarType(DecisionVariablesSection decisionVariables) {

		JPanel pnlVarType = new JPanel(new FlowLayout());
		JLabel lblVarType = new JLabel("Problem variable's type: ");
		String[] variableDataTypes = { "Select a data type", "Binary", "Double", "Integer" };
		JComboBox<String> cmbVariableDataTypes = new JComboBox<>(variableDataTypes);
		cmbVariableDataTypes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataType = (String) cmbVariableDataTypes.getSelectedItem();
				if (decisionVariables.getTblDecisionVariables().getRowCount() == 0) {
					System.out.println("null");
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
	public String getDataType() {
		return dataType;
	}
	
}
