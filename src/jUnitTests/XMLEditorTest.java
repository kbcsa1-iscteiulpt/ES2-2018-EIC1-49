package jUnitTests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import problem.UserProblem;
import problem.Time;
import problem.Type;
import problem.Variable;
import support.ConfigXML;
import support.XMLEditor;

/**
 * JUnit test cases for XML_Editor
 * 
 * @author Kevin Corrales n� 73529
 *
 */
public class XMLEditorTest {
	private XMLEditor xml = new XMLEditor();

	/**
	 * Test cases for writing and reading a xml file (problem and config)
	 */
	@Test
	public void writeReadTest() {
		List<Variable> l = new ArrayList<Variable>();
		Variable v1 = new Variable("Variable 1", "-1", "5", "0");
		Variable v2 = new Variable("Variable 2", "0.5", "2.5", "0;1.0");
		l.add(v1);
		l.add(v2);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date current = new Date();  
		UserProblem p = new UserProblem("problemName", "problemDescription", "email",dateFormat.format(current)
				, new Time(2, 2, 2),new Time(3, 3, 3), Type.INTEGER, "decisionVariablesGroupName", 2, l);

		xml.write("src/jUnitTests/test.xml", p);

		UserProblem problem = xml.read("src/jUnitTests/test.xml");

		assertTrue(p.getDescription().equals(problem.getDescription()));

	}

	@Test
	public void readConfigTest() {

		ConfigXML conf = new ConfigXML();
		assertTrue(!conf.getEmailAdmin().equals(""));
	}

}
