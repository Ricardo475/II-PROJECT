package mes;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class CreateXMLFile {

	public CreateXMLFile(Transformação trans) {
		try {
			 
			String filePath="C:\4ano\ficheiro.xml";
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
 
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
            Document document = documentBuilder.newDocument();
 
            // root element
            Element root = document.createElement("Order_Schedule");
            document.appendChild(root);
 
            // employee element
            Element employee = document.createElement("Order");
 
            root.appendChild(employee);
 
            // set an attribute to staff element
            Attr attr = document.createAttribute("Number");
            attr.setValue(Integer.toString((trans.orderNumber)));
            employee.setAttributeNode(attr);
 
            //you can also use staff.setAttribute("id", "1") for this
 
            // firstname element
            Element transform = document.createElement("Transform");
            employee.appendChild(transform);
 
            // lastname element
            Attr From = document.createAttribute("From");
            From.setValue(trans.From);
            transform.setAttributeNode(From);
 
            // email element
            Attr To = document.createAttribute("To");
            To.setValue(trans.To);
            transform.setAttributeNode(To);
 
            // department elements
            Attr Quant = document.createAttribute("Quantity");
            Quant.setValue(Integer.toString((trans.quantTotal)));
            transform.setAttributeNode(Quant);
            
            // department elements
            Attr Quant1 = document.createAttribute("Quantity1");
            Quant1.setValue(Integer.toString((trans.quantProcessed)));
            transform.setAttributeNode(Quant1);
 
            //department elements
            Attr Quant2 = document.createAttribute("Quantity2");
            Quant2.setValue(Integer.toString((trans.quantExe)));
            transform.setAttributeNode(Quant2);
            
            //department elements
            Attr Quant3 = document.createAttribute("Quantity2");
            Quant3.setValue(Integer.toString((trans.quantToBe)));
            transform.setAttributeNode(Quant3);
            
            //department elements
            Attr time = document.createAttribute("Time");
            time.setValue(Integer.toString((trans.instanteEnviado)));
            transform.setAttributeNode(time);
            
            //department elements
            Attr time2 = document.createAttribute("Time1");
            time2.setValue(Integer.toString((trans.instanteChegada))); //CORRIGIR ISTO
            transform.setAttributeNode(time2);
            
            //department elements
            Attr maxD = document.createAttribute("MaxDelay");
            maxD.setValue(Integer.toString((trans.MaxDelay)));
            transform.setAttributeNode(maxD);
            
            //department elements
            Attr pen = document.createAttribute("Penalty");
            pen.setValue(Integer.toString((trans.Penalty)));
            transform.setAttributeNode(pen);
            
            //department elements
            Attr end = document.createAttribute("End");
            end.setValue(Integer.toString((trans.finTime)));
            transform.setAttributeNode(end);
            
            //department elements
            Attr start = document.createAttribute("Start");
            start.setValue(Integer.toString((trans.instanteEnviado)));
            transform.setAttributeNode(start);
            
            
            
            //department elements
            Attr penInc = document.createAttribute("PenaltyIncurred");
            penInc.setValue(Integer.toString((trans.PenaltyInc)));
            transform.setAttributeNode(penInc);
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            
            StreamResult streamResult = new StreamResult(new File(filePath));
           
            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging 

            transformer.transform(domSource, streamResult);
            String text = streamResult.getOutputStream().toString();
           System.out.println("\nAQUI: \n" +text+"\n END\n");
            System.out.println("Done creating XML File");
 
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
	

}
