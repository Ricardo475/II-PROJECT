package mes;

import java.util.ArrayList;

public class PathFinder {
	
	int cell, piece, typeBegin, typeEnd;
	
	Machine mchs[] = new Machine[8];
	Pusher pshs[] = new Pusher[3];
	SystemState sys;
	Transformação trans_before;
	boolean flag_left = true;
	boolean flag_right = false;
	//boolean flag1 = true;
	//boolean flag2 = true;
	
	int[] buildPathTransformation(Transformação trans, TransformationTable[] tts) {
		
		
		System.out.println("------------------------------------------------------TESTING NEW PATHFINDER ALGORITHM------------------------------------------------------");
		
		String result = "";
		int[] res = {0,0,0,0,0,0};
		
		//
		// DECONSTRUCTING TRANSFORMATION
		//
		
		String transformation = contructTranformations(trans, tts);
		String[] divideTransformation;
		divideTransformation = transformation.split("/");
		
		int[] counter_time = {0,0,0,0};
		String tools_before[] = { mchs[0].tool,mchs[1].tool,mchs[2].tool,mchs[3].tool };
		String aux_result = "[";
		
		//
		// SAVE AVAILABLE MACHINES  ----> PODE SER SEPARADO DESTE CODIGO
		//
		
		ArrayList<Machine> mchs_available = new ArrayList<Machine>();
		
		System.out.println("MACHINES AVAILABLE:");
		
		for(int i = 0; i < 4; i++) {	
			if(mchs[i].state) {
				mchs_available.add(mchs[i]);
				mchs[i].print_machine();
			}
			
		}
		
		
		
		//
		// SEE HOW MANY MACHINES NEEDED ----> PODE SER SEPARADO DESTE CODIGO
		//
		
		/*
		int mchs_needed = 0;
		
		for(int i = 0; i < divideTransformation.length-1;i++) {
			mchs_needed++;
			if(mchs_needed == 4) break;
		}
		
		System.out.println("MACHINES NEEDED: " + mchs_needed + "\n");
			
		*/
		//System.out.println("\n");
		
		//
		// COMPARE MACHINES AVAILABLE VS MACHINES NEEDED
		//
		/*
		if(mchs_needed>mchs_available.size()) {
			System.out.println("PASSING TO RIGHT SIDE!" + "\n");
			return buildPathOnRight(trans,tts,divideTransformation);
		}
		else System.out.println("LEFT SIDE IS VALID!" + "\n");
		*/
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
		
		System.out.println("T1: " +toolUsed[0]+ " || T2: " + toolUsed[1] + " || T3: " + toolUsed[2]);
		System.out.println("TOOL COUNTER: " + tool_counter + "\n");
		
		//
		// TOOLS CHANGES BEFORE THE PATH (mes only)
		//
		
		pathing_changeToolsMES(tool_counter,mchs_available, toolUsed,trans.quantTotal);


		for(int i = 0; i < mchs_available.size();i++)
			mchs[i].print_machine();
		
		
		//
		// SEE IF THE LAST CYCLE WAS FROM THE SAME ORDER	
		//
		
		
		if(trans_before.getOrderNumber()==trans.getOrderNumber() && tool_counter>3) {
			
			if(flag_left) {
				
		
				if(trans.pathRight[0]!=0) {
					
					
					if((short)Main.opc.get_Value("CR1T4.Oper_Faltam", 3)==0) {
						System.out.println("--PIECE GO RIGHT--");
						res = trans.pathRight;
						
						for(int i = 0; i<res.length;i++) {
							if(res[i] == 0) break;
							
							mchs[res[i]-1].state = false;
						}
						flag_left = false;
						flag_right = true;
					}
					
					return res;
				}
				else {
					flag_left = false;
					flag_right = true;
					return buildPathOnRight(trans,tts,divideTransformation);
				}
			}
			
			else if(flag_right) {
				
				
				if(trans.pathLeft[0]!=0) {
					if((short)Main.opc.get_Value("CL1T4.Oper_Faltam", 2)==0) {
						System.out.println("--PIECE GO LEFT--");
						
						res = trans.pathLeft;
							
						for(int i = 0; i<res.length;i++) {
							if(res[i] == 0) break;
							
							mchs[res[i]-1].state = false;
						}
						flag_right = false;
						flag_left = true;	
					}
					return res;	
					
				}

			}
		}
		

		/*
		
		if(trans.pathLeft[0]!=0 && trans.pathLeft[0]<4 && toolUsed[0] && toolUsed[1] && toolUsed[2]) {
			//System.out.println("Hello!");
			for(int i = 0; i<4;i++) {
				while(!mchs[i].state) {
					if(trans_before.getOrderNumber()==trans.getOrderNumber()) {
						//System.out.println("Hello2!");
						flag_left = false;
						flag_right = true;
						return buildPathOnRight(trans,tts,divideTransformation);
						//break;
						}
					}
				mchs[i].setToolCodesys(i);
			}
			
				return trans.pathLeft;
		}
		
		else if(trans.pathRight[0]!=0 && trans.pathRight[0]>=4 && toolUsed[0] && toolUsed[1] && toolUsed[2]) {
			
			return buildPathOnRight(trans,tts,divideTransformation);
		}
			
		
		*/
		
		//
		// PATHFINDER
		//

		for(int i = 0; i < (divideTransformation.length-1);i++) {
			
			boolean already_chosen = false;
			
			for(int n = mchs_available.size()-1; n >=0; n--) {
					
				if(i==0 && n==mchs_available.size()-1 && tool_counter >=3 && mchs[3].state && mchs[0].state) 
					continue;
				
				else {
					
					for(int k = 0; k < tts.length; k++) {
						
						if(tts[k].existsTranformationInTable(divideTransformation[i], divideTransformation[i+1])) {
							
							if(mchs[mchs_available.get(n).machineID].tool == tts[k].get_toolNeeded(divideTransformation[i], divideTransformation[i+1])) {
								
								//System.out.println("\nMACHINE TO GO: " + mchs_available.get(n).machineID + "\n");
								
								aux_result = aux_result + (mchs_available.get(n).machineID+1);
								res[i] = (mchs_available.get(n).machineID+1);
								counter_time[mchs_available.get(n).machineID] = counter_time[mchs_available.get(n).machineID] + tts[k].processTimeSeconds;
								already_chosen  = true;
								break;
								
								
							}
						}
					}
					
				}
				
				if (already_chosen) break;	
			}
			if (!already_chosen) {
				System.out.println("NO MACHINES AVAILABLE IN LEFT SIDE");
				for(int n = 0; n < 4; n++) {
					
					mchs[n].changeTool(	tools_before[n]);
					
				}
				
				
				//res[0] = 0;
				//res[1] = 0;
				//res[2] = 0;
				//res[3] = 0;
				//res[4] = 0;
				//res[5] = 0;
				
				res = buildPathOnRight(trans,tts,divideTransformation);
				return res;
			}
		}
	
		if(trans.quantToBe==trans.quantTotal && res[0]!=0) {
			
			trans.exeTime = (((int)System.currentTimeMillis()-Main.start)/1000) + counter_time[0] + counter_time[1] + counter_time[2]+ counter_time[3];
			//System.out.println("EXECUTING TIME: " + trans.exeTime);
			trans.finTime = trans.exeTime*trans.quantTotal/mchs_available.size();
			//flag1 = false;
		}
		
		for(int i = 0; i<res.length;i++) {
			
			if(res[i]!=0) {
				mchs[res[i]-1].state = false;
				//mchs[res[i]-1].flag = false;
				trans.set_PathLeft(res);
			}
			
		}
		
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
		
		trans_before = trans;
		
		return res;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////
	////////////////////RIGHT SIDE PATHFINDER/////////////////////// 
	////////////////////////////////////////////////////////////////
	
	private int[] buildPathOnRight(Transformação trans, TransformationTable[] tts, String[] divideTransformation) {
	
		int[] res =  {0,0,0,0,0,0};
		String result = "";
		
		
		ArrayList<Machine> mchs_available = new ArrayList<Machine>();
		
		//
		// SAVE AVAILABLE MACHINES
		//
		
		for(int i = 4; i < 8; i++) {
			
			if(mchs[i].state) {
				mchs_available.add(mchs[i]);	
				//counter_mch++;
			}
					
		}
		

		//
		// SAVE TOOLS TO USE AND IF THERE IS MORE OR LESS THEN 3 TRANSFORMATIONS
		//		
		
		boolean[] toolUsed = {false,false,false};
 		int tool_counter = 0;
 		
		int[] counter_time = {0,0,0,0};
		String tools_before[] = { mchs[4].tool,mchs[5].tool,mchs[6].tool,mchs[7].tool };
		String aux_result = "[";
 		
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
		
		if(trans.pathRight[0]!=0 && tool_counter>3)
			return trans.pathRight;
		
		//
		// TOOLS CHANGES BEFORE THE PATH (mes only)
		//
		
		//for(int i= 4; i<8;i++) 
			//mchs[i].print_machine();
		//System.out.println("------------------------------------------------------------");
		pathing_changeToolsMES(tool_counter,mchs_available, toolUsed,trans.quantTotal);

/*
		if(trans.pathRight[0]!=0 && trans.pathRight[0]>=4 &&toolUsed[0] && toolUsed[1] && toolUsed[2]) {
			//System.out.println("Hello!");
			for(int i = 4; i<8;i++) {
				while(!mchs[i].state) {
					if(trans_before.getOrderNumber()==trans.getOrderNumber()) {
						res[0] = 0;
						res[1] = 0;
						res[2] = 0;
						res[3] = 0;
						res[4] = 0;
						res[5] = 0;
						break;
					}
				}
				mchs[i].setToolCodesys(i);
			}
			return trans.pathRight;
		}
		*/
	for(int i = 0; i < (divideTransformation.length-1);i++) {
			
			boolean already_chosen = false;
			
			for(int n = mchs_available.size()-1; n >=0; n--) {
					
				if(i==0 && n==mchs_available.size()-1 && tool_counter >=3 && mchs[7].state && mchs[4].state) 
					continue;
				
				else {
					
					for(int k = 0; k < tts.length; k++) {
						
						//System.out.println(i);
						if(tts[k].existsTranformationInTable(divideTransformation[i], divideTransformation[i+1])) {
							
							if(mchs[mchs_available.get(n).machineID].tool == tts[k].get_toolNeeded(divideTransformation[i], divideTransformation[i+1])) {
								
								//System.out.println("\nMACHINE TO GO: " + mchs_available.get(n).machineID + "\n");
								
								aux_result = aux_result + (mchs_available.get(n).machineID+1);
								res[i] = (mchs_available.get(n).machineID+1);
								counter_time[mchs_available.get(n).machineID-4] = counter_time[mchs_available.get(n).machineID-4] + tts[k].processTimeSeconds;
								already_chosen  = true;
								break;
								
								
							}
						}
					}
					
				}
				
				if (already_chosen) break;	
			}
			if (!already_chosen) {
				System.out.println("NO MACHINES AVAILABLE IN RIGHT SIDE");
				for(int n = 0; n < 4; n++) {
					
					mchs[n+4].changeTool(tools_before[n]);
					
				}
				
				res[0] = 0;
				res[1] = 0;
				res[2] = 0;
				res[3] = 0;
				res[4] = 0;
				res[5] = 0;
				
			break;
			}
		}

		if(trans.quantToBe==trans.quantTotal && res[0]!=0) {
			
			trans.exeTime = (((int)System.currentTimeMillis()-Main.start)/1000) + counter_time[0] + counter_time[1] + counter_time[2]+ counter_time[3];
			//System.out.println("EXECUTING TIME: " + trans.exeTime);
			trans.finTime = trans.exeTime*trans.quantTotal/mchs_available.size();
			//flag2 = false;
		}
		
		for(int i = 0; i<res.length;i++) {
		
			if(res[i]!=0) {
				mchs[res[i]-1].state = false;
				//mchs[res[i]-1].flag = false;
				trans.set_PathRight(res);
			}
		
		}
	
		for(int i= 4; i<8;i++) 
			mchs[i].print_machine();

	
		if(res[0]!=0) {
			for(int i = 4; i<8;i++) {
				mchs[i].setToolCodesys(i);
			}
		}
	
		if(aux_result.length()!=7) {
		
			int length = aux_result.length();
		
			for(int i = 0; i< 7-length; i++) {
			
				aux_result = aux_result + "0";
			
			}
		}
	

		result = "TR" + trans.quantTotal + " [";
	
		for(int i = 0; i < res.length;i++)
			result = result + res[i];
	
		result = result + "]";
		System.out.println("PATHING:" + result);
		System.out.println("TIMES: [" + counter_time[0] + " " + counter_time[1] + " " + counter_time[2] + " "  + counter_time[3] + "]" );
	
		trans_before = trans;
		
		return res;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private void pathing_changeToolsMES(int tool_counter, ArrayList<Machine> mchs_available, boolean[] toolUsed, int quant) {
		
		if(tool_counter == 1 && mchs_available.size()>0) {
			
			if(toolUsed[0] == true) {
				
				for(int i = 0; i < mchs_available.size(); i++) {
					mchs[mchs_available.get(i).machineID].changeTool("T1");
					
					if(quant==1) break;
					else if(quant==2 && i>0) break;
					else if(quant==3 && i>1) break;
				}
			}
			
			else if(toolUsed[1] == true) {
				
				for(int i = 0; i < mchs_available.size(); i++) {
					mchs[mchs_available.get(i).machineID].changeTool("T2");
					
					if(quant==1) break;
					else if(quant==2 && i>0) break;
					else if(quant==3 && i>1) break;
				}
			}
			
			else if(toolUsed[2] == true) {
				
				for(int i = 0; i < mchs_available.size(); i++) {
					mchs[mchs_available.get(i).machineID].changeTool("T3");
					
					if(quant==1) break;
					else if(quant==2 && i>0) break;
					else if(quant==3 && i>1) break;
				}
				
			
			}
		}
		
		else if(tool_counter == 2 && mchs_available.size()>1) {
			
			if(toolUsed[0] == true && toolUsed[1] == true) {
				
				for(int i = 0; i < mchs_available.size(); i=i+2) {
					mchs[mchs_available.get(i).machineID].changeTool("T1");
					mchs[mchs_available.get(i).machineID].changeTool("T2");
				}
				
			}
			
			else if(toolUsed[1] == true && toolUsed[2] == true) {
				
				for(int i = 0; i < mchs_available.size(); i=i+2) {
					mchs[mchs_available.get(i).machineID].changeTool("T2");
					mchs[mchs_available.get(i).machineID].changeTool("T3");
				}
			}
			
			else if(toolUsed[2] == true && toolUsed[0] == true) {
				
				for(int i = 0; i < mchs_available.size(); i++) {
					mchs[mchs_available.get(i).machineID].changeTool("T1");
					mchs[mchs_available.get(i).machineID].changeTool("T3");
				}
			}
			
			
		}
		
		else if(tool_counter >= 3 && mchs_available.size()>2) {
			
			if(tool_counter > 3 && mchs_available.size()>3) {
				mchs[mchs_available.get(3).machineID].changeTool("T1");
				mchs[mchs_available.get(2).machineID].changeTool("T3");
				mchs[mchs_available.get(1).machineID].changeTool("T2");
				mchs[mchs_available.get(0).machineID].changeTool("T1");
			}
			else if(tool_counter == 3) {
				mchs[mchs_available.get(2).machineID].changeTool("T3");
				mchs[mchs_available.get(1).machineID].changeTool("T2");
				mchs[mchs_available.get(0).machineID].changeTool("T1");
			}
			
		}
		
		
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
		System.out.println("TRANSFORMATIONS: " + result + "\n");
		return result;
	}
	
	
	
	
	public int buildPathUnloading(Unloading un) {
		 int i = 0;
		 String result = "D" + un.quantity + "->";
		 
		 if(un.dest.contains("D1")) {
			 
			 i = 1;
			 result = result + "PUSHER NO" + i;
		 }
		 
		 else if(un.dest.contains("D2")) {
			 
			 i = 2;
			 result = result + "PUSHER NO" + i;
		 }
		 
		 else if(un.dest.contains("D3")) {
			 
			 i = 3;
			 result = result + "PUSHER NO" + i;
		 }
		 
		 System.out.println("UNLOADING " + result + ": " + i);
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
			//Main.DB.store_maquina(mchs[i]);
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