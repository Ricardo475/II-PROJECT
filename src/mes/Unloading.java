package mes;

public class Unloading extends Order{
	
	String unloadType, dest;
	int quantity;

	
	public Unloading(int orderNumber, String unloadType, String dest, int quantity) {
		super(orderNumber,0,0,0);
		this.unloadType = unloadType;
		this.dest = dest;
		this.quantity = quantity;
	}

	@Override
	public String toString()
	{
		return "unloading";
	}
	
}
