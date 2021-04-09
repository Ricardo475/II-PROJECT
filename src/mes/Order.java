package mes;

public class Order {
	
	int orderNumber;
	boolean activeOrder;
	
	public Order(int orderNumber) {
		
		this.orderNumber = orderNumber;
		this.activeOrder = false;  
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
	
}
