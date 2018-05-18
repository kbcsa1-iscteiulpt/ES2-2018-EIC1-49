//package jUnitTests;
//
//import javax.swing.JButton;
//import javax.swing.JSpinner;
//import javax.swing.JTextField;
//
//import org.junit.Test;
//
//import mainAndGui.Interface;
//import support.Config;
//import support.XML_Editor;
//
///**
// * This is a jUnitTestCase that tests the interface
// * @author Diana Lopes n� 72898 
// **/
//public class InterfaceTest {
//
//	private Interface interfaceTest;
//	private XML_Editor xml = new XML_Editor();
//	private Config config = xml.readConfig();
//
//	@Test
//	public void inicialInterface() {
//		interfaceTest = new Interface(config.getEmailAdmin());
//
//		JButton btnCreateProblem = interfaceTest.getCreateProblem();
//		btnCreateProblem.doClick();
//		
//		JButton btnGoBack = interfaceTest.getGoBack();
//		btnGoBack.doClick();
//		
//		JButton btnReadProblem = interfaceTest.getReadProblem();
//		btnReadProblem.doClick();
//		
//		JButton btnHelp = interfaceTest.getHelpButton();
//		btnHelp.doClick();
//
//		interfaceTest.setEmail("dianalopes@sapo.pt");
//		JButton btnEmail = interfaceTest.getEmailButton();
//		btnEmail.doClick();
//
//		interfaceTest.setEmailTitle("email title");
//		interfaceTest.setEmailMessage("email message body");
//		
//		JButton btnMessageSend = interfaceTest.getMessageSendButton();
//		btnMessageSend.doClick();
//		
//		JButton btnDecisionVariables = interfaceTest.getDecisionVarButton();
//		btnDecisionVariables.doClick();
//
//		
//		JButton btnDecisionVariablesFinishButton = interfaceTest.getDecisionVariablesFinishButton();
//	
//		btnDecisionVariablesFinishButton.doClick();
//
//		JButton btnRead = interfaceTest.getReadButton();
//		btnRead.doClick();		
//
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
//		
//	}
//
//}