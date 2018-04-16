package problemClasses;

import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class that represents a problem
 * contains:
 * 		- name of the problem
 * 		- description of the problem
 * 		- email of the sender
 * 		- date of the creation
 * 		- maximum waiting time for solution
 * 		- ideal waiting time for solution
 * 		- name of the group of variables
 * 		- total number of the variables
 * 		- list of the variables of decision
 * 		- list of criterias for evaluation
 * 
 * @author Kevin Corrales n� 73529
 *
 */
public class Problem {
	private String name;
	private String description;
	private String email;
	private String creationDate;
	private Time max;
	private Time ideal;
	private String groupName;
	private int numberVariables;
	private List<Variable> variables = new ArrayList<Variable>();
	private List<Criteria> criterias = new ArrayList<Criteria>();
	
	/**
	 * Class constructor
	 * 
	 * @param name
	 * @param description
	 * @param email
	 * @param max
	 * @param ideal
	 * @param groupName
	 * @param numberVariables
	 * @param variables
	 */
	public Problem(String name, String description, String email, Time max, Time ideal,
			String groupName,int numberVariables, List<Variable> variables) {
		super();
		this.name = name;
		this.description = description;
		this.email = email;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date current = new Date();  
		this.creationDate = dateFormat.format(current);
		this.max = max;
		this.ideal = ideal;
		this.groupName = groupName;
		this.numberVariables = numberVariables;
		this.variables = variables;
	}


	public Problem() {
	}


	public int getNumberVariables() {
		return numberVariables;
	}


	public String getName() {
		return name;
	}


	public List<Criteria> getCriterias() {
		return criterias;
	}
	
	public void addCriteria(Criteria criteria) {
		criterias.add(criteria);
	}


	public String getDescription() {
		return description;
	}


	public String getEmail() {
		return email;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public Time getMax() {
		return max;
	}


	public Time getIdeal() {
		return ideal;
	}


	public String getGroupName() {
		return groupName;
	}


	public List<Variable> getVariables() {
		return variables;
	}
	
	public String toString() {
		String allVariables = "";
		for(Variable var : getVariables()) {
			allVariables+="   -"+var.toString()+System.getProperty("line.separator");
		}
		
		return "Name:"+getName()+"; Description:"+getDescription()+";  E-mail:"+getEmail()+
				"; Date of creation:"+getCreationDate()+System.getProperty("line.separator")+
				"Max time:"+getMax().toString()+System.getProperty("line.separator")+
				"Ideal time:"+getIdeal().toString()+System.getProperty("line.separator")+
				"Variable group name:"+getGroupName()+"; Number of variables:"+getNumberVariables()+
				System.getProperty("line.separator") + allVariables;
	}
	
	
}
