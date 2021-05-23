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
							Transformação trans= new Transformação(Number,From,To,Quant,Time,MaxDelay+(((int)System.currentTimeMillis()-Main.start)/1000),Penalty,timeE);
							OL.addOrder(trans);
							//Main.pr.buildPathTransformation(trans,Main.tts);
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
							String storedMessage = "<Current_Stores>";

							for(int i1 = 0; i1<9;i1++) {

								int pNumber = i1+1;
								siw[i1] = new SoredInWarehouse(i1);

								if( siw[i1].sendResponse(i1,"P" + pNumber,Main.pr.sys).compareTo("DOESNT EXIST")!=0)
									storedMessage = storedMessage + "\n" + siw[i1].sendResponse(i1,"P" + pNumber,Main.pr.sys);	

							}
							storedMessage = storedMessage + "\n" + "</Current_Stores>";
							System.out.println(storedMessage);

						}
						
						else if(eElement.getElementsByTagName("Request_Orders").item(0) != null) {
							String storedOrders = "<?xml version=\"1.0\"?>\n<Order_Schedule>\n";
								
							for(int i1 = 0; i1<OL.OrdersList.size(); i1++) {

								Order order = new Order();
								order = OL.OrdersList.get(i1);
								
								storedOrders = storedOrders + "<Order Number=" + order.getOrderNumber() + "\n";

								if(order.toString().contains("Transformation")) {

									storedOrders = storedOrders + "<Transform From=\"" + ((Transformação)order).From + "\" To=\"" + ((Transformação)order).To + "\" Quantity=\"" + ((Transformação)order).quantTotal + "\" Quantity1=\"" + ((Transformação)order).quantProcessed + "\" Quantity2=\""
											+ ((Transformação)order).quantExe + "\"Quantity3=\"" + ((Transformação)order).quantToBe + "\" Time=\"" + order.instanteEnviado + "\" Time1=\"" + order.instanteChegada + "\" MaxDelay=\"" + order.MaxDelay + "\" Penalty=\"" + ((Transformação)order).Penalty + "\" Start=\"" + ((Transformação)order).tobestarted
											+ "\"End=\"" + ((Transformação)order).finTime + "\" PenaltyIncurred=\"" + ((Transformação)order).Penalty + "\"/>\n";
									//CreateXMLFile c=new CreateXMLFile((Transformação) order);
									storedOrders = storedOrders + "</Order>\n";
								}

									
							}
							storedOrders = storedOrders + "</Order_Schedule>";
							InetAddress addr = Main.address;
						    DatagramSocket dsock = new DatagramSocket();
						    byte[] send = storedOrders.getBytes( "UTF-8" );
						    DatagramPacket data = new DatagramPacket( send, send.length, addr, 54321 );
						    dsock.send( data );
							//storedOrders = storedOrders + "</Order_Schedule>";
							//System.out.println(storedOrders);
						}
					
					}
				}
			}
		}
		else System.out.println(xml);

	}
}
