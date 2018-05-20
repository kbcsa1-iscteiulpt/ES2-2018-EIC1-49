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
	

	  public DoubleProblemEvaluator( UserProblem problem) {
		this.problem = problem;  
		  
	    setNumberOfVariables(problem.getNumberVariables());
	    setNumberOfObjectives(2);
	    setName("DoubleProblem");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    List<Variable> variables = problem.getVariables() ;
	    
	    for (int i = 0; i < variables.size(); i++) {
	    	lowerLimit.add(Double.parseDouble(variables.get(i).getMin()));
	    	upperLimit.add(Double.parseDouble(variables.get(i).getMax()));
		}
	    
	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-5.0);
	      upperLimit.add(5.0);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);	    	    
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
	    	Process p = Runtime.getRuntime().exec("java -jar /Users/gustavomorais/Downloads/Kursawe.jar" + " " + solutionString);
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
