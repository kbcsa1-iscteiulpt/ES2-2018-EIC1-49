package mainAndGui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import problem.Type;
import problem.UserProblem;
import support.Config;
import support.EmailHandler;

public class AlgorithmSelectionSection {
	public JFrame beforeOptimizationProcess;
	public JButton btnExecuteProcess;
	public Config config;
	public Type dataType;
	public JCheckBox algorithmNSGAII;
	public JCheckBox algorithmSMSEMOA;
	public JCheckBox algorithmMOCell;
	public JCheckBox algorithmMOEAD;
	public JCheckBox algorithmPAES;
	public JCheckBox algorithmRandomSearch;
	public JCheckBox algorithmGDE3;
	public JCheckBox algorithmIBEA;
	public JCheckBox algorithmMOCH;
	public JCheckBox algorithmSPEA2;
	public List<JCheckBox> algorithmsList;
	public List<String> algorithmsSelectedList;
	public JButton btnSaveAlgorithms;
	public NameDescriptionSection nameDescription;
	public EmailSection email;
	public DecisionVariablesSection decisionVariables;
	public EmailHandler emailHandler;
	public String adminEmail;
	private ExecutionProcess execute;

	public JPanel algorithmSelection(NameDescriptionSection nameDescription, EmailSection email, UserProblem problem,
			TypeVarSection typeVarSection, EmailHandler support, String adminEmail) {
		this.dataType = typeVarSection.getDataType();
		this.nameDescription = nameDescription;
		this.email = email;
		JPanel executeProcessPanel = new JPanel(new FlowLayout());
		algorithmsList = new ArrayList<JCheckBox>();
		algorithmsSelectedList = new ArrayList<String>();
		problem(nameDescription, email, problem, decisionVariables, support, adminEmail);
		ImageIcon icoExecute = new ImageIcon(((new ImageIcon("./src/images/execute.png")).getImage())
				.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnExecuteProcess.setContentAreaFilled(false);
		btnExecuteProcess.setIcon(icoExecute);
		executeProcessPanel.add(btnExecuteProcess);
		return executeProcessPanel;
	}

	/**
	 * Checks if the mandatory fields are completed and executes the optimization
	 * process Sends an email warning if there was an error while running the
	 * problem requested.
	 **/
	private void problem(NameDescriptionSection nameDescription, EmailSection email, UserProblem problem,
			DecisionVariablesSection decisionVariables, EmailHandler support, String adminEmail)
			throws java.awt.HeadlessException, java.lang.NumberFormatException {
		btnExecuteProcess = new JButton("Select the algorithms and start the optimization process");
		btnExecuteProcess.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameDescription.getProblemName().getText().isEmpty()
						|| nameDescription.getProblemDescription().getText().isEmpty()
						|| !email.getBtnWriteEmail().isEnabled() || dataType == null) {
					JOptionPane.showMessageDialog(null, "Please fill all the mandatory fields", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					execute =  new ExecutionProcess();
					beforeOptimizationProcess = new JFrame("Select the algorithm");
					FrameSize.setFrame(beforeOptimizationProcess, 0.25);
					setBeforeOptimizationProcess(beforeOptimizationProcess, problem);
					beforeOptimizationProcess.setVisible(true);
				}

			}
		});
	}

	private void setBeforeOptimizationProcess(JFrame beforeOptimizationProcess, UserProblem problem) {
		JPanel pnlBeforeOptimization = new JPanel(new BorderLayout());
		JPanel pnlTitleBeforeOptimization = new JPanel();
		JPanel pnlAlgorithms = new JPanel();
		JPanel pnlSaveAlgorithms = new JPanel();
		JLabel lblBeforeOptimization = new JLabel(
				"Select the algorithm for " + dataType.toString().toLowerCase() + " problem's type: ");
		setPnlAlgorithms(pnlAlgorithms);
		btnSaveAlgorithms = new JButton("Start the optimization process");
		btnSaveAlgorithms.setContentAreaFilled(false);
		btnSaveAlgorithms.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				execute.executeOptimization(problem, nameDescription, email, decisionVariables, emailHandler, adminEmail);
			}
		});
		pnlTitleBeforeOptimization.add(lblBeforeOptimization);
		pnlSaveAlgorithms.add(btnSaveAlgorithms);
		pnlBeforeOptimization.add(pnlTitleBeforeOptimization, BorderLayout.NORTH);
		pnlBeforeOptimization.add(pnlAlgorithms);
		pnlBeforeOptimization.add(pnlSaveAlgorithms, BorderLayout.SOUTH);
		beforeOptimizationProcess.add(pnlBeforeOptimization);
	}

	private void setPnlAlgorithms(JPanel pnlAlgorithms) {

		checkBoxInteger();
		addToListInteger();

		if (dataType.equals(Type.DOUBLE)) {
			pnlAlgorithms.setLayout(new GridLayout(4, 2));
			checkBoxDouble();
			addToListDouble();
			pnlAlgorithms.add(algorithmGDE3);
			pnlAlgorithms.add(algorithmIBEA);
			pnlAlgorithms.add(algorithmMOEAD);
		} else if (dataType.equals(Type.BINARY)) {
			checkBoxBinary();
			addToListBinary();
			pnlAlgorithms.add(algorithmMOCH);
			pnlAlgorithms.add(algorithmSPEA2);
			pnlAlgorithms.setLayout(new GridLayout(4, 2));
		} else {
			pnlAlgorithms.setLayout(new GridLayout(3, 2));
		}
		pnlAlgorithms.add(algorithmNSGAII);
		pnlAlgorithms.add(algorithmSMSEMOA);
		pnlAlgorithms.add(algorithmMOCell);
		pnlAlgorithms.add(algorithmPAES);
		pnlAlgorithms.add(algorithmRandomSearch);
		algorithmsListener();
	}

	private void checkBoxBinary() {
		algorithmMOCH = new JCheckBox("MOCH");
		algorithmSPEA2 = new JCheckBox("SPEA2");
	}

	private void checkBoxDouble() {
		algorithmGDE3 = new JCheckBox("GDE3");
		algorithmIBEA = new JCheckBox("IBEA");
		algorithmMOEAD = new JCheckBox("MOEAD");
	}

	private void checkBoxInteger() {
		algorithmNSGAII = new JCheckBox("NSGAII");
		algorithmSMSEMOA = new JCheckBox("SMSEMOA");
		algorithmMOCell = new JCheckBox("MOCell");
		algorithmPAES = new JCheckBox("PAES");
		algorithmRandomSearch = new JCheckBox("RandomSearch");
	}

	private void addToListBinary() {
		algorithmsList.add(algorithmMOCH);
		algorithmsList.add(algorithmSPEA2);
	}

	private void addToListDouble() {
		algorithmsList.add(algorithmGDE3);
		algorithmsList.add(algorithmIBEA);
		algorithmsList.add(algorithmMOEAD);
	}

	private void addToListInteger() {
		algorithmsList.add(algorithmNSGAII);
		algorithmsList.add(algorithmSMSEMOA);
		algorithmsList.add(algorithmMOCell);
		algorithmsList.add(algorithmPAES);
	}

	private void algorithmsListener() {
		for (int i = 0; i < algorithmsList.size(); i++) {
			JCheckBox chb = algorithmsList.get(i);
			algorithmsList.get(i).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (chb.isSelected()) {
						algorithmsSelectedList.add(chb.getText());
					} else {

						algorithmsSelectedList.remove(chb.getText());
					}
				}
			});
		}
	}

	
	public void setDataType(Type type) {
		dataType = type;
	}
}