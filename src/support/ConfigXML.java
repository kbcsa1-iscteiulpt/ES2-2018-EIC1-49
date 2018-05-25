package support;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class that represents system configuration
 * contains:
 * 		- email of the administrator
 * 		- email password of the administrador
 * 		- maximum jmetal evaluations
 * 		- Path of the results file from the integer evaluation
 * 		- Path of the results file from the integer evaluation
 * 		- Path of the results file from the double evaluation
 * 		- Path of the compiler .exe file to convert R file to EPS
 * 		- Path of the R file
 * 		- Path of the EPS environment variable
 * 		- Path of the EPS file result (after compiling)
 * 		- Path of the compiler .exe to convert Latex file to PDF
 * 		- Path of the Latex file
 * 		- Path of the PDF environment variable
 * 		- Path of the PDF file result (after compiling)
 * 
 * @author Gustavo Morais nº 73036
 *
 */
public class ConfigXML {
	
	private final String configPath = "./config.xml";

	private static final ConfigXML INSTANCE = new ConfigXML();
	
	//Administrator
	private String emailAdmin;
	private String emailPassword;
	//JMetal
	private int maxEvaluations;
	private String resultsPathInteger;
	private String resultsPathDouble;
	private String resultsPathBinary;
	
	//EPS Compiler
	private String epsRPath;
	private String rPath;
	private String rFilename;
	private String epsEnvironmentVar;
	private String epsDestinationPath;
	private String epsOpenPath;
	//PDF Compiler
	private String pdfLatexPath;
	private String latexPath;
	private String latexFilename;
	private String pdfEnvironmentVar;
	private String pdfDestinationPath;
	private String pdfOpenPath;
	
		
	public ConfigXML(){
		readConfig();
	}
	
	public static ConfigXML getInstance() {
		return INSTANCE;
	}

	public String getEmailAdmin() {
		return emailAdmin;
	}
	

	public String getConfigPath() {
		return configPath;
	}

	public int getMaxEvaluations() {
		return maxEvaluations;
	}

	
	public String getResultsPathInteger() {
		return resultsPathInteger;
	}

	public String getResultsPathDouble() {
		return resultsPathDouble;
	}

	public String getResultsPathBinary() {
		return resultsPathBinary;
	}

	public String getEpsRPath() {
		return epsRPath;
	}

	public String getRPath() {
		return rPath;
	}
	
	public String getRFilename() {
		return rFilename;
	}

	public String getEpsEnviromentVar() {
		return epsEnvironmentVar;
	}

	public String getEpsDestinationPath() {
		return epsDestinationPath;
	}

	public String getPdflatexPath() {
		return pdfLatexPath;
	}

	public String getLatexPath() {
		return latexPath;
	}
	
	public String getLatexFilename() {
		return latexFilename;
	}
	
	public String getEpsOpenPath() {
		return epsOpenPath;
	}

	public String getPdfEnviromentVar() {
		return pdfEnvironmentVar;
	}

	public String getPdfDestinationPath() {
		return pdfDestinationPath;
	}

	public String getPdfOpenPath() {
		return pdfOpenPath;
	}
	
	public String getEmailPassword() {
		return emailPassword;
	}


	/**
	 * Reads a XML file from the received path and creates a Configuration (Config Class) 
	 * 
	 * @param path of xml file
	 * @return Config
	 */	
	public void readConfig() {
		try {
	
			File fXmlFile = new File(configPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
	
			doc.getDocumentElement().normalize();
						
			Node mainNode = (Element) doc.getElementsByTagName("config").item(0);
			
			if(mainNode.getNodeType() == Node.ELEMENT_NODE) {
				Element config = (Element) mainNode;
				
				Element administrator = (Element) config.getElementsByTagName("administrator").item(0);
				Element JMetal = (Element) config.getElementsByTagName("jmetal").item(0);
				Element EPS = (Element) config.getElementsByTagName("eps").item(0);
				Element PDF = (Element) config.getElementsByTagName("pdf").item(0);
				
				this.emailAdmin=administrator.getAttribute("email");
				this.emailPassword=administrator.getAttribute("emailPassword");
				
				this.maxEvaluations= Integer.parseInt(JMetal.getAttribute("maxEvaluations"));
				
				this.epsEnvironmentVar= EPS.getAttribute("epsEnvironmentVar");
				this.pdfEnvironmentVar= PDF.getAttribute("pdfEnvironmentVar");
				
				Element jmetalPaths = (Element) JMetal.getElementsByTagName("paths").item(0);
				Element epsPaths = (Element) EPS.getElementsByTagName("paths").item(0);
				Element pdfPaths = (Element) PDF.getElementsByTagName("paths").item(0);
				
				this.resultsPathInteger=jmetalPaths.getAttribute("resultsPathInteger");
				this.resultsPathDouble=jmetalPaths.getAttribute("resultsPathDouble");
				this.resultsPathBinary=jmetalPaths.getAttribute("resultsPathBinary");
				
				this.epsDestinationPath= epsPaths.getAttribute("epsDestinationPath");
				this.epsRPath= epsPaths.getAttribute("epsRPath");
				this.epsOpenPath = epsPaths.getAttribute("epsOpenPath");
				this.rPath= epsPaths.getAttribute("rPath");
				this.rFilename= epsPaths.getAttribute("rFilename");
				
				this.pdfDestinationPath= pdfPaths.getAttribute("pdfDestinationPath");
				this.pdfLatexPath= pdfPaths.getAttribute("pdfLatexPath");
				this.pdfOpenPath= pdfPaths.getAttribute("pdfOpenPath");
				this.latexPath= pdfPaths.getAttribute("latexPath");
				this.latexFilename=pdfPaths.getAttribute("latexFilename");
			}
			
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		
		
	}

	
}
