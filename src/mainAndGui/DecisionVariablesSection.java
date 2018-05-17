package mainAndGui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import problem.UserProblem;
import problem.Variable;

public class DecisionVariablesSection {
	private JButton btnDecisionVariables;
	private JSpinner spnNumberOfDecisionVariables;
	private JTextField txtNameOfDecisionVariablesGroup;
	private JTable tblDecisionVariables;
	private DefaultTableModel dtmDecisionVariables;
	private JButton btnDecisionVariablesFinish;

	/**
	 * Returns a panel with a JSpinner to select the number of decision variables
	 * and button. When clicked, a new frame is displayed to write the decision
	 * variable group name and to fill the table of the variable decision.
	 **/

	public JPanel decisionVarPanel(UserProblem problem) {
		JPanel pnlDecisionVar = new JPanel(new FlowLayout());

		btnDecisionVariables = new JButton("Decision Variables");
		btnDecisionVariables.setContentAreaFilled(false);
		spnNumberOfDecisionVariables = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		txtNameOfDecisionVariablesGroup = new JTextField();
		tblDecisionVariables = new JTable();
		JFrame decisionVarFrame = new JFrame("Decision Variables");
		FrameSize.setFrame(decisionVarFrame, 0.5);
		setDecisionFrame(decisionVarFrame, problem);
		btnDecisionVariables.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				decisionVarFrame.setVisible(true);
			}

		});

		pnlDecisionVar.add(btnDecisionVariables);
		return pnlDecisionVar;
	}

	/**
	 * Adds content to the frame given with a table to write the decision variable
	 * group name and to fill the table of the variable decision. Each decision
	 * variable has a name, type, and potential intervals and restrictions.
	 **/
	private void setDecisionFrame(JFrame decisionVarFrame, UserProblem problem) {
		JPanel pnlDecision = new JPanel(new BorderLayout());

		JPanel pnlNameOfDecisionVariablesGroup = new JPanel();
		JLabel lblNameOfDecisionVariablesGroup = new JLabel("Name of Decision Variable Group:");
		txtNameOfDecisionVariablesGroup.setColumns(25);
		JLabel lblNumberOfDecisionVariable = new JLabel("Number of Decision Variables");
		pnlNameOfDecisionVariablesGroup.add(lblNumberOfDecisionVariable, BorderLayout.NORTH);
		pnlNameOfDecisionVariablesGroup.add(spnNumberOfDecisionVariables, BorderLayout.NORTH);

		dtmDecisionVariables = new DefaultTableModel();

		tblDecisionVariables.setModel(dtmDecisionVariables);

		dtmDecisionVariables.addColumn("Name");
		dtmDecisionVariables.addColumn("Type");
		dtmDecisionVariables.addColumn("Minimum Value");
		dtmDecisionVariables.addColumn("Maximum Value");
		dtmDecisionVariables.addColumn("Restrictions");

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Please use ; to separate the restrictions");
		tblDecisionVariables.getColumnModel().getColumn(4).setCellRenderer(renderer);
		String[] variableDataTypes = { "Binary", "Double", "Integer"};
		JComboBox<String> cmbVariableDataTypes = new JComboBox<>(variableDataTypes);

		if (Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString()) != 0) {
			for (int i = 0; i < Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString()); i++) {
				dtmDecisionVariables.addRow(new Object[] { "", "", "", "", "" });
				tblDecisionVariables.getColumnModel().getColumn(1)
						.setCellEditor(new DefaultCellEditor(cmbVariableDataTypes));
			}
		}

		spnNumberOfDecisionVariables.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int spinnerValue = Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString());
				if (dtmDecisionVariables.getRowCount() < spinnerValue) {
					for (int j = 0; j < spinnerValue - dtmDecisionVariables.getRowCount(); j++) {
						dtmDecisionVariables.addRow(new Object[] { "", "", "", "", "" });
						tblDecisionVariables.getColumnModel().getColumn(1)
								.setCellEditor(new DefaultCellEditor(cmbVariableDataTypes));
					}
				} else if (dtmDecisionVariables.getRowCount() > spinnerValue) {
					for (int k = 0; k < Math.abs(spinnerValue - dtmDecisionVariables.getRowCount()); k++) {
						dtmDecisionVariables.removeRow(dtmDecisionVariables.getRowCount() - 1);
						tblDecisionVariables.revalidate();
					}
				}

			}
		});
		pnlNameOfDecisionVariablesGroup.add(lblNameOfDecisionVariablesGroup);
		pnlNameOfDecisionVariablesGroup.add(txtNameOfDecisionVariablesGroup);
		pnlDecision.add(pnlNameOfDecisionVariablesGroup, BorderLayout.NORTH);
		pnlDecision.add(new JScrollPane(tblDecisionVariables));
		btnDecisionVariablesFinish = new JButton("Finish");
		btnDecisionVariablesFinish.setContentAreaFilled(false);
		ImageIcon icoFinish = new ImageIcon(((new ImageIcon("./src/images/finish.png")).getImage())
				.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
		btnDecisionVariablesFinish.setIcon(icoFinish);
		decisionVariablesFinish(problem);
		pnlDecision.add(btnDecisionVariablesFinish, BorderLayout.PAGE_END);
		decisionVarFrame.add(pnlDecision);
	}

	private void decisionVariablesFinish(UserProblem problem) {
		btnDecisionVariablesFinish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean varsReady = true;
				for (int i = 0; i < tblDecisionVariables.getRowCount(); i++) {
					if (dtmDecisionVariables.getValueAt(i, 0).toString().equals("")
							|| dtmDecisionVariables.getValueAt(i, 1).toString().equals("")
							|| dtmDecisionVariables.getValueAt(i, 2).toString().equals("")
							|| dtmDecisionVariables.getValueAt(i, 3).toString().equals("")) {
						JOptionPane.showMessageDialog(null, "Please fill the variable's name, type and interval fields",
								"Warning", JOptionPane.WARNING_MESSAGE);
						varsReady = false;
						break;
					}
				}

				if (varsReady) {
					if (tblDecisionVariables.getCellEditor() != null) {
						tblDecisionVariables.getCellEditor().stopCellEditing();
					}

					for (int j = 0; j < tblDecisionVariables.getRowCount(); j++) {
						Variable variable = new Variable(dtmDecisionVariables.getValueAt(j, 0).toString(),
								dtmDecisionVariables.getValueAt(j, 1).toString(),
								dtmDecisionVariables.getValueAt(j, 2).toString(),
								dtmDecisionVariables.getValueAt(j, 3).toString(),
								dtmDecisionVariables.getValueAt(j, 4).toString());
						problem.addVariable(variable);
					}
				}
			}
		});
	}

	public JSpinner getSpnNumberOfDecisionVariables() {
		return spnNumberOfDecisionVariables;
	}

	public JTextField getTxtNameOfDecisionVariablesGroup() {
		return txtNameOfDecisionVariablesGroup;
	}

	public JTable getTblDecisionVariables() {
		return tblDecisionVariables;
	}

	public DefaultTableModel getDtmDecisionVariables() {
		return dtmDecisionVariables;
	}

}
