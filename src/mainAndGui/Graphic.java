package mainAndGui;

import org.jfree.chart.ChartPanel;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import jUnitTests.AlgorithmConfigTest;
import problem.UserProblem;
import support.AlgorithmsConfig;
import support.Config;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * This class creates a frame that shows a graphic with the values of the rf and
 * rs files that contains the JMetal results.
 * 
 * @author Kevin Corrales n� 73529
 **/
public class Graphic {

	private final double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
	private final double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;

	private UserProblem problem;
	private List<double[]> xAxis = new ArrayList<double[]>();
	private Config config = Config.getInstance();
	
	private String rsPath;
	private String rfPath;
	private List<String> algorithmsSelectedList = new ArrayList<String>();

	public Graphic(UserProblem problem, List<String> algorithmsSelectedList) {
		this.problem = problem;
		this.algorithmsSelectedList = algorithmsSelectedList;

		switch(problem.getType()) {
			case DOUBLE:
				rsPath=config.getInstance().getrsPathDouble();
				rfPath=config.getInstance().getrfPathDouble();
				break;
			case INTEGER:
				rsPath=config.getInstance().getrsPathInteger();
				rfPath=config.getInstance().getrfPathInteger();
				break;
			case BINARY:
				rsPath=config.getInstance().getrsPathBinary();
				rfPath=config.getInstance().getrfPathBinary();
				break;
		}
		
		File rsFolder = new File(rsPath);
		File[] listOfrs = rsFolder.listFiles();

		File rfFolder = new File(rfPath);
		File[] listOfrf = rfFolder.listFiles();

		for (int i = 0; i < listOfrf.length; i++) {
			File file = listOfrf[i];
			if (file.isFile() && file.getName().endsWith(".rf")
					&& file.getName().toUpperCase().contains(problem.getType().toString())
					&& containsAlgorithm(file.getName())) {
				readResults(file.getPath());
				setContent(file.getName());
			}
		}

	}

	public boolean containsAlgorithm(String filename) {
		for (String algorithm : algorithmsSelectedList) {
			if (algorithm.contains(filename)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method sets the panel content of the graphic with the results
	 */
	public void setContent(String title) {
		JFrame algorithmFrame = new JFrame();
		JPanel pnlChart = new JPanel(new BorderLayout());
		JFreeChart lineChart = ChartFactory.createLineChart(title, "", "", createDataset(problem),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension((int) width, (int) height));
		JButton btnSaveAlgorithms = new JButton("Save Algorithms");
		btnSaveAlgorithms.setContentAreaFilled(false);
		btnSaveAlgorithms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fchXMLSave = new JFileChooser();
				fchXMLSave.setDialogTitle("Save");
				fchXMLSave.setFileFilter(new FileNameExtensionFilter("txt Files", "txt"));
				if (fchXMLSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String filePath = fchXMLSave.getSelectedFile().getPath();
					if (!filePath.endsWith(".txt")) {
						filePath += ".txt";
					}
					xAxis.add(yAxis);

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				scanner.close();
			}
		}

	}
}
