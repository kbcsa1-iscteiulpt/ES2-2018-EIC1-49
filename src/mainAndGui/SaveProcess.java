package mainAndGui;


import problem.UserProblem;
import java.util.List;
import problem.Variable;
import problem.Time;
import problem.Type;

public class SaveProcess {
	/**
	* Saves the problem with the data given by the fields that were filled.
	* @param nameDescriptionProblem
	* @param problem
	* @param decisionVariables
	* @param email
	* @param timeOptimization
	* @param type
	*/
	public void saveProblem(NameDescriptionSection nameDescriptionProblem, UserProblem problem,
			DecisionVariablesSection decisionVariables, EmailSection email, TimeOptimizationSection timeOptimization,
			TypeSection type) {
		String groupName = groupName(decisionVariables);
		setProblem(nameDescriptionProblem, problem, decisionVariables, email, timeOptimization, type, groupName,
				problem.getVariables());
	}

	public String groupName(DecisionVariablesSection decisionVariables) {
		String groupName = "";
		if (decisionVariables.getTxtNameOfDecisionVariablesGroup() != null) {
			groupName = decisionVariables.getTxtNameOfDecisionVariablesGroup().getText();
		}
		return groupName;
	}

	/**
	* Sets the problem with the data given by the fields
	* @param nameDescriptionProblem
	* @param problem
	* @param decisionVariables
	* @param email
	* @param timeOptimization
	* @param type
	* @param groupName
	* @param variablesList
	*/
	public void setProblem(NameDescriptionSection nameDescriptionProblem, UserProblem problem,
			DecisionVariablesSection decisionVariables, EmailSection email, TimeOptimizationSection timeOptimization,
			TypeSection type, String groupName, List<Variable> variablesList)
			throws java.lang.NumberFormatException {
		problem.setName(nameDescriptionProblem.getProblemName().getText());
		problem.setDescription(nameDescriptionProblem.getProblemDescription().getText());
		problem.setEmail(email.getEmail().getText());
		problem.setMax(new Time(Integer.parseInt(timeOptimization.getSpnMaxNumberOfDays().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnMaxNumberOfHours().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnMaxNumberOfMinutes().getValue().toString())));
		problem.setIdeal(new Time(Integer.parseInt(timeOptimization.getSpnIdealNumberOfDays().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnIdealNumberOfHours().getValue().toString()),
				Integer.parseInt(timeOptimization.getSpnIdealNumberOfMinutes().getValue().toString())));
		problem.setType(type.getType());
		problem.setGroupName(groupName);
		if (type.getType().equals(Type.BINARY)) {
			problem.setNumberVariables(1);
		} else {
			problem.setNumberVariables(
					Integer.parseInt(decisionVariables.getSpnNumberOfDecisionVariables().getValue().toString()));
		}
		problem.setVariables(variablesList);
	}
}