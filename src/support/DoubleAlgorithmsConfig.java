package support;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder.Variant;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

public class DoubleAlgorithmsConfig {
	
	private ConfigXML config = ConfigXML.getInstance();
	private final int maxEvaluations = config.getMaxEvaluations();
	
	private List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> doubleAlgorithms = new ArrayList<>();
	
	
	/**
	 * This method will configure the necessary double algorithms to a problem chosen by the user   
	 * 
	 * @param problemList - list of problems to configure the algorithms to the problem
	 * @param algorithmsID - list of algorithms to configure
	 * @return 	list of the configured algorithms
	 * */
	public List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureDoubleAlgorithms(List<ExperimentProblem<DoubleSolution>> problemList,List<String> algorithmsID) {
		doubleAlgorithms.clear();
		
		Problem<DoubleSolution> problem ;
		int numberOfVariables;
		String tag;
		System.out.println(algorithmsID.get(0));
		for (int i = 0; i < problemList.size(); i++) {
			problem = problemList.get(i).getProblem();
			numberOfVariables = problem.getNumberOfVariables();
			tag=problemList.get(i).getTag();
			
			if(algorithmsID.contains("NSGAII")) {
				System.out.println("in");
				buildDoubleNGASAII(problem,numberOfVariables,tag);
			}
			if(algorithmsID.contains("SMSEMOA")) {
				System.out.println("in");
				buildDoubleSMSEMOA(problem,numberOfVariables,tag);
			}
			if(algorithmsID.contains("GDE3")) {
				System.out.println("in");
				buildDoubleGDE3(problem,tag);
			}
			if(algorithmsID.contains("IBEA")) {
				System.out.println("in");
				buildDoubleIBEA(problem,tag);
			}
			if(algorithmsID.contains("MOCell")) {
				System.out.println("in");
				buildDoubleMOCell(problem, numberOfVariables, tag);
			}
			if(algorithmsID.contains("MOEAD")) {
				System.out.println("in");
				buildDoubleMOEAD(problem,tag);
			}
			if(algorithmsID.contains("PAES")) {
				System.out.println("in");
				buildDoublePAES(problem,numberOfVariables,tag);
			}
			if(algorithmsID.contains("RandomSearch")) {
				System.out.println("in");
				buildDoubleRandomSearch(problem,tag);
			}	
		}
		return doubleAlgorithms;
	}
	
	
	/**
	 * This method will build the RandomSearch algorithm with a problem and add it to the doubleAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildDoubleRandomSearch(Problem<DoubleSolution> problem, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new RandomSearchBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "RandomSearch",tag));
	}
	
	/**
	 * This method will build the PAES algorithm with a problem and add it to the doubleAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param numberOfVariables - the number of variables present in the problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildDoublePAES(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new PAESBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.setArchiveSize(100)
				.setBiSections(2)
				.setMutationOperator(new PolynomialMutation(1.0 /numberOfVariables, 10.0))
				.build();
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "PAES", tag)); 	
	}
	
	/**
	 * This method will build the MOEAD algorithm with a problem and add it to the doubleAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildDoubleMOEAD(Problem<DoubleSolution> problem, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new MOEADBuilder(problem,Variant.MOEAD)
				.setMaxEvaluations(maxEvaluations)
				.build();
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "MOEAD",tag));
	}
	
	/**
	 * This method will build the MOCell algorithm with a problem and add it to the doubleAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param numberOfVariables - the number of variables present in the problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildDoubleMOCell(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new MOCellBuilder<>(problem,
				new SBXCrossover(1.0, 5), 
				new PolynomialMutation(1.0 / numberOfVariables, 10.0))
				.setMaxEvaluations(maxEvaluations)
				.build();
		 doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "MOCell", tag));
		
	}
	
	/**
	 * This method will build the IBEA algorithm with a problem and add it to the doubleAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildDoubleIBEA(Problem<DoubleSolution> problem, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new IBEABuilder(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "IBEA", tag));
	}
	
	/**
	 * This method will build the SMSEMOA algorithm with a problem and add it to the doubleAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param numberOfVariables - the number of variables present in the problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildDoubleSMSEMOA(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
	 	Algorithm<List<DoubleSolution>> algorithm = new SMSEMOABuilder<>(problem,
	 			new SBXCrossover(1.0, 5),
	 			new PolynomialMutation(1.0 / numberOfVariables, 10.0))
	 			.setMaxEvaluations(maxEvaluations)
	 			.build();      
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "SMSEMOA", tag));
	}

	/**
	 * This method will build the NGASAII algorithm with a problem and add it to the doubleAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param numberOfVariables - the number of variables present in the problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildDoubleNGASAII(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(
	              problem,
	              new SBXCrossover(1.0, 5),
	              new PolynomialMutation(1.0 / numberOfVariables, 10.0))
	              .setMaxEvaluations(maxEvaluations)
	              .setPopulationSize(100)
	              .build();
	     doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", tag));
	}

	/**
	 * This method will build the GDE3 algorithm with a problem and add it to the doubleAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildDoubleGDE3(Problem<DoubleSolution> problem, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new GDE3Builder((DoubleProblem) problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
	    doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "GDE3", tag));
	}

}
