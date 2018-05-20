package support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

import problem.UserProblem;
import problem.Variable;

/**
 * This class has every configuration needed for any algorithm 
 * 
 * @author Ricardo Santos n 72973 e Gustavo Morais n 73036
 *
 */
public class AlgorithmsConfig {

	private static final int maxEvaluations = 250;
	
	private List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> doubleAlgorithms = new ArrayList<>();
	private List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> integerAlgorithms = new ArrayList<>();
	private List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> binaryAlgorithms = new ArrayList<>();
	
	public AlgorithmsConfig() {}
	
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
			
			if(algorithmsID.contains("NGASAII")) {
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
			
			if(algorithmsID.contains("NGASAII"))
				buildIntegerNGASAII(problem,numberOfVariables,tag);
			
			if(algorithmsID.contains("SMSEMOA")) {
				System.out.println("in");
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
	
	/**
	 * This method will build the RandomSearch algorithm with a problem and add it to the integerAlgorithms list   
	 * 
	 * @param problem - problem to use in the builder
	 * @param tag - tag to use in the builder
	 * 
	 * */
	private void buildIntegerRandomSearch(Problem<IntegerSolution> problem, String tag) {
		Algorithm<List<IntegerSolution>> algorithm5 = new RandomSearchBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations)
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm5, "RandomSearch", tag));

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
		Algorithm<List<IntegerSolution>> algorithm4 = new PAESBuilder<>(problem)
				.setMaxEvaluations(maxEvaluations).setArchiveSize(100)
				.setBiSections(2)
				.setMutationOperator(new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm4, "PAES", tag)); 	
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
		Algorithm<List<IntegerSolution>> algorithm3 = new MOCellBuilder<>(problem,
				new IntegerSBXCrossover(0.9, 20.0), 
				new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.setMaxEvaluations(maxEvaluations)
				.build();
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", tag));    
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
		Algorithm<List<IntegerSolution>> algorithm2 = new SMSEMOABuilder<>(problem,
				new IntegerSBXCrossover(0.9, 20.0),
				new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
				.setMaxEvaluations(maxEvaluations)
				.build();      
		integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", tag));
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
		 Algorithm<List<IntegerSolution>> algorithm1 = new NSGAIIBuilder<>(
	              problem,
	              new IntegerSBXCrossover(0.9, 20.0),
	              new IntegerPolynomialMutation(1/numberOfVariables, 20.0))
	              .setMaxEvaluations(maxEvaluations)
	              .setPopulationSize(100)
	              .build();
		 integerAlgorithms.add(new ExperimentAlgorithm<>(algorithm1, "NSGAII", tag));
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
	
	/**
	 * This method will write a configuration of algorithms in a file so the users can just read the algorithms to use in a experiment    
	 * 
	 * @param algorithmIDs - algorithms to include in the conf file 
	 * @param fileName - name of the file to write the automatic conf
	 * 
	 * */
	public void writeAutomaticConfig(List<String> algorithmIDs, String fileName) {
		String userHomeFolder = System.getProperty("user.home");
		File textFile = new File(userHomeFolder, fileName);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
			
			for(int i=0; i<algorithmIDs.size();i++) {
				out.write(algorithmIDs.get(i)+";");
			}	

			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will read a configuration of algorithms in a file     
	 * 
	 * @param file - file from wic to read the algorithms
	 * 
	 * */
	public List<String> readAutomaticConfi(File file) {
		Scanner scanner = null;
		List<String> algorithmIDs = new ArrayList<String>();
		try {
			scanner = new Scanner(file);
			String line="";
			while(scanner.hasNext()) {
				line = scanner.nextLine();
				String[] Tokens = line.split("/");
				
				for(int i=0;i<Tokens.length;i++) {
					algorithmIDs.add(Tokens[i]);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			scanner.close();
		}
		return algorithmIDs;
	}
	
	
	public void checkDoubleRestrictions(){
		for (int i = 0; i < fileVector.length; i++) {
			for (int j = 0; j < restrictions.get(i).length; j++) {
//				UNIQ
				if(Double.parseDouble(fileVector[i]) == Double.parseDouble(restrictions.get(i)[j]) ){
					fileVector[i]="x";
				}
//					UNIQ
			}
		}
	}
	
	
//	TODO
	public void applyRestrictions(List<Variable> variables ,String path) {
		List<String[]> restrictions = new ArrayList<String[]>();
		List<String[]> fileOutputVector = new ArrayList<String[]>();
		String[] fileVector = new String[0];
		String fileOutput ="";
		
		for (int i = 0; i < variables.size(); i++) {
			restrictions.add(variables.get(i).getRestriction().split(";"));
		}
		
		Scanner in;
		try {
//			in = new Scanner(new FileReader("../../experimentBaseDirectory/referenceFronts/DoubleProblem.rs"));
			in = new Scanner(new FileReader(path));
			StringBuilder sb = new StringBuilder();
			
			while(in.hasNext()) {
				fileVector = in.next().split(" ");
				for (int i = 0; i < fileVector.length; i++) {
					for (int j = 0; j < restrictions.get(i).length; j++) {
//						UNIQ
						if(Double.parseDouble(fileVector[i]) == Double.parseDouble(restrictions.get(i)[j]) ){
							fileVector[i]="x";
						}
//							UNIQ
					}
				}
				fileOutputVector.add(fileVector);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Problem rs file not found");
		}
		

		for (int i = 0; i < fileOutputVector.size(); i++) {
			for (int j = 0; j < fileOutputVector.get(i).length; j++) {
				fileOutput +=  fileOutputVector.get(i)[j] ;
			}
			fileOutput += "\n";
		}
		
		try {
//			UNIQ
			BufferedWriter writer = new BufferedWriter(new FileWriter("./experimentBaseDirectory/referenceFronts/DoubleProblem.rs"));
//			UNIQ
			writer.write(fileOutput);
			writer.close();
		} catch (IOException e) {
			System.out.println("Problem rs file not found");
		}
	}
	
	
}
