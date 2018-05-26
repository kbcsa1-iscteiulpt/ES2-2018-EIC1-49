package support;

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

import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;

import problem.UserProblem;
import problem.Variable;

public class AlgorithmsUtils {
	
	private Config config = Config.getInstance();
	

	
	/**
	 * This method will write a configuration of algorithms in a file so the users can just read the algorithms to use in a experiment    
	 * 
	 * @param algorithmIDs - algorithms to include in the conf file 
	 * @param path - name of the file to write the automatic conf
	 * 
	 * */
	public void writeAutomaticConfig(List<String> algorithmIDs, String path ) {
//		String userHomeFolder = System.getProperty("user.home");
		File textFile = new File(path );
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
			
			for(int i=0; i<algorithmIDs.size();i++) {
				out.write(algorithmIDs.get(i)+";");
			}	

			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			scanner.close();
		}
		return algorithmIDs;
	}
	
	
	
//	TODO
	public void applyRestrictions(List<Variable> variables ,String path) {
		List<String[]> restrictions = new ArrayList<String[]>();
		List<String[]> fileOutputVector = new ArrayList<String[]>();
		String[] fileVector = new String[0];
		String fileOutput ="";
		
		
		
		for (int i = 0; i < variables.size(); i++) {
			System.out.println(variables.get(i).getRestriction());
			restrictions.add(variables.get(i).getRestriction().split(";"));
		}
		
		Scanner in;
		try {
			in = new Scanner(new FileReader(path));
			
			while(in.hasNext()) {
				fileVector = in.nextLine().split(" ");
				
				for (int i = 0; i < fileVector.length; i++) {
					System.out.print(fileVector[i]+",");
					for (int j = 0; j < restrictions.get(i).length; j++) {
						if(fileVector[i].trim().equals(restrictions.get(i)[j].trim()) ){
							fileVector[i]="x";
						}
					}
				}
				System.out.println();
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
	
	public void otimizationEmails(UserProblem problem,int counter) {
		
		EmailHandler emailHandler = new EmailHandler();
		String adminEmail = config.getEmailAdmin();
		String subject = "Update on " + problem.getName() + " problem";
		String content = "Your optimization process is currently at ";
		int maxIterations = Config.getInstance().getMaxEvaluations() ; 
		
		try {
			if(counter == (int)(maxIterations * 0.25) ) { 
					System.out.println("teste ricardo " + adminEmail);
					emailHandler.sendEmail(adminEmail, problem.getEmail(),adminEmail,subject,content + "25%");
			}else if(counter == (int)(maxIterations  * 0.5) ) {
				emailHandler.sendEmail(adminEmail, problem.getEmail(),adminEmail,subject,content + "50%");
			}else if(counter == (int)(maxIterations  * 0.75) ) {
				emailHandler.sendEmail(adminEmail, problem.getEmail(),adminEmail,subject,content + "75%");
			}else if(counter == (int)(maxIterations ) ) { 
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
