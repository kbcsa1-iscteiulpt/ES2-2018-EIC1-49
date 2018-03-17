package Classes;

public class Variable {
	private String name;
	private String type;
	// Interval is String because we don't know the type of the variable yet
	private String min; //Start of the range
	private String max;	//End of the range
	private String restriction;
	
	
	public Variable(String name, String type, String min, String max, String restriction) {
		super();
		this.name = name;
		this.type = type;
		this.min = min;
		this.max = max;
		this.restriction = restriction;
	}
}
