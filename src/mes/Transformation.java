package mes;

public class Transformation extends Order{

	int quantity, time, maxDelay, penalty;
	String from, to;
	
	
	public Transformation(int orderNumber,String from, String to, int quantity,int maxDelay,int penalty) {
		super(orderNumber);
		this.from = from;
		this.to = to;
		this.quantity = quantity;
		this.maxDelay = maxDelay;
		this.penalty = penalty;
		
	}
	
	
	
	

}
