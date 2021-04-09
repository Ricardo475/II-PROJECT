package mes;

public class Unloading extends Order{
	
	String unloadType, dest;
	int quantity;

	
	public Unloading(int orderNumber, String unloadType, String dest, int quantity) {
		super(orderNumber);
		this.unloadType = unloadType;
		this.dest = dest;
		this.quantity = quantity;
	}

}
