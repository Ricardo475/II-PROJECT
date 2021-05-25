package mes;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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

		    if(xml.contains("<Transform") || xml.contains("<Unload") || xml.contains("<Request_Stores/>") || xml.contains("<Request_Orders/>"))
			{
				NodeList nlist=doc.getElementsByTagName("Order");
				NodeList nlist1=doc.getElementsByTagName("ORDERS");
				
				for(int i=0;i<nlist.getLength();i++)
				{
					Node nNode= nlist.item(i);

					if(nNode.getNodeType() == Node.ELEMENT_NODE)
					{

						Element eElement= (Element) nNode;
						
						if(eElement.getElementsByTagName("Transform").item(0)!= null)
						{
							System.out.println("Order roll no :"+eElement.getAttribute("Number"));
							Integer Number=Integer.valueOf(eElement.getAttribute("Number"));
							String From = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("From").getTextContent();
							//System.out.println("Order:"+ eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("From").getTextContent());
							String To = eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("To").getTextContent();
							int Quant = Integer.valueOf(eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Quantity").getTextContent());
							int Time = Integer.valueOf(eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Time").getTextContent());
							int MaxDelay = Integer.valueOf(eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("MaxDelay").getTextContent());
							int Penalty =Integer.valueOf(eElement.getElementsByTagName("Transform").item(0).getAttributes().getNamedItem("Penalty").getTextContent());
							Transformação trans= new Transformação(Number,From,To,Quant,Time,MaxDelay,Penalty,timeE);
							OL.addOrder(trans);
						}
						else if(eElement.getElementsByTagName("Unload").item(0) != null)
						{
							Integer Number=Integer.valueOf(eElement.getAttribute("Number"));
							String Type = eElement.getElementsByTagName("Unload").item(0).getAttributes().getNamedItem("Type").getTextContent();
							String Dest = eElement.getElementsByTagName("Unload").item(0).getAttributes().getNamedItem("Destination").getTextContent();
							int Quant = Integer.valueOf(eElement.getElementsByTagName("Unload").item(0).getAttributes().getNamedItem("Quantity").getTextContent());
							System.out.println(Number + " " + Type + " " + Dest + " " + Quant);
							Unloading u = new Unloading(Number, Type ,Dest,Quant);
							OL.addOrder(u);
							
						}
						
					}

				}
				
				for(int i=0;i<nlist1.getLength();i++) {
					
					Node nNode1= nlist1.item(i);
					
					if(nNode1.getNodeType() == Node.ELEMENT_NODE)
					{
						Element eElement= (Element) nNode1;
						
						if(eElement.getElementsByTagName("Request_Stores").item(0) != null)
						{
							SoredInWarehouse[] siw = new SoredInWarehouse[9];
							//System.out.println(xml);
							Order n= new Order(0,0,0,0);
							OL.addOrder(n);
							String storedMessage = "<?xml version=\"1.0\"?>\r\n<Current_Stores>\r\n";

							for(int i1 = 0; i1<9;i1++) {

								int pNumber = i1+1;
								siw[i1] = new SoredInWarehouse(i1);

								if( siw[i1].sendResponse(i1,"P" + pNumber,Main.pr.sys).compareTo("DOESNT EXIST")!=0)
									storedMessage = storedMessage + siw[i1].sendResponse(i1,"P" + pNumber,Main.pr.sys);	

							}
							storedMessage = storedMessage + "</Current_Stores>";												   
						    byte[] send = storedMessage.getBytes();
						    System.out.println( InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()) + "PORT: "+Main.packet.getPort());
						    DatagramPacket data = new DatagramPacket( send, send.length, Main.packet.getAddress(),Main.packet.getPort());
						    Main.Erp.socket.send(data);
							System.out.println(storedMessage);

						}
						
						else if(eElement.getElementsByTagName("Request_Orders").item(0) != null) {
							String storedOrders = "<?xml version=\"1.0\"?>\r\n<Order_Schedule>\r\n";
								
							for(int i1 = 0; i1<OL.OrdersList.size(); i1++) {

								Order order = new Order();
								order = OL.OrdersList.get(i1);
								
								storedOrders = storedOrders + "<Order Number=" + order.getOrderNumber() +">"+ "\n";

								if(order.toString().contains("Transformation")) {

									storedOrders = storedOrders + "<Transform From=\"" + ((Transformação)order).From + "\" To=\"" + ((Transformação)order).To + "\" Quantity=\"" + ((Transformação)order).quantTotal + "\" Quantity1=\"" + ((Transformação)order).quantProcessed + "\" Quantity2=\""
											+ ((Transformação)order).quantExe + "\" Quantity3=\"" + ((Transformação)order).quantToBe + "\" Time=\"" + order.instanteEnviado + "\" Time1=\"" + order.instanteChegada + "\" MaxDelay=\"" + order.MaxDelay + "\" Penalty=\"" + ((Transformação)order).Penalty + "\" Start=\"" + ((Transformação)order).startTime
											+ "\" End=\"" + ((Transformação)order).finTime + "\" PenaltyIncurred=\"" + ((Transformação)order).PenaltyInc + "\"/>\r\n";
									//CreateXMLFile c=new CreateXMLFile((Transformação) order);
									storedOrders = storedOrders + "</Order>\r\n";
								}

									
							}
							storedOrders = storedOrders + "</Order_Schedule>";						  
						    byte[] send = storedOrders.getBytes();
						    System.out.println( InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()) + "PORT: "+Main.packet.getPort());
						    DatagramPacket data = new DatagramPacket( send, send.length, Main.packet.getAddress(),Main.packet.getPort());
					   
						    Main.Erp.socket.send(data);
						    
							System.out.println(storedOrders);
						}
					
					}
				}
			}
		}
		else System.out.println("BUG:" + xml);

	}
}
