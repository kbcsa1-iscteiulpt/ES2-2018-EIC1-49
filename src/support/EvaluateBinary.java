package support;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.binarySet.BinarySet;

import problem.Problem;

/**
 * Jmetal class to evaluate criteria with Binary arguments
 * 
 * @author Kevin Corrales nº 73529 ; Ricardo Santos nº 72973
 *
 */
public class EvaluateBinary extends AbstractBinaryProblem{

	private Problem problem;
	
	 public void setProblem(Problem problem) {
		  this.problem=problem;
	  }

		  public EvaluateBinary() {
		    this((byte) 335);
		  }

		  public EvaluateBinary(byte numberOfVariables) {
			setNumberOfVariables(numberOfVariables);
		    setNumberOfObjectives(2);
		    setName("EvaluateBinary");

		    getBitsPerVariable(numberOfVariables);

		  }
		  
		  
		  public void evaluate(BinarySolution solution){
		    BinarySet[] fx = new BinarySet[getNumberOfObjectives()];
		    BinarySet[] x = new BinarySet[getNumberOfVariables()];
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

		@Override
		protected int getBitsPerVariable(int arg0) {
			// TODO Auto-generated method stub
			
			return Integer.toBinaryString(arg0).length();
		}
		
}
