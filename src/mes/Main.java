package mes;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;



public class Main {

	static PathFinder pr = new PathFinder();
	static TransformationTable[] tts = new TransformationTable[8];
	static OPC_UA opc=new OPC_UA();
	static SwingApp sw = new SwingApp();
	static OrdersState OL=new OrdersState();
	static int start=(int) System.currentTimeMillis();
	static    DatagramPacket packet;
	static 	Erp_connection Erp ;
	static XML_parser parse;
	static DataBase DB = new DataBase();
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException, SQLException {

		tts[0] = new TransformationTable("P1","P2","T1",15);
		tts[1] = new TransformationTable("P4","P5","T1",15);
		tts[2] = new TransformationTable("P6","P8","T1",15);
		tts[3] = new TransformationTable("P2","P3","T2",15);
		tts[4] = new TransformationTable("P5","P6","T2",30);
		tts[5] = new TransformationTable("P3","P4","T3",15);
		tts[6] = new TransformationTable("P5","P9","T3",30);
		tts[7] = new TransformationTable("P6","P7","T3",30);
		
		if(DB.existeArm(1))
		{
			
			pr.sys=DB.get_armazem();
			start=DB.get_startTime();
			pr.sys.print_quantityPieces();
			pr.mchs=DB.get_maquinaID();
			pr.mchs[0].print_machine();
			pr.pshs=DB.get_pushers();
			pr.pshs[0].print_Pusher();
			opc.connect();
			Thread.sleep(1000);
			DB.getOrdersList();
		}
		else
		{
			
			pr.initializeMachines();
			System.out.println("------------------------------------------------------------");

			pr.initializeSystemState();

			System.out.println("------------------------------------------------------------");

			pr.initializePushers();

			System.out.println("------------------------------------------------------------");
			opc.connect();
		}

		sw.initApp();
		//sw.run();

		//System.out.println("OK");
		//OL.addOrder(trans1);
		pr.aux_trans1 = new Transformação();
		pr.aux_trans2 = new Transformação();
		pr.aux_trans1.orderNumber = -1;
		pr.aux_trans2.orderNumber = -1;
		pr.transBefore = new Transformação();
		pr.transBefore.orderNumber = -1;
		pr.aux_trans1Before = new Transformação();
		pr.aux_trans2Before = new Transformação();
		pr.aux_trans1Before.orderNumber = -1;
		pr.aux_trans2Before.orderNumber = -1;
		
		int l=0;
		String ordem="aaa";
		Erp=new Erp_connection(OL);
		parse=new XML_parser();
	


		Erp.start();


		Order prio = new Order(0,0,0,0);

		
		//System.out.println(opc.get_Value("int_var"));
		for(int i=1;i>0;i++)
		{
			
			String aux=Erp.getXML();
			//System.out.println(i);


			if(!aux.equals(ordem))
			{	
				ordem=aux;
				
				System.out.println("------------------------------------------------------------");

			}

			if((OL.LengthOrderList()-OL.DoneOrders)!=0)
			{


				prio=OL.OrdemPrioritária();
				if(prio != null)
				{
					
					prio.orderActivate();
					//System.out.println("OLA");
					DB.storeOrder(prio);	
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
