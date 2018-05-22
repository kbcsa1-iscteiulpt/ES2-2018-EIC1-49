package mainAndGui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import problem.Type;
import problem.UserProblem;
import problem.Variable;

/**
 * This class represents the section of the problem decision variables.
 * 
 * @author Diana nr 72898
 **/
public class DecisionVariablesSection {
	private JButton btnDecisionVariables;
	private JSpinner spnNumberOfDecisionVariables;
	private JTextField txtNameOfDecisionVariablesGroup;
	private JTable tblDecisionVariables;
	private DefaultTableModel dtmDecisionVariables;
	private JButton btnDecisionVariablesFinish;
	private JFrame decisionVarFrame;
	private Type dataType = null;
	private Type previousDataType = null;
	private boolean filled = false;
	private AlgorithmSelectionSection algorithmSelection;

	/**
	 * Returns a panel with a JSpinner to select the number of decision variables
	 * and button. When clicked, a new frame is displayed to write the decision
	 * variable group name and to fill the table of the variable decision.
	 * 
	 **/

	public JPanel decisionVarPanel(UserProblem problem, JFrame frame, AlgorithmSelectionSection algorithmSelection) {
		this.algorithmSelection = algorithmSelection;
		JPanel pnlDecisionVar = new JPanel(new FlowLayout());
		btnDecisionVariables = new JButton("Decision Variables");
		btnDecisionVariables.setContentAreaFilled(false);
		decisionVarFrame = new JFrame("Decision Variables");
		tblDecisionVariables = new JTable();
		setDecisionFrame(decisionVarFrame, problem);
		btnDecisionVariables.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataType != null) {
					if (filled == false) {
						if (previousDataType == null || !previousDataType.equals(dataType)) {
							decisionVarFrame = new JFrame("Decision Variables");
							setDecisionFrame(decisionVarFrame, problem);
							previousDataType = dataType;
						} 
					}else {
						if (previousDataType!= null && !previousDataType.equals(dataType)) {
							decisionVarFrame = new JFrame("Decision Variables");
							setDecisionFrame(decisionVarFrame, problem);
							previousDataType = dataType;
						}
					}
					FrameSize.setFrame(decisionVarFrame, 1);
					decisionVarFrame.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null, "Please, select the problem data type", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
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
		spnNumberOfDecisionVariables = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		txtNameOfDecisionVariablesGroup = new JTextField();
		JLabel lblNameOfDecisionVariablesGroup = new JLabel("Name of Decision Variable Group:");
		txtNameOfDecisionVariablesGroup = new JTextField();
		txtNameOfDecisionVariablesGroup.setColumns(15);
		JLabel lblNumberOfDecisionVariable = new JLabel("Number of Decision Variables");
		pnlNameOfDecisionVariablesGroup.add(lblNumberOfDecisionVariable, BorderLayout.NORTH);

		spnNumberOfDecisionVariables = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		spinnerHandler(pnlNameOfDecisionVariablesGroup);
		tblDecisionVariables = new JTable();
		createDecisionVariableTable();

		btnDecisionVariablesFinish = new JButton("Finish");
		btnDecisionVariablesFinish.setContentAreaFilled(false);
		ImageIcon icoFinish = new ImageIcon(((new ImageIcon("./src/images/finish.png")).getImage())
				.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
		btnDecisionVariablesFinish.setIcon(icoFinish);
		decisionVariablesFinish(problem, decisionVarFrame);

		pnlNameOfDecisionVariablesGroup.add(lblNameOfDecisionVariablesGroup);
		pnlNameOfDecisionVariablesGroup.add(txtNameOfDecisionVariablesGroup);
		pnlDecision.add(pnlNameOfDecisionVariablesGroup, BorderLayout.NORTH);
		pnlDecision.add(new JScrollPane(tblDecisionVariables), BorderLayout.CENTER);
		pnlDecision.add(btnDecisionVariablesFinish, BorderLayout.PAGE_END);
		decisionVarFrame.add(pnlDecision);
	}

	/**
	 * Creates the decision variable table
	 **/
	public void createDecisionVariableTable() {
		dtmDecisionVariables = new DefaultTableModel();
		tblDecisionVariables.setModel(dtmDecisionVariables);
		tblDecisionVariables.getTableHeader().setReorderingAllowed(false);

		if (dataType != null) {
			dtmDecisionVariables.addColumn("Name");
			if (dataType.equals(Type.BINARY)) {
				dtmDecisionVariables.addColumn("Value");
			} else {
				dtmDecisionVariables.addColumn("Minimum Value");
				dtmDecisionVariables.addColumn("Maximum Value");
				dtmDecisionVariables.addColumn("Restrictions");
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setToolTipText("Please use ; to separate the restrictions");
				tblDecisionVariables.getColumnModel().getColumn(3).setCellRenderer(renderer);
			}
		}

	}

	/**
	 * Adds or deletes decision variable table rows according to the spinner number
	 **/
	private void spinnerHandler(JPanel pnlNameOfDecisionVariablesGroup) {
		pnlNameOfDecisionVariablesGroup.add(spnNumberOfDecisionVariables, BorderLayout.NORTH);
		spnNumberOfDecisionVariables.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int spinnerValue = Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString());
				int nrRows = spinnerValue - dtmDecisionVariables.getRowCount();
				if (dtmDecisionVariables.getRowCount() < spinnerValue) {
					for (int j = 0; j < nrRows; j++) {

						if (dataType.equals(Type.BINARY)) {
							dtmDecisionVariables.addRow(new Object[] { "", "" });
						} else {
							dtmDecisionVariables.addRow(new Object[] { "", "", "", "" });
						}
					}
				} else if (dtmDecisionVariables.getRowCount() > spinnerValue) {
					for (int k = 0; k < Math.abs(spinnerValue - dtmDecisionVariables.getRowCount()); k++) {
						dtmDecisionVariables.removeRow(dtmDecisionVariables.getRowCount() - 1);
						tblDecisionVariables.revalidate();
					}
				}
			}
		});
	}

	/**
	 * Sets the problem variable if the name type and variable's values are filled
	 * 
	 **/
	private void decisionVariablesFinish(UserProblem problem, JFrame decisionVarFrame) {
		btnDecisionVariablesFinish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean varsReady = true;
				boolean varsReadyToCheck = true;
				if (txtNameOfDecisionVariablesGroup.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill the variable(s) group name", "Warning",
							JOptionPane.WARNING_MESSAGE);
					varsReady = false;
					varsReadyToCheck = false;
				} else {
					if (dataType.equals(Type.BINARY)) {
						for (int i = 0; i < tblDecisionVariables.getRowCount(); i++) {
							if (dataType.equals(Type.BINARY)) {
								if (dtmDecisionVariables.getValueAt(i, 0).toString().isEmpty()
										|| dtmDecisionVariables.getValueAt(i, 1).toString().isEmpty()) {
									JOptionPane.showMessageDialog(null, "Please fill the variable name and value",
											"Warning", JOptionPane.WARNING_MESSAGE);
									varsReady = false;
									varsReadyToCheck = false;
									break;
								}
								if (!isBinary(dtmDecisionVariables.getValueAt(i, 1).toString())) {
									JOptionPane.showMessageDialog(null,
											"Remember that the variable value must be binary!", "Warning",
											JOptionPane.WARNING_MESSAGE);
									varsReady = false;
									varsReadyToCheck = false;
									break;
								}
							}
						}
					} else {
						boolean varsReadyToCheckType = true;
						for (int j = 0; j < tblDecisionVariables.getRowCount(); j++) {
							if (dtmDecisionVariables.getValueAt(j, 0).toString().isEmpty()
									|| dtmDecisionVariables.getValueAt(j, 1).toString().isEmpty()
									|| dtmDecisionVariables.getValueAt(j, 2).toString().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Please fill the variable name and interval fields",
										"Warning", JOptionPane.WARNING_MESSAGE);
								varsReady = false;
								varsReadyToCheck = false;
								varsReadyToCheckType = false;
								break;
							}
						}
						if (varsReadyToCheckType) {
							if (dataType.equals(Type.INTEGER)) {
								for (int k = 0; k < tblDecisionVariables.getRowCount(); k++) {
									if (!isInteger(dtmDecisionVariables.getValueAt(k, 1).toString())
											|| !isInteger(dtmDecisionVariables.getValueAt(k, 2).toString())) {
										JOptionPane.showMessageDialog(null,
												"Remember that the variable values must be integer!", "Warning",
												JOptionPane.WARNING_MESSAGE);
										varsReady = false;
										varsReadyToCheck = false;
										break;
									}
								}
								if (varsReadyToCheck) {
									for (int l = 0; l < tblDecisionVariables.getRowCount(); l++) {
										double minValue = Integer
												.parseInt(dtmDecisionVariables.getValueAt(l, 1).toString());
										double maxValue = Integer
												.parseInt(dtmDecisionVariables.getValueAt(l, 2).toString());
										if (maxValue < minValue) {
											JOptionPane.showMessageDialog(null,
													"The maximum value should be greater than the minimum value",
													"Warning", JOptionPane.WARNING_MESSAGE);
											varsReady = false;
											break;
										}
									}
								}
							} else {
								for (int m = 0; m < tblDecisionVariables.getRowCount(); m++) {
									if (!isDouble(dtmDecisionVariables.getValueAt(m, 1).toString())
											|| !isDouble(dtmDecisionVariables.getValueAt(m, 2).toString())) {
										JOptionPane.showMessageDialog(null,
												"Remember that the variable values must be double!", "Warning",
												JOptionPane.WARNING_MESSAGE);
										varsReady = false;
										varsReadyToCheck = false;
										break;
									}
								}
								if (varsReadyToCheck) {
									for (int n = 0; n < tblDecisionVariables.getRowCount(); n++) {
										double minValue = Double
												.parseDouble(dtmDecisionVariables.getValueAt(n, 1).toString());
										double maxValue = Double
												.parseDouble(dtmDecisionVariables.getValueAt(n, 2).toString());
										if (maxValue < minValue) {
											JOptionPane.showMessageDialog(null,
													"The maximum value should be greater than the minimum value",
													"Warning", JOptionPane.WARNING_MESSAGE);
											varsReady = false;
											break;
										}
									}
								}
							}
						}
					}
				}

				if (varsReady) {
					problem.getVariables().clear();

					if (tblDecisionVariables.getCellEditor() != null) {
						tblDecisionVariables.getCellEditor().stopCellEditing();
					}
					if (dataType.equals(Type.BINARY)) {
						for (int j = 0; j < tblDecisionVariables.getRowCount(); j++) {
							Variable variable = new Variable(dtmDecisionVariables.getValueAt(j, 0).toString(),
									dtmDecisionVariables.getValueAt(j, 1).toString());
							problem.addVariable(variable);
						}
					} else {
						for (int j = 0; j < tblDecisionVariables.getRowCount(); j++) {
							Variable variable = new Variable(dtmDecisionVariables.getValueAt(j, 0).toString(),
									dtmDecisionVariables.getValueAt(j, 1).toString(),
									dtmDecisionVariables.getValueAt(j, 2).toString(),
									dtmDecisionVariables.getValueAt(j, 3).toString());
							problem.addVariable(variable);
						}
					}

					algorithmSelection.setNumberDecisionVariables(
							Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString()));
					algorithmSelection.setDecisionVariablesGroupName(txtNameOfDecisionVariablesGroup.getText());

					decisionVarFrame.dispose();
				}
			}

		});
	}

	public boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isDouble(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isBinary(String number) {
		if (isInteger(number)) {
			int copyOfInput = Integer.parseInt(number);
			if (copyOfInput < 0) {
				return false;
			} else {
				while (copyOfInput != 0) {
					if (copyOfInput % 10 > 1) {
						return false;
					}
					copyOfInput = copyOfInput / 10;
				}
				return true;
			}
		} else {
			return false;
		}

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

	public JButton getBtnDecisionVariablesFinish() {
		return btnDecisionVariablesFinish;
	}

	public void setDataType(Type dataType) {
		this.previousDataType = this.dataType;
		this.dataType = dataType;
	}

	public void setFilled(boolean b) {
		this.filled = true;
	}

	public JButton getBtnDecisionVariables() {
		return btnDecisionVariables;
	}

}
