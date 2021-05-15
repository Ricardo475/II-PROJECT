package mes;

public class Machine {
	
	int machineID;
	String tool;
	boolean state;
	int totalOperatingTime, nTotalOperated, nP1,nP2,nP3,nP4,nP5,nP6;
	
	//TIME IN SECONDS (for now)
	public void setMachine(int id, String tool) {
		
		this.machineID = id;
		this.tool = tool;
		//System.out.println("tool_maquina"+(this.machineID+1));
		//Main.opc.Set_value("tool_maquina"+(this.machineID+1),Character.getNumericValue(tool.charAt(1)));
		this.state = true;
		this.totalOperatingTime = 0;
		this.nTotalOperated = 0;
		this.nP1 = 0;
		this.nP2 = 0;
		this.nP3 = 0;
		this.nP4 = 0;
		this.nP5 = 0;
		this.nP6 = 0;
	}
	
	public void print_machine() {
		
		System.out.println("MACHINE NO:" + this.machineID +"|| Tool: " + this.tool + "|| State: " + this.state);
	}
	
	public boolean correctPart(String dest) {
		
		if( (this.tool == "T1" && (dest == "P2" || dest == "P5" || dest == "P8")) || (this.tool == "T2" && (dest == "P3" || dest == "P6")) || (this.tool == "T3" && (dest == "P4" || dest == "P9" || dest == "P7"))) return true;
		else return false;
		
	}

	public void changeTool(String toolNeeded) {
		
		this.tool = toolNeeded;
	}
	
	public void setToolCodesys(int id) {
		
		if(this.machineID == id)
			Main.opc.Set_value("tool_maquina"+(this.machineID+1),Character.getNumericValue(this.tool.charAt(1)));
	}	
	
	public void updateTime(int time) {
		
		this.totalOperatingTime =time;
	}

	public void updateOperatedPieces(Short [] piecesOperated) {
		nP1 = piecesOperated[1];
		nP2 = piecesOperated[2];
		nP3 = piecesOperated[3];
		nP4 = piecesOperated[4];
		nP5 = piecesOperated[5];
		nP6 = piecesOperated[6];
		
		nTotalOperated = nP1 + nP2 + nP3 + nP4 + nP5 + nP6;
		//print_operatedPieces();
	}

	private void print_operatedPieces() {
		
		System.out.println("MACHINE NO" + this.machineID + ": " + "P1-" + this.nP1 + "||P2-" + this.nP2 + "||P3-" + this.nP3 + "||P4-" + this.nP4 + "||P5-" + this.nP5 + "||P6-" + this.nP6);
		
	}
}
