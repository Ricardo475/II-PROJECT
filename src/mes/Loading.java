package mes;

public class Loading extends Order{
	
	String pieceType;
	
	
	public Loading(int orderNumber, String pieceType,int startingTime) {
		super(orderNumber,-1,-1,startingTime);
		this.pieceType = pieceType;
	}
	
	@Override
	public String toString()
	{
		return "Loading";
	}
	

}
