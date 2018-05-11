package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import problem.UserProblem;
import problem.Time;
import problem.Variable;
import support.Config;
import support.XML_Editor;

/**
 * JUnit test cases for XML_Editor
 * 
 * @author Kevin Corrales n� 73529
 *
 */
public class XML_EditorTest {
	private XML_Editor xml = new XML_Editor();
	
	
	/**
	 * Test cases for writing and reading a xml file (problem and config)
	 */
	@Test
	public void writeReadTest() {
		List<Variable> l = new ArrayList<Variable>();
		Variable v1= new Variable("Variable 1","int","-1","5","0");
		Variable v2= new Variable("Variable 2","double","0.5","2.5","0;1.0");
		l.add(v1);l.add(v2);
		
		UserProblem p = new UserProblem("problemName","problemDescription","email",
				new Time(2,2,2),
				new Time(3,3,3),
				"decisionVariablesGroupName",2,l);
		
		xml.write("src/jUnitTests/test.xml", p);
		
		UserProblem problem = xml.read("src/jUnitTests/test.xml");
		
		assertTrue(p.getDescription().equals(problem.getDescription()));
		

		
		
	}
	
	@Test
	public void readConfigTest() {

		Config conf = xml.readConfig();
		assertTrue(!conf.getEmailAdmin().equals(""));
	}
	

}
