package classes;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

/**
 * Jmetal class to evaluate criteria with Double arguments
 * 
 * @author Kevin Corrales nº 73529
 *
 */
public class EvaluateDouble extends AbstractDoubleProblem{


		  public EvaluateDouble() {
		    this(335);
		  }

		  public EvaluateDouble(Integer numberOfVariables) {
		    setNumberOfVariables(numberOfVariables);
		    setNumberOfObjectives(2);
		    setName("AntiSpamFilterProblem");

		    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
		    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

		    for (int i = 0; i < getNumberOfVariables(); i++) {
		      lowerLimit.add(-5.0);
		      upperLimit.add(5.0);
		    }

		    setLowerLimit(lowerLimit);
		    setUpperLimit(upperLimit);
		  }
		  
		  
		  public void evaluate(DoubleSolution solution){
		    double aux, xi, xj;
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
