package mes;

public class SystemState {
	
	int nTotalPiecesWarehouse, nPiecesSystemRight, nPiecesSystemLeft, activeMachine, nP1Warehouse, nP2Warehouse, nP3Warehouse, nP4Warehouse, nP5Warehouse, nP6Warehouse,nP7Warehouse, nP8Warehouse,nP9Warehouse ;
	//String piecesType;
	
	public SystemState(){
		
		this.nPiecesSystemLeft = 0;
		this.nPiecesSystemRight = 0;
		this.nP1Warehouse = 400;
		this.nP2Warehouse = 40;
		this.nP3Warehouse = 20;
		this.nP4Warehouse = 20;
		this.nP5Warehouse = 20;
		this.nP6Warehouse = 20;
		this.nP7Warehouse = 0;
		this.nP8Warehouse = 0;
		this.nP9Warehouse = 0;
		this.nTotalPiecesWarehouse = this.nP1Warehouse + this.nP2Warehouse + this.nP3Warehouse + this.nP4Warehouse + this.nP5Warehouse + this.nP6Warehouse + this.nP7Warehouse + this.nP8Warehouse + this.nP9Warehouse;
		
	}
	
	public void print_quantityPieces() {
		
		System.out.println("Pieces Store in warehouse: " + this.nTotalPiecesWarehouse);
		System.out.println("-- P1 Store in warehouse: " + this.nP1Warehouse);
		System.out.println("-- P2 Store in warehouse: " + this.nP2Warehouse);
		System.out.println("-- P3 Store in warehouse: " + this.nP3Warehouse);
		System.out.println("-- P4 Store in warehouse: " + this.nP4Warehouse);
		System.out.println("-- P5 Store in warehouse: " + this.nP5Warehouse);
		System.out.println("-- P6 Store in warehouse: " + this.nP6Warehouse);
		System.out.println("-- P7 Store in warehouse: " + this.nP7Warehouse);
		System.out.println("-- P8 Store in warehouse: " + this.nP8Warehouse);
		System.out.println("-- P9 Store in warehouse: " + this.nP9Warehouse);
		System.out.println("Pieces in the system:");
		System.out.println("-- Right Side: " + this.nPiecesSystemRight);
		System.out.println("-- Left Side: " + this.nPiecesSystemLeft);
		
	}
	

}
