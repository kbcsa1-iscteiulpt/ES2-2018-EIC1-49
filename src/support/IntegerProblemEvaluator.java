package support;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;

import problem.UserProblem;
import problem.Variable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* Implementa��o de um problema do tipo Integer que executa o .jar externo
   NMMin.jar e pode ser usado como um dos problema de teste indicados 
   no encunciado do trabalho */

@SuppressWarnings("serial")
public class IntegerProblemEvaluator extends AbstractIntegerProblem {
	
	private int counter = 0 ;
	private UserProblem problem;
	private AlgorithmsConfig algorithmsConfig = new AlgorithmsConfig();
	

	  public IntegerProblemEvaluator(UserProblem problem) throws JMetalException {
		this.problem= problem;  
		  
	    setNumberOfVariables(problem.getNumberVariables());
	    setNumberOfObjectives(2);
	    setName("IntegerProblem");

	    List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

 List<Variable> variables = problem.getVariables() ;
	    
	    for (int i = 0; i < variables.size(); i++) {
	    	lowerLimit.add(Integer.parseInt(variables.get(i).getMin()));
	    	upperLimit.add(Integer.parseInt(variables.get(i).getMax()));
		}
	    

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);

	  }

	  public void evaluate(IntegerSolution solution){
		  
		algorithmsConfig.otimizationEmails(problem,counter);
		counter ++;
		  
	    String solutionString ="";
	    String evaluationResultString ="";
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      solutionString = solutionString + " " + solution.getVariableValue(i);  
	    }
	    try {
			String line;
	    	Process p = Runtime.getRuntime().exec("java -jar /Users/gustavomorais/Downloads/NMMin.jar" + " " + solutionString);
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
		    solution.setObjective(i, Integer.parseInt(individualEvaluationCriteria[i]));    
	    }	    
	  }	  
	}
