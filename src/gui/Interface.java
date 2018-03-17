package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class Interface {

	private JFrame frame;

	public Interface() {
		frame = new JFrame();
		setFrame(frame, 0.75);
		setContent();
		frame.setVisible(true);
	}

	private void setContent() {
		JPanel inicialPanel = new JPanel(new GridLayout(0, 2));
		JPanel userPanel = userPanelContent();

		inicialPanel.add(userPanel);
		frame.add(userPanel);
	}

	private JPanel userPanelContent() {
		JPanel leftPanel = new JPanel(new GridLayout(10, 0));
		leftPanel.add(helpFAQPanel());
		leftPanel.add(problemNamePanel());
		leftPanel.add(problemDescriptionPanel());
		leftPanel.add(emailPanel());
		leftPanel.add(maxTimePanel());
		leftPanel.add(idealTimePanel());
		leftPanel.add(numberOfDecisionVarPanel());
		leftPanel.add(readSavePanel());
		leftPanel.add(executeProcessPanel());
		return leftPanel;
	}

	private JPanel problemNamePanel() {
		JPanel problemNamePanel = new JPanel();
		JLabel problemNameL = new JLabel("Problem's Name:");
		JTextField problemNameJTF = new JTextField();
		problemNameJTF.setColumns(20);
		problemNamePanel.add(problemNameL);
		problemNamePanel.add(problemNameJTF);
		problemNamePanel.setBackground(new Color(240,240,240));
		return problemNamePanel;
	}

	private JPanel problemDescriptionPanel() {
		JPanel problemDescriptionPanel = new JPanel(new FlowLayout());
		JLabel problemDescriptionL = new JLabel("Problem's Description:");
		JTextArea problemDescriptionJTA = new JTextArea(4, 60);
		JScrollPane problemDescriptionSP = new JScrollPane(problemDescriptionJTA);
		problemDescriptionJTA.setLineWrap(true);
		problemDescriptionPanel.add(problemDescriptionL);
		problemDescriptionPanel.add(problemDescriptionSP);
		problemDescriptionPanel.setBackground(new Color(240,240,240));
		return problemDescriptionPanel;
	}

	private JPanel emailPanel() {
		JPanel emailPanel = new JPanel(new FlowLayout());
		JLabel emailL = new JLabel("Enter your Email:");
		JTextField emailJTF = new JTextField();
		emailJTF.setColumns(20);
		JButton emailB = new JButton("Write Email");
		emailB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mailFrame();
			}
		});
		emailPanel.add(emailL);
		emailPanel.add(emailJTF);
		emailPanel.add(emailB);
		emailPanel.setBackground(new Color(240,240,240));
		return emailPanel;
	}

	private JPanel helpFAQPanel() {
		JPanel helpFAQPanel = new JPanel(new BorderLayout());
		JButton helpButton = new JButton();
		ImageIcon question_mark = new ImageIcon(((new ImageIcon("./src/images/question_mark.png")).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		helpButton.setIcon(question_mark);
		helpButton.setContentAreaFilled(false);
		helpButton.setBorderPainted(false);
		helpButton.setFocusPainted(false);
		helpFAQPanel.add(helpButton, BorderLayout.LINE_END);
		helpFAQPanel.setBackground(new Color(240,240,240));
		return helpFAQPanel;
	}

	private JPanel maxTimePanel() {
		JPanel maxTimePanel = new JPanel(new FlowLayout());
		JLabel maxTimeL = new JLabel("Maximum time for otimization:");

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
		maxTimePanel.setBackground(new Color(240,240,240));
		return maxTimePanel;
	}

	private JPanel idealTimePanel() {
		// Ideal time for wait
		JPanel idealTimePanel = new JPanel(new FlowLayout());
		JLabel idealTimeL = new JLabel("Ideal time for otimization:");

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
		idealTimePanel.setBackground(new Color(240,240,240));
		return idealTimePanel;
	}

	private JFrame mailFrame() {
		JFrame sendEmailFrame = new JFrame("Email");
		setFrame(sendEmailFrame, 0.5);
		JPanel sendEmailPanel = new JPanel(new BorderLayout());
		JPanel messageTitlePanel = new JPanel(new BorderLayout());
		JPanel messageBodyPanel = new JPanel(new BorderLayout());
		JPanel messageSendPanel = new JPanel();

		JLabel messageTitleL = new JLabel("Title:");
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

		JButton messageSendButton = new JButton("Send Message");

		messageSendPanel.add(messageSendButton);

		sendEmailPanel.add(messageTitlePanel, BorderLayout.NORTH);
		sendEmailPanel.add(messageBodyPanel, BorderLayout.CENTER);
		sendEmailPanel.add(messageSendPanel, BorderLayout.SOUTH);

		sendEmailFrame.add(sendEmailPanel);
		sendEmailFrame.setVisible(true);
		return sendEmailFrame;
	}

	private JPanel numberOfDecisionVarPanel() {
		JPanel numberOfDecisionVarPanel = new JPanel(new FlowLayout());
		JLabel numberOfDecisionVarL = new JLabel("Number of Decision Variables");
		JSpinner numberOfDecisionVarSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
		JButton decisionVarButton = new JButton("Decision Variables");
		decisionVarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame decisionVarFrame = new JFrame(); 
				setFrame(decisionVarFrame, 0.5);
				setDecisionFrame(decisionVarFrame);
				decisionVarFrame.setVisible(true);
			}

		});
		numberOfDecisionVarPanel.add(numberOfDecisionVarL);
		numberOfDecisionVarPanel.add(numberOfDecisionVarSpinner);
		numberOfDecisionVarPanel.add(decisionVarButton);
		numberOfDecisionVarPanel.setBackground(new Color(240,240,240));
		return numberOfDecisionVarPanel;
	}

	private JPanel readSavePanel() {

		JPanel readSave = new JPanel(new FlowLayout());
		JButton readB = new JButton("Read XML File");
		readB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.getName());
				}
			}
		});

		JButton saveB = new JButton("Save XML File");
		saveB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.getName());
				}
			}
		});
		readSave.add(readB);
		readSave.add(saveB);
		readSave.setBackground(new Color(240,240,240));
		return readSave;
	}

	private JPanel executeProcessPanel() {
		JPanel executeProcessPanel = new JPanel(new FlowLayout());
		JButton executeProcessB = new JButton("Execute Otimization Process");
		executeProcessPanel.add(executeProcessB);
		executeProcessPanel.setBackground(new Color(240,240,240));
		return executeProcessPanel;

	}

	private void setDecisionFrame(JFrame decisionVarFrame) {
		JPanel decisionPanel = new JPanel();
		JTable decisionTable = new JTable();
	}

	private void setFrame(JFrame frame, double size) {
		double frameWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double frameHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		frame.setSize((int) (frameWidth * size), (int) (frameHeight * size));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
