package mainAndGui;

import org.jfree.chart.ChartPanel;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Graph extends  ApplicationFrame {
		
	private final double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
	private final double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
	private List<double[]> xAxis = new ArrayList<double[]>();
	private final String path="./src/files/exemplo.txt";

		public Graph(  ) {
		      super("");
		      readResults();
		      JFreeChart lineChart = ChartFactory.createLineChart(
		         "JMetal Results",
		         "","",
		         createDataset(),
		         PlotOrientation.VERTICAL,
		         true,true,false);
		         
		      ChartPanel chartPanel = new ChartPanel( lineChart );
		      chartPanel.setPreferredSize( new java.awt.Dimension((int) width ,(int) height) );
		      setContentPane( chartPanel );
		   }

		   private DefaultCategoryDataset createDataset( ) {
		
			   DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
			   for(int x=0; x<xAxis.size();x++) {
				   double[] yAxis = xAxis.get(x);
				   
				   for(int y=0; y<yAxis.length;y++) {
					   dataset.addValue(yAxis[y], "Variable "+x, "Criteria " +y);
				   }
			   }
			   
		      return dataset;
		   }
		
		 		   
		   public void readResults() {
				Scanner scanner = null;
				try{
					scanner = new Scanner(new File(path));
					
					while(scanner.hasNextLine()){
						String line = scanner.nextLine();
						String[] splitLine = line.split(" ");
						double[] yAxis = new double[splitLine.length];
						
						for(int i=0;i<splitLine.length;i++) {
							yAxis[i] = Double.parseDouble(splitLine[i]);
						}
						xAxis.add(yAxis);
						
					}
				}catch(FileNotFoundException e){
						e.printStackTrace();
				}finally{
						scanner.close();
				}
			}
		}


