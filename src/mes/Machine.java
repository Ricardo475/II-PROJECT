package mes;

public class Machine {
	
	int machineID;
	String tool;
	boolean state;
	
	public void setMachine(int id, String tool) {
		
		this.machineID = id;
		this.tool = tool;
		this.state = true;
		
	}
	
	public void print_machine() {
		
		System.out.println("MACHINE NO:" + this.machineID +"|| Tool: " + this.tool + "|| State: " + this.state);
	}
	
	public boolean correctPart(String dest) {
		
		if(this.tool == "T1" && (dest == "P2" || dest == "P5" || dest == "P8")) return true;
		else return false;
		
	}

	public void changeTool(String toolNeeded) {
		
		this.tool = toolNeeded;
		
	}

}
