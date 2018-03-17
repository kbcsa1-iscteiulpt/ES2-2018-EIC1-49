package Classes;

import java.util.List;
import java.util.ArrayList;

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
 * 
 * @author Kevin Corrales nº 73529
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
	
	
	public Problem(String name, String description, String email, String creationDate, Time max, Time ideal,
			String groupName,int numberVariables, List<Variable> variables) {
		super();
		this.name = name;
		this.description = description;
		this.email = email;
		this.creationDate = creationDate;
		this.max = max;
		this.ideal = ideal;
		this.groupName = groupName;
		this.variables = variables;
		this.numberVariables = numberVariables;
	}


	public Problem() {
	}


	public int getNumberVariables() {
		return numberVariables;
	}


	public String getName() {
		return name;
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
