package problem;

import java.util.BitSet;

/**
 * Class that represents a variable of decision contains: - name of the variable
 * - minimum range of the variable value - maximum range of the variable value -
 * restricted values of the variable
 * 
 * @author Kevin Corrales n� 73529
 *
 */
public class Variable {
	private String name;
	// Interval is String because we don't know the type of the variable yet
	private String min; // Start of the range
	private String max; // End of the range
	private String restriction;

	private BitSet bits = new BitSet();

	public Variable(String name, String min, String max, String restriction) {
		super();
		this.name = name;
		this.min = min;
		this.max = max;
		this.restriction = restriction;
	}

	public Variable(BitSet bits) {
		this.bits = bits;
	}

	public Variable(String name) {
		this.name = name;
	}

	public BitSet getBits() {
		return bits;
	}

	public String getName() {
		return name;
	}

	public String getMin() {
		return min;
	}

	public String getMax() {
		return max;
	}

	public String getRestriction() {
		return restriction;
	}

	public String toString() {
		if (min == null) {
			return "Variable name:" + getName() + System.getProperty("line.separator");
		} else {
			return "Variable name:" + getName() + ";Minimum range:" + getMin() + ";Maximum range:" + getMax()
					+ ";Restrictions:" + getRestriction().replace(";", ":") + System.getProperty("line.separator");
		}
	}

}
