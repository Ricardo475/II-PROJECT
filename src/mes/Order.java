package mes;

public class Order {
	
	int orderNumber;
	int activeOrder;
	
	public Order(int orderNumber ,int activeOrder) {
		
		this.orderNumber = orderNumber;
		this.activeOrder = activeOrder;   //idk if this is right :/
	}
	
	public int getOrderNumber() {
		return this.orderNumber;
	}
	
	//void orderActivate(int orderNumber){}
	//void orderDisactivate(int orderNumber){}
	
	
	
}
