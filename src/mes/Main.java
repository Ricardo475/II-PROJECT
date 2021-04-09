package mes;

import java.io.IOException;
import java.net.ServerSocket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
		
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
				parse.parse(ordem, OL);
				
						
			}
			//Thread.sleep(1);
			
		}
	
	}

}
