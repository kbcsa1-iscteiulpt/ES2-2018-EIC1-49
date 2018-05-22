package mainAndGui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.jfree.ui.RefineryUtilities;

import problem.UserProblem;
import support.BinaryExperiment;
import support.Config;
import support.DoubleExperiment;
import support.IntegerExperiment;
import support.EmailHandler;

import problem.Type;
import problem.UserProblem;
import support.Config;
import support.EmailHandler;

/**
 * This class represents the execution process.
 * 
 * @author Diana nr 72898
 **/
public class ExecutionProcess {

	private Config config = Config.getInstance();

	/**
	 * This method shows the graphic a set of the best solutions generated by the
	 * algorithms that were selected by the user.
	 * @param algorithmsSelectedList 
	 **/
	public void showGraphic(UserProblem problem, List<String> algorithmsSelectedList) {
		Graphic chart = new Graphic(problem, algorithmsSelectedList);
	}

	/**
	 * This method compiles the script .R and shows the .eps file
	 **/
	public void compileAndShowEps(UserProblem problem) {
		String[] params = new String[2];
		params[0] = config.getEpsRPath();
		params[1] = config.getRPath()+problem.getType().toString()+config.getRFilename();
		System.out.println("?"+config.getRPath()+problem.getType().toString()+config.getRFilename());

		String[] envp = new String[1];
		envp[0] = config.getEpsEnviromentVar();
		Process p;
		try {
			p = Runtime.getRuntime().exec(params, envp, new File(config.getEpsDestinationPath()));
			p.waitFor();

			Runtime.getRuntime().exec("open " + config.getEpsOpenPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method compiles the script .tex and shows the .pdf file
	 **/
	public void compileAndShowPdf(UserProblem problem) {
		String[] params = new String[2];
		params[0] = config.getPdflatexPath();
		params[1] = config.getLatexPath()+problem.getType().toString()+config.getLatexFilename()+problem.getType().toString()+".tex";

		System.out.println(config.getLatexPath()+problem.getType().toString()+config.getLatexFilename()+problem.getType().toString()+".tex");
		
		String[] envp = new String[1];
		envp[0] = config.getPdfEnviromentVar();
		try {
			Process p = Runtime.getRuntime().exec(params, envp, new File(config.getPdfDestinationPath()));
			p.waitFor();

			Runtime.getRuntime().exec("open " + config.getPdfOpenPath()+problem.getType().toString()+".pdf");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Executes the optimization process according to the selected algorithm and
	 * calls the method to show the graphics and the .eps and .pdf files.
	 * @param criteriaSection 
	 * @param algorithmsSelectedList 
	 * 
	 * @param dataType2
	 **/
	public void executeOptimization(UserProblem problem, NameDescriptionSection nameDescription, EmailSection email,
			int decisionVariableNumber, String decisionVariableGroupName, EmailHandler emailHandler, String adminEmail,
			Type dataType, CriteriaSection criteriaSection, List<String> algorithmsSelectedList) {
		problem.setGroupName(decisionVariableGroupName);
		problem.setNumberVariables(decisionVariableNumber);
		problem.setName(nameDescription.getProblemName().getText());
		problem.setEmail(email.getEmail().getText());
		
		for (int i = 0; i < problem.getVariables().size(); i++) {
			System.out.println("!"+problem.getVariables().get(i).getRestriction());
		}
		
//		try { 
//		 	switch (dataType) {
//			case DOUBLE:
//				new DoubleExperiment(problem,algorithmsSelectedList,criteriaSection.getTxtJarPath().getText());
//				break;
//			case INTEGER:
//				new IntegerExperiment(problem,algorithmsSelectedList,criteriaSection.getTxtJarPath().getText());
//				break;
//			case BINARY:
//				new BinaryExperiment(problem,algorithmsSelectedList,criteriaSection.getTxtJarPath().getText());
//				break;
//			default:
//				return; 
//			}
			showGraphic(problem, algorithmsSelectedList);
//			compileAndShowEps(problem);
//			compileAndShowPdf(problem);
//		} catch (IOException e2) {
//			try {
//				e2.printStackTrace();
//				emailHandler.sendEmail(adminEmail, email.getEmail().getText(), adminEmail, "There was a problem",
//						"There was a problem running the problem you requested, please try again.\r\n"
//								+ "If the problem continues, contact us so we can help");
//				return;
//			} catch (AddressException e1) {
//			} catch (MessagingException e1) {
//			}
//		}
//		try {
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			Date date = new Date();
//			String subject = "Otimiza��o em curso: " + nameDescription.getProblemName().getText() + " "
//					+ dateFormat.format(date);
//			emailHandler.sendEmail(adminEmail, email.getEmail().getText(), adminEmail, subject,
//					"Muito obrigado por usar esta plataforma de otimiza��o. Ser� informado por email\r\n"
//							+ "sobre o progresso do processo de otimiza��o, quando o processo de otimiza��o tiver atingido 25%,\r\n"
//							+ "50%, 75% do total do (n�mero de avalia��es ou) tempo estimado, e tamb�m quando o processo tiver\r\n"
//							+ "terminado, com sucesso ou devido � ocorr�ncia de erros.");
//		} catch (MessagingException e1) {
//		}
	}
}