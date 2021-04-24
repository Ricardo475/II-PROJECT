package mes;

public class PathFinder {
	
	int cell, piece, typeBegin, typeEnd;
	
	Machine mchs[] = new Machine[8];
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
		
		for(int k = 0; k < divideTransformation.length-1;k++) {

			for(int n = 0; n < 4; n++) {
				
				boolean already_chosen = false;
				
				if(mchs[n].state) {
					
					if(k>0 && n==0) continue;
					
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
				
				else if (n==3) System.out.println("NO MACHINES AVAILABLE");
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
