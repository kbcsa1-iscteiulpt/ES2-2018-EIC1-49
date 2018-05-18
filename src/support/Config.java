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

 * 
 * @author Kevin Corrales n� 73529
 *
 */
public class Config {
	
	private final String configPath = "./config.xml";
	
	//E-mail
	private String emailAdmin;
	private String emailPassword;
	//JMetal
	private int maxEvaluations;
	private String rsPathInteger;
	private String rsPathDouble;
	private String rsPathBinary;
	private String rfPathInteger;
	private String rfPathDouble;
	private String rfPathBinary;
	//EPS Compiler
	private String epsLatexPath;
	private String rPath;
	private String epsEnvironmentVar;
	private String epsDestinationPath;
	//PDF Compiler
	private String pdfLatexPath;
	private String latexPath;
	private String pdfEnvironmentVar;
	private String pdfDestinationPath;
	private String pdfPath;
	
		
	public Config(){
		readConfig();
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

	public String getrsPathInteger() {
		return rsPathInteger;
	}

	public String getrsPathDouble() {
		return rsPathDouble;
	}

	public String getrsPathBinary() {
		return rsPathBinary;
	}
	
	public String getrfPathInteger() {
		return rfPathInteger;
	}

	public String getrfPathDouble() {
		return rfPathDouble;
	}

	public String getrfPathBinary() {
		return rfPathBinary;
	}

	public String getEpslatexPath() {
		return epsLatexPath;
	}

	public String getrPath() {
		return rPath;
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

	public String getPdfEnviromentVar() {
		return pdfEnvironmentVar;
	}

	public String getPdfDestinationPath() {
		return pdfDestinationPath;
	}

	public String getPdfPath() {
		return pdfPath;
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
				
				this.rsPathInteger=jmetalPaths.getAttribute("rPathInteger");
				this.rsPathDouble=jmetalPaths.getAttribute("rPathDouble");
				this.rsPathBinary=jmetalPaths.getAttribute("rPathBinary");
				
				this.epsDestinationPath= epsPaths.getAttribute("epsDestinationPath");
				this.epsLatexPath= epsPaths.getAttribute("epsLatexPath");
				this.rPath= epsPaths.getAttribute("rPath");
				
				this.pdfDestinationPath= pdfPaths.getAttribute("pdfDestinationPath");
				this.pdfLatexPath= pdfPaths.getAttribute("pdfLatexPath");
				this.pdfPath= pdfPaths.getAttribute("pdfPath");
				this.latexPath= pdfPaths.getAttribute("latexPath");
			}
			
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		
		
	}

	public String getEmailPassword() {
		return emailPassword;
	}
}
