package support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ComputeQualityIndicators;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.component.GenerateBoxplotsWithR;
import org.uma.jmetal.util.experiment.component.GenerateLatexTablesWithStatistics;
import org.uma.jmetal.util.experiment.component.GenerateReferenceParetoSetAndFrontFromDoubleSolutions;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import problem.UserProblem;

public class DoubleExperiment {
  private static final int INDEPENDENT_RUNS = 2;
  private static DoubleAlgorithmsConfig algorithmConf = new DoubleAlgorithmsConfig();
  private static AlgorithmsUtils algorithmUtils = new AlgorithmsUtils();
  private ConfigXML config = ConfigXML.getInstance();

  public DoubleExperiment(UserProblem problem ,List<String> selectedAlgorithms  , String jarPath) throws IOException {
	
    String experimentBaseDirectory = "experimentBaseDirectory";
    List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
    problemList.add(new ExperimentProblem<>(new DoubleProblemEvaluator(problem,jarPath)));

    List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = algorithmConf.configureDoubleAlgorithms(problemList,selectedAlgorithms);
    		
    
    System.out.println(experimentBaseDirectory+"/"+config.getResultsPathDouble());
    Experiment<DoubleSolution, List<DoubleSolution>> experiment =
        new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>("ProblemResultsDOUBLE")
            .setAlgorithmList(algorithmList)
            .setProblemList(problemList)
            .setExperimentBaseDirectory(experimentBaseDirectory)
            .setOutputParetoFrontFileName("FUN")
            .setOutputParetoSetFileName("VAR")
            .setReferenceFrontDirectory(experimentBaseDirectory+"/"+config.getResultsPathDouble())
            .setIndicatorList(Arrays.asList(new PISAHypervolume<DoubleSolution>()))
            .setIndependentRuns(INDEPENDENT_RUNS)
            .setNumberOfCores(8)
            .build();
    new ExecuteAlgorithms<>(experiment).run();
    new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
    new ComputeQualityIndicators<>(experiment).run() ;
    new GenerateLatexTablesWithStatistics(experiment).run() ;
    new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run() ;
    
    File dir = new File("./experimentBaseDirectory/referenceFronts");
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (File file : directoryListing) {
    	  if(file.getName().contains("Double")&& file.getName().endsWith(".rs")) {
    		  algorithmUtils.applyRestrictions(problem.getVariables(), "./experimentBaseDirectory/referenceFronts/" + file.getName());
    	  }
      }
    } 
  }


}
