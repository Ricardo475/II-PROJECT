package mes;

public class SoredInWarehouse extends Order{

	String pieceType;
	int quantity;
	
	
	public SoredInWarehouse(int orderNumber) {
		super(orderNumber,0,0,0);
	}
	
	void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}
	
	void setQuantity(int quantity) {
		this.quantity = quantity;
		
	}
	
	public String sendResponse(int orderNumber,String pieceType ,SystemState system) {
		
		String result = "<WordPiece type=";
		
		this.setPieceType(pieceType);
		
		if(pieceType.compareTo("P1")==0 && system.nP1Warehouse!=0)
			this.setQuantity(system.nP1Warehouse);
		
		else if(pieceType.compareTo("P2")==0 && system.nP2Warehouse!=0)
			this.setQuantity(system.nP2Warehouse);
		
		else if(pieceType.compareTo("P3")==0 && system.nP3Warehouse!=0)
			this.setQuantity(system.nP3Warehouse);
		
		else if(pieceType.compareTo("P4")==0 && system.nP4Warehouse!=0)
			this.setQuantity(system.nP4Warehouse);
		
		else if(pieceType.compareTo("P5")==0 && system.nP5Warehouse!=0)
			this.setQuantity(system.nP5Warehouse);
		
		else if(pieceType.compareTo("P6")==0 && system.nP6Warehouse!=0)
			this.setQuantity(system.nP6Warehouse);
		
		else if(pieceType.compareTo("P7")==0 && system.nP7Warehouse!=0)
			this.setQuantity(system.nP7Warehouse);
		
		else if(pieceType.compareTo("P8")==0 && system.nP8Warehouse!=0)
			this.setQuantity(system.nP8Warehouse);
		
		else if(pieceType.compareTo("P9")==0 && system.nP9Warehouse!=0)
			this.setQuantity(system.nP9Warehouse);
		
		else return "DOESNT EXIST";
		
		result = result + this.pieceType + " quantity=" + this.quantity + "/>";
		
		
		return result;
	}
	

}
