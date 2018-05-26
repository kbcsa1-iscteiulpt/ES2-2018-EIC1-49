package mainAndGui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import problem.Type;
import problem.UserProblem;
import resources.ResourceLoader;
import support.AlgorithmUtils;
import support.ConfigXML;
import support.EmailHandler;

public class AlgorithmSelectionSection {
	public JFrame beforeOptimizationProcess;
	public JButton btnExecuteProcess;
	public ConfigXML config;
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
	public JButton btnExecuteAlgorithms;
	public NameDescriptionSection nameDescription;
	public EmailSection email;
	public DecisionVariablesSection decisionVariables;
	public EmailHandler emailHandler = new EmailHandler();
	public String adminEmail;
	private ExecutionProcess execute;
	private int decisionVariablesNumber;
	private String decisionVariablesGroupName;
	private TimeOptimizationSection time;
	private CriteriaSection criteriaSection;
	private ResourceLoader resourceLoader = new ResourceLoader();
	private AlgorithmUtils algorithmUtils = new AlgorithmUtils();

	public JPanel algorithmSelection(NameDescriptionSection nameDescription, EmailSection email,
			TimeOptimizationSection time, UserProblem problem, EmailHandler support, String adminEmail,
			CriteriaSection criteriaSection) {
		this.nameDescription = nameDescription;
		this.email = email;
		this.time = time;
		this.criteriaSection = criteriaSection;
		JPanel executeProcessPanel = new JPanel(new FlowLayout());
		algorithmsList = new ArrayList<JCheckBox>();
		algorithmsSelectedList = new ArrayList<String>();
		problem(nameDescription, email, problem, decisionVariables, support, adminEmail);
		Image imgExecute = Toolkit.getDefaultToolkit().getImage(resourceLoader.getClass().getResource("images/execute.png"));
		ImageIcon icoExecute = new ImageIcon(imgExecute.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		
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
					JOptionPane.showMessageDialog(null, "Please fill all fields!", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (problem.getVariables().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill the variable decisions table!", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (problem.getCriterias().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill the jar path!", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					if (timeCheck()) {
						execute = new ExecutionProcess();
						beforeOptimizationProcess = new JFrame("Select the algorithm");
						FrameSize.setFrame(beforeOptimizationProcess, 0.5);
						setBeforeOptimizationProcess(beforeOptimizationProcess, problem);
						beforeOptimizationProcess.setVisible(true);
					}
				}

			}

			private boolean timeCheck() {
				if ((Integer.parseInt(time.getSpnIdealNumberOfDays().getValue().toString()) == 0
						&& Integer.parseInt(time.getSpnIdealNumberOfHours().getValue().toString()) == 0
						&& Integer.parseInt(time.getSpnIdealNumberOfMinutes().getValue().toString()) == 0)
						|| (Integer.parseInt(time.getSpnMaxNumberOfDays().getValue().toString()) == 0
								&& Integer.parseInt(time.getSpnMaxNumberOfHours().getValue().toString()) == 0
								&& Integer.parseInt(time.getSpnMaxNumberOfMinutes().getValue().toString()) == 0)

				) {
					JOptionPane.showMessageDialog(null, "Please fill the maximum and ideal time", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return false;
				} else {
					return true;
				}
			}
		});
	}

	private void setBeforeOptimizationProcess(JFrame beforeOptimizationProcess, UserProblem problem) {
		JPanel pnlBeforeOptimization = new JPanel(new BorderLayout());
		JPanel pnlTitleBeforeOptimization = new JPanel();
		JPanel pnlAlgorithms = new JPanel();
		JPanel pnlLoadExecuteAlgorithms = new JPanel(new BorderLayout());
		JPanel pnlLoadAlgorithms = new JPanel();
		JPanel pnlExecuteAlgorithms = new JPanel();
		JTextField txtAlgorithmsPath = new JTextField();
		txtAlgorithmsPath.setColumns(15);
		JButton btnLoadAlgorithms = new JButton("Load Algorithms:");
		btnLoadAlgorithms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fchUploadJar = new JFileChooser();
				if (fchUploadJar.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String jarPath = fchUploadJar.getSelectedFile().getPath();
					txtAlgorithmsPath.setText(jarPath);
					List<String> listSelectedAlgorithms = algorithmUtils.readAutomaticConf(fchUploadJar.getSelectedFile());
					String[] splitted = listSelectedAlgorithms.get(0).split(";");
					for(JCheckBox ch : algorithmsList) {
						for(String algorithm : splitted) {
							System.out.println(algorithm);
							if(ch.getText().equals(algorithm)) {
								ch.setSelected(true);
								System.out.println(ch.getText());
							}
						}
					}
				}
			}
		});
		btnLoadAlgorithms.setContentAreaFilled(false);

		JLabel lblBeforeOptimization = new JLabel(
				"Select the algorithm for " + dataType.toString().toLowerCase() + " problem's type: ");
		setPnlAlgorithms(pnlAlgorithms);

		btnExecuteAlgorithms = new JButton("Start the optimization process");
		btnExecuteAlgorithms.setContentAreaFilled(false);
		setBtnExecuteAlgorithmsAction(beforeOptimizationProcess, problem);
		addToPanel(beforeOptimizationProcess, pnlBeforeOptimization, pnlTitleBeforeOptimization, pnlAlgorithms,
				pnlLoadExecuteAlgorithms, pnlLoadAlgorithms, pnlExecuteAlgorithms, txtAlgorithmsPath, btnLoadAlgorithms,
				lblBeforeOptimization);
	}

	private void addToPanel(JFrame beforeOptimizationProcess, JPanel pnlBeforeOptimization,
			JPanel pnlTitleBeforeOptimization, JPanel pnlAlgorithms, JPanel pnlLoadExecuteAlgorithms,
			JPanel pnlLoadAlgorithms, JPanel pnlExecuteAlgorithms, JTextField txtAlgorithmsPath,
			JButton btnLoadAlgorithms, JLabel lblBeforeOptimization) {
		pnlTitleBeforeOptimization.add(lblBeforeOptimization);
		pnlLoadAlgorithms.add(btnLoadAlgorithms);
		pnlLoadAlgorithms.add(txtAlgorithmsPath);
		pnlLoadAlgorithms.add(btnExecuteAlgorithms);
		pnlLoadExecuteAlgorithms.add(pnlLoadAlgorithms);
		pnlLoadExecuteAlgorithms.add(pnlExecuteAlgorithms, BorderLayout.SOUTH);
		pnlBeforeOptimization.add(pnlTitleBeforeOptimization, BorderLayout.NORTH);
		pnlBeforeOptimization.add(pnlAlgorithms);
		pnlBeforeOptimization.add(pnlLoadExecuteAlgorithms, BorderLayout.SOUTH);
		beforeOptimizationProcess.add(pnlBeforeOptimization);
	}

	private void setBtnExecuteAlgorithmsAction(JFrame beforeOptimizationProcess, UserProblem problem) {
		btnExecuteAlgorithms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (algorithmsSelectedList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select at least one algorithm", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
				execute.executeOptimization(problem, nameDescription, email, decisionVariablesNumber,
						decisionVariablesGroupName, emailHandler, dataType, criteriaSection,
						algorithmsSelectedList, time, beforeOptimizationProcess);
			}
		});
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
		algorithmsList.add(algorithmRandomSearch);
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

	public void setNumberDecisionVariables(int value) {
		this.decisionVariablesNumber = value;
	}

	public void setDecisionVariablesGroupName(String text) {
		this.decisionVariablesGroupName = text;
	}
}