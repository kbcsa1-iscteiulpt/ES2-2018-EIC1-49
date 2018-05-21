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
	private int bitIndex = 0;
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
		System.out.println(binaryValue);
		for (int i = 0; i < binaryValue.length(); i++) {
			if (binaryValue.charAt(i) == '1')
				bits.set(i);
		}
		System.out.println("bits " + bits.get(1));
		bitIndex = binaryValue.length();
	}

	public BitSet getBits() {
		return bits;
	}

	public void addBits(String bitString) {
		System.out.println("adding" + bitString);
		for (int i = 0; i < bitString.length(); i++) {
			if (bitString.charAt(i) == '1')
				bits.set(i + bitIndex);
		}
		bitIndex += bitString.length();
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
					+ ";Restrictions:" + getRestriction() + System.getProperty("line.separator");
		}
	}

}
