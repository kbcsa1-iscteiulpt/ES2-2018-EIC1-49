package problem;

/**
 * Class that represents a criteria
 * contains:
 * 		- name of the criteria
 * 		- path of the .jar file
 * 
 * @author Kevin Corrales nº 73529
 *
 */
public class Criteria {
	private String name;
	private String path;
	
	
	public Criteria(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}


	public String getName() {
		return name;
	}


	public String getPath() {
		return path;
	}


	
	public String toString() {
		return "Name:"+getName()+";Path:"+getPath()+System.getProperty("line.separator");
	}
}
