package mes;

public class Machine {
	
	int machineID, processTime;
	String tool;
	boolean state;
	
	public void setMachine(int id, String tool, int processTime) {
		
		this.machineID = id;
		this.tool = tool;
		this.processTime = processTime;
		this.state = true;
		
	}
	
	public void print_machine() {
		
		System.out.println("MACHINE NO:" + this.machineID +"|| Tool: " + this.tool + " || Processing Time: " + this.processTime + "|| State: " + this.state);
	}
	
	public boolean correctPart(String dest) {
		
		if(this.tool == "T1" && (dest == "P2" || dest == "P5" || dest == "P8")) return true;
		else return false;
		
	}

}
