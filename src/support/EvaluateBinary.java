package support;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.binarySet.BinarySet;

import problem.Criteria;
import problem.UserProblem;
import problem.Variable;

/**
 * Jmetal class to evaluate criteria with Binary arguments
 * 
 * @author Kevin Corrales n� 73529 ; Ricardo Santos n� 72973
 *
 */
public class EvaluateBinary extends AbstractBinaryProblem{

	private List<Variable> variables = new ArrayList<Variable>();
	private List<Criteria> criterias = new ArrayList<Criteria>();
	
	 public EvaluateBinary(byte numberOfVariables,UserProblem problem) {
		
		 
		 	setNumberOfVariables(numberOfVariables);
		    setNumberOfObjectives(2);
		    setName("EvaluateBinary");

		    getBitsPerVariable(numberOfVariables);

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
