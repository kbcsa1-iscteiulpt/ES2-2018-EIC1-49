package mainAndGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import resources.ResourceLoader;
import support.EmailHandler;
/**
 * This class represents the email section.
 * @author Diana nr 72898
 **/
public class EmailSection {
	private EmailSectionFrame emailSectionFrame = new EmailSectionFrame();
	private JTextField txtEmail;
	private JButton btnWriteEmail;
	private JButton btnWriteEmailFAQ;
	/**
	 * Returns a frame with two JTextField to fill and a button to send the email.
	 * One with the subject of the email and the other with the email body.
	 **/
	public JFrame setEmailFrame(JFrame frame, EmailHandler support, String adminEmail) {
		return emailSectionFrame.setEmailFrame(frame, support, adminEmail, this.txtEmail);
	}

	/**
	 * Returns a panel with a JTextField to fill with user's email. When the button
	 * is clicked, a new frame is displayed to write and send the email.
	 * @param frame
	 * @param emailHandler
	 * @param adminEmail
	 * @param help
	 * @return pnlEmail
	 **/
	public JPanel emailPanel(JFrame frame, EmailHandler emailHandler, String adminEmail, HelpSection help) {
		btnWriteEmailFAQ = help.getBtnWriteEmailFAQ();
		JPanel pnlEmail = new JPanel(new FlowLayout());
		JLabel lblEmailMandatory = new JLabel("*");
		lblEmailMandatory.setForeground(Color.red);
		JLabel lblEmail = new JLabel("Enter your Email:");
		txtEmail = new JTextField();
		txtEmail.setColumns(20);
		btnWriteEmail = new JButton("Write Email");
		btnWriteEmail.setToolTipText("Enter your email, then you can write one.");
		btnWriteEmail.setContentAreaFilled(false);
		btnWriteEmail.setEnabled(false);
		btnWriteEmailFAQ.setEnabled(false);

		checkEmail();

		btnWriteEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				emailSectionFrame.setEmailFrame(frame, emailHandler, adminEmail, txtEmail);
			}
		});
		pnlEmail.add(lblEmailMandatory);
		pnlEmail.add(lblEmail);
		pnlEmail.add(txtEmail);
		pnlEmail.add(btnWriteEmail); 
		return pnlEmail;
	}

	/**
	 * Checks if the email given by the user is valid 
	 **/
	private void checkEmail() {
		txtEmail.getDocument().addDocumentListener(new DocumentListener() {

			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
					+ "A-Z]{2,7}$";
			Pattern patEmail = Pattern.compile(emailRegex);

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!patEmail.matcher(txtEmail.getText()).matches()) {
					btnWriteEmailFAQ.setEnabled(false);
					btnWriteEmail.setEnabled(false);
					btnWriteEmail.setToolTipText("Enter your email, then you can write one.");

				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				
				if (patEmail.matcher(txtEmail.getText()).matches()) {
					btnWriteEmail.setEnabled(true);
					btnWriteEmailFAQ.setEnabled(true);
					btnWriteEmail.setToolTipText("Write an email");

				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
	}

	public JTextField getEmail() {
		return txtEmail;
	}

	public JButton getBtnWriteEmail() {
		return btnWriteEmail;
	}

	public JTextField getTxtMessageTitle() {
		return emailSectionFrame.getTxtMessageTitle();
	}

	public JTextArea getTxaMessageBody() {
		return emailSectionFrame.getTxaMessageBody();
	}

	public JButton getBtnMessageSend() {
		return emailSectionFrame.getBtnMessageSend();
	}
	
	
}
