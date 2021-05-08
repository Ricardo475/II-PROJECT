package mes;

public class PathFinder {
	
	int cell, piece, typeBegin, typeEnd;
	
	Machine mchs[] = new Machine[8];
	Pusher pshs[] = new Pusher[3];
	SystemState sys;
	
	
	int[] buildPathTransformation(Transformação trans, TransformationTable[] tts) {
		
		//ONLY LEFT SIDE
		
		//
		//TO DO: DECIDE HOW TO CHOOSE LEFT AND RIGHT SIDE
		//
		
		String result = "";
		int[] res = {0,0,0,0,0,0};
		String transformation = contructTranformations(trans, tts);
		String[] divideTransformation;
		divideTransformation = transformation.split("/");
		
		int[] counter_time = {0,0,0,0};
		
		String aux_result = "[";
		
		
		//
		// SAVE AVAILABLE MACHINES
		//
		
		Machine mchs_available[] = new Machine[4];
		int counter = 0;
		
		
		for(int i = 0; i < 4; i++) {
			
			if(mchs[i].state) {
				mchs_available[counter] = new Machine();
				mchs_available[counter] = mchs[i];
			//	mchs_available[counter].print_machine();
				
				counter ++;
			}
					
		}
		
		//
		// SAVE TOOLS TO USE AND IF THERE IS MORE OR LESS THEN 3 TRANSFORMATIONS
		//		
		
		boolean[] toolUsed = {false,false,false};
 		int tool_counter = 0;
 		
		for(int i = 0; i < divideTransformation.length-1;i++) {
			
			for(int j = 0; j < tts.length; j++) {
				
				if(tool_counter>3) break;
				
				else if(tts[j].existsTranformationInTable(divideTransformation[i],divideTransformation[i+1])) {
					
					 if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T1") {
						 toolUsed[0] = true;
						 tool_counter++;
						 break;
					 }
					 else if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T2") {
						 toolUsed[1] = true;
						 tool_counter++;
						 break;
					 }
					
					 else if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T3") {
						 toolUsed[2] = true;
						 tool_counter++;
						 break;
					 }
					
					
				}
				
				
			}
			
			
		}
		
		//System.out.println("T1: " +toolUsed[0]+ " || T2: " + toolUsed[1] + " || T3: " + toolUsed[2]);
		//System.out.println(tool_counter);
	
		
		//
		// TOOLS CHANGES BEFORE THE PATH
		//
		
		if(tool_counter == 1 && counter>0) {
			
			if(toolUsed[0] == true) {
				
				for(int i = 0; i < counter; i++)
					mchs[mchs_available[i].machineID].changeTool("T1");
			}
			
			else if(toolUsed[1] == true) {
				
				for(int i = 0; i < counter; i++)
					mchs[mchs_available[i].machineID].changeTool("T2");
			}
			
			else if(toolUsed[2] == true) {
				
				for(int i = 0; i < counter; i++)
					mchs[mchs_available[i].machineID].changeTool("T3");
			}
			
		}
		
		else if(tool_counter == 2 && counter>1) {
			
			if(toolUsed[0] == true && toolUsed[1] == true) {
				
				for(int i = 0; i < counter; i=i+2) {
					mchs[mchs_available[i].machineID].changeTool("T1");
					mchs[mchs_available[i+1].machineID].changeTool("T2");
				}
				
			}
			
			else if(toolUsed[1] == true && toolUsed[2] == true) {
				
				for(int i = 0; i < counter; i=i+2) {
					mchs[mchs_available[i].machineID].changeTool("T2");
					mchs[mchs_available[i+1].machineID].changeTool("T3");
				}
			}
			
			else if(toolUsed[2] == true && toolUsed[0] == true) {
				
				for(int i = 0; i < counter; i++) {
					mchs[mchs_available[i].machineID].changeTool("T1");
					mchs[mchs_available[i+1].machineID].changeTool("T3");
				}
			}
			
			
		}
		
		else if(tool_counter >= 3 && counter>2) {
			
				mchs[mchs_available[3].machineID].changeTool("T1");
				mchs[mchs_available[2].machineID].changeTool("T3");
				mchs[mchs_available[1].machineID].changeTool("T2");
				mchs[mchs_available[0].machineID].changeTool("T1");
			
		}
		

		
		//
		// PATHFINDER
		//
		
		//int breaker = 0;
		
		for(int k = 0; k < divideTransformation.length-1;k++) {

			for(int n = 3; n >= 0; n--) {
				
				boolean already_chosen = false;
				
				if(mchs[n].state) {
					
					if(k==0 && n==3 && tool_counter >=3) continue;
					
					for(int i = 0; i<tts.length;i++) {
						
						if(tts[i].existsTranformationInTable(divideTransformation[k], divideTransformation[k+1])) {
							
							if(mchs[n].tool == tts[i].get_toolNeeded(divideTransformation[k], divideTransformation[k+1])) {
								
								aux_result = aux_result + (n+1);
								res[k] = (n+1);
								counter_time[n] = counter_time[n] + tts[i].processTimeSeconds;
								already_chosen  = true;
								break;
							}
							
						}
						
					}
					if(already_chosen) break;	
					
				}
				
				else if (n==0) { 
					System.out.println("NO MACHINES AVAILABLE");
					res[0] = 0;
					res[1] = 0;
					res[2] = 0;
					res[3] = 0;
					res[4] = 0;
					res[5] = 0;
					break;
					//n = 4;
					//breaker++;
					/*
					if(breaker == 10) {
						breaker = 0;
						mchs[0].state = true;
						mchs[1].state = true;
						mchs[2].state = true;
						mchs[3].state = true;
					}
					*/
				}
			}
			
		}
		/*
		for(int i = 0; i<res.length;i++) {
			
			if(res[i]!=0)
				mchs[res[i]-1].state = false;
			
		}
		*/
		for(int i= 0; i<4;i++) 
			mchs[i].print_machine();
	
		
		if(res[0]!=0) {
			for(int i = 0; i<4;i++) {
				mchs[i].setToolCodesys(i);
			}
		}
		
		if(aux_result.length()!=7) {
			
			int length = aux_result.length();
			
			for(int i = 0; i< 7-length; i++) {
				
				aux_result = aux_result + "0";
				
			}
		}
		

		result = "TL" + trans.quantTotal + " [";
		
		for(int i = 0; i < res.length;i++)
			result = result + res[i];
		
		result = result + "]";
		System.out.println("PATHING:" + result);
		System.out.println("TIMES: [" + counter_time[0] + " " + counter_time[1] + " " + counter_time[2] + " "  + counter_time[3] + "]" );
		
		return res;
		
	}
	
	
	
	private String contructTranformations(Transformação trans, TransformationTable[] tts) {
		
		String result = trans.From;
		String newBegin = trans.From;
		int n = 0;
		
		while(newBegin.compareTo(trans.To)!=0) {
			
			for(int i = 0; i<tts.length;i++) {
				
				if(tts[i].existsTranformationInTable(newBegin, trans.To)) {
					newBegin = tts[i].producedPartByStartingPart(newBegin);;
					result = result.concat("/" + newBegin);
					break;
				}
				
				else if(tts[i].startingPart.compareTo(newBegin) == 0) {
					if(tts[i].startingPart.compareTo("P5")==0 && trans.To.compareTo("P9")!=0) {
						newBegin = "P6";
						result = result.concat("/" + newBegin);
						break;
					}
					else if(tts[i].startingPart.compareTo("P5")==0 && trans.To.compareTo("P9")==0) {
						newBegin = "P9";
						result = result.concat("/" + newBegin);
						break;
					}
					else if(tts[i].startingPart.compareTo("P6")!=0) {
						newBegin = tts[i].producedPartByStartingPart(newBegin);
						result = result.concat("/" + newBegin);
						break;	
					}	
				
				}
				
				
			}
			n++;
			if(n > 10) {
				System.out.println("INVALID PATHING");
				return "-1";
			}
			
		}
		System.out.println("TRANSFORMATIONS: " + result);
		return result;
	}
	
	
	
	
	public int buildPathUnloading(Unloading un) {
		 int i = 0;
		 String result = "P" + un.quantity + "->";
		 
		 if(un.dest == "P1") {
			 
			 i = 1;
			 result = result + "PUSHER NO" + i;
		 }
		 
		 else if(un.dest == "P2") {
			 
			 i = 2;
			 result = result + "PUSHER NO" + i;
		 }
		 
		 else if(un.dest == "P3") {
			 
			 i = 3;
			 result = result + "PUSHER NO" + i;
		 }
		 
		 System.out.println("UNLOADING: " + result);
		 return i;
		
	}


	/*
	void print_machines() {
		
		for(int i = 0; i<machines.length; i++) {
			//String state
			System.out.print("MACHINE NO:" + i +" Tool: " + machines[i].tool + " || Processing Time: " + machines[i].processTime + "|| State: " + machines[i].state);
			
		}
	}
	*/
	
	
	void initializeSystemState() {
		
		this.sys = new SystemState();
		sys.print_quantityPieces();
		
	}
	
	
	
	void initializeMachines() {
		
		for(int i = 0; i < 8; i++) {
			
			//mchs = new Machine[i];
			mchs[i] = new Machine();
			mchs[i].setMachine(i, "T1");
			mchs[i].print_machine();
		}
		
	}
	
	void initializePushers() {
		
		for(int i = 0; i < 3; i++) {
			
			pshs[i] = new Pusher();
			pshs[i].setPusher(i);
			
			pshs[i].print_Pusher();
		}
		
	}
	
	/*
    void print_fabric() {
    	
		for(int i=0;i < fabric_map.length;i++) 
		{
	
			for(int j = 0; j< fabric_map[0].length; j++) {
				System.out.print(fabric_map[i][j]);
				System.out.print(" ");
				
			}
	
			System.out.println();
		}
    }
		
		*/

				

}
