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

import support.Config;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * This class creates a frame that shows a graphic with the values of the rf and rs files that contains
 * the JMetal results. 
 * @author Kevin Corrales nº 73529
 **/
public class Graphic extends  ApplicationFrame {
		
	private final double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
	private final double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
	private List<double[]> xAxis = new ArrayList<double[]>();
	private Config config = new Config();
	private String[] paths = new String[] {
			config.getrfPathBinary(),config.getrfPathDouble(),config.getrfPathInteger(),
			config.getrsPathBinary(),config.getrsPathDouble(),config.getrsPathInteger()};

	
		public Graphic(  ) {
		      super("");
		      
		      for(int i=0; i<paths.length; i++) {
		    	 if(paths[i]!=null)
		    		 if(!(paths[i].isEmpty() || paths[i].equals("")))
		    			 readResults(paths[i]);
		      }
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

		
		  /**
		   * 
		   * This method generates a dataset from the list that contains the results
		   * @return dataset
		   */
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
		
		 		   
		   /**
		    * This method reads JMetal results from a file
		    * @param path
		    */
		   public void readResults(String path) {
				Scanner scanner = null;
				try{
					File file = new File(path);
					if(file.exists() && !file.isDirectory()) {
					scanner = new Scanner(file);
					
					while(scanner.hasNextLine()){
						String line = scanner.nextLine();
						String[] splitLine = line.split(" ");
						double[] yAxis = new double[splitLine.length];
						
						for(int i=0;i<splitLine.length;i++) {
							yAxis[i] = Double.parseDouble(splitLine[i]);
						}
						xAxis.add(yAxis);
						
					}
					}
				}catch(FileNotFoundException e){
						e.printStackTrace();
				}finally{
						scanner.close();
				}
			}
		}


