package support;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.IntegerSolution;
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

public class IntegerExperiment {
  private static final int INDEPENDENT_RUNS = 2;
  private static AlgorithmsConfig algorithmConf = new AlgorithmsConfig();
  private UserProblem problem;
  
  public IntegerExperiment(UserProblem problem) throws IOException {
	  
	this.problem=problem;
    String experimentBaseDirectory = "experimentBaseDirectory";

    List<String> selectedAlgorithms = new ArrayList<String>();
    selectedAlgorithms.add("SMSEMOA");
    List<ExperimentProblem<IntegerSolution>> problemList = new ArrayList<>();
    problemList.add(new ExperimentProblem<>(new IntegerProblemEvaluator(problem.getNumberVariables())));

    List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithmList = algorithmConf.configureIntegerAlgorithms(problemList,selectedAlgorithms);

    Experiment<IntegerSolution, List<IntegerSolution>> experiment =
        new ExperimentBuilder<IntegerSolution, List<IntegerSolution>>("ExperimentsIntegerExternalViaJAR")
            .setAlgorithmList(algorithmList)
            .setProblemList(problemList)
            .setExperimentBaseDirectory(experimentBaseDirectory)
            .setOutputParetoFrontFileName("FUN")
            .setOutputParetoSetFileName("VAR")
            .setReferenceFrontDirectory(experimentBaseDirectory+"/referenceFronts")
            .setIndicatorList(Arrays.asList(new PISAHypervolume<IntegerSolution>()))
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