package classes;

/**
 * Class that represents a variable of decision
 * contains:
 * 		- name of the variable
 * 		- type of the variable
 * 		- minimum range of the variable value
 * 		- maximum range of the variable value
 * 		- restricted values of the variable
 * 
 * @author Kevin Corrales nº 73529
 *
 */
public class Variable {
	private String name;
	private String type;
	// Interval is String because we don't know the type of the variable yet
	private String min; //Start of the range
	private String max;	//End of the range
	private String restriction;
	
	/**
	 * Class constructor
	 * 
	 * @param name
	 * @param type
	 * @param min
	 * @param max
	 * @param restriction
	 */
	public Variable(String name, String type, String min, String max, String restriction) {
		super();
		this.name = name;
		this.type = type;
		this.min = min;
		this.max = max;
		this.restriction = restriction;
	}


	public String getName() {
		return name;
	}


	public String getType() {
		return type;
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
		return "Variable name:"+getName()+"; Type:"+getType()+"; Minimum range:"+getMin()+
				"; Maximum range:"+getMax()+"; Restrictions:"+getRestriction();
	}
}
