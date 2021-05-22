package mes;


import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dijkstraAlgorithm_Test.test;


public class Main {

	static PathFinder pr = new PathFinder();
	static TransformationTable[] tts = new TransformationTable[8];
	static OPC_UA opc=new OPC_UA();
	static SwingApp sw = new SwingApp();
	static OrdersState OL=new OrdersState();
	static int start=(int) System.currentTimeMillis();
	//static DataBase DB = new DataBase();
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException, SQLException {

		//------------------------------------------------PAULO--------------------------------------------------------------//

		/*
		test t = new test();
		t.doTest();
		 */

		//int orderNumber = 1;


		//Unloading u = new Unloading(4, "P4","P2",2);
		//Transformação trans1 = new Transformação(100, "P1", "P8", 2, 1, 300, 0, 0);
		//Transformação trans2 = new Transformação(101, "P4", "P8", 5, 2, 100, 0, 0);
		//Transformação trans3 = new Transformação(102, "P4", "P6", 20, 0, 10, 0, 0);
		//SoredInWarehouse[] siw = new SoredInWarehouse[9];
		//if(DB.existeArm(1))
		//{
			//DB.getOrdersList();
			//pr.sys=DB.get_armazem();
			//pr.sys.print_quantityPieces();
			//pr.mchs=DB.get_maquinaID();
			//pr.mchs[0].print_machine();
			//pr.pshs=DB.get_pushers();
			//pr.pshs[0].print_Pusher();
		//}
		//else
		//{
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
			/*
		//OL.printOrders();

		System.out.println("------------------------------------------------------------");

		OL.addOrder(trans1);
		OL.printOrders();

		System.out.println("------------------------------------------------------------");
		pr.buildPathTransformation(trans1,tts);

		System.out.println("------------------------------------------------------------");

		TimeUnit.SECONDS.sleep(2);

		OL.addOrder(trans2);
		OL.printOrders();

		System.out.println("------------------------------------------------------------");

		TimeUnit.SECONDS.sleep(2);
		pr.buildPathTransformation(trans2,tts);


		OL.addOrder(trans3);
		OL.printOrders();
		System.out.println("------------------------------------------------------------");

		pr.buildPathTransformation(trans3,tts);

		//pr.mchs[0].state = false;
		//pr.mchs[1].state = false;
		//pr.mchs[2].state = false;
		//pr.mchs[3].state = false;

		//System.out.println("------------------------------------------------------------");

		//pr.buildPathTransformation(trans2,tts);

		//System.out.println("------------------------------------------------------------");
		//pr.buildPathUnloading(u);

		//System.out.println("------------------------------------------------------------");

			 */

		//}
		//----------------------------------------------------LOIRO---------------------------------------------------------//
		opc.connect();
		sw.initApp();
		//sw.run();

		//System.out.println("OK");
		//OL.addOrder(trans1);
		pr.trans_before = new Transformação();
		pr.trans_before.orderNumber = -1;
		int l=0;
		String ordem="aaa";
		XML_parser parse=new XML_parser();
		Erp_connection Erp =new Erp_connection(OL);


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
					//System.out.println("OLA");
					//DB.storeOrder(prio);
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
