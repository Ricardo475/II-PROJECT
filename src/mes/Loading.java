package mes;

public class Loading extends Order{
	
	String pieceType;
	
	
	public Loading(int orderNumber, String pieceType) {
		super(orderNumber,0,0,0);
		this.pieceType = pieceType;
	}
	
	@Override
	public String toString()
	{
		return "Loading";
	}
	

}
