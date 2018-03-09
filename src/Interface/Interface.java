package Interface;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class Interface {

	private JFrame frame;

	public Interface() {
		frame = new JFrame();
		setFrame(frame,1);
		setContent();
		frame.setVisible(true);
	}

	private void setContent() {
		JPanel userPanel = new JPanel(new GridLayout(0, 2));
		JPanel leftPanel = leftPanelContent();
		JPanel rightPanel = rightPanelContent();

		userPanel.add(leftPanel);
		userPanel.add(rightPanel);
		frame.add(userPanel);
	}

	private JPanel leftPanelContent() {
		JPanel leftPanel = new JPanel(new GridLayout(6, 0));

		// Problem's Name
		JPanel problemNamePanel = new JPanel(new FlowLayout());
		JLabel problemNameL = new JLabel("Problem's Name:");
		JTextField problemNameJTF = new JTextField();
		problemNameJTF.setColumns(20);
		problemNamePanel.add(problemNameL);
		problemNamePanel.add(problemNameJTF);

		// Problem's description
		JPanel problemDescriptionPanel = new JPanel(new FlowLayout());
		JLabel problemDescriptionL = new JLabel("Problem's Description:");
		JTextArea problemDescriptionJTA = new JTextArea(10, 50);
		JScrollPane problemDescriptionSP = new JScrollPane(problemDescriptionJTA);
		problemDescriptionJTA.setLineWrap(true);
		problemDescriptionPanel.add(problemDescriptionL);
		problemDescriptionPanel.add(problemDescriptionSP);

		// Email
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

		// Max time for wait
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

		leftPanel.add(problemNamePanel);
		leftPanel.add(problemDescriptionPanel);
		leftPanel.add(emailPanel);
		leftPanel.add(maxTimePanel);
		leftPanel.add(idealTimePanel);
		return leftPanel;
	}

	private JFrame mailFrame() {
		JFrame sendEmailFrame = new JFrame("Email");
		setFrame(sendEmailFrame,0.5);
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

	private JPanel rightPanelContent() {
		JPanel rightPanel = new JPanel(new GridLayout(5, 0));

		// Number of Decision Variables
		JPanel numberOfDecisionVarPanel = new JPanel(new FlowLayout());
		JLabel numberOfDecisionVarL = new JLabel("Number of Decision Variables");
			// Qual o nr maximo de var de decisao?
		JSpinner numberOfDecisionVarSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
		
		numberOfDecisionVarPanel.add(numberOfDecisionVarL);
		numberOfDecisionVarPanel.add(numberOfDecisionVarSpinner);
		
		//Table: Decision Variables
		JPanel decisionVarPanel = new JPanel(new FlowLayout());
		JTable decisionVarTable = new JTable();
		decisionVarTable.setModel(new DefaultTableModel((int) numberOfDecisionVarSpinner.getValue(),3));
		decisionVarPanel.add(new JScrollPane(decisionVarTable), BorderLayout.CENTER);
		
		// Read and Save XML File

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

		readSave.add(readB);

		// Execute Process
		JButton executeProcessB = new JButton("Execute Otimization Process");

		rightPanel.add(numberOfDecisionVarPanel);
		rightPanel.add(decisionVarPanel);
		rightPanel.add(readSave);
		rightPanel.add(executeProcessB);
		return rightPanel;
	}
	
	private void setFrame(JFrame frame, double size){
		double frameWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double frameHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		frame.setSize((int)(frameWidth * size), (int)(frameHeight * size));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
