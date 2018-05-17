package mainAndGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import support.Support;

public class HelpSection {
	private JButton btnGoBack;
	private JButton btnHelp;
	private JButton btnWriteEmailFAQ;

	/**
	 * Returns a panel with a question mark placed at the top-right and a return
	 * button at the top-left of the frame. When the question mark is clicked, a new
	 * frame is displayed to show the FAQ. When the return symbol is clicked, it
	 * returns to the initial decision panel.
	 **/

	public JPanel getHelpPanel(JFrame frame, JFrame decisionFrame, EmailSection email, Support support,
			String adminEmail) {
		JPanel pnlHelp = new JPanel(new BorderLayout());
		goBackButton(frame, decisionFrame);
		helpButton(frame, email, support, adminEmail);
		pnlHelp.add(btnGoBack, BorderLayout.LINE_START);
		pnlHelp.add(btnHelp, BorderLayout.LINE_END);
		return pnlHelp;
	}

	private void helpButton(JFrame frame, EmailSection email, Support support, String adminEmail) {
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
				FrameSize.setFrame(helpFrame, 0.5);
				setHelpFrame(frame, helpFrame, email, support, adminEmail);
				helpFrame.setVisible(true);
			}
		});
	}

	private void goBackButton(JFrame frame, JFrame decisionFrame) {
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
	}

	/**
	 * Adds content to the Help frame
	 **/
	private void setHelpFrame(JFrame frame, JFrame helpFrame, EmailSection email, Support support, String adminEmail) {
		JPanel pnlHelp = new JPanel(new BorderLayout());
		JPanel pnlFAQ = new JPanel(new GridLayout(6, 0));
		JPanel pnlSendEmail = new JPanel();
		btnWriteEmailFAQ = new JButton("Write Email");
		btnWriteEmailFAQ.setContentAreaFilled(false);
		btnWriteEmailFAQ.setFocusable(false);
		btnWriteEmailFAQ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				email.setEmailFrame(helpFrame, support, adminEmail);
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
}