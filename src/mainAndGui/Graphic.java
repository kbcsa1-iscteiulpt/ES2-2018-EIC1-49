package mainAndGui;

import org.jfree.chart.ChartPanel;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;

import problem.UserProblem;
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
	
	private UserProblem problem;
	private List<double[]> xAxis = new ArrayList<double[]>();
	private Config config = Config.getInstance();
	
	private String rfPath="";
	private String rsPath="";

	
		public Graphic(UserProblem problem) {
		      super("");
		      this.problem=problem;
		      
		      switch(problem.getType()) {
		      	case DOUBLE:
		      		rfPath=config.getrfPathDouble();
		      		rsPath=config.getrsPathDouble();
		      		break;
		      	case BINARY:
		      		rfPath=config.getrfPathBinary();
		      		rsPath=config.getrsPathBinary();
		      		break;
		      	case INTEGER:
		      		rfPath=config.getrfPathInteger();
		      		rsPath=config.getrsPathInteger();
		      		break;
		      }
		      
		    readResults(rfPath);
		    readResults(rsPath);
		    setContent();
		      
		   }
		
			/**
			 * This method sets the panel content of the graphic with the results 
			 */
			public void setContent() {
				JFreeChart lineChart = ChartFactory.createLineChart(
				         "JMetal Results",
				         "","",
				         createDataset(problem),
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
		   private DefaultCategoryDataset createDataset( UserProblem problem) {
		
			   DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
			   for(int x=0; x<xAxis.size();x++) {
				   double[] yAxis = xAxis.get(x);
				   
				   for(int y=0; y<yAxis.length;y++) {
					   if(y<problem.getCriterias().size())
						   dataset.addValue(yAxis[y], "Variable "+x, problem.getCriterias().get(y).getName());
					   else
						   dataset.addValue(yAxis[y], "Variable "+x, "Criteria "+y);
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
				
				if(Paths.get(path).toFile().exists()) {
				try{
							File file = new File(path);
						scanner = new Scanner(file);
						
						while(scanner.hasNextLine()){
							String line = scanner.nextLine();
							String[] splitLine = line.split(" ");
							double[] yAxis = new double[splitLine.length];
							
							for(int i=0;i<splitLine.length;i++) {
								if(!splitLine[i].equals("X"))
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
		}


