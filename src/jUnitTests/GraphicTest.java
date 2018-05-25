package jUnitTests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.ui.RefineryUtilities;
import org.junit.Test;

import mainAndGui.Graphic;
import problem.Time;
import problem.Type;
import problem.UserProblem;
import problem.Variable;

public class GraphicTest {

	@Test
	public void startFrameTest() {
		List<Variable> l = new ArrayList<Variable>();
		Variable v1 = new Variable("Variable 1", "-1", "5", "0");
		Variable v2 = new Variable("Variable 2", "0.5", "2.5", "0;1.0");
		l.add(v1);
		l.add(v2);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
		Date current = new Date();  
		UserProblem problem = new UserProblem("problemName", "problemDescription", "email",dateFormat.format(current)
				, new Time(2, 2, 2),new Time(3, 3, 3), Type.INTEGER, "decisionVariablesGroupName", 2, l);
		Graphic chart = new Graphic(problem);

		problem.setType(Type.BINARY);
		chart = new Graphic(problem);

		problem.setType(Type.DOUBLE);
		chart = new Graphic(problem);

		chart.readResults("./src/jUnitTests/graphic.rs");
		chart.setContent();
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}

}
