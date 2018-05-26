package support;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

import problem.UserProblem;
import problem.Time;
import problem.Type;
import problem.Variable;

/**
 * Editor of XML Reads and writes a XML file, using the Problem class
 * 
 * @author Kevin Corrales nr 73529
 *
 */
public class XMLEditor {

	/**
	 * Reads a XML file from the received path and creates a Problem(Class)
	 * 
	 * @param path of xml file
	 * @return Problem
	 */
	public UserProblem read(String path) {
		UserProblem problem = new UserProblem();
		try {

			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("problem");

			for (int k = 0; k < nodeList.getLength(); k++) {
				Node node = nodeList.item(k);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element prob = (Element) node;

					Element time = (Element) prob.getElementsByTagName("time").item(0);
					Element maxTime = (Element) time.getElementsByTagName("max").item(0);
					Element idealTime = (Element) time.getElementsByTagName("ideal").item(0);
					Element variables = (Element) prob.getElementsByTagName("groupVariables").item(0);

					List<Variable> problemVariables = new ArrayList<Variable>();
					NodeList varList = variables.getElementsByTagName("variable");
					
					for (int i = 0; i < varList.getLength(); i++) {
						Element varElement = (Element) varList.item(i);
						Variable var ;
						if (!prob.getAttribute("type").toUpperCase().equals("BINARY")) {
							var = new Variable(varElement.getAttribute("variableName"),
									varElement.getAttribute("variableMin"), varElement.getAttribute("variableMax"),
									varElement.getAttribute("variableRestriction"));
						} else {
							// Else made by Gustavo Morais
							var = new Variable(varElement.getAttribute("variableName"),
									varElement.getAttribute("binaryValue"));
							for(int j=1;j<varList.getLength();j++) {
								Element varAux = (Element) varList.item(j);
								var.addBits(varAux.getAttribute("binaryValue"));
							}
							break;
						}
						problemVariables.add(var);
					}

					problem = new UserProblem(prob.getAttribute("name"), prob.getAttribute("description"),
							prob.getAttribute("email"),prob.getAttribute("creationDate"),
							new Time(Integer.parseInt(maxTime.getAttribute("maxdays")),
									Integer.parseInt(maxTime.getAttribute("maxhours")),
									Integer.parseInt(maxTime.getAttribute("maxminutes"))),
							new Time(Integer.parseInt(idealTime.getAttribute("idealdays")),
									Integer.parseInt(idealTime.getAttribute("idealhours")),
									Integer.parseInt(idealTime.getAttribute("idealminutes"))),
							Type.valueOf(prob.getAttribute("type").toUpperCase()), variables.getAttribute("groupName"),
							Integer.parseInt(variables.getAttribute("numberVariables")), problemVariables);
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "This file doesn't exist");
		}

		return problem;
	}

	/**
	 * Writes the Problem into XML file
	 * 
	 * @param path of file
	 * @param problem
	 */
	public void write(String path, UserProblem problem) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Root elements
			Document doc = docBuilder.newDocument();
			Element probTag = doc.createElement("problem");
			probTag.setAttribute("name", problem.getName());
			probTag.setAttribute("description", problem.getDescription());
			probTag.setAttribute("email", problem.getEmail());
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
			Date current = new Date();  
			probTag.setAttribute("creationDate", dateFormat.format(current));
			probTag.setAttribute("type", problem.getType().toString());
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
			for (int i = 0; i < problem.getNumberVariables() && i < problem.getVariables().size(); i++) {
				Variable var = problem.getVariables().get(i);

				Element varTag = doc.createElement("variable");
				varTag.setAttribute("variableName", var.getName());
				if (problem.getType() != Type.BINARY) {
					varTag.setAttribute("variableMin", var.getMinRange());
					varTag.setAttribute("variableMax", var.getMaxRange());
				} else {
					varTag.setAttribute("binaryValue", var.getBinaryValue());
				}
				varTag.setAttribute("variableRestriction", var.getRestriction());
				groupTag.appendChild(varTag);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce ) {
			JOptionPane.showMessageDialog(null, "Error occurred while saving file");
		} catch (TransformerException tfe) {
			JOptionPane.showMessageDialog(null, "Error occurred while saving file");
		}
	}

}
