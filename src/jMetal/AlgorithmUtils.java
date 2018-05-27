package jMetal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import problem.UserProblem;
import problem.Variable;
import support.ConfigXML;
import support.EmailHandler;

/**
 * 
 * @author Ricardo
 *
 */
public class AlgorithmUtils {
	
	private ConfigXML config = ConfigXML.getInstance();

	
	/**
	 * This method will write a configuration of algorithms in a file so the users can just read the algorithms to use in a experiment    
	 * 
	 * @param algorithmIDs - algorithms to include in the config file 
	 * @param path - name of the file to write the automatic configuration
	 * 
	 * */
	public void writeAutomaticConfig(List<String> algorithmIDs, String path ) {
		File textFile = new File(path );
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
			
			for(int i=0; i<algorithmIDs.size();i++) {
				out.write(algorithmIDs.get(i)+";");
			}	

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will read a configuration of algorithms in a file     
	 * 
	 * @param file - file from wic to read the algorithms
	 * 
	 * */
	public List<String> readAutomaticConf(File file) {
		Scanner scanner = null;
		List<String> algorithmIDs = new ArrayList<String>();
		try {
			scanner = new Scanner(file);
			String line="";
			while(scanner.hasNext()) {
				line = scanner.nextLine();
				String[] Tokens = line.split("/");
				
				for(int i=0;i<Tokens.length;i++) {
					algorithmIDs.add(Tokens[i]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			scanner.close();
		}
		return algorithmIDs;
	}
	
	
	
	/**
	 * This method will apply restrictions to the rs files, switching the variables on witch the restrictions apply 
	 * by an x     
	 * 
	 * @param variable - Variable from witch to extract the restrictions
	 * @param path - path to the file on witch to apply the restrictions
	 * 
	 * */
	public void applyRestrictions(List<Variable> variables ,String path) {
		List<String[]> restrictions = new ArrayList<String[]>();
		List<String[]> fileOutputVector = new ArrayList<String[]>();
		String[] fileVector = new String[0];
		
		
		
		for (int i = 0; i < variables.size(); i++) {
			restrictions.add(variables.get(i).getRestriction().split(";"));
		}
		
		Scanner in;
		try {
			in = new Scanner(new FileReader(path));
			
			while(in.hasNext()) {
				fileVector = in.nextLine().split(" ");
				
				for (int i = 0; i < fileVector.length; i++) {
					for (int j = 0; j < restrictions.get(i).length; j++) {
						if(fileVector[i].trim().equals(restrictions.get(i)[j].trim()) ){
							fileVector[i]="x";
						}
					}
				}
				fileOutputVector.add(fileVector);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Problem rs file not found");
		}
		try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		for (int i = 0; i < fileOutputVector.size(); i++) {
			for (int j = 0; j < fileOutputVector.get(i).length; j++) {
				writer.write(fileOutputVector.get(i)[j]+ " ");
			}
			writer.write("\n");
		}
			
		writer.close();
		} catch (IOException e) {
			System.out.println("Problem rs file not found");
		}
	}
	
	public void otimizationEmails(UserProblem problem,int counter, int numOfAlgorithms) {
		
		EmailHandler emailHandler = new EmailHandler();
		String adminEmail = config.getEmailAdmin();
		String subject = "Update on " + problem.getName() + " problem";
		String content = "Your optimization process is currently at ";
		int maxIterations = ConfigXML.getInstance().getMaxEvaluations() ; 
		
		try {
			if(counter == (int)(maxIterations * numOfAlgorithms * 0.25) ) { 
					emailHandler.sendEmail(adminEmail, problem.getEmail(),adminEmail,subject,content + "25%");
			}else if(counter == (int)(maxIterations * numOfAlgorithms  * 0.5) ) {
				emailHandler.sendEmail(adminEmail, problem.getEmail(),adminEmail,subject,content + "50%");
			}else if(counter == (int)(maxIterations * numOfAlgorithms  * 0.75) ) {
				emailHandler.sendEmail(adminEmail, problem.getEmail(),adminEmail,subject,content + "75%");
			}else if(counter == (int)(maxIterations * numOfAlgorithms ) ) { 
				emailHandler.sendEmail(adminEmail, problem.getEmail(),adminEmail,subject,"The otimization of your problem is concluded");
			}
		} catch (AddressException e) {
			 JOptionPane.showMessageDialog(new JFrame(), "Invalid email address", "Dialog",
				        JOptionPane.WARNING_MESSAGE);
		} catch (MessagingException e) {
			 JOptionPane.showMessageDialog(new JFrame(), "There seems to be some connection issues", "Dialog",
				        JOptionPane.WARNING_MESSAGE);
		}
		
	}
	

}
