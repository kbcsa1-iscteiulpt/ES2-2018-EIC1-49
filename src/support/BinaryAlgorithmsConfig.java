package support;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.impl.crossover.HUXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.RandomSelection;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

public class BinaryAlgorithmsConfig {
	

	private Config config = Config.getInstance();
	private final int maxEvaluations = config.getMaxEvaluations();
	
	private List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> binaryAlgorithms = new ArrayList<>();
	
	/**
	 * This method will configure the necessary Binary algorithms to a problem chosen by the user   
	 * 
	 * @param problemList - list of problems to configure the algorithms to the problem
	 * @param algorithmsID - list of algorithms to configure
	 * @return 	list of the configured algorithms
	 * */
	public List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> configureBinaryAlgorithms(List<ExperimentProblem<BinarySolution>> problemList,List<String> algorithmsID) {
		binaryAlgorithms.clear();
		
		Problem<BinarySolution> problem ;
		String tag;
		
		for (int i = 0; i < problemList.size(); i++) {
			problem = problemList.get(i).getProblem();
			tag=problemList.get(i).getTag();
			
			if(algorithmsID.contains("NGASAII"))
				buildBinaryNGASAII(problem,tag);
			
			if(algorithmsID.contains("SMSEMOA"))
				buildBinarySMSEMOA(problem,tag);
			
			if(algorithmsID.contains("MOCell"))
				buildBinaryMOCell(problem, tag);
			
			if(algorithmsID.contains("PAES"))
				buildBinaryPAES(problem,tag);
			
			if(algorithmsID.contains("RandomSearch"))
				buildBinaryRandomSearch(problem,tag);
			
			if(algorithmsID.contains("MOCHC"))
				buildBinaryMOCHC(problem,tag);
			
			if(algorithmsID.contains("SPEA2"))
				buildBinarySPEA2(problem,tag);
		}
		return binaryAlgorithms;
	}
	
	
	/**
	 * This method will build the SPEA2 algorithm with a problem and add it to the binaryAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildBinarySPEA2(Problem<BinarySolution> problem, String tag) {
		Algorithm<List<BinarySolution>> algorithm7 = new SPEA2Builder<>(problem,
				new SinglePointCrossover(1.0),
				new BitFlipMutation(1.0 / ((BinaryProblemEvaluator) problem).getNumberOfBits(0)))
				.setMaxIterations(maxEvaluations)
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm7, "SPEA2", tag));
	}
	
	/**
	 * This method will build the MOCHC algorithm with a problem and add it to the binaryAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildBinaryMOCHC(Problem<BinarySolution> problem, String tag) {
		Algorithm<List<BinarySolution>> algorithm4 = new MOCHCBuilder((BinaryProblem) problem)
				.setMaxEvaluations(maxEvaluations)
				.setCrossover(new HUXCrossover(1.0))
				.setNewGenerationSelection(new RankingAndCrowdingSelection<BinarySolution>(100))
				.setCataclysmicMutation(new BitFlipMutation(0.35))
				.setParentSelection(new RandomSelection<BinarySolution>())
				.setEvaluator(new SequentialSolutionListEvaluator<BinarySolution>())
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm4, "MOCH", tag));
	}
	
	/**
	 * This method will build the RandomSearch algorithm with a problem and add it to the binaryAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildBinaryRandomSearch(Problem<BinarySolution> problem, String tag) {
		Algorithm<List<BinarySolution>> algorithm6 = new RandomSearchBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm6, "RandomSearch", tag));
	}
	
	/**
	 * This method will build the PAES algorithm with a problem and add it to the binaryAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildBinaryPAES(Problem<BinarySolution> problem, String tag) {
		Algorithm<List<BinarySolution>> algorithm5 = new PAESBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.setArchiveSize(100)
				.setBiSections(2)
				.setMutationOperator(new BitFlipMutation(1.0 / ((BinaryProblemEvaluator) problem).getNumberOfBits(0)))
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm5, "PAES", tag));
	}
	
	/**
	 * This method will build the MOCell algorithm with a problem and add it to the binaryAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildBinaryMOCell(Problem<BinarySolution> problem,String tag) {
		Algorithm<List<BinarySolution>> algorithm3 = new MOCellBuilder<>(problem,
				new SinglePointCrossover(1.0),
				new BitFlipMutation(1.0 / ((BinaryProblemEvaluator) problem).getNumberOfBits(0)))
				.setMaxEvaluations(maxEvaluations)
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", tag));
	}
	
	/**
	 * This method will build the SMSEMOA algorithm with a problem and add it to the binaryAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildBinarySMSEMOA(Problem<BinarySolution> problem,String tag) {
		Algorithm<List<BinarySolution>> algorithm2 = new SMSEMOABuilder<>(problem,
				new SinglePointCrossover(1.0), new BitFlipMutation(1.0 / ((BinaryProblemEvaluator) problem).getNumberOfBits(0)))
				.setMaxEvaluations(maxEvaluations)
				.build();      
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", tag));
	}
	
	/**
	 * This method will build the NGASAII algorithm with a problem and add it to the binaryAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildBinaryNGASAII(Problem<BinarySolution> problem,String tag) {
		Algorithm<List<BinarySolution>> algorithm = new NSGAIIBuilder<>(
	              problem,
	              new SinglePointCrossover(1.0),
	              new BitFlipMutation(1.0 / ((BinaryProblemEvaluator) problem).getNumberOfBits(0)))
	              .setMaxEvaluations(maxEvaluations)
	              .setPopulationSize(100)
	              .build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", tag));
	}
	

}
