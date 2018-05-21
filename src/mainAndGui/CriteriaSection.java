package mainAndGui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import problem.Criteria;
import problem.UserProblem;

/**
 * This class represents the criteria section.
 * 
 * @author Diana nr 72898
 **/
public class CriteriaSection {
	private JButton btnCriteria;
	private JButton btnAddCriteria;
	private JButton btnCriteriaFinish;
	private JButton btnReadJar;
	private int criteriaAdded;
	private Map<String, String> criteriaPanel = new HashMap<String, String>();
	private JButton btnRemoveCriteria;
	private JTextField txtCriteriaName;
	private JTextField txtJarPath;

	public JTextField getTxtJarPath() {
		return txtJarPath;
	}

	/**
	 * Returns a panel with a button. When clicked, a new frame is displayed to
	 * specify the criteria.
	 **/
	public JPanel criteriaPanel(int criteriaAdded, UserProblem problem) {
		this.criteriaAdded = criteriaAdded;
		JPanel pnlCriteria = new JPanel();
		btnCriteria = new JButton("Criteria to be optimized");
		btnCriteria.setContentAreaFilled(false);
		JFrame criteriaFrame = new JFrame("Criterias");
		FrameSize.setFrame(criteriaFrame, 0.75);
		setCriteriaFrame(criteriaFrame, problem);
		btnCriteria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				criteriaFrame.setVisible(true);
			}
		});
		pnlCriteria.add(btnCriteria);
		return pnlCriteria;
	}

	/**
	 * Adds content to the frame with a JTextField to define the criteria(s), a
	 * button to add a new criteria and a button to upload the .jar file.
	 **/
	private void setCriteriaFrame(JFrame criteriaFrame, UserProblem problem) {
		JPanel pnlCriteria = new JPanel(new BorderLayout());
		JPanel pnlAddCriteria = new JPanel();
		JPanel pnlCriteriaList = new JPanel();
		JLabel lblInform = new JLabel(
				"This platform assumes in the optimization process that optimizing means minimizing the optimization criteria expressed through the objective functions");
		btnRemoveCriteria = new JButton("Remove criteria");
		pnlCriteriaList.setLayout(new BoxLayout(pnlCriteriaList, BoxLayout.Y_AXIS));
		JPanel pnlCriteriaFinish = new JPanel();
		addCriteria(pnlCriteria, pnlCriteriaList);
		removeCriteria(pnlCriteria, pnlCriteriaList);

		criteriaFinish(problem, criteriaFrame);
		pnlAddCriteria.add(btnAddCriteria);
		pnlAddCriteria.add(btnRemoveCriteria);
		pnlCriteriaList.add(lblInform);
		pnlCriteriaList.add(jarPanel());
		pnlCriteriaList.add(addCriteriaPanel());
		pnlCriteriaFinish.add(btnCriteriaFinish);

		pnlCriteria.add(pnlAddCriteria, BorderLayout.PAGE_START);
		pnlCriteria.add(pnlCriteriaList);
		pnlCriteria.add(pnlCriteriaFinish, BorderLayout.PAGE_END);
		criteriaFrame.add(new JScrollPane(pnlCriteria));
	}

	private JPanel jarPanel() {
		JPanel pnlAddJar = new JPanel();
		JLabel lblJarPath = new JLabel("Jar Path");
		txtJarPath = new JTextField();
		txtJarPath.setColumns(30);
		btnReadJar = new JButton("Add jar");
		pnlAddJar.add(lblJarPath);
		pnlAddJar.add(txtJarPath);
		pnlAddJar.add(btnReadJar);
		criteriaJarRead(txtJarPath, pnlAddJar);
		return pnlAddJar;
	}

	/**
	 * When the add criteria button is clicked, it adds a new criteria
	 **/
	private void addCriteria(JPanel pnlCriteria, JPanel pnlCriteriaList) {
		btnAddCriteria = new JButton("Add criteria");
		btnAddCriteria.setContentAreaFilled(false);
		btnAddCriteria.setFocusable(false);
		btnAddCriteria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlCriteriaList.add(addCriteriaPanel());
				pnlCriteria.revalidate();
			}
		});
	}

	/**
	 * When the remove criteria button is clicked, it removes the last criteria
	 **/
	private void removeCriteria(JPanel pnlCriteria, JPanel pnlCriteriaList) {
		btnRemoveCriteria.setContentAreaFilled(false);
		btnRemoveCriteria.setFocusable(false);
		btnRemoveCriteria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (criteriaAdded > 1) {

					List<String> keys = new ArrayList<String>(criteriaPanel.keySet());
					pnlCriteriaList.remove(criteriaAdded);
					criteriaPanel.remove(keys.get(keys.size() - 1));
					criteriaAdded--;
					pnlCriteria.revalidate();
				}
			}
		});
	}

	/**
	 * Creates the criteria finished button and when clicked, sets the problem
	 * criteria
	 * 
	 **/
	private void criteriaFinish(UserProblem problem, JFrame criteriaFrame) {
		btnCriteriaFinish = new JButton("Finish");
		btnCriteriaFinish.setContentAreaFilled(false);
		ImageIcon icoFinish = new ImageIcon(((new ImageIcon("./src/images/finish.png")).getImage())
				.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
		btnCriteriaFinish.setContentAreaFilled(false);
		btnCriteriaFinish.setIcon(icoFinish);
		btnCriteriaFinish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean criteriaReady = true;
				boolean addToProblem = true;
				for (String key : criteriaPanel.keySet()) {
					String name = criteriaPanel.get(key);
					if (name.isEmpty()) {
						criteriaReady = false;
						break;
					}
				}

				if (criteriaReady) {
					for (String key : criteriaPanel.keySet()) {
						String name = criteriaPanel.get(key);
						Criteria criteria = new Criteria(name, txtJarPath.getText());
						for (int i = 0; i < problem.getCriterias().size(); i++) {
							if (problem.getCriterias().get(i).toString().equals(criteria.toString())) {
								addToProblem = false;
								break;
							}
						}
						if (addToProblem == true) {
							problem.addCriteria(criteria);

						}
						criteriaFrame.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please fill all criteria fields", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	/**
	 * Returns a JPanel with a JTextField to define the criteria(s) and a button to
	 * upload the .jar file.
	 **/
	private JPanel addCriteriaPanel() {
		criteriaAdded++;
		JPanel pnlCriteria = new JPanel();
		pnlCriteria.setName(Integer.toString(criteriaAdded));
		JPanel pnlCriteriaName = new JPanel();
		JPanel pnlCriteriaDataType = new JPanel();
		txtCriteriaName = new JTextField();

		
		criteriaPanel.put(pnlCriteria.getName(), txtCriteriaName.getText());

		criteriaName(txtCriteriaName, pnlCriteria);
		JPanel pnlCriteriaJar = new JPanel();
		JLabel lblCriteriaName = new JLabel("Criteria Name:");
		

		pnlCriteriaName.add(lblCriteriaName);
		pnlCriteriaName.add(txtCriteriaName);
		pnlCriteria.add(pnlCriteriaName);
		pnlCriteria.add(pnlCriteriaJar);
		pnlCriteria.add(pnlCriteriaDataType);
		return pnlCriteria;
	}

	/**
	 * Sets a criteria jar path when the button is clicked, and a file is chosen
	 **/
	private void criteriaJarRead(JTextField txtJarPath, JPanel pnlCriteria) {
		btnReadJar.setContentAreaFilled(false);
		btnReadJar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchUploadJar = new JFileChooser();
				fchUploadJar.setFileFilter(new FileNameExtensionFilter("Jar files", "jar"));
				if (fchUploadJar.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String jarPath = fchUploadJar.getSelectedFile().getPath();
					if(jarPath.endsWith(".jar")) {
						txtJarPath.setText(jarPath);
					}else {
						JOptionPane.showMessageDialog(null, "Please upload a jar file", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
	}



	/**
	 * Sets a criteria name if a name is written
	 * 
	 **/
	private void criteriaName(JTextField txtCriteriaName, JPanel pnlCriteria) {
		txtCriteriaName.setColumns(30);
		txtCriteriaName.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!txtCriteriaName.getText().isEmpty()) {
					criteriaPanel.put(pnlCriteria.getName(), txtCriteriaName.getText());
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaPanel.put(pnlCriteria.getName(), "");
			}
		});
	}

	public JButton getBtnCriteria() {
		return btnCriteria;
	}

	public JButton getBtnAddCriteria() {
		return btnAddCriteria;
	}

	public JButton getBtnCriteriaFinish() {
		return btnCriteriaFinish;
	}

	public JButton getBtnReadJar() {
		return btnReadJar;
	}

	public JButton getBtnRemoveCriteria() {
		return btnRemoveCriteria;
	}

	public JTextField getTxtCriteriaName() {
		return txtCriteriaName;
	}
	
	
}
