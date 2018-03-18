package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import classes.Problem;
import classes.Support;
import classes.Time;
import classes.Variable;
import classes.XML_Editor;

/**
 * This class represents the interface
 * 
 * @author Diana Lopes nº 72898
 **/

public class Interface {

	private JFrame frame;
	private JFrame helpFrame;
	private JButton btnHelp;
	private JButton btnEmail;
	private JFrame decisionVarFrame;
	private JButton btnDecisionVariables;
	private JFrame criterionFrame;
	private JButton btnCriterion;
	private JButton btnSave;
	private JButton btnRead;
	private JButton btnAddCriterion;
	private JButton btnReadJar;
	private JTextArea txaProblemDescription;
	private JButton btnMessageSend;
	private JTextField txtEmail;
	private JTextField txtProblemName;
	private JTextField txtNameOfDecisionVariablesGroup;
	private JSpinner spnNumberOfDays;
	private JSpinner spnNumberOfHours;
	private JSpinner spnNumberOfMinutes;
	private JSpinner spnIdealNumberOfDays;
	private JSpinner spnIdealNumberOfHours;
	private JSpinner spnIdealNumberOfMinutes;
	private JSpinner spnNumberOfDecisionVariables;
	private JTable tblDecisionVariables;

	private Support support = new Support();

	private XML_Editor xml = new XML_Editor();
	private Problem problem = new Problem();
	private JButton btnDecisionVariablesFinish;

	public Interface() {
		frame = new JFrame("Problem to be optimized");
		setFrame(frame, 0.75);
		setContent(frame);
		frame.setVisible(true);
	}

	/**
	 * The panels are added to the initial frame by calling methods that return
	 * JPanel.
	 **/
	private void setContent(JFrame frame) {
		JPanel initialPanel = new JPanel(new GridLayout(11, 0));
		initialPanel.add(helpFAQPanel());
		initialPanel.add(problemNamePanel());
		initialPanel.add(problemDescriptionPanel());
		initialPanel.add(emailPanel());
		initialPanel.add(maxTimePanel());
		initialPanel.add(idealTimePanel());
		initialPanel.add(decisionVarPanel());
		initialPanel.add(readPanel());
		initialPanel.add(savePanel());
		initialPanel.add(criterionPanel());
		initialPanel.add(executeProcessPanel());
		frame.add(initialPanel);
	}

	/**
	 * Returns a panel with a question mark placed at the top-right of the
	 * frame. When clicked, a new frame is displayed to show the FAQ.
	 **/
	private JPanel helpFAQPanel() {
		JPanel pnlHelpFAQ = new JPanel(new BorderLayout());
		btnHelp = new JButton();
		ImageIcon question_mark = new ImageIcon(((new ImageIcon("./src/images/question_mark.png")).getImage())
				.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnHelp.setIcon(question_mark);
		btnHelp.setContentAreaFilled(false);
		btnHelp.setBorderPainted(false);
		btnHelp.setFocusPainted(false);

		btnHelp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				helpFrame = new JFrame("Help Section");
				setFrame(helpFrame, 0.5);
				setHelpFrame(helpFrame);
				helpFrame.setVisible(true);
			}
		});
		pnlHelpFAQ.add(btnHelp, BorderLayout.LINE_END);
		return pnlHelpFAQ;
	}

	/**
	 * Returns a panel with a JTextField for user to fill with the problem's
	 * name.
	 **/
	private JPanel problemNamePanel() {
		JPanel pnlProblemName = new JPanel();
		JLabel lblProblemName = new JLabel("Problem's Name:");
		txtProblemName = new JTextField();
		txtProblemName.setColumns(20);
		pnlProblemName.add(lblProblemName);
		pnlProblemName.add(txtProblemName);
		return pnlProblemName;
	}

	/**
	 * Returns a panel with a JTextField for user to fill with the problem's
	 * description.
	 **/
	private JPanel problemDescriptionPanel() {
		JPanel pnlProblemDescription = new JPanel(new FlowLayout());
		JLabel lblProblemDescription = new JLabel("Problem's Description:");
		txaProblemDescription = new JTextArea(4, 60);
		JScrollPane scrProblemDescription = new JScrollPane(txaProblemDescription);
		txaProblemDescription.setLineWrap(true);
		pnlProblemDescription.add(lblProblemDescription);
		pnlProblemDescription.add(scrProblemDescription);
		return pnlProblemDescription;
	}

	/**
	 * Returns a panel with a JTextField to fill with user's email. When the
	 * button is clicked, a new frame is displayed to write and send the email.
	 **/
	private JPanel emailPanel() {
		JPanel pnlEmail = new JPanel(new FlowLayout());
		JLabel lblEmail = new JLabel("Enter your Email:");
		txtEmail = new JTextField();
		txtEmail.setColumns(20);
		btnEmail = new JButton("Write Email");
		btnEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEmailFrame();
			}
		});
		pnlEmail.add(lblEmail);
		pnlEmail.add(txtEmail);
		pnlEmail.add(btnEmail);
		return pnlEmail;
	}

	/**
	 * Returns a panel with a JSpinner for user to select the maximum time to
	 * wait for the optimization.
	 **/
	private JPanel maxTimePanel() {
		JPanel pnlMaxTime = new JPanel(new FlowLayout());
		JLabel lblMaxTime = new JLabel("Maximum time for optimization:");

		JLabel lblNumberofDays = new JLabel("Days");
		JLabel lblNumberofHours = new JLabel("Hours");
		JLabel lblNumberofMinutes = new JLabel("Minutes");

		spnNumberOfDays = new JSpinner(new SpinnerNumberModel(0, 0, 31, 1));
		spnNumberOfHours = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
		spnNumberOfMinutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		pnlMaxTime.add(lblMaxTime);
		pnlMaxTime.add(spnNumberOfDays);
		pnlMaxTime.add(lblNumberofDays);
		pnlMaxTime.add(spnNumberOfHours);
		pnlMaxTime.add(lblNumberofHours);
		pnlMaxTime.add(spnNumberOfMinutes);
		pnlMaxTime.add(lblNumberofMinutes);
		return pnlMaxTime;
	}

	/**
	 * Returns a panel with a JSpinner for user to select the ideal time to wait
	 * for the optimization.
	 **/
	private JPanel idealTimePanel() {
		JPanel pnlIdealTime = new JPanel(new FlowLayout());
		JLabel lblIdealTime = new JLabel("Ideal time for optimization:");

		JLabel lblIdealNumberofDays = new JLabel("Days");
		JLabel lblIdealNumberofHours = new JLabel("Hours");
		JLabel lblIdealNumberofMinutes = new JLabel("Minutes");

		spnIdealNumberOfDays = new JSpinner(new SpinnerNumberModel(0, 0, 31, 1));
		spnIdealNumberOfHours = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
		spnIdealNumberOfMinutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		pnlIdealTime.add(lblIdealTime);
		pnlIdealTime.add(spnIdealNumberOfDays);
		pnlIdealTime.add(lblIdealNumberofDays);
		pnlIdealTime.add(spnIdealNumberOfHours);
		pnlIdealTime.add(lblIdealNumberofHours);
		pnlIdealTime.add(spnIdealNumberOfMinutes);
		pnlIdealTime.add(lblIdealNumberofMinutes);
		return pnlIdealTime;
	}

	/**
	 * Returns a panel with a JSpinner to select the number of decision
	 * variables and button. When clicked, a new frame is displayed to write the
	 * decision variable group name and to fill the table of the variable
	 * decision.
	 **/
	private JPanel decisionVarPanel() {
		JPanel pnlDecisionVar = new JPanel(new FlowLayout());
		JLabel lblNumberOfDecisionVariable = new JLabel("Number of Decision Variables");
		spnNumberOfDecisionVariables = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		btnDecisionVariables = new JButton("Decision Variables");
		btnDecisionVariables.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				decisionVarFrame = new JFrame("Decision Variables");
				setFrame(decisionVarFrame, 0.5);
				setDecisionFrame(decisionVarFrame);
				decisionVarFrame.setVisible(true);
				fillDecisionVariableForm();
			}

		});
		pnlDecisionVar.add(lblNumberOfDecisionVariable);
		pnlDecisionVar.add(spnNumberOfDecisionVariables);
		pnlDecisionVar.add(btnDecisionVariables);
		return pnlDecisionVar;
	}

	/**
	 * Returns a panel with a JTextField to fill by the user or to be filled
	 * automatically with the file path (to read XML) through the button.
	 **/
	private JPanel readPanel() {
		JPanel pnlRead = new JPanel(new FlowLayout());
		JLabel lblRead = new JLabel("Read from XML (path):");
		JTextField txtRead = new JTextField();
		txtRead.setColumns(40);
		btnRead = new JButton("Read XML File");
		btnRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchReadXML = new JFileChooser();
				if (fchReadXML.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String filePath = fchReadXML.getSelectedFile().getPath();
					txtRead.setText(filePath);
					problem = xml.read(filePath);
					fillInicialForm();
				}
			}
		});
		pnlRead.add(lblRead);
		pnlRead.add(txtRead);
		pnlRead.add(btnRead);
		return pnlRead;
	}

	/**
	 * Returns a panel with a JTextField to fill by the user or to be filled
	 * automatically with the file path (to save into XML) through the button.
	 **/
	private JPanel savePanel() {
		JPanel pnlSave = new JPanel();
		JLabel lblSave = new JLabel("Save into XML (path):");
		JTextField txtSave = new JTextField();
		txtSave.setColumns(40);
		btnSave = new JButton("Save XML File");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchXMLSave = new JFileChooser();
				if (fchXMLSave.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File fileXML = fchXMLSave.getSelectedFile();
					if (!fileXML.exists()) {
						try {
							fileXML.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					txtSave.setText(fileXML.getPath());
					saveProblem();
					xml.write(fileXML.getPath(), problem);
				}
			}
		});
		pnlSave.add(lblSave);
		pnlSave.add(txtSave);
		pnlSave.add(btnSave);
		return pnlSave;
	}

	/**
	 * Returns a panel with a button. When clicked, a new frame is displayed to
	 * specify the criterion.
	 **/
	private JPanel criterionPanel() {
		JPanel pnlCriterion = new JPanel();
		btnCriterion = new JButton("Criterion to be optimized");
		btnCriterion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				criterionFrame = new JFrame("Criterions");
				setFrame(criterionFrame, 0.5);
				setCriterionFrame(criterionFrame);
				criterionFrame.setVisible(true);
			}
		});
		pnlCriterion.add(btnCriterion);
		return pnlCriterion;
	}

	/**
	 * Returns a panel with the button to execute the optimization process.
	 **/
	private JPanel executeProcessPanel() {
		JPanel pnlExecuteProcess = new JPanel(new FlowLayout());
		JButton btnExecuteProcess = new JButton("Execute Optimization Process");
		pnlExecuteProcess.add(btnExecuteProcess);
		pnlExecuteProcess.setBackground(new Color(240, 240, 240));
		return pnlExecuteProcess;
	}

	/**
	 * Returns a frame with two JTextField to fill and a button to send the
	 * email. One with the subject of the email and the other with the email
	 * body.
	 **/
	private JFrame setEmailFrame() {
		JFrame sendEmailFrame = new JFrame("Email");
		setFrame(sendEmailFrame, 0.5);
		JPanel pnlSendEmail = new JPanel(new BorderLayout());
		JPanel pnlMessageTitle = new JPanel(new BorderLayout());
		JPanel pnlMessageBody = new JPanel(new BorderLayout());
		JPanel pnlMessageSend = new JPanel();

		JLabel lblMessageTitle = new JLabel("Subject:");
		JTextField txtMessageTitle = new JTextField();
		txtMessageTitle.setColumns(30);

		pnlMessageTitle.add(lblMessageTitle, BorderLayout.NORTH);
		pnlMessageTitle.add(txtMessageTitle, BorderLayout.CENTER);

		JLabel lblMessageBody = new JLabel("Message:");
		JTextArea txaMessageBody = new JTextArea();
		txaMessageBody.setLineWrap(true);
		JScrollPane scrMessageBody = new JScrollPane(txaMessageBody);

		pnlMessageBody.add(lblMessageBody, BorderLayout.NORTH);
		pnlMessageBody.add(scrMessageBody, BorderLayout.CENTER);

		btnMessageSend = new JButton("Send Message");
		btnMessageSend.addActionListener(new ActionListener() {

			/**
			 * Closes the email frame and sends the intended email, if the
			 * operation fails shows a warning message.
			 **/
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sendEmailFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					String subject = "From: " + txtEmail.getText() + txtMessageTitle.getText();
					support.SendEmail(txtEmail.getText(), txtMessageTitle.getText(), subject);
				} catch (MessagingException e1) {
					JOptionPane.showMessageDialog(pnlSendEmail, "Error sending email, connection issue!", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		pnlMessageSend.add(btnMessageSend);

		pnlSendEmail.add(pnlMessageTitle, BorderLayout.NORTH);
		pnlSendEmail.add(pnlMessageBody, BorderLayout.CENTER);
		pnlSendEmail.add(pnlMessageSend, BorderLayout.SOUTH);

		sendEmailFrame.add(pnlSendEmail);
		sendEmailFrame.setVisible(true);
		return sendEmailFrame;
	}

	/**
	 * Adds content to the frame given with a table to write the decision
	 * variable group name and to fill the table of the variable decision. Each
	 * decision variable has a name, type, and potential intervals and
	 * restrictions.
	 **/
	private void setDecisionFrame(JFrame decisionVarFrame) {
		JPanel pnlDecision = new JPanel(new BorderLayout());

		JPanel pnlNameOfDecisionVariablesGroup = new JPanel();
		JLabel lblNameOfDecisionVariablesGroup = new JLabel("Name of Decision Variable Group:");
		txtNameOfDecisionVariablesGroup = new JTextField();
		txtNameOfDecisionVariablesGroup.setColumns(50);

		DefaultTableModel dtmDecisionVariables = new DefaultTableModel();
		tblDecisionVariables = new JTable();
		tblDecisionVariables.setModel(dtmDecisionVariables);

		dtmDecisionVariables.addColumn("Name");
		dtmDecisionVariables.addColumn("Type");
		dtmDecisionVariables.addColumn("Interval");
		dtmDecisionVariables.addColumn("Restrictions");

		String[] variableDataTypes = { "boolean", "byte", "char", "double", "float", "integer", "long", "real", "short",
				"String" };
		JComboBox<String> cmbVariableDataTypes = new JComboBox<>(variableDataTypes);

		for (int i = 0; i < Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString()); i++) {
			dtmDecisionVariables.addRow(new Object[] { "", "", "" });
			tblDecisionVariables.getColumnModel().getColumn(1)
					.setCellEditor(new DefaultCellEditor(cmbVariableDataTypes));
		}

		pnlNameOfDecisionVariablesGroup.add(lblNameOfDecisionVariablesGroup);
		pnlNameOfDecisionVariablesGroup.add(txtNameOfDecisionVariablesGroup);
		pnlDecision.add(pnlNameOfDecisionVariablesGroup, BorderLayout.NORTH);
		pnlDecision.add(new JScrollPane(tblDecisionVariables));
		btnDecisionVariablesFinish = new JButton("Finish");
		pnlDecision.add(btnDecisionVariablesFinish, BorderLayout.PAGE_END);
		decisionVarFrame.add(pnlDecision);
	}

	/**
	 * Adds content to the frame with a JTextField to define the criterion(s), a
	 * button to add a new criterion and a button to upload the .jar file.
	 **/
	private void setCriterionFrame(JFrame criterionFrame) {
		JPanel pnlCriterion = new JPanel();
		pnlCriterion.setLayout(new BoxLayout(pnlCriterion, BoxLayout.Y_AXIS));
		btnAddCriterion = new JButton("Add Criterion");
		btnAddCriterion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pnlCriterion.add(addCriterion());
				pnlCriterion.revalidate();
			}
		});
		pnlCriterion.add(btnAddCriterion);
		pnlCriterion.add(addCriterion());
		criterionFrame.add(new JScrollPane(pnlCriterion));
	}

	/**
	 * Returns a JPanel with a JTextField to define the criterion(s) and a
	 * button to upload the .jar file.
	 **/
	private JPanel addCriterion() {
		JPanel pnlCriterion = new JPanel(new FlowLayout());
		JPanel pnlCriterionName = new JPanel();
		JPanel pnlCriterionJar = new JPanel();

		JLabel lblCriterionName = new JLabel("Criterion Name:");
		JTextField txtCriterionName = new JTextField();
		txtCriterionName.setColumns(30);

		JLabel lblJarPath = new JLabel("Jar Path");
		JTextField txtJarPath = new JTextField();
		txtJarPath.setColumns(25);
		btnReadJar = new JButton("Add jar");
		btnReadJar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchUploadJar = new JFileChooser();
				if (fchUploadJar.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtJarPath.setText(fchUploadJar.getSelectedFile().getPath());
				}
			}
		});

		pnlCriterionName.add(lblCriterionName);
		pnlCriterionName.add(txtCriterionName);
		pnlCriterionJar.add(lblJarPath);
		pnlCriterionJar.add(txtJarPath);
		pnlCriterionJar.add(btnReadJar);
		pnlCriterion.add(pnlCriterionName);
		pnlCriterion.add(pnlCriterionJar);
		return pnlCriterion;
	}

	/**
	 * Adds content to the Help frame
	 **/
	private void setHelpFrame(JFrame frame) {
		
		JPanel pnlHelp= new JPanel(new BorderLayout());
		
		JPanel pnlFAQS = new JPanel(new GridLayout(6, 0));
		JPanel pnlSendEmail = new JPanel();
		
		
		JButton btnSendEmail = new JButton("Send Email");
		btnSendEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEmailFrame();
			}
		});
		
		//Colocar noutro sitio a ler de um .txt
		List<String> listFAQ = new ArrayList<String>();
		listFAQ.add("What kind of problems can this software optimize?");
		listFAQ.add("Any problem, if you can give us the solution you already have to compare");
		
		listFAQ.add("How can I upload my current solution?");
		listFAQ.add("Click on button \"Criterion\" to be optimzed and then you can upload your .jar file");
		
		for(int i = 0; i<listFAQ.size(); i+=2){
			pnlFAQS.add(addFAQPanel(listFAQ.get(i), listFAQ.get(i+1)));
		}
		
		pnlSendEmail.add(btnSendEmail);
		pnlHelp.add(pnlFAQS, BorderLayout.CENTER);
		pnlHelp.add(pnlSendEmail, BorderLayout.PAGE_END);
		helpFrame.add(pnlHelp);
		
	}

	private JPanel addFAQPanel(String question, String answer){
		JPanel pnlFAQ = new JPanel(new GridLayout(2, 0)); 
		JLabel lblQuestion = new JLabel("Question: "+question);
		JLabel lblAnswer = new JLabel("Answer: "+answer);
		lblAnswer.setForeground(Color.gray);
		
		pnlFAQ.add(lblQuestion);
		pnlFAQ.add(lblAnswer);
		return pnlFAQ;
	}
	/**
	 * Defines the size of the given frame. The second parameter indicates the
	 * number that the screen is divided by.
	 **/
	private void setFrame(JFrame frame, double size) {
		double frameWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double frameHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		frame.setSize((int) (frameWidth * size), (int) (frameHeight * size));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public JButton getHelpButton() {
		return btnHelp;
	}

	public JButton getEmailButton() {
		return btnEmail;
	}

	public JButton getMessageSendButton() {
		return btnMessageSend;
	}

	public JButton getDecisionVarButton() {
		return btnDecisionVariables;
	}

	public JButton getCriterionButton() {
		return btnCriterion;
	}

	public JButton getSaveButton() {
		return btnSave;
	}

	public JButton getReadButton() {
		return btnRead;
	}

	public JButton getAddCriterionButton() {
		return btnAddCriterion;
	}

	public JButton getReadJarButton() {
		return btnReadJar;
	}

	public void fillInicialForm() {
		txtProblemName.setText(problem.getName());
		txaProblemDescription.setText(problem.getDescription());
		txtEmail.setText(problem.getEmail());
		spnNumberOfDays.setValue(problem.getMax().getDays());
		spnNumberOfHours.setValue(problem.getMax().getHours());
		spnNumberOfMinutes.setValue(problem.getMax().getMinutes());
		spnIdealNumberOfDays.setValue(problem.getIdeal().getDays());
		spnIdealNumberOfHours.setValue(problem.getIdeal().getHours());
		spnIdealNumberOfMinutes.setValue(problem.getIdeal().getMinutes());
		spnNumberOfDecisionVariables.setValue(problem.getNumberVariables());
	}

	public void fillDecisionVariableForm() {
		txtNameOfDecisionVariablesGroup.setText(problem.getGroupName());

		DefaultTableModel dtmDecisionVariablesXML = new DefaultTableModel();
		tblDecisionVariables.setModel(dtmDecisionVariablesXML);

		dtmDecisionVariablesXML.addColumn("Name");
		dtmDecisionVariablesXML.addColumn("Type");
		dtmDecisionVariablesXML.addColumn("Interval");
		dtmDecisionVariablesXML.addColumn("Restrictions");
		String[] variableDataTypesXML = { "boolean", "byte", "char", "double", "float", "integer", "long", "real",
				"short", "String" };
		JComboBox<String> cmbVariableDataTypesXML = new JComboBox<>(variableDataTypesXML);

		for (int i = 0; i < Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString()); i++) {
			List<Variable> variablesList = problem.getVariables();
			if (i < variablesList.size()) {
				dtmDecisionVariablesXML
						.addRow(new Object[] { variablesList.get(i).getName(), variablesList.get(i).getType(),
								variablesList.get(i).getMin() + ":" + variablesList.get(i).getMax(),
								variablesList.get(i).getRestriction() });
			} else {
				dtmDecisionVariablesXML.addRow(new Object[] { "", "", "" });
			}
			tblDecisionVariables.getColumnModel().getColumn(1)
					.setCellEditor(new DefaultCellEditor(cmbVariableDataTypesXML));
		}
	}

	public void saveProblem() {
		String groupName = "";
		List<Variable> variablesList = new ArrayList<Variable>();

		if (txtNameOfDecisionVariablesGroup != null && tblDecisionVariables != null) {
			groupName = txtNameOfDecisionVariablesGroup.getText();

			DefaultTableModel dtmDecisionVariables = (DefaultTableModel) tblDecisionVariables.getModel();
			int numberOfRows = dtmDecisionVariables.getRowCount();
			for (int i = 0; i < numberOfRows; i++) {
				String decisionVariableName = "";
				String decisionVariableType = "";
				String decisionVariableMinValue = "";
				String decisionVariableMaxValue = "";
				String decisionVariableRestriction = "";
				String[] decisionVariableRange = dtmDecisionVariables.getValueAt(i, 2).toString().split(":");

				if (dtmDecisionVariables.getValueAt(i, 0).toString() != null)
					decisionVariableName = dtmDecisionVariables.getValueAt(i, 0).toString();
				if (dtmDecisionVariables.getValueAt(i, 1).toString() != null)
					decisionVariableType = dtmDecisionVariables.getValueAt(i, 1).toString();
				if (decisionVariableRange.length > 0)
					decisionVariableMinValue = decisionVariableRange[0];
				if (decisionVariableRange.length > 1)
					decisionVariableMaxValue = decisionVariableRange[1];
				if (dtmDecisionVariables.getValueAt(i, 3).toString() != null)
					decisionVariableRestriction = dtmDecisionVariables.getValueAt(i, 3).toString();
				variablesList.add(new Variable(decisionVariableName, decisionVariableType, decisionVariableMinValue,
						decisionVariableMaxValue, decisionVariableRestriction));
			}
		}

		problem = new Problem(txtProblemName.getText(), txaProblemDescription.getText(), txtEmail.getText(),
				new Time(Integer.parseInt(spnNumberOfDays.getValue().toString()),
						Integer.parseInt(spnNumberOfHours.getValue().toString()),
						Integer.parseInt(spnNumberOfMinutes.getValue().toString())),
				new Time(Integer.parseInt(spnIdealNumberOfDays.getValue().toString()),
						Integer.parseInt(spnIdealNumberOfHours.getValue().toString()),
						Integer.parseInt(spnIdealNumberOfMinutes.getValue().toString())),
				groupName, Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString()), variablesList);
	}

}