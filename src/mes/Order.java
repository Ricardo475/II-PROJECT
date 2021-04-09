package mes;

public class Order {
	
	int orderNumber;
	boolean activeOrder;
	boolean done;
	public Order(int orderNumber) {
		
		this.orderNumber = orderNumber;
		this.activeOrder = false; 
		this.done=false;
	}
	
	public int getOrderNumber() {
		return this.orderNumber;
	}
	
	
	void orderActivate() {
		this.activeOrder=true;
	}
	
	
	void orderDisactivate() {
		this.activeOrder=false;
	}
	
	@Override
	public String toString()
	{
		
		return "ola";
		
	}
	
}
