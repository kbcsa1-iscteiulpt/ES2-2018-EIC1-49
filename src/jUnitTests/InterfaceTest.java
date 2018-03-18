package jUnitTests;

import javax.swing.JButton;
import org.junit.Test;
import gui.Interface;

/**
 * This is a jUnitTestCase that tests the interface
 * @author Diana Lopes nº 72898 
 **/
public class InterfaceTest {

	private Interface interfaceTest;

	@Test
	public void inicialInterface() {
		interfaceTest = new Interface();

		JButton helpButton = interfaceTest.getHelpButton();
		helpButton.doClick();

		JButton emailButton = interfaceTest.getEmailButton();
		emailButton.doClick();

		JButton messageSendButton = interfaceTest.getMessageSendButton();
		messageSendButton.doClick();

		JButton decisionVarButton = interfaceTest.getDecisionVarButton();
		decisionVarButton.doClick();

		JButton readButton = interfaceTest.getReadButton();
		readButton.doClick();

		JButton saveButton = interfaceTest.getSaveButton();
		saveButton.doClick();

		JButton criterionButton = interfaceTest.getCriterionButton();
		criterionButton.doClick();

		JButton addCriterionButton = interfaceTest.getAddCriterionButton();
		addCriterionButton.doClick();

		JButton readJarButton = interfaceTest.getReadJarButton();
		readJarButton.doClick();

	}

}