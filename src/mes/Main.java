package mes;


import java.io.IOException;
import java.net.ServerSocket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dijkstraAlgorithm_Test.test;


public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
		
		//------------------------------------------------PAULO--------------------------------------------------------------//
		
		/*
		test t = new test();
		t.doTest();
		
		*/
		
		PathFinder pr = new PathFinder();
		TransformationTable[] tts = new TransformationTable[8];
		Transformação trans = new Transformação(1, "P3", "P9", 10, 0, 0, 0, 0);
		
		pr.initializeMachines();
		pr.mchs[1].changeTool("T2");
		pr.mchs[2].changeTool("T3");
		
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
		
		pr.buildPathTransformation(trans,tts);
		
		
		//System.out.println("------------------------------------------------------------");
		
		
		
		
		//----------------------------------------------------LOIRO---------------------------------------------------------//
		/*
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
