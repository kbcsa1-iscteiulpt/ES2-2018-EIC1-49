package support;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import problem.Criteria;
import problem.UserProblem;
import problem.Variable;

/**
 * Jmetal class to evaluate criteria with Integer arguments
 * 
 * @author Kevin Corrales n� 73529
 *
 */
public class EvaluateInteger extends AbstractIntegerProblem{
			
	
	private List<Variable> variables = new ArrayList<Variable>();
	private List<Criteria> criterias = new ArrayList<Criteria>();
	
	 public EvaluateInteger(Integer numberOfVariables,UserProblem problem) {		 
		 setNumberOfVariables(numberOfVariables);
		    setNumberOfObjectives(2);
		    setName("EvaluateInteger");

		    List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
		    List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

		    for (int i = 0; i < getNumberOfVariables(); i++) {
		      lowerLimit.add(-5);
		      upperLimit.add(5);
		    }

		    setLowerLimit(lowerLimit);
		    setUpperLimit(upperLimit);
	  }

		 

		  public EvaluateInteger(Integer numberOfVariables) {
		    setNumberOfVariables(numberOfVariables);
		    setNumberOfObjectives(2);
		    setName("EvaluateInteger");

		    List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
		    List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

		    for (int i = 0; i < getNumberOfVariables(); i++) {
		      lowerLimit.add(-5);
		      upperLimit.add(5);
		    }

		    setLowerLimit(lowerLimit);
		    setUpperLimit(upperLimit);
		  }
		  
		 
		  
		  
		  public void evaluate(IntegerSolution solution){
		    double[] fx = new double[getNumberOfObjectives()];
		    double[] x = new double[getNumberOfVariables()];
		    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
		      x[i] = solution.getVariableValue(i) ;
		    }
		    
		    /**
		  		 * 
		  		 * Feedback para o jMetal
		  		 */
		    System.out.println("its in");
/*
		    List<Rule> ruleList = reader.getRulesFromFile(Interface_Window.RulePath,x);
		    
		    solution.setObjective(0,  analyser.getFPcount(reader.getEmailsFromFile(Interface_Window.SpamPath), ruleList));
		    solution.setObjective(1, analyser.getFNcount(reader.getEmailsFromFile(Interface_Window.HamPath), ruleList));
	*/
		  }
		
}
