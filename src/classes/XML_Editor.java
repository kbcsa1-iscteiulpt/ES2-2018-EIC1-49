package classes;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

/**
 * Editor of XML
 * Reads and writes a XML file, using the Problem class
 * 
 * @author Kevin Corrales nº 73529
 *
 */
public class XML_Editor {
	
	/**
	 * Reads a XML file from the received path and creates a Problem(Class) 
	 * 
	 * @param path of xml file
	 * @return Problem
	 */
	public Problem read(String path) {
		Problem problem = new Problem();
		try {

			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
						
			NodeList nodeList = doc.getElementsByTagName("problem");
			
			//Set the date format of actual date
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar current = Calendar.getInstance();
			
			for (int k = 0; k < nodeList.getLength(); k++) {
				Node node = nodeList.item(k);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element prob = (Element) node;
						
					Element time =(Element) prob.getElementsByTagName("time").item(0);
					Element maxTime = (Element)time.getElementsByTagName("max").item(0);
					Element idealTime = (Element)time.getElementsByTagName("ideal").item(0);
					Element variables = (Element) prob.getElementsByTagName("groupVariables").item(0);
					
					List<Variable> problemVariables = new ArrayList<Variable>();
					NodeList varList = variables.getElementsByTagName("variable");
					for (int i = 0; i < varList.getLength(); i++) {
						
						Element varElement = (Element) varList.item(i);
						Variable var = new Variable(
								varElement.getAttribute("variableName"),
								varElement.getAttribute("variableType"),
								varElement.getAttribute("variableMin"),
								varElement.getAttribute("variableMax"),
								varElement.getAttribute("variableRestriction")
								);
						problemVariables.add(var);
					}
					
					problem = new Problem(
							prob.getAttribute("name"),
							prob.getAttribute("description"),
							prob.getAttribute("email"),
							new Time(Integer.parseInt(maxTime.getAttribute("maxdays")),
									Integer.parseInt(maxTime.getAttribute("maxhours")),
									Integer.parseInt(maxTime.getAttribute("maxminutes"))),
							new Time(Integer.parseInt(idealTime.getAttribute("idealdays")),
									Integer.parseInt(idealTime.getAttribute("idealhours")),
									Integer.parseInt(idealTime.getAttribute("idealminutes"))),
							variables.getAttribute("groupName"),
							Integer.parseInt(variables.getAttribute("numberVariables")),
							problemVariables
							);
				}
			}
		
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		
		return problem;
	}
	
	/**
	 * Writes the Problem into XML file
	 * 
	 * @param path of file
	 * @param problem
	 */
	public void write(String path,Problem problem) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Root elements
			Document doc = docBuilder.newDocument();
			Element probTag = doc.createElement("problem");
			probTag.setAttribute("name", problem.getName());
			probTag.setAttribute("description", problem.getDescription());
			probTag.setAttribute("email", problem.getEmail());
			probTag.setAttribute("creationDate", problem.getCreationDate());
			doc.appendChild(probTag);

			// Problem elements
			
			Element timeTag = doc.createElement("time");
			probTag.appendChild(timeTag);
			
			Element groupTag = doc.createElement("groupVariables");
			groupTag.setAttribute("groupName", problem.getGroupName());
			groupTag.setAttribute("numberVariables", Integer.toString(problem.getNumberVariables()));
			probTag.appendChild(groupTag);
			
			// Time Elements
			Element maxTimeTag = doc.createElement("max");
			maxTimeTag.setAttribute("maxdays", Integer.toString(problem.getMax().getDays()));
			maxTimeTag.setAttribute("maxhours", Integer.toString(problem.getMax().getHours()));
			maxTimeTag.setAttribute("maxminutes", Integer.toString(problem.getMax().getMinutes()));
			timeTag.appendChild(maxTimeTag);
			
			Element idealTimeTag = doc.createElement("ideal");
			idealTimeTag.setAttribute("idealdays", Integer.toString(problem.getIdeal().getDays()));
			idealTimeTag.setAttribute("idealhours", Integer.toString(problem.getIdeal().getHours()));
			idealTimeTag.setAttribute("idealminutes", Integer.toString(problem.getIdeal().getMinutes()));
			timeTag.appendChild(idealTimeTag);
			
			// Group Variables elements
			for(int i=0;i<problem.getNumberVariables() && i<problem.getVariables().size();i++) {
				Variable var = problem.getVariables().get(i);
				
				Element varTag = doc.createElement("variable");
				varTag.setAttribute("variableName", var.getName());
				varTag.setAttribute("variableType", var.getType());
				varTag.setAttribute("variableMin",var.getMin());
				varTag.setAttribute("variableMax",var.getMax());
				varTag.setAttribute("variableRestriction",var.getRestriction());
				groupTag.appendChild(varTag);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);

			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		}
	
	

}
