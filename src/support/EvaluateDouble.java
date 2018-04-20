package support;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import problem.Criteria;
import problem.Problem;
import problem.Variable;

/**
 * Jmetal class to evaluate criteria with Double arguments
 * 
 * @author Kevin Corrales nº 73529
 *
 */
public class EvaluateDouble extends AbstractDoubleProblem{

	
	private List<Variable> variables = new ArrayList<Variable>();
	private List<Criteria> criterias = new ArrayList<Criteria>();
	
	 public EvaluateDouble(Integer numberOfVariables,Problem problem) {
		 for(Variable var: problem.getVariables()) {
			 if(var.getType().toLowerCase().equals("double"))
				 variables.add(var);
		 }
		 for(Criteria crit: problem.getCriterias()) {
			 if(crit.getType().toLowerCase().equals("double"))
				 criterias.add(crit);
		 }
		 
		 setNumberOfVariables(numberOfVariables);
		    setNumberOfObjectives(2);
		    setName("EvaluateDouble");

		    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
		    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

		    for (int i = 0; i < getNumberOfVariables(); i++) {
		      lowerLimit.add(-5.0);
		      upperLimit.add(5.0);
		    }

		    setLowerLimit(lowerLimit);
		    setUpperLimit(upperLimit);
		 
	  }

		  public EvaluateDouble(Integer numberOfVariables) {
		    setNumberOfVariables(numberOfVariables);
		    setNumberOfObjectives(2);
		    setName("EvaluateDouble");

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
		    

		    for(int i=0;i<criterias.size();i++) {
			    Process proc;
				try {
					proc = Runtime.getRuntime().exec("java -jar "+criterias.get(i).getPath());
					InputStream in = proc.getInputStream();
					solution.setObjective(i,  Double.parseDouble(in.toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			    
			    
			   
		    }
		    
		    
		  }
		
}
