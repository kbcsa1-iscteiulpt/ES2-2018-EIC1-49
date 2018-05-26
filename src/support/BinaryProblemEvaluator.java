package support;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;

import problem.UserProblem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.BitSet;


@SuppressWarnings("serial")
public class BinaryProblemEvaluator extends AbstractBinaryProblem {
	  private int bits ;
	  
	  private int counter = 0 ;
		private UserProblem problem;
		private AlgorithmUtils algorithmsUtils = new AlgorithmUtils();
		private String jarPath;
		private int numOfAlgorithms;
	  

	  public BinaryProblemEvaluator(UserProblem problem, int numberOfBits , String jarPath, int numOfAlgorithms) throws JMetalException {
		this.problem = problem;  
		this.jarPath = jarPath;
		this.numOfAlgorithms = numOfAlgorithms;
		  
		setNumberOfVariables(numberOfBits);
	    setNumberOfObjectives(2);
	    setName("BinaryProblem");
	    bits = numberOfBits ;

	  }
	  
	  @Override
	  protected int getBitsPerVariable(int index) {
//	  	if (index != 0) {
//	  		throw new JMetalException("Problem BinaryProblem has only a variable. Index = " + index) ;
//	  	}
	  	return bits ;
	  }

	  @Override
	  public BinarySolution createSolution() {
	    return new DefaultBinarySolution(this) ;
	  }

	  @Override
	  public void evaluate(BinarySolution solution){
		  
		  counter ++;
		algorithmsUtils.otimizationEmails(problem,counter,numOfAlgorithms);

	    String solutionString ="";
	    String evaluationResultString ="";
	    BitSet bitset = solution.getVariableValue(0) ;
	    solutionString = bitset.toString();
	    
	    try {
			String line;
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
