package mainAndGui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * @author Diana nr 72898
 **/
public class CriteriaSection {
	private JButton btnCriteria;
	private JButton btnAddCriteria;
	private JButton btnCriteriaFinish;
	private JButton btnReadJar;
	private int criteriaAdded;
	private Map<Integer, String> criteriaNames = new HashMap<Integer, String>();
	private Map<Integer, String> criteriaPaths = new HashMap<Integer, String>();
	private JButton btnRemoveCriteria;

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
		pnlCriteriaList.setLayout(new BoxLayout(pnlCriteriaList, BoxLayout.Y_AXIS));
		JPanel pnlCriteriaFinish = new JPanel();

		btnRemoveCriteria = new JButton("Remove criteria");
		removeCriteria(pnlCriteria, pnlCriteriaList);

		addCriteria(pnlCriteria, pnlCriteriaList);
		criteriaFinish(problem);
		pnlAddCriteria.add(btnAddCriteria);
		pnlAddCriteria.add(btnRemoveCriteria);
		pnlCriteriaList.add(addCriteriaPanel());
		pnlCriteriaFinish.add(btnCriteriaFinish);

		pnlCriteria.add(pnlAddCriteria, BorderLayout.PAGE_START);
		pnlCriteria.add(pnlCriteriaList, BorderLayout.CENTER);
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
					criteriaAdded--;
					pnlCriteriaList.remove(criteriaAdded);
					criteriaNames.size();
					pnlCriteria.revalidate();
				}
			}
		});
	}

	/**
	 * Creates the criteria finished button and when clicked, sets the problem
	 * criteria
	 **/
	private void criteriaFinish(UserProblem problem) {
		btnCriteriaFinish = new JButton("Finish");
		btnCriteriaFinish.setContentAreaFilled(false);
		ImageIcon icoFinish = new ImageIcon(((new ImageIcon("./src/images/finish.png")).getImage())
				.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
		btnCriteriaFinish.setContentAreaFilled(false);
		btnCriteriaFinish.setIcon(icoFinish);
		btnCriteriaFinish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(criteriaNames.toString());
				System.out.println(criteriaPaths.toString());
				if (criteriaPaths.size() == criteriaAdded
						&& criteriaNames.size() == criteriaAdded) {
					for (int j = 1; j <=criteriaAdded; j++) {
						Criteria criteria = new Criteria(criteriaNames.get(j), criteriaPaths.get(j));
						problem.addCriteria(criteria);
					}
//					frame.dispose();
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
		JPanel pnlCriteriaName = new JPanel();
		JPanel pnlCriteriaJar = new JPanel();
		JPanel pnlCriteriaDataType = new JPanel();

		JLabel lblCriteriaName = new JLabel("Criteria Name:");
		JTextField txtCriteriaName = new JTextField();
		criteriaName(txtCriteriaName);

		JLabel lblJarPath = new JLabel("Jar Path");
		JTextField txtJarPath = new JTextField();
		criteriaJarPath(txtJarPath);
		btnReadJar = new JButton("Add jar");
		criteriaJarRead(txtJarPath);

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
	private void criteriaJarRead(JTextField txtJarPath) {
		btnReadJar.setContentAreaFilled(false);
		btnReadJar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchUploadJar = new JFileChooser();
				if (fchUploadJar.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String jarPath = fchUploadJar.getSelectedFile().getPath();
					txtJarPath.setText(jarPath);
					 if (criteriaPaths.containsKey(criteriaAdded)) {
					 criteriaPaths.remove(criteriaAdded);
					 }
					 criteriaPaths.put(criteriaAdded, jarPath);
				}
			}
		});
	}
	/**
	 * Sets a criteria jar path if a path is written
	 **/
	private void criteriaJarPath(JTextField txtJarPath) {
		txtJarPath.setColumns(15);

		txtJarPath.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (criteriaPaths.containsKey(criteriaAdded)) {
					criteriaPaths.remove(criteriaAdded);
				}
				if (!txtJarPath.getText().isEmpty()) {
					criteriaPaths.put(criteriaAdded, txtJarPath.getText());
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaPaths.remove(criteriaAdded);
			}
		});
	}

	/**
	 * Sets a criteria name if a name is written
	 **/
	private void criteriaName(JTextField txtCriteriaName) {
		txtCriteriaName.setColumns(30);
		txtCriteriaName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (!txtCriteriaName.getText().isEmpty()) {
					criteriaNames.put(criteriaAdded, txtCriteriaName.getText());
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaNames.remove(criteriaAdded);
			}
		});
	}
}
