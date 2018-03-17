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
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.mail.MessagingException;
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

import classes.Support;

/**
 * This class represents the interface
 * @author diana
 **/

public class Interface {

	private JFrame frame;
	private JFrame helpFrame;
	private JButton helpButton;
	private JButton emailButton;
	private JFrame decisionVarFrame;
	private JButton decisionVarButton;
	private JFrame criterionFrame;
	private JButton criterionButton;
	private JButton saveButton;
	private JButton readButton;
	private JButton addCriterionButton;
	private JButton readJarButton;
	private JTextField emailJTF;

	private Support support = new Support();
	private JButton messageSendButton;

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
		JPanel helpFAQPanel = new JPanel(new BorderLayout());
		helpButton = new JButton();
		ImageIcon question_mark = new ImageIcon(((new ImageIcon("./src/images/question_mark.png")).getImage())
				.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		helpButton.setIcon(question_mark);
		helpButton.setContentAreaFilled(false);
		helpButton.setBorderPainted(false);
		helpButton.setFocusPainted(false);

		helpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				helpFrame = new JFrame("Help Section");
				setFrame(helpFrame, 0.5);
				setHelpFrame(helpFrame);
				helpFrame.setVisible(true);
			}
		});
		helpFAQPanel.add(helpButton, BorderLayout.LINE_END);
		helpFAQPanel.setBackground(new Color(240, 240, 240));
		return helpFAQPanel;
	}

	/**
	 * Returns a panel with a JTextField for user to fill with the problem's
	 * name.
	 **/
	private JPanel problemNamePanel() {
		JPanel problemNamePanel = new JPanel();
		JLabel problemNameL = new JLabel("Problem's Name:");
		JTextField problemNameJTF = new JTextField();
		problemNameJTF.setColumns(20);
		problemNamePanel.add(problemNameL);
		problemNamePanel.add(problemNameJTF);
		problemNamePanel.setBackground(new Color(240, 240, 240));
		return problemNamePanel;
	}

	/**
	 * Returns a panel with a JTextField for user to fill with the problem's
	 * description.
	 **/
	private JPanel problemDescriptionPanel() {
		JPanel problemDescriptionPanel = new JPanel(new FlowLayout());
		JLabel problemDescriptionL = new JLabel("Problem's Description:");
		JTextArea problemDescriptionJTA = new JTextArea(4, 60);
		JScrollPane problemDescriptionSP = new JScrollPane(problemDescriptionJTA);
		problemDescriptionJTA.setLineWrap(true);
		problemDescriptionPanel.add(problemDescriptionL);
		problemDescriptionPanel.add(problemDescriptionSP);
		problemDescriptionPanel.setBackground(new Color(240, 240, 240));
		return problemDescriptionPanel;
	}

	/**
	 * Returns a panel with a JTextField to fill with user's email. When the
	 * button is clicked, a new frame is displayed to write and send the email.
	 **/
	private JPanel emailPanel() {
		JPanel emailPanel = new JPanel(new FlowLayout());
		JLabel emailL = new JLabel("Enter your Email:");
		emailJTF = new JTextField();
		emailJTF.setColumns(20);
		emailButton = new JButton("Write Email");
		emailButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEmailFrame();
			}
		});
		emailPanel.add(emailL);
		emailPanel.add(emailJTF);
		emailPanel.add(emailButton);
		emailPanel.setBackground(new Color(240, 240, 240));
		return emailPanel;
	}

	/**
	 * Returns a panel with a JSpinner for user to select the maximum time to
	 * wait for the optimization.
	 **/
	private JPanel maxTimePanel() {
		JPanel maxTimePanel = new JPanel(new FlowLayout());
		JLabel maxTimeL = new JLabel("Maximum time for optimization:");

		JLabel numberofDaysLabel = new JLabel("Days");
		JLabel numberofHoursLabel = new JLabel("Hours");
		JLabel numberofMinutesLabel = new JLabel("Minutes");

		JSpinner numberOfDaysSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 31, 1));
		JSpinner numberOfHoursSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
		JSpinner numberOfMinutesSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		maxTimePanel.add(maxTimeL);
		maxTimePanel.add(numberOfDaysSpinner);
		maxTimePanel.add(numberofDaysLabel);
		maxTimePanel.add(numberOfHoursSpinner);
		maxTimePanel.add(numberofHoursLabel);
		maxTimePanel.add(numberOfMinutesSpinner);
		maxTimePanel.add(numberofMinutesLabel);
		maxTimePanel.setBackground(new Color(240, 240, 240));
		return maxTimePanel;
	}

	/**
	 * Returns a panel with a JSpinner for user to select the ideal time to wait
	 * for the optimization.
	 **/
	private JPanel idealTimePanel() {
		JPanel idealTimePanel = new JPanel(new FlowLayout());
		JLabel idealTimeL = new JLabel("Ideal time for optimization:");

		JLabel idealNumberofDaysLabel = new JLabel("Days");
		JLabel idelNumberofHoursLabel = new JLabel("Hours");
		JLabel idealNumberofMinutesLabel = new JLabel("Minutes");

		JSpinner idealNumberOfDaysSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 31, 1));
		JSpinner idealNumberOfHoursSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
		JSpinner idealNumberOfMinutesSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		idealTimePanel.add(idealTimeL);
		idealTimePanel.add(idealNumberOfDaysSpinner);
		idealTimePanel.add(idealNumberofDaysLabel);
		idealTimePanel.add(idealNumberOfHoursSpinner);
		idealTimePanel.add(idelNumberofHoursLabel);
		idealTimePanel.add(idealNumberOfMinutesSpinner);
		idealTimePanel.add(idealNumberofMinutesLabel);
		idealTimePanel.setBackground(new Color(240, 240, 240));
		return idealTimePanel;
	}

	/**
	 * Returns a panel with a JSpinner to select the number of decision
	 * variables and button. When clicked, a new frame is displayed to write the
	 * decision variable group name and to fill the table of the variable
	 * decision.
	 **/
	private JPanel decisionVarPanel() {
		JPanel decisionVarPanel = new JPanel(new FlowLayout());
		JLabel numberOfDecisionVarL = new JLabel("Number of Decision Variables");
		JSpinner numberOfDecisionVarSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		decisionVarButton = new JButton("Decision Variables");
		decisionVarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				decisionVarFrame = new JFrame("Decision Variables");
				setFrame(decisionVarFrame, 0.5);
				setDecisionFrame(decisionVarFrame, numberOfDecisionVarSpinner.getValue());
				decisionVarFrame.setVisible(true);
			}

		});
		decisionVarPanel.add(numberOfDecisionVarL);
		decisionVarPanel.add(numberOfDecisionVarSpinner);
		decisionVarPanel.add(decisionVarButton);
		decisionVarPanel.setBackground(new Color(240, 240, 240));
		return decisionVarPanel;
	}

	/**
	 * Returns a panel with a JTextField to fill by the user or to be filled
	 * automatically with the file path (to read XML) through the button.
	 **/
	private JPanel readPanel() {
		JPanel readPanel = new JPanel(new FlowLayout());
		JLabel readL = new JLabel("Read from XML (path):");
		JTextField readJTF = new JTextField();
		readJTF.setColumns(40);
		readButton = new JButton("Read XML File");
		readButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					readJTF.setText(selectedFile.getPath());
				}
			}
		});
		readPanel.add(readL);
		readPanel.add(readJTF);
		readPanel.add(readButton);
		readPanel.setBackground(new Color(240, 240, 240));
		return readPanel;
	}

	/**
	 * Returns a panel with a JTextField to fill by the user or to be filled
	 * automatically with the file path (to save into XML) through the button.
	 **/
	private JPanel savePanel() {
		JPanel savePanel = new JPanel();
		JLabel saveL = new JLabel("Save into XML (path):");
		JTextField saveJTF = new JTextField();
		saveJTF.setColumns(40);
		saveButton = new JButton("Save XML File");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					saveJTF.setText(selectedFile.getPath());
				}
			}
		});
		savePanel.add(saveL);
		savePanel.add(saveJTF);
		savePanel.add(saveButton);
		savePanel.setBackground(new Color(240, 240, 240));
		return savePanel;
	}

	/**
	 * Returns a panel with a button. When clicked, a new frame is displayed to
	 * specify the criterion.
	 **/
	private JPanel criterionPanel() {
		JPanel criterionPanel = new JPanel();
		criterionButton = new JButton("Criterion to be optimized");
		criterionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				criterionFrame = new JFrame("Criterions");
				setFrame(criterionFrame, 0.5);
				setCriterionFrame(criterionFrame);
				criterionFrame.setVisible(true);
			}
		});
		criterionPanel.add(criterionButton);
		return criterionPanel;
	}

	/**
	 * Returns a panel with the button to execute the optimization process.
	 **/
	private JPanel executeProcessPanel() {
		JPanel executeProcessPanel = new JPanel(new FlowLayout());
		JButton executeProcessB = new JButton("Execute Optimization Process");
		executeProcessPanel.add(executeProcessB);
		executeProcessPanel.setBackground(new Color(240, 240, 240));
		return executeProcessPanel;
	}

	/**
	 * Returns a frame with two JTextField to fill and a button to send the
	 * email. One with the subject of the email and the other with the email
	 * body.
	 **/
	private JFrame setEmailFrame() {
		JFrame sendEmailFrame = new JFrame("Email");
		setFrame(sendEmailFrame, 0.5);
		JPanel sendEmailPanel = new JPanel(new BorderLayout());
		JPanel messageTitlePanel = new JPanel(new BorderLayout());
		JPanel messageBodyPanel = new JPanel(new BorderLayout());
		JPanel messageSendPanel = new JPanel();

		JLabel messageTitleL = new JLabel("Subject:");
		JTextField messageTitleJTF = new JTextField();
		messageTitleJTF.setColumns(30);

		messageTitlePanel.add(messageTitleL, BorderLayout.NORTH);
		messageTitlePanel.add(messageTitleJTF, BorderLayout.CENTER);

		JLabel messageBodyL = new JLabel("Message:");
		JTextArea messageBodyJTA = new JTextArea();
		messageBodyJTA.setLineWrap(true);
		JScrollPane messageBodyScroll = new JScrollPane(messageBodyJTA);

		messageBodyPanel.add(messageBodyL, BorderLayout.NORTH);
		messageBodyPanel.add(messageBodyScroll, BorderLayout.CENTER);

		messageSendButton = new JButton("Send Message");
		messageSendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sendEmailFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					String subject = "From: " + emailJTF.getText() + messageTitleJTF.getText();
					support.SendEmail(emailJTF.getText(), messageTitleJTF.getText(), subject);
				} catch (MessagingException e1) {
					JOptionPane.showMessageDialog(sendEmailPanel, "Error sending email, connection issue!", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		messageSendPanel.add(messageSendButton);

		sendEmailPanel.add(messageTitlePanel, BorderLayout.NORTH);
		sendEmailPanel.add(messageBodyPanel, BorderLayout.CENTER);
		sendEmailPanel.add(messageSendPanel, BorderLayout.SOUTH);

		sendEmailFrame.add(sendEmailPanel);
		sendEmailFrame.setVisible(true);
		return sendEmailFrame;
	}

	/**
	 * Adds content to the frame given with a table to write the decision
	 * variable group name and to fill the table of the variable decision. Each
	 * decision variable has a name, type, and potential intervals and
	 * restrictions.
	 **/
	private void setDecisionFrame(JFrame decisionVarFrame, Object numberOfVariableDecisionGroup) {
		JPanel decisionPanel = new JPanel(new BorderLayout());

		JPanel nameOfDecisionVarGroupPanel = new JPanel();
		JLabel nameOfDecisionVarGroupL = new JLabel("Name of Decision Variable Group:");
		JTextField nameOfDecisionVarGroupJTF = new JTextField();
		nameOfDecisionVarGroupJTF.setColumns(50);

		DefaultTableModel model = new DefaultTableModel();
		JTable decisionVarT = new JTable();
		decisionVarT.setModel(model);

		model.addColumn("Name");
		model.addColumn("Type");
		model.addColumn("Interval");
		model.addColumn("Restrictions");
		String[] variableDataTypesString = { "boolean", "byte", "char", "double", "float", "integer", "long", "real",
				"short", "String" };
		JComboBox variableDataTypes = new JComboBox(variableDataTypesString);

		for (int i = 0; i < Integer.parseInt(numberOfVariableDecisionGroup.toString()); i++) {
			model.addRow(new Object[] { "", "", "" });
			decisionVarT.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(variableDataTypes));
		}

		nameOfDecisionVarGroupPanel.add(nameOfDecisionVarGroupL);
		nameOfDecisionVarGroupPanel.add(nameOfDecisionVarGroupJTF);
		decisionPanel.add(nameOfDecisionVarGroupPanel, BorderLayout.NORTH);
		decisionPanel.add(new JScrollPane(decisionVarT));
		JButton button = new JButton("Finish");
		decisionPanel.add(button, BorderLayout.PAGE_END);
		decisionVarFrame.add(decisionPanel);
	}

	/**
	 * Adds content to the frame with a JTextField to define the criterion(s), a
	 * button to add a new criterion and a button to upload the .jar file.
	 **/
	private void setCriterionFrame(JFrame criterionFrame) {
		JPanel criterionPanel = new JPanel();
		criterionPanel.setLayout(new BoxLayout(criterionPanel, BoxLayout.Y_AXIS));
		addCriterionButton = new JButton("Add Criterion");
		addCriterionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				criterionPanel.add(addCriterion());
				criterionPanel.revalidate();
			}
		});
		criterionPanel.add(addCriterionButton);
		criterionPanel.add(addCriterion());
		criterionFrame.add(new JScrollPane(criterionPanel));
	}

	/**
	 * Returns a JPanel with a JTextField to define the criterion(s) and a
	 * button to upload the .jar file.
	 **/
	private JPanel addCriterion() {
		JPanel criterionPanel = new JPanel(new FlowLayout());
		JPanel criterionNamePanel = new JPanel();

		JLabel criterionNameL = new JLabel("Criterion Name:");
		JTextField criterionNameJTF = new JTextField();
		criterionNameJTF.setColumns(30);
		JPanel criterionJarPanel = new JPanel();

		JLabel jarPathL = new JLabel("Jar Path");
		JTextField jarPathJTF = new JTextField();
		jarPathJTF.setColumns(25);
		readJarButton = new JButton("Add jar");
		readJarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					jarPathJTF.setText(selectedFile.getPath());
				}
			}
		});

		criterionNamePanel.add(criterionNameL);
		criterionNamePanel.add(criterionNameJTF);
		criterionJarPanel.add(jarPathL);
		criterionJarPanel.add(jarPathJTF);
		criterionJarPanel.add(readJarButton);
		criterionPanel.add(criterionNamePanel);
		criterionPanel.add(criterionJarPanel);
		return criterionPanel;
	}

	/**
	 * Adds content to the Help frame
	 **/
	private void setHelpFrame(JFrame frame) {

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
		return helpButton;
	}

	public JButton getEmailButton() {
		return emailButton;
	}
	
	public JButton getMessageSendButton() {
		return messageSendButton;
	}

	public JButton getDecisionVarButton() {
		return decisionVarButton;
	}

	public JButton getCriterionButton() {
		return criterionButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getReadButton() {
		return readButton;
	}

	public JButton getAddCriterionButton() {
		return addCriterionButton;
	}

	public JButton getReadJarButton() {
		return readJarButton;
	}

}