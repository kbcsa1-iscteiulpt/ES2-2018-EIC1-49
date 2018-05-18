package mainAndGui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.ui.RefineryUtilities;

import problem.UserProblem;
import support.BinaryExperiment;
import support.DoubleExperiment;
import support.IntegerExperiment;
import support.Support;

public class ExecutionSection {
	private JFrame afterOptimizationProcess;
	private JButton btnExecuteProcess;
	private JButton btnResults;

	/**
	 * Returns a panel with the button to execute the optimization process.
	 **/
	public JPanel executeProcessPanel(NameDescriptionSection nameDescription, EmailSection email, UserProblem problem,
			DecisionVariablesSection decisionVariables, String problemType, Support support, String adminEmail) {
		JPanel executeProcessPanel = new JPanel(new FlowLayout());
		problem(nameDescription, email, problem, decisionVariables, problemType, support, adminEmail);
		ImageIcon icoExecute = new ImageIcon(((new ImageIcon("./src/images/execute.png")).getImage())
				.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		btnExecuteProcess.setContentAreaFilled(false);
		btnExecuteProcess.setIcon(icoExecute);
		btnResults = new JButton("Results");
		btnResults.setContentAreaFilled(false);
		btnResults.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				afterOptimizationProcess = new JFrame("Results");
				FrameSize.setFrame(afterOptimizationProcess, 0.25);
				setAfterOptimizationProcess(afterOptimizationProcess);
				afterOptimizationProcess.setVisible(true);
			}

		});
		executeProcessPanel.add(btnExecuteProcess);
		executeProcessPanel.add(btnResults);
		return executeProcessPanel;
	}
	
	/**
	 * Checks if the mandatory fields are completed and executes the optimization process
	 * Sends an email warning if there was an error while running the problem requested.
	 **/
	private void problem(NameDescriptionSection nameDescription, EmailSection email, UserProblem problem,
			DecisionVariablesSection decisionVariables, String problemType, Support support, String adminEmail)
			throws java.awt.HeadlessException, java.lang.NumberFormatException {
		btnExecuteProcess = new JButton("Execute Optimization Process");
		btnExecuteProcess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameDescription.getProblemName().getText().isEmpty()
						|| nameDescription.getProblemDescription().getText().isEmpty()
						|| !email.getBtnWriteEmail().isEnabled()) {
					JOptionPane.showMessageDialog(null, "Please fill all the mandatory fields", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					problem.setGroupName(decisionVariables.getTxtNameOfDecisionVariablesGroup().getText());
					problem.setNumberVariables(Integer
							.parseInt(decisionVariables.getSpnNumberOfDecisionVariables().getValue().toString()));
					try {
						switch (problemType) {
						case "Double":
							new DoubleExperiment(problem);
							break;
						case "Integer":
							new IntegerExperiment(problem);
							break;
						case "Binary":
							new BinaryExperiment(problem);
							break;
						default:
							return;
						}
					} catch (IOException e2) {
						try {
							support.SendEmail(adminEmail, email.getEmail().getText(), "There was a problem","There was a problem running the problem you requested, please try again.\r\n"
									+ "If the problem continues, contact us so we can help");
							return;
						} catch (AddressException e1) {
						} catch (MessagingException e1) {
						}
					}
					try {
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Date date = new Date();
						String subject = "Otimiza��o em curso: " + nameDescription.getProblemName().getText() + " "
								+ dateFormat.format(date);
						
						support.SendEmail(adminEmail, email.getEmail().getText(), subject, "Muito obrigado por usar esta plataforma de otimiza��o. Ser� informado por email\r\n"
								+ "sobre o progresso do processo de otimiza��o, quando o processo de otimiza��o tiver atingido 25%,\r\n"
								+ "50%, 75% do total do (n�mero de avalia��es ou) tempo estimado, e tamb�m quando o processo tiver\r\n"
								+ "terminado, com sucesso ou devido � ocorr�ncia de erros.");
					} catch (MessagingException e1) {
					}
				}
			}
		});
	}

	/**
	 * Sets the content of the Results frame 
	 **/
	private void setAfterOptimizationProcess(JFrame frame) {
		JPanel pnlOptProcess = new JPanel(new GridLayout(3, 0));
		JButton btnGraphics = new JButton("Graphics");
		JButton btnEpsFile = new JButton(".eps File");
		JButton btnPdfFile = new JButton(".pdf File");
		btnGraphics.setContentAreaFilled(false);
		btnEpsFile.setContentAreaFilled(false);
		btnPdfFile.setContentAreaFilled(false);

		btnGraphics.setBorderPainted(true);
		btnEpsFile.setBorderPainted(true);
		btnPdfFile.setBorderPainted(true);

		btnGraphics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Graphic chart = new Graphic();
				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);
			}
		});

		btnEpsFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] params = new String[2];
				params[0] = "C:\\Program Files\\R\\R-3.5.0\\bin\\x64\\Rscript.exe";
				params[1] = "C:\\Users\\ASUS\\git\\ES2-2018-EIC1-49\\experimentBaseDirectory\\ExperimentsDoubleExternalViaJAR\\R\\HV.Boxplot.R";

				String[] envp = new String[1];
				envp[0] = "Path=C:\\Program Files\\R\\R-3.5.0\\bin\\x64";

				Process p;
				try {
					p = Runtime.getRuntime().exec(params, envp,
							new File("C:\\Users\\ASUS\\git\\ES2-2018-EIC1-49\\src\\files"));
					p.waitFor();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnPdfFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] params = new String[2];

					params[0] = "C:\\Users\\ASUS\\Desktop\\pdflatex.exe";

					params[1] = "C:\\Users\\ASUS\\git\\ES2-2018-EIC1-49\\experimentBaseDirectory\\ExperimentsDoubleExternalViaJAR\\latex\\ExperimentsDoubleExternalViaJAR.tex";

					String[] envp = new String[1];

					envp[0] = "Path=C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64";

					Process p = Runtime.getRuntime().exec(params, envp,
							new File("C:\\Users\\ASUS\\git\\ES2-2018-EIC1-49\\src\\files"));

					p.waitFor();
					Runtime.getRuntime().exec(
							"open C:\\Users\\ASUS\\git\\ES2-2018-EIC1-49\\src\\files\\ExperimentsDoubleExternalViaJAR.pdf");

				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		pnlOptProcess.add(btnGraphics);
		pnlOptProcess.add(btnEpsFile);
		pnlOptProcess.add(btnPdfFile);

		frame.add(pnlOptProcess);
	}

}
