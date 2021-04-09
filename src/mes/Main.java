package mes;

import java.io.IOException;
import java.net.ServerSocket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
		
		int start=(int) System.currentTimeMillis();
		OrdersList OL=new OrdersList();
		String ordem="aaa";
		XML_parser parse=new XML_parser();
		Erp_connection Erp =new Erp_connection(OL);
		Erp.start();
		
		for(int i=1;i>0;i++)
		{
			String aux=Erp.getXML();
			if(!aux.equals(ordem))
			{
			
				ordem=aux;
				int duration= (((int)System.currentTimeMillis()-start)/1000);
				System.out.println(duration);
				parse.parse(ordem, OL,duration);
				
										
			}
			
			if((OL.LengthOrderList()-OL.DoneOrders)!=0)
			{
							
				/*for(int j=0;j<OL.LengthOrderList();j++)
				{
					System.out.print(OL.OrdersList.get(j)); System.out.println("  n: "+OL.OrdersList.get(j).getOrderNumber());
				}
				OL.OrdersList.clear();*/
				
			}
			Thread.sleep(1);
		}
	
	}

}
