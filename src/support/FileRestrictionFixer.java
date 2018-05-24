package support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileRestrictionFixer {

	private ConfigXML config = ConfigXML.getInstance();
	
	public FileRestrictionFixer() {}
	
	public void fixFiles(int nLines) throws FileNotFoundException, IOException {
		String[] paths = new String[]{config.getResultsPathBinary(),config.getResultsPathDouble(),
			config.getResultsPathInteger()};
		
		for(String path : paths) {
			File dir = new File("./"+config.getExperimentBaseDirectory()+"/"+path);
		    File[] directoryListing = dir.listFiles();
		    if (directoryListing != null) {
		      for (File file : directoryListing) {
			    	  if(file.getName().endsWith(".rs")) {
			    		  try (BufferedReader reader = new BufferedReader(new FileReader(file)); FileWriter writer = new FileWriter(file);) {
			    		        String line;
			    		        while ((line = reader.readLine()) != null) {
			    		           String variables;
			    		           for(int i=0; i<nLines;i++) {
			    		        	   
			    		           }
			    		        }
			    		    }
			    	  }
		      }
		    } 
		}
	}
	
}
