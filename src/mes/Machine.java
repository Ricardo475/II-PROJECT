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
	
	public void updateTime(int time) {
		
		this.totalOperatingTime = this.totalOperatingTime + time;
	}

	public void updateOperatedPieces(String pieceType) {
		
		if(pieceType == "P1") nP1++;
		else if(pieceType == "P2") nP2++;
		else if(pieceType == "P3") nP3++;
		else if(pieceType == "P4") nP4++;
		else if(pieceType == "P5") nP5++;
		else if(pieceType == "P6") nP6++;
		
		this.nTotalOperated++;
	}
}
