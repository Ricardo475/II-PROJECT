package mes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

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
	
	
	
	
	public void initApp() {
		
		th = new Thread(this);
		
		frame_mesStats = new JFrame();
		frame_mesStats.setTitle("FabricUI");
		frame_mesStats.setSize(1200,1000);
		frame_mesStats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_mesStats.setVisible(true);
		frame_mesStats.getContentPane().setLayout(null);
		MainFrame(frame_mesStats);
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
				new String [] {"Loader ID", "Time", "Piece Type"} 
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
				new String [] {"ID", "Status", "Entry Time", "Start Time", "Initial Piece", "Final Piece", "End Time", "Deadline", "Processed", "Processing", "To be Processed", "Total"} 
	));
		
		tableTrans.getColumnModel().getColumn(0).setPreferredWidth(50);
		
		for(int i = 1; i < 11;i++) {
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
				
				
			}
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}

}
