package mainAndGui;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collections;
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
	private Map<String, List<String>> criteriaPanel = new HashMap<String, List<String>>();
	private JButton btnRemoveCriteria;
	private JTextField txtCriteriaName;

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
		FrameSize.setFrame(criteriaFrame, 1);
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
		pnlCriteriaList.add(addCriteriaPanel());
		pnlCriteriaFinish.add(btnCriteriaFinish);

		pnlCriteria.add(pnlAddCriteria, BorderLayout.PAGE_START);
		pnlCriteria.add(pnlCriteriaList);
		pnlCriteria.add(pnlCriteriaFinish, BorderLayout.PAGE_END);
		criteriaFrame.add(new JScrollPane(pnlCriteria));
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
	 * @param criteriaFrame
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
					List<String> list = criteriaPanel.get(key);
					System.out.println(list);
					if (list.contains("")) {
						criteriaReady = false;
						break;
					}
				}

				if (criteriaReady) {
					for (String key : criteriaPanel.keySet()) {
						List<String> list = criteriaPanel.get(key);
						Criteria criteria = new Criteria(list.get(0), list.get(1));
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
		JPanel pnlCriteriaJar = new JPanel();
		JPanel pnlCriteriaDataType = new JPanel();

		JLabel lblCriteriaName = new JLabel("Criteria Name:");
		txtCriteriaName = new JTextField();
		JLabel lblJarPath = new JLabel("Jar Path");
		JTextField txtJarPath = new JTextField();

		List<String> listOfComponents = new ArrayList<String>();
		listOfComponents.add(txtCriteriaName.getText());
		listOfComponents.add(txtJarPath.getText());
		criteriaPanel.put(pnlCriteria.getName(), listOfComponents);

		criteriaName(txtCriteriaName, pnlCriteria);
		criteriaJarPath(txtJarPath, pnlCriteria);
		btnReadJar = new JButton("Add jar");
		criteriaJarRead(txtJarPath, pnlCriteria);

		pnlCriteriaName.add(lblCriteriaName);
		pnlCriteriaName.add(txtCriteriaName);
		pnlCriteriaJar.add(lblJarPath);
		pnlCriteriaJar.add(txtJarPath);
		pnlCriteriaJar.add(btnReadJar);
		pnlCriteria.add(pnlCriteriaName);
		pnlCriteria.add(pnlCriteriaJar);
		pnlCriteria.add(pnlCriteriaDataType);
		return pnlCriteria;
	}

	/**
	 * Sets a criteria jar path when the button is clicked, and a file is chosen
	 **/
	private void criteriaJarRead(JTextField txtJarPath, JPanel pnlCriteria) {
		List<String> values = criteriaPanel.get(pnlCriteria.getName());
		btnReadJar.setContentAreaFilled(false);
		btnReadJar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchUploadJar = new JFileChooser();
				if (fchUploadJar.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String jarPath = fchUploadJar.getSelectedFile().getPath();
					txtJarPath.setText(jarPath);
					values.set(1, txtJarPath.getText());
				}
			}
		});
	}

	/**
	 * Sets a criteria jar path if a path is written
	 **/
	private void criteriaJarPath(JTextField txtJarPath, JPanel pnlCriteria) {
		txtJarPath.setColumns(15);
		List<String> values = criteriaPanel.get(pnlCriteria.getName());
		txtJarPath.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				values.set(1, txtJarPath.getText());
			}

			@Override
			public void focusGained(FocusEvent e) {
				values.set(1, "");
			}
		});
	}

	/**
	 * Sets a criteria name if a name is written
	 * 
	 * @param pnlCriteria
	 **/
	private void criteriaName(JTextField txtCriteriaName, JPanel pnlCriteria) {
		txtCriteriaName.setColumns(30);
		List<String> values = criteriaPanel.get(pnlCriteria.getName());
		txtCriteriaName.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!txtCriteriaName.getText().isEmpty()) {
					values.set(0, txtCriteriaName.getText());

				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				values.set(0, "");
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
