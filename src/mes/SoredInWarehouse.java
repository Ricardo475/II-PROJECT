package mes;

public class SoredInWarehouse extends Order{

	String pieceType;
	int quantity;
	
	
	public SoredInWarehouse(int orderNumber) {
		super(orderNumber);
	}
	
	void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}
	
	void setQuantity(int quantity) {
		this.quantity = quantity;
		
	}
	

}
