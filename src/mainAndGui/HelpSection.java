package mainAndGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

import resources.ResourceLoader;
import support.EmailHandler;

/**
 * This class represents the help section.
 * 
 * @author Diana nr 72898
 **/
public class HelpSection {
	private JButton btnGoBack;
	private JButton btnHelp;
	private JButton btnWriteEmailFAQ = new JButton("Write Email");
	private ResourceLoader resourceLoader = new ResourceLoader();

	/**
	 * Returns a panel with a question mark placed at the top-right and a return
	 * button at the top-left of the frame. When the question mark is clicked, a new
	 * frame is displayed to show the FAQ. When the return symbol is clicked, it
	 * returns to the initial decision panel.
	 * @param frame
	 * @param decisionFrame
	 * @param email
	 * @param emailHandler
	 * @param adminEmail
	 * @return pnlHelp
	 **/

	public JPanel getHelpPanel(JFrame frame, JFrame decisionFrame, EmailSection email, EmailHandler emailHandler,
			String adminEmail) {
		JPanel pnlHelp = new JPanel(new BorderLayout());
		goBackButton(frame, decisionFrame);
		helpButton(frame, email, emailHandler, adminEmail);
		pnlHelp.add(btnGoBack, BorderLayout.LINE_START);
		pnlHelp.add(btnHelp, BorderLayout.LINE_END);
		return pnlHelp;
	}

	/**
	 * Creates a question mark icon placed at the top-right that is a button. When
	 * clicked, shows the help frame with FAQs.
	 * @param frame
	 * @param email
	 * @param emailHandler
	 * @param adminEmail
	 **/
	private void helpButton(JFrame frame, EmailSection email, EmailHandler emailHandler, String adminEmail) {
		btnHelp = new JButton();
		Image question_mark = Toolkit.getDefaultToolkit()
				.getImage(resourceLoader.getClass().getResource("images/question_mark.png"));
		ImageIcon icoQuestionMark = new ImageIcon(question_mark.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));

		btnHelp.setIcon(icoQuestionMark);
		btnHelp.setContentAreaFilled(false);
		btnHelp.setBorderPainted(false);
		btnHelp.setFocusPainted(false);
		btnHelp.setToolTipText("Help and FAQs");
		setBtnHelpAction(frame, email, emailHandler, adminEmail);
	}

	/**
	 * Opens a new frame when the button is clicked 
	 * @param frame
	 * @param email
	 * @param emailHandler
	 * @param adminEmail
	 **/
	private void setBtnHelpAction(JFrame frame, EmailSection email, EmailHandler emailHandler, String adminEmail) {
		btnHelp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame helpFrame = new JFrame("Help Section");
				FrameSize.setFrame(helpFrame, 0.5);
				setHelpFrame(frame, helpFrame, email, emailHandler, adminEmail);
				helpFrame.setVisible(true);
			}
		});
	}

	/**
	 * Creates a return icon placed at the top-left that is a button. When clicked,
	 * returns to the previous frame.
	 * @param frame
	 * @param decisionFrame
	 **/
	private void goBackButton(JFrame frame, JFrame decisionFrame) {
		btnGoBack = new JButton();
		Image goBack = Toolkit.getDefaultToolkit().getImage(resourceLoader.getClass().getResource("images/goBack.png"));
		System.out.println(resourceLoader.getClass().getResource("images/goBack.png"));
		ImageIcon icoGoBack = new ImageIcon(goBack.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
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
	 * @param frame
	 * @param helpFrame
	 * @param email
	 * @param emailHandler
	 * @param adminEmail
	 **/
	private void setHelpFrame(JFrame frame, JFrame helpFrame, EmailSection email, EmailHandler emailHandler,
			String adminEmail) {
		JPanel pnlHelp = new JPanel(new BorderLayout());
		JPanel pnlFAQ = new JPanel(new GridLayout(6, 0));
		JPanel pnlSendEmail = new JPanel();
		btnWriteEmailFAQ.setContentAreaFilled(false);
		btnWriteEmailFAQ.setFocusable(false);
		btnWriteEmailFAQ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				email.setEmailFrame(helpFrame, emailHandler, adminEmail);
			}
		});
		;

		URL url = resourceLoader.getClass().getResource("files/faq.txt");
		Map<String, String> listFAQ = readFAQfile(url);

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
	 * @param url
	 * @return listFAQ
	 **/
	private Map<String, String> readFAQfile(URL url) {
		Scanner scanner = null;
		Map<String, String> listFAQ = new HashMap<String, String>();
		File file = new File("faqTeste.txt");
		try {
			try {
				FileUtils.copyURLToFile(url, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			scanner = new Scanner(file);

			String line = "";
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				String[] tokens = line.split(";");
				listFAQ.put(tokens[0], tokens[1]);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
			file.delete();
		}

		return listFAQ;

	}

	/**
	 * Returns a panel with a question and an answer.
	 * @param question
	 * @param answer
	 * @return pnlFAQ
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

	public JButton getBtnGoBack() {
		return btnGoBack;
	}

	public JButton getBtnHelp() {
		return btnHelp;
	}

	public JButton getBtnWriteEmailFAQ() {
		return btnWriteEmailFAQ;
	}

}