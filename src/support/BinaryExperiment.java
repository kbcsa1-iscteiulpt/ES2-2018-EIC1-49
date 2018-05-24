package support;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import problem.UserProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryExperiment {
  private static final int INDEPENDENT_RUNS = 2;
  private static AlgorithmsConfig algorithmConf = new AlgorithmsConfig();
  private Config config = Config.getInstance();
  
  public BinaryExperiment(UserProblem problem, List<String> selectedAlgorithms  ,String jarPath) throws IOException {
	  
    String experimentBaseDirectory = config.getExperimentBaseDirectory(); 

    List<ExperimentProblem<BinarySolution>> problemList = new ArrayList<>();
    System.out.println("nº of bits: "+ problem.getVariables().get(0).getBits().length());
    problemList.add(new ExperimentProblem<>(new BinaryProblemEvaluator(problem, problem.getVariables().get(0).getBits().length(), jarPath)));

    List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithmList = algorithmConf.configureBinaryAlgorithms(problemList,selectedAlgorithms);


    Experiment<BinarySolution, List<BinarySolution>> experiment =
        new ExperimentBuilder<BinarySolution, List<BinarySolution>>("ProblemResultsBINARY")
            .setAlgorithmList(algorithmList)
            .setProblemList(problemList)
            .setExperimentBaseDirectory(experimentBaseDirectory)
            .setOutputParetoFrontFileName("FUN")
            .setOutputParetoSetFileName("VAR")
            .setReferenceFrontDirectory(experimentBaseDirectory+"/"+config.getResultsPathBinary())
            .setIndicatorList(Arrays.asList(new PISAHypervolume<BinarySolution>()))
            .setIndependentRuns(INDEPENDENT_RUNS)
            .setNumberOfCores(8)
            .build();

    new ExecuteAlgorithms<>(experiment).run();
    new GenerateReferenceParetoFront(experiment).run();
    new ComputeQualityIndicators<>(experiment).run() ;
    new GenerateLatexTablesWithStatistics(experiment).run() ;
    new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run() ;
    
  }

 

}
