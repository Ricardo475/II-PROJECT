package mes;

import java.util.ArrayList;
import java.util.List;

import dijkstraAlgorithm_Test.Node;

public class PathFinder {
	
	int cell, piece, typeBegin, typeEnd;
	
	Machine mchs[] = new Machine[4];
	List<List<Node>> adjLeft = new ArrayList<List<Node>>();
	
	static int[] positionLeft = {0,2};
	//ArrayList<Machine> machines = new ArrayList<Machine>();
	
	/*
	public static String[][] fabric_map = {{"[CL1T0]","[CL0T1]","[ALT5]","[XXXX]","[ART1]","[CR0T1]","[CR1T0]","[CR2Ta]" ,"[CR2T1b]"},
									 	   {"[CL1T1]","[CL0T2]","[XXXX]","[XXXX]","[XXXX]","[CR0T2]","[CR1T1]","[CR2T2]" ,"[XXXX]"  },
									       {"[CL1T2]","[CL0T3]","[XXXX]","[XXXX]","[XXXX]","[CR0T3]","[CR1T2]","[CR2T3]" ,"[XXXX]"  },
									       {"[CL1T3]","[CL0T4]","[ALT6]","[XXXX]","[ART2]","[CR0T4]","[CR1T3]","[CR2T4]" ,"[XXXX]"  },
									       {"[CL1T4]","[CL0T5]","[ALT7]","[XXXX]","[ART3]","[CR0T5]","[CR1T4]","[CR2T5]" ,"[XXXX]"  },
									       {"[CL1T5]","[CL0T6]","[ALT8]","[XXXX]","[ART4]","[CR0T6]","[CR1T5]","[CR2T6]" ,"[CR2T7b]"},
									       {"[XXXX]" ,"[XXXX]" ,"[XXXX]","[XXXX]","[XXXX]","[XXXX]" ,"[XXXX]" ,"[CR2T7a]","[XXXX]" }};
	
	
	
	//  Note: The 2nd to last row, the below R is connected to the LH's on his left and right
	/*
	void setInitValidPaths() {
			
		//LEFT SIDE
			paths[0] = new paths("[CL0T1]","[CL1T0]");
			paths[1] = new paths("[CL0T1]","[CL0T2]");
			paths[2] = new paths("[ALT5]","[CL0T1]");
			paths[3] = new paths("[CL1T1]","[CL0T2]");
			paths[4] = new paths("[CL0T2]","[CL1T1]");
			paths[5] = new paths("[CL0T2]","[CL0T3]");
			paths[6] = new paths("[CL1T2]","[CL0T3]");
			paths[7] = new paths("[CL0T3]","[CL0T4]");
			paths[8] = new paths("[CL1T3]","[CL0T4]");
			paths[9] = new paths("[CL0T4]","[CL0T5]");
			paths[10] = new paths("[CL1T4]","[CL0T5]");
			paths[11] = new paths("[CL0T5]","[CL0T6]");
			paths[12] = new paths("[CL0T6]","[CL1T5]");
			paths[13] = new paths("[CL0T6]","[ALT8]");
			paths[14] = new paths("[ALT8]","[ALT7]");
			paths[15] = new paths("[ALT7]","[ALT6]");
		
			System.out.println("LEFT SIDE:");
			for(int i = 0; i<15; i++)
				paths[i].print_path();
		
			
		//RIGHT SIDE	
			
	}
	
*/
	
	
	String buildPathTransformation(Transformação trans, TransformationTable[] tts) {
		
		//ONLY LEFT SIDE
		
		String str_tools = "[CL";
		String result = "[TL";
		String transformation = contructTranformations(trans, tts);
		//System.out.println();
		String[] divideTransformation;
		divideTransformation = transformation.split("/");
		
		//mchs[0].state = false;
		
		/*
		for(String aux: divideTransformation) {
			System.out.println(aux);	
		}
		*/
		
		ArrayList<Integer> machineAvailable_noTool = new ArrayList<Integer>();
		ArrayList<Integer> counterTime_noTool = new ArrayList<Integer>();
		
		String toolToUse = "";
		int processTimeToUse = 0;
		int[] counter_time = {0,0,0,0};
		
		//ONLY MACHINES PATH
		
		for(int k = 0; k < divideTransformation.length-1;k++) {
				
			int counter_min = Integer.MAX_VALUE;
			
			for(int i = 0; i<counter_time.length;i++) {
				
				if(counter_min > counter_time[i])
					counter_min = counter_time[i];
				
				//System.out.println(counter_min);
			}
			
			//System.out.println("---------------------");
			//System.out.println(k);
			for(int n = 0; n < 4; n++) {
				
				
				if(mchs[n].state) {
					
					//counter_available++;
					if(counter_time[n]==counter_min) {
						for(int i = 0; i<tts.length;i++) {
							
							if(tts[i].existsTranformationInTable(divideTransformation[k], divideTransformation[k+1])) {
								
								if(mchs[n].tool == tts[i].get_toolNeeded(divideTransformation[k], divideTransformation[k+1])) {
									
									result = result + (n+1);
									str_tools = str_tools + "0";
									machineAvailable_noTool.clear();
									counterTime_noTool.clear();
									toolToUse = mchs[n].tool;
									counter_time[n] = counter_time[n] + tts[i].processTimeSeconds;
									
								}
								else {
									machineAvailable_noTool.add(n);
									counterTime_noTool.add(counter_min);
									toolToUse = tts[i].get_toolNeeded(divideTransformation[k], divideTransformation[k+1]);
									processTimeToUse = tts[i].processTimeSeconds;
									//System.out.println(toolToUse);
								}
								
								break;
							}
							
						}
						 break;		
					}
					
				}
				
				else if (n==3) System.out.println("NO MACHINES AVAILABLE");
			}
			
			if(machineAvailable_noTool.size()!=0) {
				
				int order = -1;
				int counter_time2 = Integer.MAX_VALUE;
				
				for(int i = 0; i < machineAvailable_noTool.size();i++) {
					
					if(counter_time2 > counterTime_noTool.get(i)) {
						counter_time2 = counterTime_noTool.get(i);
						order = i;
					}
				}
				
				
				int n = machineAvailable_noTool.get(order);
				mchs[n].changeTool(toolToUse);
				result = result + (n+1);
				
				if(toolToUse == "T1")
					str_tools = str_tools + "1";
				else if(toolToUse == "T2")
					str_tools = str_tools + "2";
				else if(toolToUse == "T3")
					str_tools = str_tools + "3";
				
				counter_time[n] = counter_time[n] + processTimeToUse + 30;
					
					
			}
			
			machineAvailable_noTool.clear();
			counterTime_noTool.clear();

			
		}
		
		
		
		
		if(result.length()!=9) {
			
			int length = result.length();
			
			for(int i = 0; i< 9-length; i++) {
				
				result = result + "0";
				
			}
		}
		
		if(str_tools.length()!=9) {
			
			int length = str_tools.length();
			
			for(int i = 0; i< 9-length; i++) {
				
				str_tools = str_tools + "0";
				
			}
		}
		
		
		result = result + "]";
		str_tools = str_tools + "]";
		
		System.out.println("PATHING:\t" + result + "\t" );
		System.out.println("TOOL SWAP:\t" + str_tools + "\t");
		System.out.println("TIMES: [" + counter_time[0] + " " + counter_time[1] + " " + counter_time[2] + " "  + counter_time[3] + "]" );
		return result;
		
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
			
			//mchs = new Machine[i];
			mchs[i] = new Machine();
			mchs[i].setMachine(i, "T1", 15);
			
			mchs[i].print_machine();
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
