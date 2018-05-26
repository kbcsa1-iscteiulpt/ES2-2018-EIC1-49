package mainAndGui;


import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import resources.ResourceLoader;
import javax.swing.JFrame;
import support.EmailHandler;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.event.WindowEvent;
import javax.mail.MessagingException;

public class EmailSectionFrame {
	private JTextField txtMessageTitle;
	private JTextArea txaMessageBody;
	private JButton btnMessageSend;
	private ResourceLoader resourceLoader = new ResourceLoader();

	public JTextField getTxtMessageTitle() {
		return txtMessageTitle;
	}

	public JTextArea getTxaMessageBody() {
		return txaMessageBody;
	}

	public JButton getBtnMessageSend() {
		return btnMessageSend;
	}

	/**
	* Returns a frame with two JTextField to fill and a button to send the email. One with the subject of the email and the other with the email body.
	*/
	public JFrame setEmailFrame(JFrame frame, EmailHandler support, String adminEmail, JTextField thisTxtEmail) {
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
		Image imgSendMessage = Toolkit.getDefaultToolkit()
				.getImage(resourceLoader.getClass().getResource("images/send_message.png"));
		ImageIcon icoSendMessage = new ImageIcon(imgSendMessage.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnMessageSend.setContentAreaFilled(false);
		btnMessageSend.setIcon(icoSendMessage);
		sendEmail(frame, support, adminEmail, sendEmailFrame, pnlSendEmail, thisTxtEmail);
		pnlMessageSend.add(btnMessageSend, BorderLayout.SOUTH);
		pnlSendEmail.add(pnlMessageTitle, BorderLayout.NORTH);
		pnlSendEmail.add(pnlMessageBody, BorderLayout.CENTER);
		pnlSendEmail.add(pnlMessageSend, BorderLayout.SOUTH);
		sendEmailFrame.add(pnlSendEmail);
		sendEmailFrame.setVisible(true);
		return sendEmailFrame;
	}

	/**
	* Closes the email frame and sends the intended email, if the operation fails shows a warning message.
	*/
	public void sendEmail(JFrame frame, EmailHandler support, String adminEmail, JFrame sendEmailFrame,
			JPanel pnlSendEmail, final JTextField thisTxtEmail) {
		btnMessageSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtMessageTitle.getText().isEmpty() || txaMessageBody.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill the subject and the message body", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						sendEmailFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						String subject = "From: " + thisTxtEmail.getText() + "  " + txtMessageTitle.getText();
						support.sendEmail(thisTxtEmail.getText(), adminEmail, "none", subject,
								txaMessageBody.getText());
					} catch (MessagingException e1) {
						JOptionPane.showMessageDialog(pnlSendEmail, "Error sending email, connection issue!", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
	}
}