package mainAndGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

import support.EmailHandler;
/**
 * This class represents the email section.
 * @author Diana nr 72898
 **/
public class EmailSection {
	private JTextField txtEmail;
	private JTextField txtMessageTitle;
	private JTextArea txaMessageBody;
	private JButton btnMessageSend;
	private JButton btnWriteEmail;
	private JButton btnWriteEmailFAQ;

	/**
	 * Returns a frame with two JTextField to fill and a button to send the email.
	 * One with the subject of the email and the other with the email body.
	 **/
	public JFrame setEmailFrame(JFrame frame, EmailHandler support, String adminEmail) {
		JFrame sendEmailFrame = new JFrame("Email");
		FrameSize.setFrame(sendEmailFrame, 0.5);
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
		sendEmail(frame, support, adminEmail, sendEmailFrame, pnlSendEmail);

		pnlMessageSend.add(btnMessageSend, BorderLayout.SOUTH);

		pnlSendEmail.add(pnlMessageTitle, BorderLayout.NORTH);
		pnlSendEmail.add(pnlMessageBody, BorderLayout.CENTER);
		pnlSendEmail.add(pnlMessageSend, BorderLayout.SOUTH);

		sendEmailFrame.add(pnlSendEmail);
		sendEmailFrame.setVisible(true);
		return sendEmailFrame;
	}

	/**
	 * Closes the email frame and sends the intended email, if the operation fails
	 * shows a warning message.
	 **/
	private void sendEmail(JFrame frame, EmailHandler support, String adminEmail, JFrame sendEmailFrame,
			JPanel pnlSendEmail) {
		btnMessageSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtMessageTitle.getText().isEmpty() || txaMessageBody.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill the subject and the message body", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						sendEmailFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						String subject = "From: " + txtEmail.getText() +"  "+  txtMessageTitle.getText();
						support.SendEmail(txtEmail.getText(), adminEmail, subject, txaMessageBody.getText());
					} catch (MessagingException e1) {
						JOptionPane.showMessageDialog(pnlSendEmail, "Error sending email, connection issue!", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
	}

	/**
	 * Returns a panel with a JTextField to fill with user's email. When the button
	 * is clicked, a new frame is displayed to write and send the email.
	 **/
	public JPanel emailPanel(JFrame frame, EmailHandler support, String adminEmail) {
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

		checkEmail();

		btnWriteEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEmailFrame(frame, support, adminEmail);
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
	}

	public JTextField getEmail() {
		return txtEmail;
	}

	public JButton getBtnWriteEmail() {
		return btnWriteEmail;
	}
}
