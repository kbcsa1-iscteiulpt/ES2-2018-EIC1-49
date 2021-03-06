package jMetal;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import problem.UserProblem;
import support.ConfigXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Ricardo,Gustavo
 *
 */
public class BinaryExperiment {
  private static final int INDEPENDENT_RUNS = 2;
  private static BinaryAlgorithmsConfig algorithmConf = new BinaryAlgorithmsConfig();
  private ConfigXML config = ConfigXML.getInstance();
  
  public BinaryExperiment(UserProblem problem, List<String> selectedAlgorithms  ,String jarPath) throws IOException {
	  
    String experimentBaseDirectory = "experimentBaseDirectory"; 

    List<ExperimentProblem<BinarySolution>> problemList = new ArrayList<>();
    problemList.add(new ExperimentProblem<>(new BinaryProblemEvaluator(problem, problem.getVariables().get(0).getBits().length(), jarPath,selectedAlgorithms.size())));

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
