package support;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.mochc.MOCHCBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder.Variant;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.impl.crossover.HUXCrossover;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.RandomSelection;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;



public class AlgorithmsConfig {

	private static final int INDEPENDENT_RUNS = 2;
	private static final int maxEvaluations = 250;
	
	private List<String> algorithmsID = new ArrayList<String>();
	private List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> doubleAlgorithms = new ArrayList<>();
	private List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> integerAlgorithms = new ArrayList<>();
	private List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> binaryAlgorithms = new ArrayList<>();
	
	public AlgorithmsConfig(List<String> algorithmsID) {
		this.algorithmsID=algorithmsID;
	}
	public AlgorithmsConfig() {}
	
	public List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureDoubleAlgorithms(List<ExperimentProblem<DoubleSolution>> problemList) {
		doubleAlgorithms.clear();
		
		Problem<DoubleSolution> problem ;
		int numberOfVariables;
		String tag;
		
		for (int i = 0; i < problemList.size(); i++) {
			problem = problemList.get(i).getProblem();
			numberOfVariables = problem.getNumberOfVariables();
			tag=problemList.get(i).getTag();
			
			if(algorithmsID.contains("NSGAII"))
				buildDoubleNSGAII(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("SMSEMOA"))
				buildDoubleSMSEMOA(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("GDE3"))
				buildDoubleGDE3(problem,tag);
			
			if(algorithmsID.contains("IBEA"))
				buildDoubleIBEA(problem,tag);
			
			if(algorithmsID.contains("MOCell"))
				buildDoubleMOCell(problem, numberOfVariables, tag);
			
			if(algorithmsID.contains("MOEAD"))
				buildDoubleMOEAD(problem,tag);
			
			if(algorithmsID.contains("PAES"))
				buildDoublePAES(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("RandomSearch"))
				buildDoubleRandomSearch(problem,tag);
		}
		return doubleAlgorithms;
	}
	
	public List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureIntegerAlgorithms(List<ExperimentProblem<IntegerSolution>> problemList) {
		integerAlgorithms.clear();
		
		Problem<IntegerSolution> problem ;
		int numberOfVariables;
		String tag;
		
		for (int i = 0; i < problemList.size(); i++) {
			problem = problemList.get(i).getProblem();
			numberOfVariables = problem.getNumberOfVariables();
			tag=problemList.get(i).getTag();
			
			if(algorithmsID.contains("NSGAII"))
				buildIntegerNSGAII(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("SMSEMOA"))
				buildIntegerSMSEMOA(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("MOCell"))
				buildIntegerMOCell(problem, numberOfVariables, tag);
			
			if(algorithmsID.contains("PAES"))
				buildIntegerPAES(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("RandomSearch"))
				buildIntegerRandomSearch(problem,tag);
		}
		return integerAlgorithms;
	}
	
	public List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> configureBinaryAlgorithms(List<ExperimentProblem<BinarySolution>> problemList) {
		binaryAlgorithms.clear();
		
		Problem<BinarySolution> problem ;
		int numberOfVariables;
		String tag;
		
		for (int i = 0; i < problemList.size(); i++) {
			problem = problemList.get(i).getProblem();
			numberOfVariables = problem.getNumberOfVariables();
			tag=problemList.get(i).getTag();
			
			if(algorithmsID.contains("NSGAII"))
				buildBinaryNSGAII(problem,tag);
			
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
	
	
	
	private void buildBinarySPEA2(Problem<BinarySolution> problem, String tag) {
		Algorithm<List<BinarySolution>> algorithm7 = new SPEA2Builder<>(problem,
				new SinglePointCrossover(1.0),
				new BitFlipMutation(1.0 / ((MyProblemBinaryExternalViaJAR) problem).getNumberOfBits(0)))
				.setMaxIterations(maxEvaluations)
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm7, "SPEA2", tag));
	}
	
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
	
	private void buildBinaryRandomSearch(Problem<BinarySolution> problem, String tag) {
		Algorithm<List<BinarySolution>> algorithm6 = new RandomSearchBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm6, "RandomSearch", tag));
	}
	
	private void buildBinaryPAES(Problem<BinarySolution> problem, String tag) {
		Algorithm<List<BinarySolution>> algorithm5 = new PAESBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.setArchiveSize(100)
				.setBiSections(2)
				.setMutationOperator(new BitFlipMutation(1.0 / ((MyProblemBinaryExternalViaJAR) problem).getNumberOfBits(0)))
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm5, "PAES", tag));
	}
	
	private void buildBinaryMOCell(Problem<BinarySolution> problem,String tag) {
		Algorithm<List<BinarySolution>> algorithm3 = new MOCellBuilder<>(problem,
				new SinglePointCrossover(1.0),
				new BitFlipMutation(1.0 / ((MyProblemBinaryExternalViaJAR) problem).getNumberOfBits(0)))
				.setMaxEvaluations(maxEvaluations)
				.build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", tag));
	}
	
	private void buildBinarySMSEMOA(Problem<BinarySolution> problem,String tag) {
		Algorithm<List<BinarySolution>> algorithm2 = new SMSEMOABuilder<>(problem,
				new SinglePointCrossover(1.0), new BitFlipMutation(1.0 / ((MyProblemBinaryExternalViaJAR) problem).getNumberOfBits(0)))
				.setMaxEvaluations(maxEvaluations)
				.build();      
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", tag));
	}
	
	private void buildBinaryNSGAII(Problem<BinarySolution> problem,String tag) {
		Algorithm<List<BinarySolution>> algorithm = new NSGAIIBuilder<>(
	              problem,
	              new SinglePointCrossover(1.0),
	              new BitFlipMutation(1.0 / ((MyProblemBinaryExternalViaJAR) problem).getNumberOfBits(0)))
	              .setMaxEvaluations(maxEvaluations)
	              .setPopulationSize(100)
	              .build();
		binaryAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", tag));
	}
	
	private void buildIntegerRandomSearch(Problem<IntegerSolution> problem, String tag) {
		Algorithm<List<IntegerSolution>> algorithm5 = new RandomSearchBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm5, "RandomSearch", tag));

	}
	
	private void buildIntegerPAES(Problem<IntegerSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<IntegerSolution>> algorithm4 = new PAESBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations).setArchiveSize(100)
				.setBiSections(2)
				.setMutationOperator(new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm4, "PAES", tag)); 	
	}
	
	
	private void buildIntegerMOCell(Problem<IntegerSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<IntegerSolution>> algorithm3 = new MOCellBuilder<>(problem,
				new IntegerSBXCrossover(0.9, 20.0), 
				new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.setMaxEvaluations(maxEvaluations)
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", tag));    
	}
	
	private void buildIntegerSMSEMOA(Problem<IntegerSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<IntegerSolution>> algorithm2 = new SMSEMOABuilder<>(problem,
				new IntegerSBXCrossover(0.9, 20.0),
				new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.setMaxEvaluations(maxEvaluations)
				.build();      
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", tag));
	}
	private void buildIntegerNSGAII(Problem<IntegerSolution> problem, int numberOfVariables, String tag) {
		 Algorithm<List<IntegerSolution>> algorithm1 = new NSGAIIBuilder<>(
	              problem,
	              new IntegerSBXCrossover(0.9, 20.0),
	              new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
	              .setMaxEvaluations(maxEvaluations)
	              .setPopulationSize(100)
	              .build();
		 integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm1, "NSGAII", tag));
	}
	private void buildDoubleRandomSearch(Problem<DoubleSolution> problem, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new RandomSearchBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "RandomSearch",tag));
	}
	
	private void buildDoublePAES(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new PAESBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.setArchiveSize(100)
				.setBiSections(2)
				.setMutationOperator(new PolynomialMutation(1.0 /numberOfVariables, 10.0))
				.build();
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "PAES", tag)); 	
	}
	
	private void buildDoubleMOEAD(Problem<DoubleSolution> problem, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new MOEADBuilder(problem,Variant.MOEAD)
				.setMaxEvaluations(maxEvaluations)
				.build();
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "MOEAD",tag));
	}
	
	private void buildDoubleMOCell(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new MOCellBuilder<>(problem,
				new SBXCrossover(1.0, 5), 
				new PolynomialMutation(1.0 / numberOfVariables, 10.0))
				.setMaxEvaluations(maxEvaluations)
				.build();
		 doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "MOCell", tag));
		
	}
	
	private void buildDoubleIBEA(Problem<DoubleSolution> problem, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new IBEABuilder(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "IBEA", tag));
	}
	
	private void buildDoubleSMSEMOA(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
	 	Algorithm<List<DoubleSolution>> algorithm = new SMSEMOABuilder<>(problem,
	 			new SBXCrossover(1.0, 5),
	 			new PolynomialMutation(1.0 / numberOfVariables, 10.0))
	 			.setMaxEvaluations(maxEvaluations)
	 			.build();      
		doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "SMSEMOA", tag));
	}

	private void buildDoubleNSGAII(Problem<DoubleSolution> problem, int numberOfVariables, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(
	              problem,
	              new SBXCrossover(1.0, 5),
	              new PolynomialMutation(1.0 / numberOfVariables, 10.0))
	              .setMaxEvaluations(maxEvaluations)
	              .setPopulationSize(100)
	              .build();
	     doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", tag));
	}

	private void buildDoubleGDE3(Problem<DoubleSolution> problem, String tag) {
		Algorithm<List<DoubleSolution>> algorithm = new GDE3Builder((DoubleProblem) problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
	    doubleAlgorithms.add(new ExperimentAlgorithm<>(algorithm, "GDE3", tag));
	}
	
	
	
	
}
