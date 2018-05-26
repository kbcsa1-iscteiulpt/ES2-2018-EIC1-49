package jUnitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import problem.Variable;

/**
 * JUnit test cases for Variable
 * 
 * @author Gustavo Morais n73036
 *
 */

public class VariableTest {

	private Variable binaryVariable = new Variable("binaryVar","0110");
	
	@Test
	public void addBitsTest() {
		String before = binaryVariable.getBinaryValue();
		String addedBits = "11";
		
		binaryVariable.addBits(addedBits);
		
		assertTrue(binaryVariable.getBinaryValue().equals(before+addedBits));
	}

}
