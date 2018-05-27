package jMetal;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import support.ConfigXML;

/**
 * 
 * @author Ricardo,Gustavo
 *
 */
public class IntegerAlgorithmsConfig {
	
	private ConfigXML config = ConfigXML.getInstance();
	private final int maxEvaluations = config.getMaxEvaluations();
	
	private List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> integerAlgorithms = new ArrayList<>();

	
	/**
	 * This method will configure the necessary Integer algorithms to a problem chosen by the user   
	 * 
	 * @param problemList - list of problems to configure the algorithms to the problem
	 * @param algorithmsID - list of algorithms to configure
	 * @return 	list of the configured algorithms
	 * */
	public List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureIntegerAlgorithms(List<ExperimentProblem<IntegerSolution>> problemList,List<String> algorithmsID) {
		integerAlgorithms.clear();
		
		Problem<IntegerSolution> problem ;
		int numberOfVariables;
		String tag;
		
		for (int i = 0; i < problemList.size(); i++) {
			problem = problemList.get(i).getProblem(); 
			numberOfVariables = problem.getNumberOfVariables();
			tag=problemList.get(i).getTag();
			
			if(algorithmsID.contains("NSGAII"))
				buildIntegerNGASAII(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("SMSEMOA")) {
				buildIntegerSMSEMOA(problem,numberOfVariables,tag);
			}	
			
			if(algorithmsID.contains("MOCell"))
				buildIntegerMOCell(problem, numberOfVariables, tag);
			
			if(algorithmsID.contains("PAES"))
				buildIntegerPAES(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("RandomSearch"))
				buildIntegerRandomSearch(problem,tag);
		}
		return integerAlgorithms;
	}
	
	
	/**
	 * This method will build the RandomSearch algorithm with a problem and add it to the integerAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildIntegerRandomSearch(Problem<IntegerSolution> problem, String tag) {
		Algorithm<List<IntegerSolution>> algorithm = new RandomSearchBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "RandomSearch", tag));

	}
	
	/**
	 * This method will build the PAES algorithm with a problem and add it to the integerAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param numberOfVariables - the number of variables present in the problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildIntegerPAES(Problem<IntegerSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<IntegerSolution>> algorithm = new PAESBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations).setArchiveSize(100)
				.setBiSections(2)
				.setMutationOperator(new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "PAES", tag)); 	
	}
	
	/**
	 * This method will build the MOCell algorithm with a problem and add it to the integerAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param numberOfVariables - the number of variables present in the problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildIntegerMOCell(Problem<IntegerSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<IntegerSolution>> algorithm = new MOCellBuilder<>(problem,
				new IntegerSBXCrossover(0.9, 20.0), 
				new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.setMaxEvaluations(maxEvaluations)
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "MOCell", tag));    
	}
	
	/**
	 * This method will build the SMSEMOA algorithm with a problem and add it to the integerAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param numberOfVariables - the number of variables present in the problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildIntegerSMSEMOA(Problem<IntegerSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<IntegerSolution>> algorithm = new SMSEMOABuilder<>(problem,
				new IntegerSBXCrossover(0.9, 20.0),
				new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.setMaxEvaluations(maxEvaluations)
				.build();      
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "SMSEMOA", tag));
	}
	
	/**
	 * This method will build the NGASAII algorithm with a problem and add it to the integerAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param numberOfVariables - the number of variables present in the problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildIntegerNGASAII(Problem<IntegerSolution> problem, int numberOfVariables, String tag) {
		 Algorithm<List<IntegerSolution>> algorithm = new NSGAIIBuilder<>(
	              problem,
	              new IntegerSBXCrossover(0.9, 20.0),
	              new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
	              .setMaxEvaluations(maxEvaluations)
	              .setPopulationSize(100)
	              .build();
		 integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", tag));
	}
	
	
}
