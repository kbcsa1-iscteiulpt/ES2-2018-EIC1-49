package mainAndGui;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

import problem.Time;
import problem.Type;
import problem.UserProblem;
import problem.Variable;
import support.Config;
import support.XMLEditor;

public class Main {

	public static void main(String[] args) {
	//	Config config = new Config();
		//new Interface(config.getEmailAdmin());
		
		List<Variable> l = new ArrayList<Variable>();
		Variable v1= new Variable("Variable 1","-1","5","0");
		Variable v2= new Variable("Variable 2","0.5","2.5","0;1.0");
		l.add(v1);l.add(v2);
		
		UserProblem problem = new UserProblem("problemName","problemDescription","email",Type.DOUBLE,
				new Time(2,2,2),
				new Time(3,3,3),
				"decisionVariablesGroupName",2,l);
		
		Graphic chart = new Graphic(problem);
		chart.readResults("./src/jUnitTests/graphic.rs");
		chart.setContent();
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		fail("Not yet implemented");
	}

}
