package mainAndGui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TypeVarSection {
	public JPanel setVarType(DecisionVariablesSection decisionVariables) {

		JPanel pnlVarType = new JPanel(new FlowLayout());
		JLabel lblVarType = new JLabel("Problem variable's type: ");
		String[] variableDataTypes = { "Select a data type", "Binary", "Double", "Integer" };
		JComboBox<String> cmbVariableDataTypes = new JComboBox<>(variableDataTypes);
		cmbVariableDataTypes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (decisionVariables.getTblDecisionVariables().getRowCount() == 0) {
					System.out.println("null");
				} else {
					int nrRows = decisionVariables.getTblDecisionVariables().getRowCount();
					for (int i = 0; i < nrRows; i++) {
						System.out.println(i);
						decisionVariables.getDtmDecisionVariables().removeRow(i);
						decisionVariables.getSpnNumberOfDecisionVariables().setValue(0);
					}
				}
			}
		});
		pnlVarType.add(lblVarType);
		pnlVarType.add(cmbVariableDataTypes);
		return pnlVarType;
	}
}
