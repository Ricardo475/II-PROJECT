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
	
	public void initApp() {
		
		th = new Thread(this);
		
		frame_mesStats = new JFrame();
		frame_mesStats.setTitle("FabricUI");
		frame_mesStats.setSize(1000,1000);
		frame_mesStats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_mesStats.setVisible(true);
		frame_mesStats.getContentPane().setLayout(null);
		MainFrame(frame_mesStats);
	}


	void MainFrame(JFrame j) {
		
		//Basic layout
		
		JLabel lb_mchStats = new JLabel("Machine Statistics");
		lb_mchStats.setHorizontalAlignment(SwingConstants.CENTER);
		lb_mchStats.setBounds(25, 25, 300, 20);
		lb_mchStats.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		lb_mchStats.setForeground(Color.blue);
		j.getContentPane().add(lb_mchStats);
		
		//Scroller
		JScrollPane scroller = new JScrollPane();
		scroller.setBounds(25, 65, 300, 200);
		j.getContentPane().add(scroller);		
		
		//Table
		JTable tableMch = new JTable();
		scroller.setViewportView(tableMch);
		tableMch.setModel(new DefaultTableModel(
				new Object[][] {
					{"LINHA1", null,null,null},
					{"LINHA2", null,null,null},
					{"LINHA3", null,null,null},
					{"LINHA4", null,null,null},
					{"LINHA5", null,null,null},
					{"LINHA6", null,null,null},
					{"LINHA7", null,null,null},
					}, 
				new String [] {"MACHINE", "STAT1", "STAT2", "STAT3"}
				
	));
	
		
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
