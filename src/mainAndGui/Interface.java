package mainAndGui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import problem.UserProblem;
import support.EmailHandler;
import support.XMLEditor;

/**
 * This class represents the interface
 * 
 * @author Diana Lopes n� 72898
 **/

public class Interface {

	private String adminEmail;

	private JFrame decisionFrame;
	private JFrame readProblemFrame;
	private JFrame createProblemFrame;
	private EmailHandler support = new EmailHandler();
	private XMLEditor xml = new XMLEditor();
	private UserProblem problem = new UserProblem();

	private EmailSection emailSection = new EmailSection();
	private HelpSection helpSection = new HelpSection();
	private TimeOptimizationSection timeOptimizationSection = new TimeOptimizationSection();
	private NameDescriptionSection nameDescriptionSection = new NameDescriptionSection();
	private TypeVarSection typeVarSection = new TypeVarSection();
	private DecisionVariablesSection decisionVariablesSection = new DecisionVariablesSection();
	private CriteriaSection criteriaSection = new CriteriaSection();
	private SaveSection saveSection = new SaveSection();
	private ReadSection readSection = new ReadSection();
	private FillForms fillForms = new FillForms();
	private AlgorithmSelectionSection algorithmSelectionSection = new AlgorithmSelectionSection();

	private int criteriaAdded = 0;
	private JButton btnReadProblem;
	private JButton btnCreateProblem;

	public Interface(String adminEmail) {
		decisionFrame = new JFrame("Problem to be optimized");
		decisionFrame.setVisible(true);
		FrameSize.setFrame(decisionFrame, 0.25);
		setDecisionContent(decisionFrame);
		this.adminEmail = adminEmail;
	}

	/**
	 * Returns a frame with two options: read a problem from a XML file or create a
	 * new one.
	 **/
	private void setDecisionContent(JFrame frame) {
		GridLayout gly = new GridLayout(0, 2);
		gly.setHgap(5);
		JPanel decisionPanel = new JPanel(gly);
		btnReadProblem = new JButton("Read problem from XML file");
		btnReadProblem.setContentAreaFilled(false);
		btnReadProblem.setFocusable(false);
		readButton();
		btnCreateProblem = new JButton("Create a new problem");
		btnCreateProblem.setContentAreaFilled(false);
		btnCreateProblem.setFocusable(false);
		createButton();
		decisionPanel.add(btnReadProblem);
		decisionPanel.add(btnCreateProblem);
		frame.add(decisionPanel);
	}

	/**
	 * When the "Create a new problem" button is clicked, a new frame is created and
	 * shown
	 **/
	private void createButton() {
		btnCreateProblem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (createProblemFrame == null) {
					createProblemFrame = new JFrame("Problem to be optimized");
					FrameSize.setFrame(createProblemFrame, 0.75);
					setContent(createProblemFrame, false);
				}
				createProblemFrame.setVisible(true);
				decisionFrame.setVisible(false);
			}
		});
	}

	/**
	 * When the "Read problem from XML file" button is clicked, a new frame is
	 * created and shown with the form filled with the data given by the XML file
	 **/
	private void readButton() {
		btnReadProblem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (readProblemFrame == null) {
					readProblemFrame = new JFrame("Problem to be optimized");
					FrameSize.setFrame(readProblemFrame, 0.75);
					setContent(readProblemFrame, true);

					JFileChooser fchReadXML = new JFileChooser();
					if (fchReadXML.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						String filePath = fchReadXML.getSelectedFile().getPath();
						problem = xml.read(filePath);
						fillForms.fillInicialForm(filePath, nameDescriptionSection, problem, emailSection, typeVarSection, timeOptimizationSection,
								readSection.getFilePathXML());
						fillForms.fillDecisionVariableForm(decisionVariablesSection, problem);
					}
				}
				readProblemFrame.setVisible(true);
				decisionFrame.setVisible(false);
			}
		});
	}

	/**
	 * The panels are added to the initial frame by calling methods that return
	 * JPanel.
	 **/
	private void setContent(JFrame frame, boolean problemReadFromXML) {
		JPanel initialPanel = new JPanel(new GridLayout(problemReadFromXML ? 12 : 11, 0));

		problemHelp(frame, initialPanel);

		if (problemReadFromXML) {
			problemRead(initialPanel);
		}

		problemNameDescription(initialPanel);
		problemUserEmail(frame, initialPanel, support, adminEmail, helpSection);
		problemTime(initialPanel);
		problemType(initialPanel, decisionVariablesSection, algorithmSelectionSection);
		problemDecisionVariables(initialPanel, frame, algorithmSelectionSection);

		problemSave(initialPanel, typeVarSection);
		problemCriteria(initialPanel);
		problemAlgorithmSelection(initialPanel);
		frame.add(initialPanel);
	}

	/**
	 * Adds the help panel to the initial frame
	 **/
	private void problemHelp(JFrame frame, JPanel initialPanel) {
		initialPanel.add(helpSection.getHelpPanel(frame, decisionFrame, emailSection, support, adminEmail));
	}

	/**
	 * Adds the read problem panel to the initial frame
	 **/
	private void problemRead(JPanel initialPanel) {
		initialPanel
				.add(readSection.readPanel(nameDescriptionSection, emailSection, problem, xml, typeVarSection ,decisionVariablesSection, timeOptimizationSection));
	}

	/**
	 * Adds the problem name and description panel to the initial frame
	 **/
	private void problemNameDescription(JPanel initialPanel) {
		initialPanel.add(nameDescriptionSection.problemNamePanel());
		initialPanel.add(nameDescriptionSection.problemDescriptionPanel());
	}

	/**
	 * Adds the email panel to the initial frame
	 **/
	private void problemUserEmail(JFrame frame, JPanel initialPanel, EmailHandler support, String adminEmail, HelpSection help) {
		initialPanel.add(emailSection.emailPanel(frame, support, adminEmail, help));
	}
	/**
	 * Adds the type problem panel to the initial frame
	 **/
	private void problemType(JPanel initialPanel, DecisionVariablesSection decisionVariable, AlgorithmSelectionSection algorithmSelection) {
		initialPanel.add(typeVarSection.setVarType(decisionVariable, algorithmSelection));
	}
	/**
	 * Adds the ideal and maximum time for optimization panel to the initial frame
	 **/
	private void problemTime(JPanel initialPanel) {
		initialPanel.add(timeOptimizationSection.maxTimePanel());
		initialPanel.add(timeOptimizationSection.idealTimePanel());
	}

	/**
	 * Adds the decision variable panel to the initial frame
	 **/
	private void problemDecisionVariables(JPanel initialPanel, JFrame frame, AlgorithmSelectionSection algorithmSelection) {
		initialPanel.add(decisionVariablesSection.decisionVarPanel(problem, frame, algorithmSelection));
	}

	/**
	 * Adds the save problem panel to the initial frame
	 **/
	private void problemSave(JPanel initialPanel, TypeVarSection typeVarSection) {
		initialPanel
				.add(saveSection.savePanel(nameDescriptionSection, emailSection, problem, xml, decisionVariablesSection, timeOptimizationSection, typeVarSection));
	}

	/**
	 * Adds the criteria panel to the initial frame
	 **/
	private void problemCriteria(JPanel initialPanel) {
		initialPanel.add(criteriaSection.criteriaPanel(criteriaAdded, problem));
	}

	/**
	 * Adds the algorithm selection panel to the initial frame
	 **/
	private void problemAlgorithmSelection(JPanel initialPanel) {
		initialPanel.add(algorithmSelectionSection.algorithmSelection(nameDescriptionSection, emailSection, timeOptimizationSection, problem, support, adminEmail,criteriaSection));
	}

	public JButton getBtnReadProblem() {
		return btnReadProblem;
	}

	public JButton getBtnCreateProblem() {
		return btnCreateProblem;
	}

	public HelpSection getHelpSection() {
		return helpSection;
	}

	public EmailSection getEmailSection() {
		return emailSection;
	}

	public DecisionVariablesSection getDecisionVariablesSection() {
		return decisionVariablesSection;
	}

	public CriteriaSection getCriteriaSection() {
		return criteriaSection;
	}

	public ReadSection getReadSection() {
		return readSection;
	}

	public TypeVarSection getProblemDataTypeSection() {
		return typeVarSection;
	}

	public SaveSection getSaveSection() {
		return saveSection;
	}

	
}