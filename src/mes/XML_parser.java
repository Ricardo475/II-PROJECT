package mes;
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
	public void parse(String xml,OrdersState OL,int timeE) throws ParserConfigurationException, IOException, SAXException {
		
		//System.out.println("Estou aqui fora");
		if(xml.contains("<ORDERS>"))
		{
			//System.out.println("Estou aqui dentro");
			//File inputFile = new File("C:\\4ano\\2_semestre\\II\\II_comands_A_v1\\command1.xml");
			DocumentBuilderFactory dbFactory= DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
			InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
			Document doc=dBuilder.parse(inputStream);
			doc.getDocumentElement().normalize();
			
			if(xml.contains("<Request_Stores/>"))
			{
				SoredInWarehouse[] siw = new SoredInWarehouse[9];
				//System.out.println(xml);
				Order n= new Order(0,0,0,0);
				OL.addOrder(n);
				String storedMessage = "<Current_Stores>";
				
				for(int i = 0; i<9;i++) {
					
					int pNumber = i+1;
					siw[i] = new SoredInWarehouse(i);
					
					if( siw[i].sendResponse(i,"P" + pNumber,Main.pr.sys).compareTo("DOESNT EXIST")!=0)
						storedMessage = storedMessage + "\n" + siw[i].sendResponse(i,"P" + pNumber,Main.pr.sys);	
					
				}
				storedMessage = storedMessage + "\n" + "</Current_Stores>";
				System.out.println(storedMessage);
				
			}
			else if(xml.contains("<Transform"))
			{
				NodeList nlist=doc.getElementsByTagName("Order");
				
				for(int i=0;i<nlist.getLength();i++)
				{

					Node nNode= nlist.item(i);
					System.out.println("\nCurrent element: "+nNode.getNodeName());
					if(nNode.getNodeType() == Node.ELEMENT_NODE)
					{
						
						Element eElement= (Element) nNode;
						System.out.println("Order roll no :"+eElement.getAttribute("Number"));
						Integer Number=Integer.valueOf(eElement.getAttribute("Number"));
						String From = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("From").getTextContent();
						//System.out.println("Order:"+ eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("From").getTextContent());
						String To = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("To").getTextContent();
						int Quant = Integer.valueOf(eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Quantity").getTextContent());
						int Time = Integer.valueOf(eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Time").getTextContent());
						int MaxDelay = Integer.valueOf(eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("MaxDelay").getTextContent());
						int Penalty =Integer.valueOf(eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Penalty").getTextContent());
						Transformação trans= new Transformação(Number,From,To,Quant,Time,MaxDelay+(((int)System.currentTimeMillis()-Main.start)/1000),Penalty,timeE);
						OL.addOrder(trans);
						//Main.pr.buildPathTransformation(trans,Main.tts);
						
					}

				}
			}
	
			
			else if(xml.contains("<Request_Orders/>")) {
				String storedOrders = "<Order_Schedule>\n";
				
				for(int i = 0; i<OL.OrdersList.size(); i++) {
					
					Order order = new Order();
					order = OL.OrdersList.get(i);
					
					storedOrders = storedOrders + "<Order Number=" + order.getOrderNumber() + "\n";
					
					if(order.toString().contains("Transformation")) {
						
						storedOrders = storedOrders + "Transform From=" + ((Transformação)order).From + " To=" + ((Transformação)order).To + " Quantity=" + ((Transformação)order).quantTotal + " Quantity1=" + ((Transformação)order).quantProcessed + " Quantity2="
								+ ((Transformação)order).quantExe + "\nQuantity3=" + ((Transformação)order).quantToBe + " Time=" + order.instanteEnviado + " Time1=" + order.instanteChegada + " MaxDelay=" + order.MaxDelay + " Penalty=" + ((Transformação)order).Penalty + " Start=" + ((Transformação)order).exeTime
								+ "\nEnd=" + ((Transformação)order).finTime + " PenaltyIncurred=" + 0 + "/>\n";
						
					}
					
					storedOrders = storedOrders + "</Order>\n";
					
				}
				
				storedOrders = storedOrders + "</Order_Schedule>";
				System.out.println(storedOrders);
			}
			
			else if(xml.contains("<Unload")) {
				
				
				NodeList nlist=doc.getElementsByTagName("Order");
				
				for(int i=0;i<nlist.getLength();i++)
				{
					Node nNode= nlist.item(i);
					System.out.println("\nCurrent element: "+nNode.getNodeName());
					
					if(nNode.getNodeType() == Node.ELEMENT_NODE)
					{
						
						Element eElement= (Element) nNode;
						System.out.println("Order roll no :"+eElement.getAttribute("Number"));
						Integer Number=Integer.valueOf(eElement.getAttribute("Number"));
						String Type = eElement.getElementsByTagName("Unload").item(0).getAttributes().getNamedItem("Type").getTextContent();
						String Dest = eElement.getElementsByTagName("Unload").item(0).getAttributes().getNamedItem("Destination").getTextContent();
						int Quant = Integer.valueOf(eElement.getElementsByTagName("Unload").item(0).getAttributes().getNamedItem("Quantity").getTextContent());
						System.out.println(Number + " " + Type + " " + Dest + " " + Quant);
						Unloading u = new Unloading(Number, Type ,Dest,Quant);
						OL.OrdersList.add(u);
						//Main.pr.buildPathUnloading(u);
									
					}		
				}
					
			}
			
			
			
			
		}
		else System.out.println(xml);

	}
}
