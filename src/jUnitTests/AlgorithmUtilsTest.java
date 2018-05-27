package jUnitTests;

import java.io.File; 
import java.util.ArrayList; 
import java.util.List; 
 
import org.junit.Test;

import jMetal.AlgorithmUtils; 

/** 
 * This is a jUnitTestCase that tests the automatic Configuration for algorithms 
 * @author Kevin Corrales nr 73529 
 **/ 
 
public class AlgorithmUtilsTest { 
  AlgorithmUtils ac = new AlgorithmUtils(); 
   
  @Test 
  public void writeAlgorithmConfigurationTest(){ 
    List<String> algorithmIDs = new ArrayList<String>(); 
    algorithmIDs.add("SMSEMOA"); 
     
    String fileName = "src/jUnitTests/algoConfig"; 
        
    ac.writeAutomaticConfig(algorithmIDs, fileName); 
    
    ac.readAutomaticConf(new File(fileName)); 
  } 
   

   
} 

