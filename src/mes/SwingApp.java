package mes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class SwingApp implements Runnable{
	
	Thread th;
	JFrame frame_mesStats;
	
	JTable tableMch;
	JTable tableStocks;
	JTable tableUnl;
	JTable tableLdr;
	JTable tableTrans;
	
	JScrollPane scrollerMch;
	JScrollPane scrollerStk;
	JScrollPane scrollerUnl;
	JScrollPane scrollerLdr;
	JScrollPane scrollerTrans;
	
	int ol_length;
	
	
	
	public void initApp() {
		
		th = new Thread(this);
		ol_length = 0;
		frame_mesStats = new JFrame();
		frame_mesStats.setTitle("FabricUI");
		frame_mesStats.setSize(1200,1000);
		frame_mesStats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_mesStats.setVisible(true);
		frame_mesStats.getContentPane().setLayout(null);
		MainFrame(frame_mesStats);
		th.start();
	}


	void MainFrame(JFrame j) {
					
		//        //
		// TITLES //
		//		  //

		
		JLabel lb_mchStats = new JLabel("Machine Statistics");
		lb_mchStats.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mchStats.setBounds(25, 25, 560, 30);
		lb_mchStats.setFont(new Font("Georgia",Font.BOLD,20));
		//lb_mchStats.setForeground(Color.BLUE);
		j.getContentPane().add(lb_mchStats);
		
		JLabel lb_Stocks = new JLabel("Stocks Available");
		lb_Stocks.setHorizontalAlignment(SwingConstants.CENTER);
		lb_Stocks.setBounds(725,25, 200, 30);
		lb_Stocks.setFont(new Font("Georgia",Font.BOLD,20));
		//lb_Stocks.setForeground(Color.RED);
		j.getContentPane().add(lb_Stocks);
		
		JLabel lb_unload = new JLabel("Unloaded Workpieces");
		lb_unload.setHorizontalAlignment(SwingConstants.CENTER);
		lb_unload.setBounds(25,275, 560, 30);
		lb_unload.setFont(new Font("Georgia",Font.BOLD,20));
		//lb_unload.setForeground(Color.green);
		j.getContentPane().add(lb_unload);

		JLabel lb_load = new JLabel("Load Orders");
		lb_load.setHorizontalAlignment(SwingConstants.CENTER);
		lb_load.setBounds(700,275, 240, 30);
		lb_load.setFont(new Font("Georgia",Font.BOLD,20));
		//lb_load.setForeground(Color.orange);
		j.getContentPane().add(lb_load);
		
		JLabel lb_trans = new JLabel("Transformation Orders");
		lb_trans.setHorizontalAlignment(SwingConstants.CENTER);
		lb_trans.setBounds(25,560, 1000, 30);
		lb_trans.setFont(new Font("Georgia",Font.BOLD,20));
		//lb_trans.setForeground(Color.MAGENTA);
		j.getContentPane().add(lb_trans);
		
		
		//           //
		// SCROLLERS //
		//		     //
		
		
		//Scroller Machines
		scrollerMch = new JScrollPane();
		scrollerMch.setBounds(25, 85, 560, 151);
		j.getContentPane().add(scrollerMch);		
		
		//Scroller Stocks
		scrollerStk = new JScrollPane();
		scrollerStk.setBounds(750, 68, 140, 167);
		j.getContentPane().add(scrollerStk);	
		
		scrollerUnl = new JScrollPane();
		scrollerUnl.setBounds(25, 320, 560, 71);
		j.getContentPane().add(scrollerUnl);		
		
		scrollerLdr = new JScrollPane();
		scrollerLdr.setBounds(720, 320, 200, 200);
		j.getContentPane().add(scrollerLdr);	

		scrollerTrans = new JScrollPane();
		scrollerTrans.setBounds(25, 600, 1000, 200);
		j.getContentPane().add(scrollerTrans);
		
		//        //
		// TABLES //
		//        //
		
		//Table Machines
		tableMch = new JTable();
		scrollerMch.setViewportView(tableMch);
		tableMch.setModel(new DefaultTableModel(
				new Object[][] {
					{"CL1T1", null,null,null,null,null,null,null,null},
					{"CL1T2", null,null,null,null,null,null,null,null},
					{"CL1T3", null,null,null,null,null,null,null,null},
					{"CL1T4", null,null,null,null,null,null,null,null},
					{"CR1T1", null,null,null,null,null,null,null,null},
					{"CR1T2", null,null,null,null,null,null,null,null},
					{"CR1T3", null,null,null,null,null,null,null,null},
					{"CR1T4", null,null,null,null,null,null,null,null},
					}, 
				new String [] {"Machine", "Time(s)", "P1", "P2","P3","P4","P5","P6","Total"}
				
	));
		tableMch.getColumnModel().getColumn(0).setPreferredWidth(80);
		
		for(int i = 1; i <9;i++) {
			
			tableMch.getColumnModel().getColumn(i).setPreferredWidth(60);
		}
		
		scrollerMch.setViewportView(tableMch);
				
		//Table Stocks
		tableStocks = new JTable();
		scrollerStk.setViewportView(tableStocks);
		tableStocks.setModel(new DefaultTableModel(
				new Object[][] {
					{"P1", null},
					{"P2", null},
					{"P3", null},
					{"P4", null},
					{"P5", null},
					{"P6", null},
					{"P7", null},
					{"P8", null},
					{"P9", null},
					}, 
				new String [] {"Piece Type", "Quantity"}
				
	));
			

		tableStocks.getColumnModel().getColumn(0).setPreferredWidth(80);
		tableStocks.getColumnModel().getColumn(1).setPreferredWidth(60);
		
		scrollerStk.setViewportView(tableStocks);
		
		//Table Unloaded Workpieces
		
		tableUnl = new JTable();
		scrollerUnl.setViewportView(tableUnl);
		tableUnl.setModel(new DefaultTableModel(
				new Object[][] {
					{"PM1", null, null, null, null, null, null, null, null, null, null},
					{"PM2", null, null, null, null, null, null, null, null, null, null},
					{"PM3", null, null, null, null, null, null, null, null, null, null},
					}, 
				new String [] {"Unloader ID", "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9", "Total"} 
	));
		
		tableUnl.getColumnModel().getColumn(0).setPreferredWidth(80);
		
		for(int i = 1; i < 11;i++) {
			tableUnl.getColumnModel().getColumn(i).setPreferredWidth(48);
		}
		
		scrollerUnl.setViewportView(tableUnl);
		
		//Table Loaded pieces orders
		
		tableLdr = new JTable();
		scrollerLdr.setViewportView(tableLdr);
		tableLdr.setModel(new DefaultTableModel(
				new Object[][] {
					}, 
				new String [] {"Order ID", "Time", "Piece Type"} 
	));
		
		
		tableUnl.getColumnModel().getColumn(0).setPreferredWidth(80);
		
		for(int i = 1; i < 11;i++) {
			tableUnl.getColumnModel().getColumn(i).setPreferredWidth(60);
		}
		
		scrollerLdr.setViewportView(tableLdr);
		
		//Table Transformation orders
		
		tableTrans = new JTable();
		scrollerTrans.setViewportView(tableTrans);
		tableTrans.setModel(new DefaultTableModel(
				new Object[][] {
					}, 
				new String [] {"Order ID", "Status", "Entry Time", "Start Time", "Initial Piece", "Final Piece", "End Time", "Deadline", "Processed", "Processing", "To be Processed", "Total","Penalty"} 
	));
		
		tableTrans.getColumnModel().getColumn(0).setPreferredWidth(50);
		
		for(int i = 1; i < 12;i++) {
			tableTrans.getColumnModel().getColumn(i).setPreferredWidth(75);
		}
		
		tableTrans.getColumnModel().getColumn(10).setPreferredWidth(100);
		
		scrollerTrans.setViewportView(tableTrans);
	}
	
	
		

	
	@Override
	public void run() {
	
		try {
			Thread.sleep(2000);
			while(true) {
				
				//
				// MISSING DATABASE CONNECTION (YET)
				//
				
				UpdateMachsStats();
				UpdateStkStats();
				UpdateUnlStats();
				UpdateOrders();
				AddOrders();
				Thread.sleep(500);
				
			}
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}




	private void UpdateMachsStats() {
		
		for(int i = 0; i < 8;i++) {				
			tableMch.setValueAt( Main.pr.mchs[i].totalOperatingTime, i, 1);
			tableMch.setValueAt( Main.pr.mchs[i].nP1, i, 2);
			tableMch.setValueAt(Main.pr.mchs[i].nP2, i, 3);
			tableMch.setValueAt(Main.pr.mchs[i].nP3, i, 4);
			tableMch.setValueAt(Main.pr.mchs[i].nP4, i, 5);
			tableMch.setValueAt(Main.pr.mchs[i].nP5, i, 6);
			tableMch.setValueAt(Main.pr.mchs[i].nP6, i, 7);
			tableMch.setValueAt(Main.pr.mchs[i].nTotalOperated, i, 8);
			//Main.pr.mchs[i].print_machine();
		}
		
		scrollerMch.setViewportView(tableMch);
	}
	

	private void UpdateStkStats() {
		
		tableStocks.setValueAt(Main.pr.sys.nP1Warehouse, 0, 1);
		tableStocks.setValueAt(Main.pr.sys.nP2Warehouse, 1, 1);
		tableStocks.setValueAt(Main.pr.sys.nP3Warehouse, 2, 1);
		tableStocks.setValueAt(Main.pr.sys.nP4Warehouse, 3, 1);
		tableStocks.setValueAt(Main.pr.sys.nP5Warehouse, 4, 1);
		tableStocks.setValueAt(Main.pr.sys.nP6Warehouse, 5, 1);
		tableStocks.setValueAt(Main.pr.sys.nP7Warehouse, 6, 1);
		tableStocks.setValueAt(Main.pr.sys.nP8Warehouse, 7, 1);
		tableStocks.setValueAt(Main.pr.sys.nP9Warehouse, 8, 1);
		
		scrollerStk.setViewportView(tableStocks);
	}
	
	private void UpdateUnlStats() {
		
		for(int i = 0; i < 3; i++){
			tableUnl.setValueAt(Main.pr.pshs[i].p1Pieces_unloaded, i, 1);
			tableUnl.setValueAt(Main.pr.pshs[i].p2Pieces_unloaded, i, 2);
			tableUnl.setValueAt(Main.pr.pshs[i].p3Pieces_unloaded, i, 3);
			tableUnl.setValueAt(Main.pr.pshs[i].p4Pieces_unloaded, i, 4);
			tableUnl.setValueAt(Main.pr.pshs[i].p5Pieces_unloaded, i, 5);
			tableUnl.setValueAt(Main.pr.pshs[i].p6Pieces_unloaded, i, 6);
			tableUnl.setValueAt(Main.pr.pshs[i].p7Pieces_unloaded, i, 7);
			tableUnl.setValueAt(Main.pr.pshs[i].p8Pieces_unloaded, i, 8);
			tableUnl.setValueAt(Main.pr.pshs[i].p9Pieces_unloaded, i, 9);
			tableUnl.setValueAt(Main.pr.pshs[i].totalPieces_unloaded, i, 10);
		}
		scrollerUnl.setViewportView(tableUnl);
		
	}
	
	private void AddOrders() {
		
		//System.out.println("Supp");
		
		if(ol_length < Main.OL.LengthOrderList()) {
			ol_length = Main.OL.LengthOrderList();
			
			//System.out.println(rows);
			for(int i = 0; i <  Main.OL.LengthOrderList(); i++) {
				
				Order o = new Order();
				o = Main.OL.OrdersList.get(i);
				
				if(o.toString().contains("Transformation")) {
					
					int rows = tableTrans.getRowCount();
					boolean add = true;
					//tableTrans.setValueAt(aValue, row, column);
					if(rows>0) {
						for(int j = 0; j < rows; j++) {
							int id_table = (int) tableTrans.getValueAt(j, 0);
							//System.out.println("ID_GET: " + id_table);
							
							if (id_table == o.getOrderNumber()) {
								add = false;
							}
						}
					}
					
					if(add) {
						Vector v = new Vector();
						
						v.add(o.getOrderNumber());
						
						if(o.activeOrder && o.done)v.add("Done");
						else if(o.activeOrder && !o.done) v.add("Running");
						else v.add("Waiting");
						
						v.add(o.instanteChegada);
						v.add(((Transformação)o).startTime);
						v.add(((Transformação)o).From);
						v.add(((Transformação)o).To);
						v.add(((Transformação)o).finTime);
						v.add(((Transformação)o).deadline);
						v.add(((Transformação)o).quantProcessed);
						v.add(((Transformação)o).quantExe);
						v.add(((Transformação)o).quantToBe);
						v.add(((Transformação)o).quantTotal);
						v.add(0);
						
						DefaultTableModel model = (DefaultTableModel) tableTrans.getModel();
						model.addRow(v);
						scrollerTrans.setViewportView(tableTrans);
						
					}
					
				}
				
				else if(o.toString().contains("Loading")) {
					
					int rows = tableLdr.getRowCount();
					boolean add = true;
					//tableTrans.setValueAt(aValue, row, column);
					if(rows>0) {
						for(int j = 0; j < rows; j++) {
							int id_table = (int) tableLdr.getValueAt(j, 0);
							
							//System.out.println("ID: " + id_table);
							if (id_table == o.getOrderNumber()) add = false;
						}
					}
					
					if(add) {
						Vector v = new Vector();

						v.add(o.getOrderNumber());
						v.add(o.instanteChegada);
						v.add(((Loading)o).pieceType);
						
						DefaultTableModel model = (DefaultTableModel) tableLdr.getModel();
						model.addRow(v);
						scrollerLdr.setViewportView(tableLdr);						
						
					}
					
				}
				
			}
			
		}
	}
	

		private void UpdateOrders() {

			for(int i = 0; i <  Main.OL.LengthOrderList(); i++) {
				
				Order o = new Order();
				o = Main.OL.OrdersList.get(i);
				
				if(o.toString().contains("Transformation")) {
					
					for(int j = 0; j < tableTrans.getRowCount();j++) {
							
						if(o.getOrderNumber() == (int) tableTrans.getValueAt(j, 0)) {
							
							//System.out.println("Nº "+ j + ":" + tableTrans.getValueAt(j, 1));
							
							if (tableTrans.getValueAt(j, 1)=="Waiting" && o.activeOrder && !o.done) {
								//System.out.println(tableTrans.getValueAt(j, 1) + " to RUNNING");
								tableTrans.setValueAt("Running", j, 1);
							}
							else if((tableTrans.getValueAt(j, 1)=="Waiting" || tableTrans.getValueAt(j, 1)=="Running") && o.done && ((Transformação)o).flagEnd){
								//System.out.println(tableTrans.getValueAt(j, 1) + " to DONE");
								tableTrans.setValueAt("Done", j, 1);		
							}
							
							
							
							tableTrans.setValueAt(((Transformação)o).startTime, j, 3);
							tableTrans.setValueAt(((Transformação)o).finTime, j, 6);
							tableTrans.setValueAt(((Transformação)o).quantProcessed,j,8);
							tableTrans.setValueAt(((Transformação)o).quantExe,j,9);
							tableTrans.setValueAt(((Transformação)o).quantToBe,j,10);
							tableTrans.setValueAt(((Transformação)o).PenaltyInc,j,12);
							
							scrollerTrans.setViewportView(tableTrans);	
						}
					}	

				}
			}
			
		}


}
