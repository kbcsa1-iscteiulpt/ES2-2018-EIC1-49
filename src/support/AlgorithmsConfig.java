package support;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

public class AlgorithmsConfig {

	private static final int INDEPENDENT_RUNS = 2;
	private static final int maxEvaluations = 250;
	private List<String> algorithmsID = new ArrayList<String>();
	private List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();
	
	public AlgorithmsConfig(List<String> algorithmsID) {
		this.algorithmsID=algorithmsID;
	}
	
	public void configureAlgorithms(List<ExperimentProblem<DoubleSolution>> problemList) {
		Problem problem ;
		int numberOfVariables;
		String tag;
		
		for (int i = 0; i < problemList.size(); i++) {
			problem = problemList.get(i).getProblem();
			numberOfVariables = problem.getNumberOfVariables();
			tag=problemList.get(i).getTag();
			
			buildNSGAII(problem,numberOfVariables,tag);
			buildSMSEMOA(problem,numberOfVariables,tag);
		}
	}
	
	private void buildSMSEMOA(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
	 	Algorithm<List<DoubleSolution>> algorithm = new SMSEMOABuilder<>(problem,
	 			new SBXCrossover(1.0, 5), new PolynomialMutation(1.0 / numberOfVariables, 10.0))
	 			.setMaxEvaluations(maxEvaluations)
	 			.build();      
		algorithms.add(new ExperimentAlgorithm<>(algorithm, "SMSEMOA", tag));
	}

	private void buildNSGAII(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(
	              problem,
	              new SBXCrossover(1.0, 5),
	              new PolynomialMutation(1.0 / numberOfVariables, 10.0))
	              .setMaxEvaluations(maxEvaluations)
	              .setPopulationSize(100)
	              .build();
	      algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", tag));
	}

	
	
	public void algorithmGDE3(List<ExperimentProblem<DoubleSolution>> problemList){
			
	    for (int i = 0; i < problemList.size(); i++) {
	    		Algorithm<List<DoubleSolution>> algorithm3 = new GDE3Builder((DoubleProblem) problemList.get(i).getProblem()).setMaxEvaluations(maxEvaluations).build();
	        algorithms.add(new ExperimentAlgorithm<>(algorithm3, "GDE3", problemList.get(i).getTag()));
	    }   
	}
	
}
