package support;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.sun.mail.smtp.SMTPTransport;

import interfaceSections.NameDescriptionSection;

import java.util.Date;
import javax.mail.internet.AddressException;

/**
 * 
 * @author Ricardo
 *
 */
public class EmailHandler {
	
	private ConfigXML config = ConfigXML.getInstance();
	
	public EmailHandler() {
		
	}

	/**
     * Send email using GMail SMTP server.
     *
     * @param fromEmail the email to put in the tag FROM
     * @param ToEmail the recipient email
     * @param title title of the message
     * @param  message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */
    public void sendEmail(String fromEmail, String ToEmail ,String ccEmail  ,String title, String message) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

      
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(fromEmail)); 
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ToEmail, false));

        if (!ccEmail.equals("none")) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
        try {
        	t.connect("smtp.gmail.com",config.getEmailAdmin().split("@")[0],config.getEmailPassword());
        }catch(Exception e) {
        	JOptionPane.showMessageDialog(null,
					"You don't seem to be connected to the internet", "Warning",
					JOptionPane.WARNING_MESSAGE);
        }
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
    }
    
    public void sendEmailWithAttachment(String fromEmail, String toEmail ,String ccEmail  ,String title, String messageBody, NameDescriptionSection nameDescription) {
        final String username = config.getEmailAdmin();
        final String password = config.getEmailPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject(title);
            message.setText(messageBody);
            
            if (!ccEmail.equals("none")) {
            	message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
            }
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = "./tmp_240424042404.xml";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			Date date = new Date();
			String fileName = nameDescription.getProblemName().getText() + " " + dateFormat.format(date);
			
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            System.out.println("Sending");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
        	e.printStackTrace();
        	System.out.println("Problem sending email");
        }
     }
    }




