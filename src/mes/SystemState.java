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
		//Main.DB.store_armazem(this);
	}
	public SystemState(int p1,int p2, int p3,int p4,int p5,int p6,int p7,int p8, int p9)
	{
		this.nP1Warehouse = p1;
		this.nP2Warehouse = p2;
		this.nP3Warehouse = p3;
		this.nP4Warehouse = p4;
		this.nP5Warehouse = p5;
		this.nP6Warehouse = p6;
		this.nP7Warehouse = p7;
		this.nP8Warehouse = p8;
		this.nP9Warehouse = p9;
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
		
		if(piece.contains("P1")) {
			
			this.nP1Warehouse--;
			//System.out.println("P1: " + nP1Warehouse);
		}
		else if(piece.contains("P2")) {
			this.nP2Warehouse--;
		}
		else if(piece.contains("P3")) {
			this.nP3Warehouse--;
		}
		else if(piece.contains("P4")) {
			this.nP4Warehouse--;
		}
		else if(piece.contains("P5")) {
			this.nP5Warehouse--;
		}
		else if(piece.contains("P6")) {
			this.nP6Warehouse--;
		}
		else if(piece.contains("P7")) {
			this.nP7Warehouse--;
		}
		else if(piece.contains("P8")) {
			this.nP8Warehouse--;
		}
		else if(piece.contains("P9")) {
			this.nP9Warehouse--;
		}
		this.nTotalPiecesWarehouse = this.nP1Warehouse + this.nP2Warehouse + this.nP3Warehouse + this.nP4Warehouse + this.nP5Warehouse + this.nP6Warehouse + this.nP7Warehouse + this.nP8Warehouse + this.nP9Warehouse;
		
	}
/*
	public void increasePieces(String piece, int quantTotal) {
		
		if(piece.contains("P1")) {
			this.nP1Warehouse = this.nP1Warehouse + quantTotal;
		}
		else if(piece.contains("P2")) {
			this.nP2Warehouse = this.nP2Warehouse + quantTotal;
		}
		else if(piece.contains("P3")) {
			this.nP3Warehouse = this.nP3Warehouse + quantTotal;
		}
		else if(piece.contains("P4")) {
			this.nP4Warehouse = this.nP4Warehouse + quantTotal;
		}
		else if(piece.contains("P5")) {
			this.nP5Warehouse = this.nP5Warehouse + quantTotal;
		}
		else if(piece.contains("P6")) {
			this.nP6Warehouse = this.nP6Warehouse + quantTotal;
		}
		else if(piece.contains("P7")) {
			this.nP7Warehouse = this.nP7Warehouse + quantTotal;
		}
		else if(piece.contains("P8")) {
			this.nP8Warehouse = this.nP8Warehouse + quantTotal;
		}
		else if(piece.contains("P9")) {
			this.nP9Warehouse = this.nP9Warehouse + quantTotal;
		}
		this.nTotalPiecesWarehouse = this.nP1Warehouse + this.nP2Warehouse + this.nP3Warehouse + this.nP4Warehouse + this.nP5Warehouse + this.nP6Warehouse + this.nP7Warehouse + this.nP8Warehouse + this.nP9Warehouse;
		
	}
	*/
	public void setPieces(Short[] pieces)
	{
		this.nP1Warehouse = pieces[1];
		this.nP2Warehouse = pieces[2];
		this.nP3Warehouse = pieces[3];
		this.nP4Warehouse = pieces[4];
		this.nP5Warehouse = pieces[5];
		this.nP6Warehouse = pieces[6];
		this.nP7Warehouse = pieces[7];
		this.nP8Warehouse = pieces[8];
		this.nP9Warehouse = pieces[9];
		//Main.DB.store_armazem(this);
	}
	public int getPecas(String piece)
	{
		if(piece.contains("P1")) {
			return this.nP1Warehouse;
		}
		else if(piece.contains("P2")) {
			return this.nP2Warehouse;
		}
		else if(piece.contains("P3")) {
			return this.nP3Warehouse;
		}
		else if(piece.contains("P4")) {
			return this.nP4Warehouse;
		}
		else if(piece.contains("P5")) {
			return this.nP5Warehouse;
		}
		else if(piece.contains("P6")) {
			return this.nP6Warehouse;
		}
		return -1;
	
	}

}
