package Classes;

import java.util.List;
import java.util.ArrayList;

public class Problem {
	private String name;
	private String description;
	private String email;
	private String sendDate;
	private Time max;
	private Time ideal;
	private String groupName;
	private List<Variable> variables = new ArrayList<Variable>();
	
	
	public Problem(String name, String description, String email, String sendDate, Time max, Time ideal,
			String groupName, List<Variable> variables) {
		super();
		this.name = name;
		this.description = description;
		this.email = email;
		this.sendDate = sendDate;
		this.max = max;
		this.ideal = ideal;
		this.groupName = groupName;
		this.variables = variables;
	}
	
	
}
