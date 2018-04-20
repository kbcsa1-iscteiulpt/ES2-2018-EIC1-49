package jUnitTests;

import javax.swing.JButton;
import javax.swing.JSpinner;

import org.junit.Test;

import mainAndGui.Interface;

/**
 * This is a jUnitTestCase that tests the interface
 * @author Diana Lopes nº 72898 
 **/
public class InterfaceTest {

	private Interface interfaceTest;

	@Test
	public void inicialInterface() {
		interfaceTest = new Interface();

		JButton btnHelp = interfaceTest.getHelpButton();
		btnHelp.doClick();

		JButton btnEmail = interfaceTest.getEmailButton();
		btnEmail.doClick();

		JButton btnMessageSend = interfaceTest.getMessageSendButton();
		btnMessageSend.doClick();

		JSpinner spnNumberOfVariableDecisions = interfaceTest.getSpnNumberOfDecisionVariables();
		spnNumberOfVariableDecisions.setValue(Integer.valueOf(12));
		
		JButton btnDecisionVariables = interfaceTest.getDecisionVarButton();
		btnDecisionVariables.doClick();

		JButton btnRead = interfaceTest.getReadButton();
		btnRead.doClick();

		JButton btnSave = interfaceTest.getSaveButton();
		//btnSave.doClick();

		JButton btnCriterion = interfaceTest.getCriteriaButton();
		btnCriterion.doClick();

		JButton btnAddCriterion = interfaceTest.getAddCriteriaButton();
		btnAddCriterion.doClick();

		JButton btnReadJar = interfaceTest.getReadJarButton();
		btnReadJar.doClick();
		
		JButton btnExecuteProcess = interfaceTest.getExecuteProcessButton();
//		btnExecuteProcess.doClick();
		
		
		
	}

}