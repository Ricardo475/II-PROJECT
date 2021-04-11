package mes;

import java.util.ArrayList;

public class PathFinder {
	
	int cell, piece, typeBegin, typeEnd;
	
	Machine mchs[] = new Machine[4];
	
	int[] position = {0,3};
	//ArrayList<Machine> machines = new ArrayList<Machine>();
	
	
	public static String[][] fabric_map = {{"[CL1T0]","[CL0T1]","[ALT5]","[XXXX]","[ART1]","[CR0T1]","[CR1T0]","[CR2Ta]" ,"[CR2T1b]"},
									 	  {"[CL1T1]","[CL0T2]","[XXXX]","[XXXX]","[XXXX]","[CR0T2]","[CR1T1]","[CR2T2]" ,"[XXXX]"  },
									      {"[CL1T2]","[CL0T3]","[XXXX]","[XXXX]","[XXXX]","[CR0T3]","[CR1T2]","[CR2T3]" ,"[XXXX]"  },
									      {"[CL1T3]","[CL0T4]","[ALT6]","[XXXX]","[ART2]","[CR0T4]","[CR1T3]","[CR2T4]" ,"[XXXX]"  },
									      {"[CL1T4]","[CL0T5]","[ALT7]","[XXXX]","[ART3]","[CR0T5]","[CR1T4]","[CR2T5]" ,"[XXXX]"  },
									      {"[CL1T5]","[CL0T6]","[ALT8]","[XXXX]","[ART4]","[CR0T6]","[CR1T5]","[CR2T6]" ,"[CR2T7b]"},
									      {"[XXXX]" ,"[XXXX]" ,"[XXXX]","[XXXX]","[XXXX]","[XXXX]" ,"[XXXX]" ,"[CR2T7a]","[XXXX]" }};
	
	
	/*LH - Linear Conveyer Horizontal
	  LV - Linear Conveyer Vertical	
	  R - Rotative Conveyer
	  W - Warehouse
	  WI - Warehouse In
	  WO - Warehouse Out
	  LMx - Left Machine nºx
	  RMx - Right Machine nºx
	  P - Pusher
	  X - Doesn't exist
	  
	  B - Busy
	  
	  Note: The 2nd to last row, the below R is connected to the LH's on his left and right
	*/
	
	

	String buildPathTransformation(Transformação trans) {
		
		String result = " ";
		/*String destMachine = " ";
		Machine mch = new Machine();
		
		for(int i = 0; i < mchs.length; i++) {
			
			if(mchs[i].state == true && mchs[i].correctPart(trans.To)) {
				mch.setMachine(mchs[i].machineID, mchs[i].tool, mchs[i].processTime);
				destMachine = "MACHINE" + mch.machineID;
				System.out.println("------------------------------------------------------------");
				break;
			}
			
		}
		
		//LEFT
		
		int length = 1;
		boolean out = false;
		
		while(length!=0) {
			
			if(maze[0][2] == "WI") {
				
				result = "[ALT5][CL0T1][CL0T2]";
			
				if(destMachine.compareTo("MACHINE0")==0) {
				
					result = result + "[CL1T1]";
					position[0] = 1;
					position[1] = 0;
				
					//System.out.println("OK");
				}
			}
			
			length--;	
		}

		
		if(maze[position[0]][position[1]] == "LM1")
			result = result + "[CL0T2][CL0T3][CL0T4][CL0T5][CL0T6][ALT8][ALT7][ALT6]";
		
		
		*/
		return result;
		
		
	}
	
	
	/*
	void print_machines() {
		
		for(int i = 0; i<machines.length; i++) {
			//String state
			System.out.print("MACHINE NO:" + i +" Tool: " + machines[i].tool + " || Processing Time: " + machines[i].processTime + "|| State: " + machines[i].state);
			
		}
	}
	*/
	
	void initializeMachines() {
		
		for(int i = 0; i < 4; i++) {
			
			mchs[i] = new Machine();
			mchs[i].setMachine(i, "T1", 15);
			
			mchs[i].print_machine();
		}
		
	}
	
	
    void print_fabric() {
    	
	/*	for(int i=0;i < maze.length;i++) 
		{
	
			for(int j = 0; j< maze[0].length; j++) {
				System.out.print(maze[i][j]);
				System.out.print(" ");
				
			}
	
			System.out.println();
		}*/
    }
										 
				

}
