package problem;

/**
 * Class that represents a criteria
 * contains:
 * 		- name of the criteria
 * 		- path of the .jar file
 * 		- type of the arguments
 * 
 * @author Kevin Corrales nº 73529
 *
 */
public class Criteria {
	private String name;
	private String path;
	private String type;
	
	/**
	 * Class constructor
	 * 
	 * @param name
	 * @param path
	 * @param argumentType
	 */
	public Criteria(String name, String path, String type) {
		super();
		this.name = name;
		this.path = path;
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public String getPath() {
		return path;
	}


	public String getType() {
		return type;
	}
	
	
	public String toString() {
		return "Name:"+getName()+";Path:"+getPath()+";Arguments Type:"+getType()
		+System.getProperty("line.separator");
	}
}
