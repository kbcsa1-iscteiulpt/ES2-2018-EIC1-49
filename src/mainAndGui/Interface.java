package mainAndGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import problem.Criteria;
import problem.UserProblem;
import problem.Time;
import problem.Variable;
import support.BinaryExperiment;
import support.DoubleExperiment;
import support.IntegerExperiment;
import support.Support;
import support.XML_Editor;

/**
 * This class represents the interface
 * 
 * @author Diana Lopes n� 72898
 **/

public class Interface {

	private String adminEmail;

	private JFrame decisionFrame;
	private JFrame readProblemFrame;
	private JFrame createProblemFrame;
	private JButton btnCreateProblem;
	private JButton btnReadProblem;
	private JFrame graphicsFrame;

	private JButton btnHelp;
	private JButton btnGoBack;
	private JButton btnWriteEmailFAQ;

	private JButton btnReadXML;
	private JTextField txtFilePathXML;

	private JTextField txtProblemName;
	private JTextArea txaProblemDescription;
	private JButton btnWriteEmail;
	private JTextField txtEmail;
	private JTextField txtMessageTitle;
	private JTextArea txaMessageBody;
	private JButton btnMessageSend;

	private JSpinner spnMaxNumberOfDays;
	private JSpinner spnMaxNumberOfHours;
	private JSpinner spnMaxNumberOfMinutes;
	private JSpinner spnIdealNumberOfDays;
	private JSpinner spnIdealNumberOfHours;
	private JSpinner spnIdealNumberOfMinutes;

	private JSpinner spnNumberOfDecisionVariables;
	private JButton btnDecisionVariables;

	private DefaultTableModel dtmDecisionVariables;
	private JTextField txtNameOfDecisionVariablesGroup;
	private JTable tblDecisionVariables;
	private JButton btnDecisionVariablesFinish;

	private JButton btnCriteria;
	private JButton btnAddCriteria;
	private JButton btnReadJar;
	private int criteriaAdded = 0;
	private JButton btnCriteriaFinish;

	private JButton btnSaveToXML;
	private JButton btnExecuteProcess;
	private JButton btnExecuteGraphics;

	private Map<Integer, String> criteriaNames = new HashMap<Integer, String>();
	private Map<Integer, String> criteriaPaths = new HashMap<Integer, String>();
	private Map<Integer, String> criteriaTypes = new HashMap<Integer, String>();

	private Support support = new Support();
	private XML_Editor xml = new XML_Editor();
	private UserProblem problem = new UserProblem();
	
	private String problemType = "Binary" ; // TODO Alterar

	public Interface(String adminEmail) {
		decisionFrame = new JFrame("Problem to be optimized");
		setFrame(decisionFrame, 0.25);
		setDecisionContent(decisionFrame);
		decisionFrame.setVisible(true);
		this.adminEmail = adminEmail;
	}

	/**
	 * Returns a frame with two options: read a problem from a XML file or create a
	 * new one.
	 **/
	private void setDecisionContent(JFrame frame) {
		GridLayout gly = new GridLayout(0, 2);
		gly.setHgap(5);
		JPanel decisionPanel = new JPanel(gly);
		btnReadProblem = new JButton("Read problem from XML file");
		btnReadProblem.setContentAreaFilled(false);
		btnReadProblem.setFocusable(false);
		btnReadProblem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (readProblemFrame == null) {
					readProblemFrame = new JFrame("Problem to be optimized");
					setFrame(readProblemFrame, 0.75);
					setContent(readProblemFrame, true);

					JFileChooser fchReadXML = new JFileChooser();
					if (fchReadXML.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						String filePath = fchReadXML.getSelectedFile().getPath();
						problem = xml.read(filePath);
						fillInicialForm(filePath);
						fillDecisionVariableForm();
					}
				}
				readProblemFrame.setVisible(true);
				decisionFrame.setVisible(false);
			}
		});
		btnCreateProblem = new JButton("Create a new problem");
		btnCreateProblem.setContentAreaFilled(false);
		btnCreateProblem.setFocusable(false);
		btnCreateProblem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (createProblemFrame == null) {
					createProblemFrame = new JFrame("Problem to be optimized");
					setFrame(createProblemFrame, 0.75);
					setContent(createProblemFrame, false);
				}
				createProblemFrame.setVisible(true);
				decisionFrame.setVisible(false);
			}
		});
		decisionPanel.add(btnReadProblem);
		decisionPanel.add(btnCreateProblem);
		frame.add(decisionPanel);
	}

	/**
	 * The panels are added to the initial frame by calling methods that return
	 * JPanel.
	 **/
	private void setContent(JFrame frame, boolean problemReadFromXML) {
		JPanel initialPanel = new JPanel(new GridLayout(problemReadFromXML ? 11 : 10, 0));

		initialPanel.add(helpFAQPanel(frame));

		if (problemReadFromXML) {
			initialPanel.add(readPanel());
		}

		initialPanel.add(problemNamePanel());
		initialPanel.add(problemDescriptionPanel());
		initialPanel.add(emailPanel(frame));
		initialPanel.add(maxTimePanel());
		initialPanel.add(idealTimePanel());
		initialPanel.add(decisionVarPanel());
		initialPanel.add(savePanel());
		initialPanel.add(criteriaPanel());
		initialPanel.add(executeProcessPanel());
		frame.add(initialPanel);
	}

	/**
	 * Returns a panel with a question mark placed at the top-right and a return
	 * button at the top-left of the frame. When the question mark is clicked, a new
	 * frame is displayed to show the FAQ. When the return symbol is clicked, it
	 * returns to the initial decision panel.
	 * 
	 **/
	private JPanel helpFAQPanel(JFrame frame) {
		JPanel pnlHelpFAQ = new JPanel(new BorderLayout());
		btnGoBack = new JButton();
		ImageIcon icoGoBack = new ImageIcon(((new ImageIcon("./src/images/goBack.png")).getImage())
				.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnGoBack.setContentAreaFilled(false);
		btnGoBack.setBorderPainted(false);
		btnGoBack.setFocusPainted(false);
		btnGoBack.setIcon(icoGoBack);
		btnGoBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				decisionFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnHelp = new JButton();
		ImageIcon icoQuestion_mark = new ImageIcon(((new ImageIcon("./src/images/question_mark.png")).getImage())
				.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnHelp.setIcon(icoQuestion_mark);
		btnHelp.setContentAreaFilled(false);
		btnHelp.setBorderPainted(false);
		btnHelp.setFocusPainted(false);
		btnHelp.setToolTipText("Help and FAQs");
		btnHelp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame helpFrame = new JFrame("Help Section");
				setFrame(helpFrame, 0.5);
				setHelpFrame(frame, helpFrame);
				helpFrame.setVisible(true);
			}
		});
		pnlHelpFAQ.add(btnGoBack, BorderLayout.LINE_START);
		pnlHelpFAQ.add(btnHelp, BorderLayout.LINE_END);
		return pnlHelpFAQ;
	}

	/**
	 * Returns a panel with a JTextField filled automatically with the file path (to
	 * read XML) through the button.
	 **/
	private JPanel readPanel() {
		JPanel pnlRead = new JPanel();
		btnReadXML = new JButton("Read from a XML File:");
		btnReadXML.setToolTipText("Read a XML file. This action will replace the fields already filled");
		txtFilePathXML = new JTextField();
		txtFilePathXML.setEditable(false);
		btnReadXML.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchReadXML = new JFileChooser();
				if (fchReadXML.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String filePath = fchReadXML.getSelectedFile().getPath();
					problem = xml.read(filePath);
					txtFilePathXML.setText(filePath);
					pnlRead.add(txtFilePathXML);
					pnlRead.revalidate();
					fillInicialForm(filePath);
					fillDecisionVariableForm();
				}
			}
		});
		pnlRead.add(btnReadXML);
		pnlRead.add(txtFilePathXML);
		return pnlRead;
	}

	/**
	 * Returns a panel with a JTextField for user to fill with the problem's name.
	 **/
	private JPanel problemNamePanel() {
		JPanel pnlProblemName = new JPanel();
		JLabel lblProblemNameMandatory = new JLabel("*");
		lblProblemNameMandatory.setForeground(Color.red);
		JLabel lblProblemName = new JLabel("Problem's Name:");
		txtProblemName = new JTextField();
		txtProblemName.setToolTipText("Enter the problem's name here.");
		txtProblemName.setColumns(20);
		pnlProblemName.add(lblProblemNameMandatory);
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
		JLabel lblProblemDescriptionMandatory = new JLabel("*");
		lblProblemDescriptionMandatory.setForeground(Color.red);
		JLabel lblProblemDescription = new JLabel("Problem's Description:");
		txaProblemDescription = new JTextArea(4, 60);
		txaProblemDescription.setToolTipText("Describe your problem here.");
		JScrollPane scrProblemDescription = new JScrollPane(txaProblemDescription);
		txaProblemDescription.setLineWrap(true);
		pnlProblemDescription.add(lblProblemDescriptionMandatory);
		pnlProblemDescription.add(lblProblemDescription);
		pnlProblemDescription.add(scrProblemDescription);
		return pnlProblemDescription;
	}

	/**
	 * Returns a panel with a JTextField to fill with user's email. When the button
	 * is clicked, a new frame is displayed to write and send the email.
	 **/
	private JPanel emailPanel(JFrame frame) {
		JPanel pnlEmail = new JPanel(new FlowLayout());
		JLabel lblEmailMandatory = new JLabel("*");
		lblEmailMandatory.setForeground(Color.red);
		JLabel lblEmail = new JLabel("Enter your Email:");
		txtEmail = new JTextField();
		txtEmail.setColumns(20);
		btnWriteEmail = new JButton("Write Email");
		btnWriteEmailFAQ = new JButton("Write Email");
		btnWriteEmail.setToolTipText("Enter your email, then you can write one.");
		btnWriteEmailFAQ.setToolTipText("Enter your email, then you can write one.");
		btnWriteEmail.setContentAreaFilled(false);
		btnWriteEmail.setEnabled(false);
		btnWriteEmailFAQ.setEnabled(false);

		txtEmail.getDocument().addDocumentListener(new DocumentListener() {

			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
					+ "A-Z]{2,7}$";
			Pattern patEmail = Pattern.compile(emailRegex);

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!patEmail.matcher(txtEmail.getText()).matches()) {
					btnWriteEmail.setEnabled(false);
					btnWriteEmailFAQ.setEnabled(false);
					btnWriteEmail.setToolTipText("Enter your email, then you can write one.");
					btnWriteEmailFAQ.setToolTipText("Enter your email, then you can write one.");

				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (patEmail.matcher(txtEmail.getText()).matches()) {
					btnWriteEmail.setEnabled(true);
					btnWriteEmailFAQ.setEnabled(true);
					btnWriteEmail.setToolTipText("Write an email");
					btnWriteEmailFAQ.setToolTipText("Write an email.");

				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

		btnWriteEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEmailFrame(frame);
			}
		});
		pnlEmail.add(lblEmailMandatory);
		pnlEmail.add(lblEmail);
		pnlEmail.add(txtEmail);
		pnlEmail.add(btnWriteEmail);
		return pnlEmail;
	}

	/**
	 * Returns a panel with a JSpinner for user to select the maximum time to wait
	 * for the optimization.
	 **/
	private JPanel maxTimePanel() {
		JPanel pnlMaxTime = new JPanel(new FlowLayout());
		JLabel lblMaxTime = new JLabel("Maximum time for optimization:");

		JLabel lblNumberofDays = new JLabel("Days");
		JLabel lblNumberofHours = new JLabel("Hours");
		JLabel lblNumberofMinutes = new JLabel("Minutes");

		spnMaxNumberOfDays = new JSpinner(new SpinnerNumberModel(0, 0, 31, 1));
		spnMaxNumberOfHours = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
		spnMaxNumberOfMinutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		pnlMaxTime.add(lblMaxTime);
		pnlMaxTime.add(spnMaxNumberOfDays);
		pnlMaxTime.add(lblNumberofDays);
		pnlMaxTime.add(spnMaxNumberOfHours);
		pnlMaxTime.add(lblNumberofHours);
		pnlMaxTime.add(spnMaxNumberOfMinutes);
		pnlMaxTime.add(lblNumberofMinutes);
		return pnlMaxTime;
	}

	/**
	 * Returns a panel with a JSpinner for user to select the ideal time to wait for
	 * the optimization.
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
	 * Returns a panel with a JSpinner to select the number of decision variables
	 * and button. When clicked, a new frame is displayed to write the decision
	 * variable group name and to fill the table of the variable decision.
	 **/

	private JPanel decisionVarPanel() {
		JPanel pnlDecisionVar = new JPanel(new FlowLayout());

		btnDecisionVariables = new JButton("Decision Variables");
		btnDecisionVariables.setContentAreaFilled(false);
		spnNumberOfDecisionVariables = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		txtNameOfDecisionVariablesGroup = new JTextField();
		tblDecisionVariables = new JTable();
		JFrame decisionVarFrame = new JFrame("Decision Variables");
		setFrame(decisionVarFrame, 0.5);
		setDecisionFrame(decisionVarFrame);
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
	 * Returns a panel with a JButton that saves the configuration to a XML file.
	 **/
	private JPanel savePanel() {
		JPanel pnlSave = new JPanel();
		btnSaveToXML = new JButton("Save XML File");
		btnSaveToXML.setToolTipText("Save your problem into a XML file");
		btnSaveToXML.setContentAreaFilled(false);
		btnSaveToXML.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtProblemName.getText().isEmpty() || txaProblemDescription.getText().isEmpty()
						|| txtEmail.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all the mandatory fields", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					JFileChooser fchXMLSave = new JFileChooser();
					fchXMLSave.setDialogTitle("Save");
					fchXMLSave.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
					if (fchXMLSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						saveProblem();
						String filePath = fchXMLSave.getSelectedFile().getPath();
						if (!filePath.endsWith(".xml")) {
							filePath += ".xml";
						}
						xml.write(filePath, problem);
					}
				}
			}
		});

		pnlSave.add(btnSaveToXML);
		return pnlSave;
	}

	/**
	 * Returns a panel with a button. When clicked, a new frame is displayed to
	 * specify the criteria.
	 **/
	private JPanel criteriaPanel() {
		JPanel pnlCriteria = new JPanel();
		btnCriteria = new JButton("Criteria to be optimized");
		btnCriteria.setContentAreaFilled(false);
		JFrame criteriaFrame = new JFrame("Criterias");
		setFrame(criteriaFrame, 0.5);
		setCriteriaFrame(criteriaFrame);
		btnCriteria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				criteriaFrame.setVisible(true);
			}
		});
		pnlCriteria.add(btnCriteria);
		return pnlCriteria;
	}

	/**
	 * Returns a panel with the button to execute the optimization process.
	 **/
	private JPanel executeProcessPanel() {
		JPanel executeProcessPanel = new JPanel(new FlowLayout());
		btnExecuteProcess = new JButton("Execute Optimization Process");
		ImageIcon icoExecute = new ImageIcon(((new ImageIcon("./src/images/execute.png")).getImage())
				.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnExecuteProcess.setContentAreaFilled(false);
		btnExecuteProcess.setIcon(icoExecute);
		btnExecuteProcess.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtProblemName.getText().isEmpty() || txaProblemDescription.getText().isEmpty()
						|| txtEmail.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all the mandatory fields", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
//					sdfdsf;
//					Time idealTime = new Time(Integer.parseInt(spnMaxNumberOfDays.getValue().toString()),
//							Integer.parseInt(spnMaxNumberOfHours.getValue().toString()),
//							Integer.parseInt(spnMaxNumberOfMinutes.getValue().toString()));
//					
//					Time maxTime = new Time(Integer.parseInt(spnIdealNumberOfDays.getValue().toString()),
//							Integer.parseInt(spnIdealNumberOfHours.getValue().toString()),
//							Integer.parseInt(spnIdealNumberOfMinutes.getValue().toString()));
//					
//					UserProblem problemToOptimize = new UserProblem(txtProblemName.getText(), txaProblemDescription.getText(), txtEmail.getText(), maxTime, idealTime,
//							txtNameOfDecisionVariablesGroup.getText(),Integer.parseInt(txtNameOfDecisionVariablesGroup.getText()), createVariableList());
					problem.setGroupName(txtNameOfDecisionVariablesGroup.getText());
					
					problem.setNumberVariables(Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString()));
					try {
						switch(problemType) {
					    		case "Double":
					    			new DoubleExperiment(problem);
					    			break;
					    		case "Integer":  
					    			new IntegerExperiment(problem);
			                    break;
					    		case "Binary":
					    			new BinaryExperiment(problem);
			                    break;
					    		default: 
					    			return;
						}
					} catch (IOException e2) {
						try {
							support.SendEmail(adminEmail, txtEmail.getText(), "There was a problem", "There was a problem runnig the problem you requested, please try again."
									+ "If the problem continues, contact us so we can help");
							return;
						} catch (AddressException e1) {
							
						} catch (MessagingException e1) {
							
						}
					}
					try {
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Date date = new Date();

						String subject = "Otimiza��o em curso: " + txtProblemName.getText() + " "
								+ dateFormat.format(date);
						String message = "Muito obrigado por usar esta plataforma de otimiza��o. Ser� informado por email\r\n"
								+ "sobre o progresso do processo de otimiza��o, quando o processo de otimiza��o tiver atingido 25%,\r\n"
								+ "50%, 75% do total do (n�mero de avalia��es ou) tempo estimado, e tamb�m quando o processo tiver\r\n"
								+ "terminado, com sucesso ou devido � ocorr�ncia de erros.";

						support.SendEmail(adminEmail, txtEmail.getText(), subject, message);

					} catch (MessagingException e1) {
					}
				}
			}
		});

		btnExecuteGraphics = new JButton("Graphics");
		btnExecuteGraphics.setContentAreaFilled(false);
		btnExecuteGraphics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphicsFrame = new JFrame("Graphics");
				setFrame(graphicsFrame, 0.5);
				setGraphicsFrame(graphicsFrame);
			}
		});
		executeProcessPanel.add(btnExecuteProcess);
		executeProcessPanel.add(btnExecuteGraphics);
		return executeProcessPanel;
	}

	/**
	 * Returns a frame with two JTextField to fill and a button to send the email.
	 * One with the subject of the email and the other with the email body.
	 **/
	private JFrame setEmailFrame(JFrame frame) {
		JFrame sendEmailFrame = new JFrame("Email");
		setFrame(sendEmailFrame, 0.5);
		JPanel pnlSendEmail = new JPanel(new BorderLayout());
		JPanel pnlMessageTitle = new JPanel(new BorderLayout());
		JPanel pnlMessageBody = new JPanel(new BorderLayout());
		JPanel pnlMessageSend = new JPanel(new BorderLayout());

		JLabel lblMessageTitle = new JLabel("Subject:");
		txtMessageTitle = new JTextField();
		txtMessageTitle.setColumns(30);

		pnlMessageTitle.add(lblMessageTitle, BorderLayout.NORTH);
		pnlMessageTitle.add(txtMessageTitle, BorderLayout.CENTER);

		JLabel lblMessageBody = new JLabel("Message:");
		txaMessageBody = new JTextArea();
		txaMessageBody.setLineWrap(true);
		JScrollPane scrMessageBody = new JScrollPane(txaMessageBody);

		pnlMessageBody.add(lblMessageBody, BorderLayout.NORTH);
		pnlMessageBody.add(scrMessageBody, BorderLayout.CENTER);

		btnMessageSend = new JButton("Send Message");
		btnMessageSend.setContentAreaFilled(false);
		ImageIcon icoSendMessage = new ImageIcon(((new ImageIcon("./src/images/send_message.png")).getImage())
				.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnMessageSend.setContentAreaFilled(false);
		btnMessageSend.setIcon(icoSendMessage);
		btnMessageSend.addActionListener(new ActionListener() {

			/**
			 * Closes the email frame and sends the intended email, if the operation fails
			 * shows a warning message.
			 **/
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtMessageTitle.getText().isEmpty() || txaMessageBody.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill the subject and the message body", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						sendEmailFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						String subject = "From: " + txtEmail.getText() + txtMessageTitle.getText();
						support.SendEmail(txtEmail.getText(), adminEmail, txtMessageTitle.getText(), subject);
					} catch (MessagingException e1) {
						JOptionPane.showMessageDialog(pnlSendEmail, "Error sending email, connection issue!", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		pnlMessageSend.add(btnMessageSend, BorderLayout.SOUTH);

		pnlSendEmail.add(pnlMessageTitle, BorderLayout.NORTH);
		pnlSendEmail.add(pnlMessageBody, BorderLayout.CENTER);
		pnlSendEmail.add(pnlMessageSend, BorderLayout.SOUTH);

		sendEmailFrame.add(pnlSendEmail);
		sendEmailFrame.setVisible(true);
		return sendEmailFrame;
	}

	/**
	 * Adds content to the frame given with a table to write the decision variable
	 * group name and to fill the table of the variable decision. Each decision
	 * variable has a name, type, and potential intervals and restrictions.
	 **/
	private void setDecisionFrame(JFrame decisionVarFrame) {
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
		String[] variableDataTypes = { "boolean", "byte", "char", "double", "float", "integer", "long", "real", "short",
				"String" };
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

		pnlDecision.add(btnDecisionVariablesFinish, BorderLayout.PAGE_END);
		decisionVarFrame.add(pnlDecision);
	}

	/**
	 * Adds content to the frame with a JTextField to define the criteria(s), a
	 * button to add a new criteria and a button to upload the .jar file.
	 **/
	private void setCriteriaFrame(JFrame criteriaFrame) {
		JPanel pnlCriteria = new JPanel(new BorderLayout());
		JPanel pnlAddCriteria = new JPanel();
		JPanel pnlCriteriaList = new JPanel();
		pnlCriteriaList.setLayout(new BoxLayout(pnlCriteriaList, BoxLayout.Y_AXIS));
		JPanel pnlCriteriaFinish = new JPanel();

		btnAddCriteria = new JButton("Add criteria");
		btnAddCriteria.setContentAreaFilled(false);
		btnAddCriteria.setFocusable(false);
		btnAddCriteria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				pnlCriteriaList.add(addCriteria());
				pnlCriteria.revalidate();
			}
		});

		btnCriteriaFinish = new JButton("Finish");
		btnCriteriaFinish.setContentAreaFilled(false);
		ImageIcon icoFinish = new ImageIcon(((new ImageIcon("./src/images/finish.png")).getImage())
				.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
		btnCriteriaFinish.setContentAreaFilled(false);
		btnCriteriaFinish.setIcon(icoFinish);
		btnCriteriaFinish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean criteriaReady = true;
				for (int i = 1; i <= criteriaAdded; i++) {
					if ((criteriaTypes.isEmpty() || criteriaTypes.get(i).equals("Select a data type"))
							|| criteriaPaths.isEmpty() || criteriaNames.isEmpty()) {

						JOptionPane.showMessageDialog(null, "Please fill all criteria fields", "Warning",
								JOptionPane.WARNING_MESSAGE);
						criteriaReady = false;
						break;
					}
				}
				if (criteriaReady) {
					for (int j = 1; j <= criteriaAdded; j++) {
						Criteria criteria = new Criteria(criteriaNames.get(j), criteriaPaths.get(j),
								criteriaTypes.get(j));
						problem.addCriteria(criteria);
					}
				}
			}
		});

		pnlAddCriteria.add(btnAddCriteria);
		pnlCriteriaList.add(addCriteria());
		pnlCriteriaFinish.add(btnCriteriaFinish);

		pnlCriteria.add(pnlAddCriteria, BorderLayout.PAGE_START);
		pnlCriteria.add(pnlCriteriaList, BorderLayout.CENTER);
		pnlCriteria.add(pnlCriteriaFinish, BorderLayout.PAGE_END);

		criteriaFrame.add(new JScrollPane(pnlCriteria));
	}

	/**
	 * Returns a JPanel with a JTextField to define the criteria(s) and a button to
	 * upload the .jar file.
	 **/
	private JPanel addCriteria() {
		criteriaAdded++;
		JPanel pnlCriteria = new JPanel(new FlowLayout());
		JPanel pnlCriteriaName = new JPanel();
		JPanel pnlCriteriaJar = new JPanel();
		JPanel pnlCriteriaDataType = new JPanel();

		JLabel lblCriteriaName = new JLabel("Criteria Name:");
		JTextField txtCriteriaName = new JTextField();
		txtCriteriaName.setColumns(30);

		txtCriteriaName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				criteriaNames.put(criteriaAdded, txtCriteriaName.getText());
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaNames.remove(criteriaAdded);
				criteriaAdded--;
			}
		});

		JLabel lblJarPath = new JLabel("Jar Path");
		JTextField txtJarPath = new JTextField();
		txtJarPath.setColumns(15);
		txtJarPath.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (criteriaPaths.containsKey(criteriaAdded)) {
					criteriaPaths.remove(criteriaAdded);
					criteriaAdded--;
				}
				criteriaPaths.put(criteriaAdded, txtJarPath.getText());
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaPaths.remove(criteriaAdded);
				criteriaAdded--;
			}
		});

		btnReadJar = new JButton("Add jar");
		btnReadJar.setContentAreaFilled(false);
		btnReadJar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchUploadJar = new JFileChooser();
				if (fchUploadJar.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String jarPath = fchUploadJar.getSelectedFile().getPath();
					txtJarPath.setText(jarPath);
					if (criteriaPaths.containsKey(criteriaAdded)) {
						criteriaPaths.remove(criteriaAdded);
						criteriaAdded--;
					}
					criteriaPaths.put(criteriaAdded, jarPath);
				}
			}
		});

		String[] dataTypeCriteria = { "Select a data type", "Binary", "Double", "Integer" };
		JComboBox<String> cmbDataType = new JComboBox<String>(dataTypeCriteria);

		cmbDataType.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				criteriaTypes.put(criteriaAdded, cmbDataType.getSelectedItem().toString());
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaTypes.remove(criteriaAdded);
				criteriaAdded--;
			}
		});

		pnlCriteriaName.add(lblCriteriaName);
		pnlCriteriaName.add(txtCriteriaName);
		pnlCriteriaJar.add(lblJarPath);
		pnlCriteriaJar.add(txtJarPath);
		pnlCriteriaJar.add(btnReadJar);
		pnlCriteriaDataType.add(cmbDataType);
		pnlCriteria.add(pnlCriteriaName);
		pnlCriteria.add(pnlCriteriaJar);
		pnlCriteria.add(pnlCriteriaDataType);
		return pnlCriteria;
	}

	/**
	 * Adds content to the Help frame
	 **/
	private void setHelpFrame(JFrame frame, JFrame helpFrame) {

		JPanel pnlHelp = new JPanel(new BorderLayout());

		JPanel pnlFAQ = new JPanel(new GridLayout(6, 0));
		JPanel pnlSendEmail = new JPanel();

		btnWriteEmailFAQ.setContentAreaFilled(false);
		btnWriteEmailFAQ.setFocusable(false);
		btnWriteEmailFAQ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEmailFrame(frame);
			}
		});

		Map<String, String> listFAQ = readFAQfile("./src/files/faq.txt");

		for (String question : listFAQ.keySet()) {
			pnlFAQ.add(addFAQPanel(question, listFAQ.get(question)));
		}

		pnlSendEmail.add(btnWriteEmailFAQ);
		pnlHelp.add(pnlFAQ, BorderLayout.CENTER);
		pnlHelp.add(pnlSendEmail, BorderLayout.PAGE_END);
		helpFrame.add(pnlHelp);

	}

	/**
	 * Reads FAQ from .txt file
	 **/
	private Map<String, String> readFAQfile(String s) {
		Scanner scanner = null;
		Map<String, String> listFAQ = new HashMap<String, String>();
		try {
			scanner = new Scanner(new File(s));
			String line = "";

			while (scanner.hasNext()) {
				line = scanner.nextLine();
				String[] tokens = line.split(";");
				listFAQ.put(tokens[0], tokens[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return listFAQ;

	}

	/**
	 * Returns a panel with a question and an answer.
	 **/
	private JPanel addFAQPanel(String question, String answer) {
		JPanel pnlFAQ = new JPanel(new GridLayout(2, 0));
		JLabel lblQuestion = new JLabel("Question: " + question);
		JLabel lblAnswer = new JLabel("Answer: " + answer);
		lblAnswer.setForeground(Color.gray);

		pnlFAQ.add(lblQuestion);
		pnlFAQ.add(lblAnswer);
		return pnlFAQ;
	}

	/**
	 * Frame that shows the graphics from the optimization process
	 **/
	private void setGraphicsFrame(JFrame frame) {
		frame.setVisible(true);
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
		if (frame.getTitle().equals("Problem to be optimized")) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else if (frame.getTitle().equals("Criterias") || frame.getTitle().equals("Decision Variables")) {
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		} else {
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}

	/**
	 * Fills the form with the data from a XML file.
	 **/
	public void fillInicialForm(String filePath) {
		txtFilePathXML.setText(filePath);
		txtProblemName.setText(problem.getName());
		txaProblemDescription.setText(problem.getDescription());
		txtEmail.setText(problem.getEmail());
		spnMaxNumberOfDays.setValue(problem.getMax().getDays());
		spnMaxNumberOfHours.setValue(problem.getMax().getHours());
		spnMaxNumberOfMinutes.setValue(problem.getMax().getMinutes());
		spnIdealNumberOfDays.setValue(problem.getIdeal().getDays());
		spnIdealNumberOfHours.setValue(problem.getIdeal().getHours());
		spnIdealNumberOfMinutes.setValue(problem.getIdeal().getMinutes());
	}

	/**
	 * Fills the decision variable table with the data from a XML file.
	 **/
	public void fillDecisionVariableForm() {
		txtNameOfDecisionVariablesGroup.setText(problem.getGroupName());
		spnNumberOfDecisionVariables.setValue(problem.getNumberVariables());

		dtmDecisionVariables = new DefaultTableModel();
		tblDecisionVariables.setModel(dtmDecisionVariables);

		dtmDecisionVariables.addColumn("Name");
		dtmDecisionVariables.addColumn("Type");
		dtmDecisionVariables.addColumn("Minimum Value");
		dtmDecisionVariables.addColumn("Maximum Value");
		dtmDecisionVariables.addColumn("Restrictions");
		String[] variableDataTypesXML = { "boolean", "byte", "char", "double", "float", "integer", "long", "real",
				"short", "String" };
		JComboBox<String> cmbVariableDataTypesXML = new JComboBox<>(variableDataTypesXML);

		for (int i = 0; i < problem.getNumberVariables(); i++) {
			List<Variable> variablesList = problem.getVariables();

			if (i < variablesList.size()) {
				dtmDecisionVariables.addRow(new Object[] { variablesList.get(i).getName(),
						variablesList.get(i).getType(), variablesList.get(i).getMin(), variablesList.get(i).getMax(),
						variablesList.get(i).getRestriction() });
			} else {
				dtmDecisionVariables.addRow(new Object[] { "", "", "", "", "" });
			}
			tblDecisionVariables.getColumnModel().getColumn(1)
					.setCellEditor(new DefaultCellEditor(cmbVariableDataTypesXML));
		}
	}
	
	public List<Variable> createVariableList(){
		List<Variable> variablesList = new ArrayList<Variable>();

		if (tblDecisionVariables != null) {
			DefaultTableModel dtmDecisionVariables = (DefaultTableModel) tblDecisionVariables.getModel();
			int numberOfRows = dtmDecisionVariables.getRowCount();
			for (int i = 0; i < numberOfRows; i++) {
				String decisionVariableName = "";
				String decisionVariableType = "";
				String decisionVariableMinValue = "";
				String decisionVariableMaxValue = "";
				String decisionVariableRestriction = "";

				if (dtmDecisionVariables.getValueAt(i, 0).toString() != null)
					decisionVariableName = dtmDecisionVariables.getValueAt(i, 0).toString();
				if (dtmDecisionVariables.getValueAt(i, 1).toString() != null)
					decisionVariableType = dtmDecisionVariables.getValueAt(i, 1).toString();
				if (dtmDecisionVariables.getValueAt(i, 2).toString() != null) {
					decisionVariableMinValue = dtmDecisionVariables.getValueAt(i, 2).toString();
				}
				if (dtmDecisionVariables.getValueAt(i, 3).toString() != null) {
					decisionVariableMaxValue = dtmDecisionVariables.getValueAt(i, 3).toString();
				}
				if (dtmDecisionVariables.getValueAt(i, 4).toString() != null) {
					decisionVariableRestriction = dtmDecisionVariables.getValueAt(i, 4).toString();
				}
				variablesList.add(new Variable(decisionVariableName, decisionVariableType, decisionVariableMinValue,
						decisionVariableMaxValue, decisionVariableRestriction));
			}
		}
		return variablesList;
	}

	/**
	 * Saves the problem with the data given by the fields that were filled.
	 **/
	public void saveProblem() {
		String groupName = "";
		List<Variable> variablesList = createVariableList();
		
		if (txtNameOfDecisionVariablesGroup != null) {
			groupName = txtNameOfDecisionVariablesGroup.getText();
		}

		problem.setName(txtProblemName.getText());
		problem.setDescription(txaProblemDescription.getText());
		problem.setEmail(txtEmail.getText());
		problem.setMax(new Time(Integer.parseInt(spnMaxNumberOfDays.getValue().toString()),
				Integer.parseInt(spnMaxNumberOfHours.getValue().toString()),
				Integer.parseInt(spnMaxNumberOfMinutes.getValue().toString())));
		problem.setIdeal(new Time(Integer.parseInt(spnIdealNumberOfDays.getValue().toString()),
				Integer.parseInt(spnIdealNumberOfHours.getValue().toString()),
				Integer.parseInt(spnIdealNumberOfMinutes.getValue().toString())));
		problem.setGroupName(groupName);
		problem.setNumberVariables(Integer.parseInt(spnNumberOfDecisionVariables.getValue().toString()));
		problem.setVariables(variablesList);
	}

	public JButton getCreateProblem() {
		return btnCreateProblem;
	}

	public JButton getReadProblem() {
		return btnReadProblem;
	}

	public JButton getGoBack() {
		return btnGoBack;
	}

	public JButton getHelpButton() {
		return btnHelp;
	}

	public JButton getEmailButton() {
		return btnWriteEmail;
	}

	public JButton getMessageSendButton() {
		return btnMessageSend;
	}

	public void setEmail(String text) {
		txtEmail.setText(text);
	}

	public void setEmailTitle(String emailTitle) {
		txtMessageTitle.setText(emailTitle);
	}

	public void setEmailMessage(String emailMessage) {
		txaMessageBody.setText(emailMessage);
	}

	public void setSpnNumberOfDecisionVariables(int nrVarDecisions) {
		spnNumberOfDecisionVariables.setValue(nrVarDecisions);
	}

	public JButton getDecisionVarButton() {
		return btnDecisionVariables;
	}

	public JButton getDecisionVariablesFinishButton() {
		return btnDecisionVariablesFinish;
	}

	public JButton getCriteriaFinishButton() {
		return btnCriteriaFinish;
	}

	public JButton getCriteriaButton() {
		return btnCriteria;
	}

	public JButton getSaveButton() {
		return btnSaveToXML;
	}

	public JButton getReadButton() {
		return btnReadXML;
	}

	public JButton getAddCriteriaButton() {
		return btnAddCriteria;
	}

	public JButton getReadJarButton() {
		return btnReadJar;
	}

	public JButton getExecuteProcessButton() {
		return btnExecuteProcess;
	}

	public JSpinner getSpnNumberOfDecisionVariables() {
		return spnNumberOfDecisionVariables;
	}

}