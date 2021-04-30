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
		//Unloading u = new Unloading(4, "P4","P2",2);
		//Transformação trans1 = new Transformação(45, "P1", "P8", 10, 0, 0, 0, 0);
		//Transformação trans2 = new Transformação(45, "P1", "P2", 10, 0, 0, 0, 0);
		//SoredInWarehouse[] siw = new SoredInWarehouse[9];
		
		pr.initializeMachines();
		//pr.mchs[1].changeTool("T2");
		//pr.mchs[2].changeTool("T3");
		//pr.mchs[5].changeTool("T2");
		//pr.mchs[6].changeTool("T3");
		
		System.out.println("------------------------------------------------------------");
		
		pr.initializeSystemState();
		
		//System.out.println("------------------------------------------------------------");
		
		
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
		
		//pr.buildPathTransformation(trans1,tts);
		//OL.addOrder(trans);
		//pr.mchs[0].state = false;
		//pr.mchs[1].state = false;
		//pr.mchs[2].state = false;
		//pr.mchs[3].state = false;
		
		//System.out.println("------------------------------------------------------------");
		
		//pr.buildPathTransformation(trans2,tts);
		
		//System.out.println("------------------------------------------------------------");
		//pr.buildPathUnloading(u);
		
		//System.out.println("------------------------------------------------------------");
		
		
		
		
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
