package problem;

import java.util.BitSet;

/**
 * Class that represents a variable of decision contains: - name of the variable
 * - minimum range of the variable value - maximum range of the variable value -
 * restricted values of the variable
 * 
 * @author Kevin Corrales nr 73529
 *
 */
public class Variable {
	private String name;
	private String minRange; 
	private String maxRange;
	private String restriction;

	private BitSet bits = new BitSet();
	private String binaryValue;

	public Variable(String name, String minRange, String maxRange, String restriction) {
		super();
		this.name = name;
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.restriction = restriction;
	}

	public Variable(String name, String binaryValue) {
		this.name = name;
		this.binaryValue = binaryValue;
	}

	public BitSet getBits() {
		return bits;
	}

	public String getName() {
		return name;
	}
	
	public String getBinaryValue() {
		return binaryValue;
	}

	public String getMinRange() {
		return minRange;
	}

	public String getMaxRange() {
		return maxRange;
	}

	public String getRestriction() {
		return restriction;
	}

	public String toString() {
		if (minRange == null) {
			return "Variable name:" + getName() + System.getProperty("line.separator");
		} else {
			return "Variable name:" + getName() + ";Minimum range:" + getMinRange() + ";Maximum range:" + getMaxRange()
					+ ";Restrictions:" + getRestriction().replace(";", ":") + System.getProperty("line.separator");
		}
	}

}
