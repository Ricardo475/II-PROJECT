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

	public void decreasePieces(String piece) {
		
		if(piece=="P1") {
			this.nP1Warehouse--;
		}
		else if(piece=="P2") {
			this.nP2Warehouse--;
		}
		else if(piece=="P3") {
			this.nP3Warehouse--;
		}
		else if(piece=="P4") {
			this.nP4Warehouse--;
		}
		else if(piece=="P5") {
			this.nP5Warehouse--;
		}
		else if(piece=="P6") {
			this.nP6Warehouse--;
		}
		else if(piece=="P7") {
			this.nP7Warehouse--;
		}
		else if(piece=="P8") {
			this.nP8Warehouse--;
		}
		else if(piece=="P9") {
			this.nP9Warehouse--;
		}
		this.nTotalPiecesWarehouse = this.nP1Warehouse + this.nP2Warehouse + this.nP3Warehouse + this.nP4Warehouse + this.nP5Warehouse + this.nP6Warehouse + this.nP7Warehouse + this.nP8Warehouse + this.nP9Warehouse;
		
	}

	public void increasePieces(String piece, int quantTotal) {
		if(piece=="P1") {
			this.nP1Warehouse = this.nP1Warehouse + quantTotal;
		}
		else if(piece=="P2") {
			this.nP2Warehouse = this.nP2Warehouse + quantTotal;
		}
		else if(piece=="P3") {
			this.nP3Warehouse = this.nP3Warehouse + quantTotal;
		}
		else if(piece=="P4") {
			this.nP4Warehouse = this.nP4Warehouse + quantTotal;
		}
		else if(piece=="P5") {
			this.nP5Warehouse = this.nP5Warehouse + quantTotal;
		}
		else if(piece=="P6") {
			this.nP6Warehouse = this.nP6Warehouse + quantTotal;
		}
		else if(piece=="P7") {
			this.nP7Warehouse = this.nP7Warehouse + quantTotal;
		}
		else if(piece=="P8") {
			this.nP8Warehouse = this.nP8Warehouse + quantTotal;
		}
		else if(piece=="P9") {
			this.nP9Warehouse = this.nP9Warehouse + quantTotal;
		}
		this.nTotalPiecesWarehouse = this.nP1Warehouse + this.nP2Warehouse + this.nP3Warehouse + this.nP4Warehouse + this.nP5Warehouse + this.nP6Warehouse + this.nP7Warehouse + this.nP8Warehouse + this.nP9Warehouse;
		
	}
	

}
