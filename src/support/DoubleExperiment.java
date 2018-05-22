package support;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
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

public class DoubleExperiment {
  private static final int INDEPENDENT_RUNS = 2;
  private static AlgorithmsConfig algorithmConf = new AlgorithmsConfig();

  public DoubleExperiment(UserProblem problem ,List<String> selectedAlgorithms  , String jarPath) throws IOException {
	
    String experimentBaseDirectory = "experimentBaseDirectory"; 
    List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
    problemList.add(new ExperimentProblem<>(new DoubleProblemEvaluator(problem,jarPath)));

    List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = algorithmConf.configureDoubleAlgorithms(problemList,selectedAlgorithms);
    		
    
    
    Experiment<DoubleSolution, List<DoubleSolution>> experiment =
        new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>("ProblemResultsDOUBLE")
            .setAlgorithmList(algorithmList)
            .setProblemList(problemList)
            .setExperimentBaseDirectory(experimentBaseDirectory)
            .setOutputParetoFrontFileName("FUN")
            .setOutputParetoSetFileName("VAR")
            .setReferenceFrontDirectory(experimentBaseDirectory+"/referenceFronts")
            .setIndicatorList(Arrays.asList(new PISAHypervolume<DoubleSolution>()))
            .setIndependentRuns(INDEPENDENT_RUNS)
            .setNumberOfCores(8)
            .build();
    
    new ExecuteAlgorithms<>(experiment).run();
    new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
    new ComputeQualityIndicators<>(experiment).run() ;
    new GenerateLatexTablesWithStatistics(experiment).run() ;
    new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run() ;
    
//    algorithmConf.applyRestrictions(problem.getVariables(), "../../experimentBaseDirectory/referenceFronts/DoubleProblem.rs");
    
  }


}
