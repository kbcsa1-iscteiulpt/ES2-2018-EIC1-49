package interfaceProcess;

import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

import interfaceSections.CriteriaSection;
import interfaceSections.EmailSection;
import interfaceSections.Graphic;
import interfaceSections.NameDescriptionSection;
import interfaceSections.TimeOptimizationSection;
import jMetal.BinaryExperiment;
import jMetal.DoubleExperiment;
import jMetal.IntegerExperiment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import problem.UserProblem;
import support.ConfigXML;
import support.XMLEditor;
import support.EmailHandler;
import problem.Time;
import problem.Type;

/**
 * This class represents the execution process.
 * 
 * @author Diana nr 72898
 **/
public class ExecutionProcess {
 
	private ConfigXML config = ConfigXML.getInstance();
	private XMLEditor xmlEditor = new XMLEditor();
	private UserProblem problem;

	/**
	 * This method shows the graphic a set of the best solutions generated by the
	 * algorithms that were selected by the user.
	 * @param problem
	 * @param algorithmsSelectedList 
	 **/
	public void showGraphic(UserProblem problem, List<String> algorithmsSelectedList) {
		new Graphic(problem, algorithmsSelectedList);
	}

	/**
	 * Executes the optimization process according to the selected algorithm and
	 * calls the method to show the graphics and the .eps and .pdf files.
	 * @param problem
	 * @param nameDescription
	 * @param email
	 * @param decisionVariableNumber
	 * @param decisionVariableGroupName
	 * @param emailHandler
	 * @param type
	 * @param criteriaSection
	 * @param algorithmsSelectedList
	 * @param timeOptimization
	 **/
	public void executeOptimization(UserProblem problem, NameDescriptionSection nameDescription, EmailSection email,
			int decisionVariableNumber, String decisionVariableGroupName, EmailHandler emailHandler,
			Type type, CriteriaSection criteriaSection, List<String> algorithmsSelectedList, TimeOptimizationSection timeOptimization, JFrame beforeOptimizationProcess) {
		this.problem = problem;
		problem.setGroupName(decisionVariableGroupName);
		problem.setNumberVariables(decisionVariableNumber);
		problem.setName(nameDescription.getProblemName().getText());
		problem.setEmail(email.getEmail().getText());
		 
		this.problem.setMax(new Time(Integer.parseInt(timeOptimization.getSpnMaxNumberOfDays().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnMaxNumberOfHours().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnMaxNumberOfMinutes().getValue().toString())));
		this.problem.setIdeal(new Time(Integer.parseInt(timeOptimization.getSpnIdealNumberOfDays().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnIdealNumberOfHours().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnIdealNumberOfMinutes().getValue().toString())));
		this.problem.setName(nameDescription.getProblemName().getText());
		this.problem.setDescription(nameDescription.getProblemDescription().getText());
	
		xmlEditor.write("./tmp_240424042404.xml", problem);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		String subject = "Otimizacao em curso: " + nameDescription.getProblemName().getText() + " "
				+ dateFormat.format(date);
			emailHandler.sendEmailWithAttachment(config.getEmailAdmin(), email.getEmail().getText(), config.getEmailAdmin(), subject,
					"Muito obrigado por usar esta plataforma de otimizacaoo. Sera informado por email\r\n"
							+ "sobre o progresso do processo de otimizacao, quando o processo de otimizacao tiver atingido 25%,\r\n"
							+ "50%, 75% do total do (numero de avaliacoes ou) tempo estimado, e tambem quando o processo tiver\r\n"
							+ "terminado, com sucesso ou devido a ocorrencia de erros.", nameDescription);
		File tmp_xml = new File("./tmp_240424042404.xml");
		tmp_xml.delete();

		try { 
		 	switch (type) {
			case DOUBLE:
				new DoubleExperiment(problem,algorithmsSelectedList,criteriaSection.getTxtJarPath().getText());
				break;
			case INTEGER:
				new IntegerExperiment(problem,algorithmsSelectedList,criteriaSection.getTxtJarPath().getText());
				break;
			case BINARY:
				new BinaryExperiment(problem,algorithmsSelectedList,criteriaSection.getTxtJarPath().getText());
				break;
			default:
				return; 
			}
		 	beforeOptimizationProcess.dispose();
			config.compileAndShowEps(problem);
			config.compileAndShowPdf(problem);
			showGraphic(problem, algorithmsSelectedList);
		} catch (IOException e2) {
			try {
				e2.printStackTrace();
				emailHandler.sendEmail(config.getEmailAdmin(), email.getEmail().getText(), config.getEmailAdmin(), "There was a problem",
						"There was a problem running the problem you requested, please try again.\r\n"
								+ "If the problem continues, contact us so we can help");
				return;
			} catch (AddressException e1) {
			} catch (MessagingException e1) {
			}
		}
		
	}
}