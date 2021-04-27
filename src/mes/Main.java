package mes;


import java.io.IOException;
import java.net.ServerSocket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dijkstraAlgorithm_Test.test;


public class Main {

	static PathFinder pr = new PathFinder();
	static TransformationTable[] tts = new TransformationTable[8];
	static OPC_UA opc=new OPC_UA();
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
		
		//------------------------------------------------PAULO--------------------------------------------------------------//
		
		/*
		test t = new test();
		t.doTest();
		*/
		
		//int orderNumber = 1;
		
		OrdersState OL=new OrdersState();
		opc.connect();
		Unloading u = new Unloading(4, "P4","P2",2);
		//Transformação trans = new Transformação(45, "P1", "P8", 10, 0, 0, 0, 0);
		//SoredInWarehouse[] siw = new SoredInWarehouse[9];
		
		pr.initializeMachines();
		pr.mchs[1].changeTool("T2");
		pr.mchs[2].changeTool("T3");
		pr.mchs[5].changeTool("T2");
		pr.mchs[6].changeTool("T3");
		
		System.out.println("------------------------------------------------------------");
		
		pr.initializeSystemState();
		
		System.out.println("------------------------------------------------------------");
		
		for(int i= 0; i<pr.mchs.length;i++) 
			pr.mchs[i].print_machine();
		
		tts[0] = new TransformationTable("P1","P2","T1",15);
		tts[1] = new TransformationTable("P4","P5","T1",15);
		tts[2] = new TransformationTable("P6","P8","T1",15);
		tts[3] = new TransformationTable("P2","P3","T2",15);
		tts[4] = new TransformationTable("P5","P6","T2",30);
		tts[5] = new TransformationTable("P3","P4","T3",15);
		tts[6] = new TransformationTable("P5","P9","T3",30);
		tts[7] = new TransformationTable("P6","P7","T3",30);
		
		System.out.println("------------------------------------------------------------");
		
		pr.initializePushers();
		
		System.out.println("------------------------------------------------------------");
	//	pr.buildPathTransformation(trans,tts);
		//OL.addOrder(trans);
		
		//System.out.println("------------------------------------------------------------");
		
		//pr.buildPathUnloading(u);
		
		//System.out.println("------------------------------------------------------------");
		/*
		
		String storedMessage = "<Current_Stores>";
		
		for(int i = 0; i<9;i++) {
			
			int pNumber = i+1;
			siw[i] = new SoredInWarehouse(orderNumber);
			
			if( siw[i].sendResponse(orderNumber,"P" + pNumber,pr.sys).compareTo("DOESNT EXIST")!=0)
				storedMessage = storedMessage + "\n" + siw[i].sendResponse(orderNumber,"P" + pNumber,pr.sys);
			
			orderNumber++;	
			
		}
		storedMessage = storedMessage + "\n" + "</Current_Stores>";
		System.out.println(storedMessage);
		
		System.out.println("------------------------------------------------------------");
		
		
		String storedOrders = "<Order_Schedule>\n";
		
		for(int i = 0; i<OL.OrdersList.size(); i++) {
			
			Order order = new Order();
			order = OL.OrdersList.get(i);
			
			storedOrders = storedOrders + "<Order Number=" + order.getOrderNumber() + "\n";
			
			if(order.toString().compareTo("transformation")==0) {
				
				storedOrders = storedOrders + "Transform From=" + ((Transformação)order).From + " To=" + ((Transformação)order).To + " Quantity=" + ((Transformação)order).quantTotal + " Quantity1=" + ((Transformação)order).quantProcessed + " Quantity2="
						+ ((Transformação)order).quantExe + "\nQuantity3=" + ((Transformação)order).quantToBe + " Time=" + order.instanteEnviado + " Time1=" + order.instanteChegada + " MaxDelay=" + order.MaxDelay + " Penalty=" + ((Transformação)order).Penalty + " Start=" + ((Transformação)order).exeTime
						+ "\nEnd=" + ((Transformação)order).finTime + " PenaltyIncurred=" + 0 + "/>\n";
				
			}
			
			storedOrders = storedOrders + "</Order>\n";
			
		}
		
		storedOrders = storedOrders + "</Order_Schedule>";
		System.out.println(storedOrders);
		
		System.out.println("------------------------------------------------------------");
		*/
		//----------------------------------------------------LOIRO---------------------------------------------------------//
		
		int start=(int) System.currentTimeMillis(),l=0;
		String ordem="aaa";
		XML_parser parse=new XML_parser();
		Erp_connection Erp =new Erp_connection(OL);
		Erp.start();
		//OPC_UA opc=new OPC_UA();

		Order prio = new Order(0,0,0,0);
		
	
		//System.out.println(opc.get_Value("int_var"));
		for(int i=1;i>0;i++)
		{
			String aux=Erp.getXML();
			//System.out.println(i);
			
			
			if(!aux.equals(ordem))
			{
			
				ordem=aux;
				int duration= (((int)System.currentTimeMillis()-start)/1000);
				//System.out.println(duration);
				parse.parse(ordem, OL,duration);
				System.out.println("------------------------------------------------------------");
										
			}
			
			if((OL.LengthOrderList()-OL.DoneOrders)!=0)
			{
				
					
					prio=OL.OrdemPrioritária();
					if(prio != null)
					{
					prio.orderActivate();
					prio.doOrder(pr);
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
			Thread.sleep(1000);
		
		}
	
	}

}
