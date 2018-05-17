package mainAndGui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.uma.jmetal.util.ProblemUtils;

import problem.UserProblem;
import support.XML_Editor;

public class DecisionContentSection {

	private FillForms fillForms = new FillForms();
	private JButton btnReadProblem;
	private JButton btnCreateProblem;
	private JFrame readProblemFrame;
	private JFrame createProblemFrame;
	private boolean read = false;
	private boolean created = false;

	/**
	 * Returns a frame with two options: read a problem from a XML file or create a
	 * new one.
	 **/
	
	public ArrayList<Boolean> setDecisionContent(JFrame decisionFrame, JFrame readProblem, JFrame createProblem, UserProblem problem, XML_Editor xml,
												NameDescriptionSection nameDescriptionProblem, EmailSection email,
												TimeOptimizationSection timeOptimization, ReadSection readFile,
												DecisionVariablesSection decisionVariables) {
		ArrayList<Boolean> readCreated = new ArrayList<Boolean>();
		this.readProblemFrame = readProblem;
		this.createProblemFrame = createProblem;
		GridLayout gly = new GridLayout(0, 2);
		gly.setHgap(5);
		JPanel decisionPanel = new JPanel(gly);
		btnReadProblem = new JButton("Read problem from XML file");
		btnReadProblem.setContentAreaFilled(false);
		btnReadProblem.setFocusable(false);
		btnReadProblem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (readProblemFrame == null) {
					readProblemFrame = new JFrame("Problem to be optimized");
					FrameSize.setFrame(readProblemFrame, 0.75);
					read = true;
					JFileChooser fchReadXML = new JFileChooser();
					if (fchReadXML.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						String filePath = fchReadXML.getSelectedFile().getPath();
						fillForms.fillInicialForm(filePath, nameDescriptionProblem, xml.read(filePath), email, timeOptimization, readFile.getTxtFilePathXML());
						fillForms.fillDecisionVariableForm(decisionVariables, xml.read(filePath));
					} else {
						created = true;
					}
					readProblemFrame.setVisible(true);
					decisionFrame.setVisible(false);
				}
			}
		});
		btnCreateProblem = new JButton("Create a new problem");
		btnCreateProblem.setContentAreaFilled(false);
		btnCreateProblem.setFocusable(false);
		btnCreateProblem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (createProblemFrame == null) {
					createProblemFrame = new JFrame("Problem to be optimized");
					FrameSize.setFrame(createProblemFrame, 0.75);

				} else {
					created = true;
				}
				createProblemFrame.setVisible(true);
				decisionFrame.setVisible(false);
			}
		});
		decisionPanel.add(btnReadProblem);
		decisionPanel.add(btnCreateProblem);
		decisionFrame.add(decisionPanel);

		readCreated.add(0, read);
		readCreated.add(1, created);
		return readCreated;
	}

	public JFrame getReadProblemFrame() {
		return readProblemFrame;
	}

	public JFrame getCreateProblemFrame() {
		return createProblemFrame;
	}
	
	
}
