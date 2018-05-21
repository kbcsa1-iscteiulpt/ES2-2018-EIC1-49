 package support;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import problem.UserProblem;
import problem.Variable;


@SuppressWarnings("serial")
public class DoubleProblemEvaluator extends AbstractDoubleProblem {
	private int counter = 0 ;
	private UserProblem problem;
	private AlgorithmsConfig algorithmsConfig = new AlgorithmsConfig();
	private String jarPath;
	

	  public DoubleProblemEvaluator( UserProblem problem,String jarPath) {
		this.problem = problem;  
		this.jarPath = jarPath;
		  
	    setNumberOfVariables(problem.getNumberVariables());
	    setNumberOfObjectives(2);
	    setName("DoubleProblem");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    List<Variable> variables = problem.getVariables() ;
	    
	    for (int i = 0; i < getNumberOfVariables(); i++) {
	    	System.out.println(Double.parseDouble(variables.get(i).getMinRange()));
	    	System.out.println(Double.parseDouble(variables.get(i).getMaxRange()));
	    	lowerLimit.add(Double.parseDouble(variables.get(i).getMinRange()));
	    	upperLimit.add(Double.parseDouble(variables.get(i).getMaxRange()));
		}
	    

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);	 
	    System.out.println(getLowerBound(0));
	    System.out.println(getUpperBound(0));
	  }

	  public void evaluate(DoubleSolution solution){
		  
		algorithmsConfig.otimizationEmails(problem,counter);
		counter ++;
		
	    String solutionString ="";
	    String evaluationResultString ="";
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      solutionString = solutionString + " " + solution.getVariableValue(i);  
	    }
	    try {
			String line;
//	    	Process p = Runtime.getRuntime().exec("java -jar /Users/gustavomorais/Downloads/Kursawe.jar" + " " + solutionString);
	    	System.out.println(jarPath);
			Process p = Runtime.getRuntime().exec("java -jar \""+ jarPath + "\" " + solutionString);
	    	BufferedReader brinput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    	while ((line = brinput.readLine()) != null) 
	    		{evaluationResultString+=line;}
	    	brinput.close();
	        p.waitFor();
	      }
	      catch (Exception err) { err.printStackTrace(); }
   		String[] individualEvaluationCriteria = evaluationResultString.split("\\s+");
	    // It is assumed that all evaluated criteria are returned in the same result string
	    for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
	    	solution.setObjective(i, Double.parseDouble(individualEvaluationCriteria[i]));
	    }	    
	  }
	}
