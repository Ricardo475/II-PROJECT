package mes;

import java.util.ArrayList;

public class PathFinder {
	
	int cell, piece, typeBegin, typeEnd;
	
	Machine mchs[] = new Machine[8];
	Pusher pshs[] = new Pusher[3];
	SystemState sys;
	Transformação aux_trans1;
	Transformação aux_trans2;
	int orderBefore = 0;
	Transformação transBefore;
	Transformação aux_trans1Before;
	Transformação aux_trans2Before;
	boolean flag_left = true;
	boolean flag_right = false;
	boolean flag_Dleft = false;
	boolean flag_Dright = false;
	boolean flag_goLeft = false;
	int counter_flags = 0;
	
	int[] buildPathTransformation(Transformação trans, TransformationTable[] tts) {
		
		
		System.out.println("-----------------------------------------------------------------PATHFINDER-----------------------------------------------------------------");
		
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
		// SAVE TOOLS TO USE AND IF THERE IS MORE OR LESS THEN 3 TRANSFORMATIONS
		//		
 		
		int[] toolsToUse = {0,0,0,0,0,0};
		int tool_counter = 0;
		
		for(int i = 0; i < divideTransformation.length-1;i++) {
			
			for(int j = 0; j < tts.length; j++) {
				
			//	if(tool_counter>3) break;
				
				 if(tts[j].existsTranformationInTable(divideTransformation[i],divideTransformation[i+1])) {
					
					 if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T1") {
						 toolsToUse[i] = 1;
						 tool_counter++;
						 break;
					 }
					 else if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T2") {
						 toolsToUse[i] = 2;
						 tool_counter++;
						 break;
					 }
					
					 else if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T3") {
						 toolsToUse[i] = 3;
						 tool_counter++;
						 break;
					 }				
				}
			}		
		}		
		
		if(tool_counter>4) {
			
			if(aux_trans1.orderNumber != trans.getOrderNumber() && aux_trans2.orderNumber != trans.getOrderNumber()) {
				
				trans.flag_dividedTrans = true;
				
				flag_Dleft = false;
				flag_Dright = false;
				flag_goLeft = false;
				
				for(int i = 0; i < transBefore.pathLeft.length;i++) {
					transBefore.pathLeft[i] = 0;
					transBefore.pathRight[i] = 0;			
				}
				
				aux_trans1.orderNumber = trans.getOrderNumber();
				aux_trans2.orderNumber = trans.getOrderNumber();
				
				aux_trans1.From = trans.From;
				aux_trans1.To = divideTransformation[4];
				aux_trans2.From = divideTransformation[4];
				aux_trans2.To = trans.To;
				
				if(aux_trans1.To.contains("P1"))
					trans.aux_to1 = 1;
				else if(aux_trans1.To.contains("P2"))
					trans.aux_to1 = 2;
				else if(aux_trans1.To.contains("P3"))
					trans.aux_to1 = 3;
				else if(aux_trans1.To.contains("P4"))
					trans.aux_to1 = 4;
				else if(aux_trans1.To.contains("P5"))
					trans.aux_to1 = 5;
				else if(aux_trans1.To.contains("P6"))
					trans.aux_to1 = 6;
				else if(aux_trans1.To.contains("P7"))
					trans.aux_to1 = 7;
				else if(aux_trans1.To.contains("P8"))
					trans.aux_to1 = 8;
				else if(aux_trans1.To.contains("P9"))
					trans.aux_to1 = 9;
				
				if(aux_trans2.To.contains("P1"))
					trans.aux_to2 = 1;
				else if(aux_trans2.To.contains("P2"))
					trans.aux_to2 = 2;
				else if(aux_trans2.To.contains("P3"))
					trans.aux_to2 = 3;
				else if(aux_trans2.To.contains("P4"))
					trans.aux_to2 = 4;
				else if(aux_trans2.To.contains("P5"))
					trans.aux_to2 = 5;
				else if(aux_trans2.To.contains("P6"))
					trans.aux_to2 = 6;
				else if(aux_trans2.To.contains("P7"))
					trans.aux_to2 = 7;
				else if(aux_trans2.To.contains("P8"))
					trans.aux_to2 = 8;
				else if(aux_trans2.To.contains("P9"))
					trans.aux_to2 = 9;
				
				aux_trans1.quantTotal = trans.quantTotal;
				aux_trans2.quantTotal = trans.quantTotal;
				
				if(aux_trans2Before.getOrderNumber() != trans.getOrderNumber() && aux_trans1Before.getOrderNumber() != trans.getOrderNumber()) {

					aux_trans1.quantToBe = trans.quantToBe;
					aux_trans2.quantToBe = trans.quantToBe;
					aux_trans1.quantProcessed = 0;
					aux_trans2.quantProcessed = 0;
					aux_trans1.quantExe= 0;
					aux_trans2.quantExe = 0;
				}
				else {
					aux_trans1.quantToBe = aux_trans1Before.quantToBe;
					aux_trans1.quantProcessed = aux_trans1Before.quantProcessed;
					aux_trans1.quantExe= aux_trans1Before.quantExe;
					aux_trans2.quantToBe = aux_trans2Before.quantToBe;
					aux_trans2.quantProcessed = aux_trans2Before.quantProcessed;
					aux_trans2.quantExe = aux_trans2Before.quantExe;
				}
				
				
				
				
				aux_trans1.flag_dividedTrans = trans.flag_dividedTrans;
				aux_trans2.flag_dividedTrans = trans.flag_dividedTrans;
				
				aux_trans1Before = aux_trans1;
				aux_trans2Before = aux_trans2;
				
				
			}
			
			if(aux_trans1.pathLeft[0]==0 && aux_trans1.pathRight[0]==0) {
				int[] aux = this.buildPathTransformation(aux_trans1, tts);
				if(aux[0]!=0) {
					aux_trans1.quantExe++;
					aux_trans1.quantToBe--;
				}
				
				aux_trans1Before = aux_trans1;
				
				return aux;
			}
			else if((aux_trans1.pathLeft[0]!=0 || aux_trans1.pathRight[0]!=0) && aux_trans2.pathLeft[0]==0 && aux_trans2.pathRight[0]==0) {
				
				
				
				if(aux_trans1.pathLeft[0]!=0 && aux_trans1.pathRight[0]==0) {
					transformation = contructTranformations(aux_trans2, tts);
					divideTransformation = transformation.split("/");
					int[] aux = this.buildPathOnRight(aux_trans2, tts, divideTransformation);
					if(aux[0]!=0) {
						aux_trans2.quantExe++;
						aux_trans2.quantToBe--;
					}
					
					aux_trans2Before = aux_trans2;
					
					return aux;
				}
				else if(aux_trans1.pathRight[0]!=0 && aux_trans1.pathLeft[0]==0) {
					
					int[] aux = this.buildPathTransformation(aux_trans2, tts);
					if(aux[0]!=0 && aux[0]<5) {
						aux_trans2.quantExe++;
						aux_trans2.quantToBe--;
					}
					else if(aux[0]>4)
						for(int i = 0; i < aux.length; i++)
							aux[i] = 0;
					
					aux_trans2Before = aux_trans2;
					
					return aux;
				}
				
			}
			
		
			
			else if(trans.pathLeft[0]==0 && trans.pathRight[0]==0) {
				
				int time_fin = 0;
				
				if(aux_trans1.pathLeft[0]!=0 && aux_trans1.pathRight[0]==0) 
					for(int i = 0; i < aux_trans1.pathLeft.length; i++)
						trans.pathLeft[i] = aux_trans1.pathLeft[i];
				
				else if(aux_trans1.pathLeft[0]==0 && aux_trans1.pathRight[0]!=0)
					for(int i = 0; i < aux_trans1.pathRight.length; i++)
							trans.pathRight[i] = aux_trans1.pathRight[i];
				
				time_fin = aux_trans1.finTime;
				
				if(aux_trans2.pathLeft[0]!=0 && aux_trans2.pathRight[0]==0)
					for(int i = 0; i < aux_trans1.pathLeft.length; i++)
						trans.pathLeft[i] = aux_trans2.pathLeft[i];
				else if(aux_trans2.pathLeft[0]==0 && aux_trans2.pathRight[0]!=0)
					for(int i = 0; i < aux_trans1.pathRight.length; i++)
						trans.pathRight[i] = aux_trans2.pathRight[i];
				
				if(time_fin < aux_trans2.finTime)
					time_fin = aux_trans2.finTime;
				
				trans.finTime = time_fin;
			
			}
			
			
			
			
			if(aux_trans1.quantProcessed!=trans.quant1) {
				aux_trans1.quantProcessed = trans.quant1;
				aux_trans1.quantExe--;
			}
			
			if(aux_trans2.quantProcessed != trans.quant2) {
				aux_trans2.quantProcessed = trans.quant2;
				aux_trans2.quantExe--;
			}
			
			
			trans.quantExe = trans.quantTotal - trans.quantProcessed - trans.quantToBe;
			if(trans.pathLeft[0]!=0 && trans.pathRight[0]!=0) {
			
				if(trans.pathLeft[0]!=0 && trans.pathLeft[1]!=0 && trans.pathLeft[2]!=0 && trans.pathLeft[3]!=0 && !flag_Dleft && !flag_Dright && aux_trans1.quantToBe > 0) {
						
					
					flag_goLeft = true;
				//	if(orderBefore != trans.getOrderNumber()) {
						for(int i = 0; i < 4; i++) {
								
							if((short)Main.opc.get_Value("CL1T"+(4-i)+".Oper_Faltam", 2)==0) {
								if(trans.toolUsingLeft[i]==1)
									mchs[i].changeTool("T1");
								else if(trans.toolUsingLeft[i]==2)
									mchs[i].changeTool("T2");
								else if(trans.toolUsingLeft[i]==3)
									mchs[i].changeTool("T3");
								mchs[i].setToolCodesys(i);
							}
						}	
					//}
					
					if((short)Main.opc.get_Value("CL1T4.Oper_Faltam", 2)==0 && aux_trans1.quantTotal!=0) {
						flag_Dleft = true;
						flag_Dright = false;
						counter_flags = 0;
						aux_trans1.quantExe++;
						aux_trans1.quantToBe--;
						res = trans.pathLeft;
					}
					
					counter_flags++;
					
					if(counter_flags==3 && res[0]==0) { 
						counter_flags = 0;
						flag_Dleft = true;
						flag_Dright = false;
					}
						
					aux_trans1Before = aux_trans1;
					
					return res;
				}
				
				else if(trans.pathRight[0]!=0 && trans.pathRight[1]!=0 && trans.pathRight[2]!=0 && trans.pathRight[3]!=0 && !flag_Dleft && !flag_Dright && aux_trans1.quantToBe > 0) {
									
					//if(orderBefore != trans.getOrderNumber()) {
						
						int number = 0;
						
						if(trans.pathRight[0]==5)
							number = 4;
						else if(trans.pathRight[0]==6)
							number = 3;
						else if(trans.pathRight[0]==7)
							number = 2;
						else if(trans.pathRight[0]==7)
							number = 1;
						
						for(int i = 4; i < 8; i++) {
								
							if((short)Main.opc.get_Value("CR1T"+number+".Oper_Faltam", 3)==0) {
								if(trans.toolUsingRight[i-4]==1)
									mchs[i].changeTool("T1");
								else if(trans.toolUsingRight[i-4]==2)
									mchs[i].changeTool("T2");
								else if(trans.toolUsingRight[i-4]==3)
									mchs[i].changeTool("T3");
								mchs[i].setToolCodesys(i);
							}
						}	
					//}
							
					if((short)Main.opc.get_Value("CR1T4.Oper_Faltam", 3)==0  && aux_trans1.quantTotal!=0) {
						flag_Dright = true;
						flag_Dleft = false;
						counter_flags = 0;
						aux_trans1.quantExe++;
						aux_trans1.quantToBe--;
						res = trans.pathRight;
					}
							
					counter_flags++;
						
					if(counter_flags==3 && res[0]==0) { 
						counter_flags = 0;
						flag_Dleft = false;
						flag_Dright = true;
					}
							
					aux_trans1Before = aux_trans1;
						
					return res;
				
				}
				else if((flag_Dleft || flag_Dright) && aux_trans1.quantToBe>0 && aux_trans2.quantToBe==0) {
					flag_Dleft = false;
					flag_Dright = false;
					
				}
				else if(aux_trans1.quantToBe==0 && aux_trans2.quantToBe==0) {
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					trans.orderDisactivate();
					//pr.sys.increasePieces(this.To,this.quantTotal); 
					trans.quantExe = trans.quantTotal - trans.quantProcessed - trans.quantToBe;
					trans.done=true;
					System.out.println("ORDEM "+trans.getOrderNumber()+" ACABOU");
					return res;
				}
				
				
				//if(flag_goLeft) {
					
				if(flag_Dleft && aux_trans2.quantToBe > 0) {
					
					transformation = contructTranformations(aux_trans2, tts);
					divideTransformation = transformation.split("/");
					
					res = this.buildPathOnRight(aux_trans2, tts, divideTransformation);
					
					if(res[0]!=0) {
						counter_flags = 0;
						aux_trans2.quantToBe--;
						aux_trans2.quantExe++;
						flag_Dleft = false;
					}
		
					counter_flags++;
					
					if(counter_flags==3 && res[0]==0 && aux_trans1.quantToBe > 0) {
						flag_Dleft = false;
						counter_flags = 0;
					}
					
					aux_trans2Before = aux_trans2;
					
					return res;
				}
				
				else if(flag_Dright && aux_trans2.quantToBe > 0) {
					
					res = this.buildPathTransformation(aux_trans2, tts);
					
					if(res[0]!=0 && res[0]<5) {
						
						counter_flags = 0;
						aux_trans2.quantToBe--;
						aux_trans2.quantExe++;
						flag_Dright = false;
						flag_Dleft = false;
					}
					else if(res[0]>4)
						for(int i = 0; i < res.length; i++)
							res[i] = 0;
					
					
					counter_flags++;
					
					if(counter_flags==3 && res[0]==0) {
						flag_Dright = false;
						flag_Dleft = false;
						counter_flags = 0;
					}
					
					aux_trans2Before = aux_trans2;
					
					return res;
					
				}
				else if(!flag_Dleft && !flag_Dright && flag_goLeft && aux_trans1.quantToBe==0) {
					flag_Dleft = true;
					
				}
				else if(!flag_Dleft && !flag_Dright && !flag_goLeft && aux_trans1.quantToBe==0) {
					flag_Dright = true;
				}
				
				
			}					
				
				
		
	
		}
		
		else {
			
			flag_Dleft = false;
			flag_Dright = false;
			flag_goLeft = false;
			
			if(trans.getOrderNumber()!= aux_trans1.getOrderNumber() && trans.getOrderNumber()!= aux_trans2.getOrderNumber()) {
				aux_trans1.orderNumber = -1;
				aux_trans2.orderNumber = -1;
			}

			
			if(trans.getOrderNumber()!= aux_trans1.getOrderNumber() && trans.getOrderNumber()!= aux_trans2.getOrderNumber() && orderBefore != trans.getOrderNumber()) {
				for(int i = 0; i < transBefore.pathLeft.length;i++) {
					transBefore.pathLeft[i] = 0;
					transBefore.pathRight[i] = 0;				
				}
			}
			
			
			//
			// SAVE AVAILABLE MACHINES  ----> PODE SER SEPARADO DESTE CODIGO
			//
			
			ArrayList<Machine> mchs_available = new ArrayList<Machine>();
			
			System.out.println("MACHINES AVAILABLE:");
			
			for(int i = 0; i < 4; i++) {	
				if(mchs[i].state) {
					mchs_available.add(mchs[i]);
					//mchs[i].print_machine();
				}
				
			}
			
			//
			// TOOLS CHANGES BEFORE THE PATH (mes only)
			//
			
			pathing_changeToolsMES(tool_counter,mchs_available, toolsToUse,trans.quantToBe);


			for(int i = 0; i < mchs_available.size();i++)
				mchs[i].print_machine();
			
			
			//
			// SEE IF THE LAST CYCLE WAS FROM THE SAME ORDER	
			//
			
			
			if(orderBefore==trans.getOrderNumber() && tool_counter==4) {
				
				if(flag_left) {
					
			
					if(trans.pathRight[0]!=0) {
						
						
						if((short)Main.opc.get_Value("CR1T4.Oper_Faltam", 3)==0) {
							System.out.println("--PIECE GO RIGHT--");
							res = trans.pathRight;
							
							//for(int i = 4; i<8; i++) 
								//mchs[i].setToolCodesys(i);
							
							for(int i = 0; i<res.length;i++) {
								if(res[i] == 0) break;
								
								mchs[res[i]-1].state = false;
								
							}
						
							for(int i = 4; i < 8; i++) {
								
								if(trans.toolUsingRight[i-4]==1)
									mchs[i].changeTool("T1");
								else if(trans.toolUsingRight[i-4]==2)
									mchs[i].changeTool("T2");
								else if(trans.toolUsingRight[i-4]==3)
									mchs[i].changeTool("T3");
							}
							
							for(int i = 4; i<8;i++) {
								//if((short)Main.opc.get_Value("CR1T"+(i-3)+".Oper_Faltam", 3)==0)
									mchs[i].setToolCodesys(i);
							}
							
							flag_left = false;
							flag_right = true;
						}
						if(counter_flags == 3) {
							flag_left = false;
							flag_right = true;
							counter_flags = 0;
						}
						if(res[0] == 0) {
							counter_flags++;
						}
						else {
							counter_flags = 0;
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
							
							
							for(int i = 0; i < 4; i++) {
								
								if(trans.toolUsingLeft[i]==1)
									mchs[i].changeTool("T1");
								else if(trans.toolUsingLeft[i]==2)
									mchs[i].changeTool("T2");
								else if(trans.toolUsingLeft[i]==3)
									mchs[i].changeTool("T3");
							}
					
								
						
							
							for(int i = 0; i<4;i++) {
								//if((short)Main.opc.get_Value("CL1T"+(i+1)+".Oper_Faltam", 2)==0)
									mchs[i].setToolCodesys(i);
							}
							
							flag_right = false;
							flag_left = true;	
						}
						
						if(counter_flags == 3) {
							flag_right = false;
							flag_left = true;
							counter_flags = 0;
						}
						if(res[0] == 0) {
							counter_flags++;
						}
						else {
							counter_flags = 0;
						}
						
						//for(int i= 0; i<4;i++) 
							//mchs[i].print_machine();
					
						return res;	
						
					}

				}
			}
			
			else if(orderBefore!=trans.getOrderNumber() && transBefore.getOrderNumber()!=trans.getOrderNumber()) {
			
				flag_right = false;
				flag_left = true;
				
				for(int i = 0; i < transBefore.pathLeft.length;i++) {
					transBefore.pathLeft[i] = 0;
					transBefore.pathRight[i] = 0;			
				}
				
			}
			
			
			
			//
			// PATHFINDER
			//

			boolean chosen[] = {false,false,false,false};
			for(int i = 0; i < (divideTransformation.length-1);i++) {
				
				boolean already_chosen = false;
				
				for(int n = 0; n < mchs_available.size(); n++) {
				
					if(!chosen[n]){
						
						for(int k = 0; k < tts.length; k++) {
							
							if(tts[k].existsTranformationInTable(divideTransformation[i], divideTransformation[i+1])) {
								
								if(mchs[mchs_available.get(n).machineID].tool == tts[k].get_toolNeeded(divideTransformation[i], divideTransformation[i+1])) {
									
									
									aux_result = aux_result + (mchs_available.get(n).machineID+1);
									res[i] = (mchs_available.get(n).machineID+1);
									counter_time[mchs_available.get(n).machineID] = counter_time[mchs_available.get(n).machineID] + tts[k].processTimeSeconds;
									already_chosen  = true;
									chosen[n] = true;
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
					
					res = buildPathOnRight(trans,tts,divideTransformation);
					return res;
				}
			}
		
			if(trans.quantToBe==trans.quantTotal && res[0]!=0) {
				
				trans.exeTime = counter_time[0] + counter_time[1] + counter_time[2]+ counter_time[3];
				trans.finTime = (int) ((trans.startTime + trans.exeTime*trans.quantTotal*(divideTransformation.length)*0.6/8));
		
			}
			
			if(trans.quantToBe!=trans.quantTotal && tool_counter>3) {
				for(int i = 0; i<4;i++) {
					mchs[i].setToolCodesys(i);
				}
				
			}
			
			for(int i = 0; i<res.length;i++) {
				
				if(res[i]!=0) {
					mchs[res[i]-1].state = false;
					trans.set_PathLeft(res);
				}
				
			}
			
			//for(int i= 0; i<4;i++) 
				//mchs[i].print_machine();
		
			
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
			
			trans.toolUsingLeft = toolsToUse;
			
			result = "TL" + trans.quantTotal + " [";
			
			for(int i = 0; i < res.length;i++)
				result = result + res[i];
			
			result = result + "]";
			System.out.println("PATHING:" + result);
			System.out.println("TIMES: [" + counter_time[0] + " " + counter_time[1] + " " + counter_time[2] + " "  + counter_time[3] + "]" );
			
			
		}
		
		
		
		
		transBefore = trans;
		orderBefore = trans.getOrderNumber();
		
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
		
		//boolean[] toolUsed = {false,false,false};
 		//int tool_counter = 0;
 		
		int[] counter_time = {0,0,0,0};
		String tools_before[] = { mchs[4].tool,mchs[5].tool,mchs[6].tool,mchs[7].tool };
		String aux_result = "[";
 		
		int[] toolsToUse = {0,0,0,0,0,0};
		int tool_counter = 0;
		
		for(int i = 0; i < divideTransformation.length-1;i++) {
			
			for(int j = 0; j < tts.length; j++) {
				
			//	if(tool_counter>3) break;
				
				 if(tts[j].existsTranformationInTable(divideTransformation[i],divideTransformation[i+1])) {
					
					 if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T1") {
						 toolsToUse[i] = 1;
						 tool_counter++;
						 break;
					 }
					 else if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T2") {
						 toolsToUse[i] = 2;
						 tool_counter++;
						 break;
					 }
					
					 else if(tts[j].get_toolNeeded(divideTransformation[i], divideTransformation[i+1]) == "T3") {
						 toolsToUse[i] = 3;
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
		pathing_changeToolsMES(tool_counter,mchs_available, toolsToUse,trans.quantToBe);


	boolean chosen[] = {false,false,false,false};
	for(int i = 0; i < (divideTransformation.length-1);i++) {
			
			boolean already_chosen = false;
			
			for(int n = 0; n < mchs_available.size(); n++) {

				if(!chosen[n]) {
					
					for(int k = 0; k < tts.length; k++) {
						if(tts[k].existsTranformationInTable(divideTransformation[i], divideTransformation[i+1])) {
							
							if(mchs[mchs_available.get(n).machineID].tool == tts[k].get_toolNeeded(divideTransformation[i], divideTransformation[i+1])) {
								
								//System.out.println("\nMACHINE TO GO: " + mchs_available.get(n).machineID + "\n");
								
								aux_result = aux_result + (mchs_available.get(n).machineID+1);
								res[i] = (mchs_available.get(n).machineID+1);
								counter_time[mchs_available.get(n).machineID-4] = counter_time[mchs_available.get(n).machineID-4] + tts[k].processTimeSeconds;
								already_chosen  = true;
								chosen[n] = true;
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
			
			trans.exeTime = counter_time[0] + counter_time[1] + counter_time[2]+ counter_time[3];
			//System.out.println("EXECUTING TIME: " + trans.exeTime);
			//trans.finTime = (int) ((trans.startTime + trans.exeTime*trans.quantTotal*(divideTransformation.length)*0.6/8));
		}
		
	
		for(int i = 0; i<res.length;i++) {

			if(res[i]!=0) {
				if(i==0) 
				mchs[res[i]-1].state = false;		
				trans.set_PathRight(res);
				
			}
		}
		
		
		//for(int i= 4; i<8;i++) 
			//mchs[i].print_machine();

	
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
	
		trans.toolUsingRight = toolsToUse;
		transBefore = trans;
		orderBefore = trans.getOrderNumber();
		
		return res;
	}

	
	

	private void pathing_changeToolsMES(int tool_counter, ArrayList<Machine> mchs_available, int[] toolToUse, int quant) {
		
		int i = 0;
		for(int j = 0; j < mchs_available.size();j++)
			mchs_available.get(j).print_machine();
		
		while(toolToUse[i]!=0 || i<4) {
			
			if(toolToUse[i]==1) {
				
				if(tool_counter == 1 && mchs_available.size()>0) {
					
					for(int j = 0; j < mchs_available.size(); j++) { 
						mchs[mchs_available.get(j).machineID].changeTool("T1");
					
					if(quant==1) break;
					else if(quant==2 && j>0) break;
					else if(quant==3 && j>1) break;
					}
					
				}
				
				else if(tool_counter == 2 && mchs_available.size()>1) {
					
					for(int j = i; j < mchs_available.size(); j=j+2) 
						mchs[mchs_available.get(j).machineID].changeTool("T1");
					
				}
				
				else if(tool_counter>2 && mchs_available.size()>=tool_counter) {
					//System.out.println("MACHINE ID: " + mchs_available.get(i).machineID);
					mchs[mchs_available.get(i).machineID].changeTool("T1");
				}
				
			}
			
			else if(toolToUse[i]==2) {
				
				if(tool_counter == 1 && mchs_available.size()>0) {
					
					for(int j = 0; j < mchs_available.size(); j++) { 
						mchs[mchs_available.get(j).machineID].changeTool("T2");
					
					if(quant==1) break;
					else if(quant==2 && j>0) break;
					else if(quant==3 && j>1) break;
					}
					
				}
				
				else if(tool_counter == 2 && mchs_available.size()>1) {
					
					for(int j = i; j < mchs_available.size(); j=j+2) 
						mchs[mchs_available.get(j).machineID].changeTool("T2");
					
				}
				
				else if(tool_counter>2 && mchs_available.size()>=tool_counter) 
					mchs[mchs_available.get(i).machineID].changeTool("T2");				
				
				
			}
			
			else if(toolToUse[i]==3) {
				
				if(tool_counter == 1 && mchs_available.size()>0) {
					
					for(int j = 0; j < mchs_available.size(); j++) { 
						mchs[mchs_available.get(j).machineID].changeTool("T3");
					
					if(quant==1) break;
					else if(quant==2 && j>0) break;
					else if(quant==3 && j>1) break;
					}
					
				}
				
				else if(tool_counter == 2 && mchs_available.size()>1) {
					
					for(int j = i; j < mchs_available.size(); j=j+2) 
						mchs[mchs_available.get(j).machineID].changeTool("T3");
					
				}
				
				else if(tool_counter>2 && mchs_available.size()>=tool_counter) 
					mchs[mchs_available.get(i).machineID].changeTool("T3");
				
			}
			
			
			
			
			i++;
		}
		
		
}





	public String contructTranformations(Transformação trans, TransformationTable[] tts) {
		
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
			Main.DB.store_maquina(mchs[i]);
		}
		
	}
	
	void initializePushers() {
		
		for(int i = 0; i < 3; i++) {
			
			pshs[i] = new Pusher();
			pshs[i].setPusher(i);	
			pshs[i].print_Pusher();
		}
		
	}
	
				

}