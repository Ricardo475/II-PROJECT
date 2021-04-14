package mes;


import java.io.IOException;
import java.net.ServerSocket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dijkstraAlgorithm_Test.test;


public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
		
		//------------------------------------------------PAULO--------------------------------------------------------------//
		
		
		
		//test t = new test();
		//t.doTest();
		
		/*PathFinder pr = new PathFinder();
		Transformação trans = new Transformação(1, "P1", "P2", 1, 0, 0, 0, 0);
		pr.initializeMachines();
		
		System.out.println("------------------------------------------------------------");
		pr.setInitValidPaths();
		
		System.out.println("------------------------------------------------------------");
		
		System.out.print(pr.buildPathTransformation(trans));
		*/
		
		//----------------------------------------------------LOIRO---------------------------------------------------------//
		
		int start=(int) System.currentTimeMillis(),l=0;
		OrdersList OL=new OrdersList();
		String ordem="aaa";
		XML_parser parse=new XML_parser();
		Erp_connection Erp =new Erp_connection(OL);
		Erp.start();
		Order prio = new Order(0,0,0,0);
		OPC_UA opc=new OPC_UA();
		opc.connect();
		try {
			opc.createSubscription();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(opc.get_Value("int_var"));
		/*for(int i=1;i>0;i++)
		{
			String aux=Erp.getXML();
			if(!aux.equals(ordem))
			{
			
				ordem=aux;
				int duration= (((int)System.currentTimeMillis()-start)/1000);
				//System.out.println(duration);
				parse.parse(ordem, OL,duration);
				
										
			}
			
			if((OL.LengthOrderList()-OL.DoneOrders)!=0)
			{
				
					
					prio=OL.OrdemPrioritária();
					if(prio != null)
					{
					prio.orderActivate();;
					prio.doOrder();
					l=0;
					}
					else
					{
						if(l==0)
						{
						System.out.println("===============NAO HA MAIS ORDENS===============");
						l=1;
						}
					}
			}
			Thread.sleep(1);
		
		}*/
	
	}

}
