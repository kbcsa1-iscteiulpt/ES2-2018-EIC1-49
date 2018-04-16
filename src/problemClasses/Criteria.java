package problemClasses;

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
	private String argumentType;
	
	/**
	 * Class constructor
	 * 
	 * @param name
	 * @param path
	 * @param argumentType
	 */
	public Criteria(String name, String path, String argumentType) {
		super();
		this.name = name;
		this.path = path;
		this.argumentType = argumentType;
	}


	public String getName() {
		return name;
	}


	public String getPath() {
		return path;
	}


	public String getArgumentType() {
		return argumentType;
	}
	
	
	public String toString() {
		return "Name:"+getName()+"; Path:"+getPath()+"; Arguments Type:"+getArgumentType()
		+System.getProperty("line.separator");
	}
}
