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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import problem.UserProblem;
import support.AlgorithmsConfig;
import support.ConfigXML;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * This class creates a frame that shows a graphic with the values of the rf and
 * rs files that contains the JMetal results.
 * 
 * @author Kevin Corrales nº 73529
 **/
public class Graphic {

	private final double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
	private final double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;

	private UserProblem problem;
	private List<double[]> xAxis = new ArrayList<double[]>();
	private ConfigXML config = ConfigXML.getInstance();

	private String resultsPath;
	private List<String> algorithmsSelectedList = new ArrayList<String>();
	private AlgorithmsConfig algorithmConfig;

	public Graphic(UserProblem problem, List<String> algorithmsSelectedList) {
		this.problem = problem;
		this.algorithmsSelectedList = algorithmsSelectedList;

		switch (problem.getType()) {
		case DOUBLE:
			resultsPath = "experimentBaseDirectory/"+config.getResultsPathDouble();
			readAllFiles(".rf");
			break;
		case INTEGER:
			resultsPath = "experimentBaseDirectory/"+config.getResultsPathInteger();
			break;
		case BINARY:
			resultsPath = "experimentBaseDirectory/"+config.getResultsPathBinary();
			break;
		}
		System.out.println(resultsPath + "+++++++++");
		readAllFiles(".rs");
	}
	
	/**
	 * Reads all files from config path with given format
	 * @param format
	 */
	public void readAllFiles(String format) {
		File rfFolder = new File(resultsPath);
		File[] listOfrf = rfFolder.listFiles();
		
		for (int i = 0; i < listOfrf.length; i++) {
			File file = listOfrf[i];
			if (file.isFile() && file.getName().endsWith(format)
					&& file.getName().toUpperCase().contains(problem.getType().toString())
					&& containsAlgorithm(file.getName())) {
				readResults(file.getPath());
				setContent(file.getName(),format);
			}
		}
	}

	/**
	 * Checks if filename contains algorithm
	 * @param filename
	 * @return boolean
	 */
	public boolean containsAlgorithm(String filename) {
		for (String algorithm : algorithmsSelectedList) {
			if (filename.contains(algorithm)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method sets the panel content of the graphic with the results
	 */
	public void setContent(String title,String format) {
		JFrame algorithmFrame = new JFrame();
		JPanel pnlChart = new JPanel(new BorderLayout());
		JFreeChart lineChart = ChartFactory.createLineChart(title, "", "", createDataset(problem,format),
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
					algorithmConfig.writeAutomaticConfig(algorithmsSelectedList, filePath);
				}
			}
		});
		pnlChart.add(chartPanel);
		pnlChart.add(btnSaveAlgorithms, BorderLayout.SOUTH);
		algorithmFrame.add(pnlChart);
		algorithmFrame.pack();
		RefineryUtilities.centerFrameOnScreen(algorithmFrame);
		algorithmFrame.setVisible(true);

	}

	/**
	 * 
	 * This method generates a dataset from the list that contains the results
	 * 
	 * @return dataset
	 */
	private DefaultCategoryDataset createDataset(UserProblem problem,String format) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int x = 0; x < xAxis.size(); x++) {
			double[] yAxis = xAxis.get(x);
			
		if(format.equals(".rf")) {
			for (int y = 0; y < yAxis.length; y++) {
				if (y < problem.getCriterias().size() )
					dataset.addValue(yAxis[y], "Solution "+(x+1), problem.getCriterias().get(y).getName());
				else 
					dataset.addValue(yAxis[y], "Solution "+(x+1), "Criteria "+(y+1));
			}
		}else if(format.equals(".rs")){
			for (int y = 0; y < yAxis.length; y++) {
				if ( y<problem.getVariables().size())
					dataset.addValue(yAxis[y],"Solution "+(x+1)	, problem.getVariables().get(y).getName());
				else 
					dataset.addValue(yAxis[y],"Solution "+(x+1), "Variable " + (y+1));
			}
		}
		}

		return dataset;
	}

	/**
	 * This method reads JMetal results from a file
	 * 
	 * @param path
	 */
	public void readResults(String path) {
		Scanner scanner = null;
		xAxis = new ArrayList<double[]>();

		if (Paths.get(path).toFile().exists()) {
			try {
				File file = new File(path);
				scanner = new Scanner(file);

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] splitLine = line.split(" ");
					double[] yAxis = new double[splitLine.length];

					for (int i = 0; i < splitLine.length; i++) {
						if (!splitLine[i].equals("X"))
							yAxis[i] = Double.parseDouble(splitLine[i]);
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