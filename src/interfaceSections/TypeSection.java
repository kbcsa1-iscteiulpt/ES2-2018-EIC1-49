package interfaceSections;

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
 * 
 * @author Diana nr 72898
 **/
public class TypeSection {
	private Type type;
	private JComboBox<String> cmbTypeSelection;

	/**
	 * Returns a panel with a combo box for the user to select the problem type
	 * @param decisionVariables
	 * @param problem
	 * @param algorithmSelection
	 * 
	 * @return pnlType
	 **/
	public JPanel setType(DecisionVariablesSection decisionVariables, UserProblem problem,
			AlgorithmSelectionSection algorithmSelection) {

		JPanel pnlType = new JPanel(new FlowLayout());
		JLabel lblTypeMandatory = new JLabel("*");
		lblTypeMandatory.setForeground(Color.red);
		JLabel lblType = new JLabel("Problem variable's type: ");
		String[] types = { "Binary", "Double", "Integer" };
		cmbTypeSelection = new JComboBox<>(types);
		cmbTypeSelection.setSelectedIndex(-1);
		setCmbTypeSelectionAction(decisionVariables, problem, algorithmSelection);
		pnlType.add(lblTypeMandatory);
		pnlType.add(lblType);
		pnlType.add(cmbTypeSelection);
		return pnlType;
	}

	/**
	 * When the problem type is selected, sets the problem type and notifies
	 * algorithm and decision variables sections. Also, clears the decision
	 * variables table when a problem type is selected
	 * 
	 * @param decisionVariables
	 * @param problem
	 * @param algorithmSelection
	 **/
	private void setCmbTypeSelectionAction(DecisionVariablesSection decisionVariables, UserProblem problem,
			AlgorithmSelectionSection algorithmSelection) {
		cmbTypeSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String data = (String) cmbTypeSelection.getSelectedItem();
				type = Type.valueOf(data.toUpperCase());
				decisionVariables.setDataType(type);
				algorithmSelection.setDataType(type);
				problem.setType(type);
				if (decisionVariables.getTblDecisionVariables().getRowCount() != 0) {
					while (decisionVariables.getDtmDecisionVariables().getRowCount() > 0) {
						decisionVariables.getDtmDecisionVariables().removeRow(0);
					}
					decisionVariables.getSpnNumberOfDecisionVariables().setValue(0);
				}
			}
		});
	}

	public Type getType() {
		return type;
	}

	public JComboBox<String> getCmbTypes() {
		return cmbTypeSelection;
	}

	public void setType(int index) {
		cmbTypeSelection.setSelectedIndex(index);
	}

}
