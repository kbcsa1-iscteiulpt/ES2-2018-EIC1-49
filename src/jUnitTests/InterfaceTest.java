package jUnitTests;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.Test;

import mainAndGui.CriteriaSection;
import mainAndGui.DecisionVariablesSection;
import mainAndGui.EmailSection;
import mainAndGui.Interface;
import mainAndGui.TypeVarSection;
import support.Config;
import support.XMLEditor;

/**
 * This is a jUnitTestCase that tests the interface
 * @author Diana Lopes nr 72898 
 **/
public class InterfaceTest {

	private Interface interfaceTest;
	private Config config = new Config();
	private DecisionVariablesSection decVarSection;
	
	@Test
	public void createProblem() {
		interfaceTest = new Interface(config.getEmailAdmin());
		
		interfaceTest.getBtnCreateProblem().doClick();
	
		decVarSection = interfaceTest.getDecisionVariablesSection();
		decVarSection.getBtnDecisionVariables().doClick();
		interfaceTest.getProblemDataTypeSection().setDataType(1);
		decVarSection.getBtnDecisionVariables().doClick();
		decVarSection.getTxtNameOfDecisionVariablesGroup().setText("Group name");
		decVarSection.getSpnNumberOfDecisionVariables().setValue(1);
		decVarSection.getBtnDecisionVariablesFinish().doClick();
		decVarSection.getSpnNumberOfDecisionVariables().setValue(0);
		decVarSection.getBtnDecisionVariablesFinish().doClick();
		
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
		
		CriteriaSection criteriaSection = interfaceTest.getCriteriaSection();
		criteriaSection.getBtnCriteria().doClick();
		criteriaSection.getBtnAddCriteria().doClick();
		criteriaSection.getBtnRemoveCriteria().doClick();
		criteriaSection.getTxtCriteriaName().setText("Criteria test");
		criteriaSection.getBtnReadJar().doClick();
		criteriaSection.getBtnCriteriaFinish().doClick();
		
		
		
//		JButton btnCriteria = interfaceTest.getCriteriaButton();
//		btnCriteria.doClick();
//		
//		JButton btnAddCriteria = interfaceTest.getAddCriteriaButton();
//		btnAddCriteria.doClick();
//		
//		JButton btnCriteriaFinish = interfaceTest.getCriteriaFinishButton();
//		btnCriteriaFinish.doClick();
//		
//		
//		JButton btnReadJar = interfaceTest.getReadJarButton();
//		btnReadJar.doClick();
//		
//		JButton btnExecuteProcess = interfaceTest.getExecuteProcessButton();
//		btnExecuteProcess.doClick();
//		
//		
//		JButton btnSave = interfaceTest.getSaveButton();
//		btnSave.doClick();
//		
		
	}
	

}