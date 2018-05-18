package support;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.security.Security;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import javax.mail.internet.AddressException;

public class Support {
	
	public Support() {
		
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
    public void SendEmail(   String fromEmail, String ToEmail  ,String title, boolean success) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        String message="";
        if(success) {
	        message = "Muito obrigado por usar esta plataforma de otimiza��o. Ser� informado por email\r\n"
					+ "sobre o progresso do processo de otimiza��o, quando o processo de otimiza��o tiver atingido 25%,\r\n"
					+ "50%, 75% do total do (n�mero de avalia��es ou) tempo estimado, e tamb�m quando o processo tiver\r\n"
					+ "terminado, com sucesso ou devido � ocorr�ncia de erros.";
        }else {
        		message=	"There was a problem running the problem you requested, please try again.\r\n"
					+ "If the problem continues, contact us so we can help";
        }
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(fromEmail));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ToEmail, false));

//        if (ccEmail.length() > 0) {
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
//        }

        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", "projetodees7", "P@ssw0rd123");
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
    }

}


