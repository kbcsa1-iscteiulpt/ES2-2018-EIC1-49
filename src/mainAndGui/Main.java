package mainAndGui;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import problem.Problem;
import support.Config;
import support.XML_Editor;

public class Main {

	public static void main(String[] args) {
		
	
		
/*		Support support = new Support();
		try {
			support.SendEmail("projetodees7", "P@ssw0rd123", "ricardojoaosantos0@gmail.com", "", "test 4", "e agora");
			sup.sendEmail("ricardo.santos@letracinetica.pt", "ricardojoaosantos0@gmail.com", "test 4", "trabalha oh puta");
			System.out.println("donezo");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
		
		XML_Editor xml = new XML_Editor();
		Config config = xml.readConfig();
		
		new Interface(config.getEmailAdmin());
		
		/*
		XML_Editor xml = new XML_Editor();
		Problem p = xml.read("./src/jUnitTests/test.xml");
		PDF_Editor pdf = new PDF_Editor();
		pdf.write(p);
		*/
	}

}
