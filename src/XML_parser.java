import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




public class XML_parser {
	String orderN;
	public void parse(String xml) throws ParserConfigurationException, IOException, SAXException {
		if(xml.contains("Order"))
		{

			//File inputFile = new File("C:\\4ano\\2_semestre\\II\\II_comands_A_v1\\command1.xml");

			DocumentBuilderFactory dbFactory= DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
			InputStream inputStream = new    ByteArrayInputStream(xml.getBytes());
			Document doc=dBuilder.parse(inputStream);
			doc.getDocumentElement().normalize();
			NodeList nlist=doc.getElementsByTagName("Order");

			if(xml.contains("Transform"))
			{
				for(int i=0;i<nlist.getLength();i++)
				{

					Node nNode= nlist.item(i);
					System.out.println("\nCurrent element: "+nNode.getNodeName());
					if(nNode.getNodeType() == Node.ELEMENT_NODE)
					{

						Element eElement= (Element) nNode;
						System.out.println("Order roll no :"+eElement.getAttribute("Number"));
						String From = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("From").getTextContent();
						System.out.println("Order:"+ eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("From").getTextContent());
						String To = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("To").getTextContent();
						String Quant = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Quantity").getTextContent();
						String Time = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Time").getTextContent();
						String MaxDelay = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("MaxDelay").getTextContent();
						String Penalty = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Penalty").getTextContent();
					}

				}
			}
		}

	}
}
