package jUnitTests;

import java.io.File; 
import java.util.ArrayList; 
import java.util.List; 
 
import org.junit.Test;

import jMetal.AlgorithmUtils; 

/** 
 * This is a jUnitTestCase that tests the automatic Configuration for algorithms 
 * @author Gustavo Morais n� 73036 
 **/ 
 
public class AlgorithmUtilsTest { 
  AlgorithmUtils ac = new AlgorithmUtils(); 
   
  @Test 
  public void writeAlgorithmConfigurationTest(){ 
    List<String> algorithmIDs = new ArrayList<String>(); 
    algorithmIDs.add("SMSEMOA"); 
     
    String fileName = "algoConfig"; 
        
    ac.writeAutomaticConfig(algorithmIDs, fileName); 
  } 
   
  @Test 
  public void readAlgorithmConfigurationTest() { 
    String userHomeFolder = System.getProperty("user.home/"); 
    File textFile = new File(userHomeFolder, "algoConfig.txt"); 
     
    ac.readAutomaticConf(textFile); 
  } 
   
} 

