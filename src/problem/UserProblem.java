package problem;

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
 * @author Kevin Corrales nr 73529
 *
 */
public class UserProblem {
	private String name;
	private String description;
	private String email;
	private String creationDate;
	private Type type;
	private Time max;
	private Time ideal;
	private String groupName;
	private int numberVariables;
	private List<Variable> variables = new ArrayList<Variable>();
	private List<Criteria> criterias = new ArrayList<Criteria>();
	
	
	public UserProblem(String name, String description, String email,String creationDate, Time max, Time ideal, Type type,
			String groupName,int numberVariables, List<Variable> variables) {
		super();
		this.name = name;
		this.description = description;
		this.email = email;
		this.creationDate= creationDate;
		this.type = type;
		this.max = max;
		this.ideal = ideal;
		this.groupName = groupName;
		this.numberVariables = numberVariables;
		this.variables = variables;
	}


	public UserProblem() {
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
	
	public void addVariable(Variable variable) {
		variables.add(variable);
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


	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type=type;
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
	
	public void setName(String name) {
		this.name = name;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	
	public void setMax(Time max) {
		this.max = max;
	}


	public void setIdeal(Time ideal) {
		this.ideal = ideal;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public void setNumberVariables(int numberVariables) {
		this.numberVariables = numberVariables;
	}


	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}


	public void setCriterias(List<Criteria> criterias) {
		this.criterias = criterias;
	}

	
	public String toString() {
		String allVariables = "";
		boolean first=true;
		for(Variable var : getVariables()) {
			if(first) {
				allVariables+=";";
				first=false;
			}
			allVariables+="   -"+var.toString()+";"+System.getProperty("line.separator");
		}
		
		String allCriterias = "";
		first=true;
		for(Criteria crit : getCriterias()) {
			if(first) {
				allCriterias+=";";
				first=false;
			}
			allCriterias+="   -"+crit.toString()+";"+System.getProperty("line.separator");
		}
		
		
		
		return "Name:"+getName()+";Description:"+getDescription()+";E-mail:"+getEmail()+
				";Date of creation:"+getCreationDate()+System.getProperty("line.separator")+
				";Max time:"+getMax().toString()+System.getProperty("line.separator")+
				";Ideal time:"+getIdeal().toString()+System.getProperty("line.separator")+
				";Variable group name:"+getGroupName()+";Number of variables:"+getNumberVariables()+
				System.getProperty("line.separator") + allVariables+
				System.getProperty("line.separator") + allCriterias; 
	}
	
	
}
