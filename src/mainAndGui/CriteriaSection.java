 package mainAndGui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
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

public class CriteriaSection {
	private JButton btnCriteria;
	private JButton btnAddCriteria;
	private JButton btnCriteriaFinish;
	private JButton btnReadJar;
	private int criteriaAdded;
	private Map<Integer, String> criteriaNames = new HashMap<Integer, String>();
	private Map<Integer, String> criteriaPaths = new HashMap<Integer, String>();
	private Map<Integer, String> criteriaTypes = new HashMap<Integer, String>();

	/**
	 * Returns a panel with a JTextField filled automatically with the file path (to
	 * read XML) through the button.
	 **/

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
		FrameSize.setFrame(criteriaFrame, 0.5);
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

		btnAddCriteria = new JButton("Add criteria");
		btnAddCriteria.setContentAreaFilled(false);
		btnAddCriteria.setFocusable(false);
		btnAddCriteria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				pnlCriteriaList.add(addCriteria());
				pnlCriteria.revalidate();
			}
		});

		btnCriteriaFinish = new JButton("Finish");
		btnCriteriaFinish.setContentAreaFilled(false);
		ImageIcon icoFinish = new ImageIcon(((new ImageIcon("./src/images/finish.png")).getImage())
				.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
		btnCriteriaFinish.setContentAreaFilled(false);
		btnCriteriaFinish.setIcon(icoFinish);
		criteriaFinish(problem);

		pnlAddCriteria.add(btnAddCriteria);
		pnlCriteriaList.add(addCriteria());
		pnlCriteriaFinish.add(btnCriteriaFinish);

		pnlCriteria.add(pnlAddCriteria, BorderLayout.PAGE_START);
		pnlCriteria.add(pnlCriteriaList, BorderLayout.CENTER);
		pnlCriteria.add(pnlCriteriaFinish, BorderLayout.PAGE_END);

		criteriaFrame.add(new JScrollPane(pnlCriteria));
	}

	private void criteriaFinish(UserProblem problem) {
		btnCriteriaFinish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean criteriaReady = true;
				System.out.println(criteriaAdded);
				for (int i = 1; i <= criteriaAdded; i++) {
					if ((criteriaTypes.isEmpty() || criteriaTypes.get(i).equals("Select a data type"))
							|| criteriaPaths.isEmpty() || criteriaNames.isEmpty()) {

						JOptionPane.showMessageDialog(null, "Please fill all criteria fields", "Warning",
								JOptionPane.WARNING_MESSAGE);
						criteriaReady = false;
						break;
					}
				}
				if (criteriaReady) {
					for (int j = 1; j <= criteriaAdded; j++) {
						Criteria criteria = new Criteria(criteriaNames.get(j), criteriaPaths.get(j),
								criteriaTypes.get(j));
						problem.addCriteria(criteria);
					}
				}
			}
		});
	}

	/**
	 * Returns a JPanel with a JTextField to define the criteria(s) and a button to
	 * upload the .jar file.
	 **/
	private JPanel addCriteria() {
		criteriaAdded++;
		JPanel pnlCriteria = new JPanel(new FlowLayout());
		JPanel pnlCriteriaName = new JPanel();
		JPanel pnlCriteriaJar = new JPanel();
		JPanel pnlCriteriaDataType = new JPanel();

		JLabel lblCriteriaName = new JLabel("Criteria Name:");
		JTextField txtCriteriaName = new JTextField();
		txtCriteriaName.setColumns(30);

		txtCriteriaName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				criteriaNames.put(criteriaAdded, txtCriteriaName.getText());
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaNames.remove(criteriaAdded);
				criteriaAdded--;
			}
		});

		JLabel lblJarPath = new JLabel("Jar Path");
		JTextField txtJarPath = new JTextField();
		txtJarPath.setColumns(15);
		txtJarPath.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (criteriaPaths.containsKey(criteriaAdded)) {
					criteriaPaths.remove(criteriaAdded);
					criteriaAdded--;
				}
				criteriaPaths.put(criteriaAdded, txtJarPath.getText());
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaPaths.remove(criteriaAdded);
				criteriaAdded--;
			}
		});

		btnReadJar = new JButton("Add jar");
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
						criteriaAdded--;
					}
					criteriaPaths.put(criteriaAdded, jarPath);
				}
			}
		});

		String[] dataTypeCriteria = { "Select a data type", "Binary", "Double", "Integer" };
		JComboBox<String> cmbDataType = new JComboBox<String>(dataTypeCriteria);

		cmbDataType.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				criteriaTypes.put(criteriaAdded, cmbDataType.getSelectedItem().toString());
			}

			@Override
			public void focusGained(FocusEvent e) {
				criteriaTypes.remove(criteriaAdded);
				criteriaAdded--;
			}
		});

		pnlCriteriaName.add(lblCriteriaName);
		pnlCriteriaName.add(txtCriteriaName);
		pnlCriteriaJar.add(lblJarPath);
		pnlCriteriaJar.add(txtJarPath);
		pnlCriteriaJar.add(btnReadJar);
		pnlCriteriaDataType.add(cmbDataType);
		pnlCriteria.add(pnlCriteriaName);
		pnlCriteria.add(pnlCriteriaJar);
		pnlCriteria.add(pnlCriteriaDataType);
		return pnlCriteria;
	}


}
