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
		Config config = new Config();
		new Interface(config.getEmailAdmin());
		
	
	}

}
