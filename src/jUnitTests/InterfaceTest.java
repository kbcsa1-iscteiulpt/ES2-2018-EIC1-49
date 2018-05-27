package jUnitTests;

import javax.swing.JButton;

import org.junit.Test;

import interfaceProcess.Interface;
import interfaceSections.CriteriaSection;
import interfaceSections.DecisionVariablesSection;
import interfaceSections.EmailSection;

/**
 * This is a jUnitTestCase that tests the interface
 * @author Diana Lopes nr 72898 
 **/
public class InterfaceTest {

	private Interface interfaceTest;
	private DecisionVariablesSection decVarSection;
	
	@Test
	public void createProblem() {
		interfaceTest = new Interface();
		
		interfaceTest.getBtnCreateProblem().doClick();
	
		decVarSection = interfaceTest.getDecisionVariablesSection();
		decVarSection.getBtnDecisionVariables().doClick();
		interfaceTest.getProblemDataTypeSection().setType(1);
		decVarSection.getBtnDecisionVariables().doClick();
		decVarSection.getTxtNameOfDecisionVariablesGroup().setText("Group name");
		decVarSection.getSpnNumberOfDecisionVariables().setValue(1);
		decVarSection.getBtnDecisionVariablesFinish().doClick();
		decVarSection.getSpnNumberOfDecisionVariables().setValue(0);
		decVarSection.getBtnDecisionVariablesFinish().doClick();
		
		interfaceTest.getSaveSection().getBtnSaveToXML().doClick();
		
		interfaceTest.getHelpSection().getBtnGoBack().doClick();

		interfaceTest.getBtnReadProblem().doClick();
		
		JButton btnHelp = interfaceTest.getHelpSection().getBtnHelp();
		btnHelp.doClick();

		EmailSection email = interfaceTest.getEmailSection();
		email.getEmail().setText("dianalopes@sapo.pt");
		JButton btnEmail = email.getBtnWriteEmail();
		btnEmail.doClick();
		
		JButton btnMessageSend = email.getBtnMessageSend();
		btnMessageSend.doClick();
		email.getTxtMessageTitle().setText("This is a subject test");
		email.getTxaMessageBody().setText("This is a message test");
		btnMessageSend.doClick();

		interfaceTest.getReadSection().getBtnReadXML().doClick();

		decVarSection.getBtnDecisionVariables().doClick();
		decVarSection.getSpnNumberOfDecisionVariables().setValue(0);
		decVarSection.getTxtNameOfDecisionVariablesGroup().setText("Group name");
		decVarSection.getBtnDecisionVariablesFinish().doClick();
		
		interfaceTest.getSaveSection().getBtnSaveToXML().doClick();
		
		CriteriaSection criteriaSection = interfaceTest.getCriteriaSection();
		criteriaSection.getBtnCriteria().doClick();
		criteriaSection.getBtnAddCriteria().doClick();
		criteriaSection.getBtnRemoveCriteria().doClick();
		criteriaSection.getTxtCriteriaName().setText("Criteria test");
		criteriaSection.getBtnReadJar().doClick();
		criteriaSection.getBtnCriteriaFinish().doClick();	
	}
	

}