package mes;

import java.util.ArrayList;

public class OrdersList {
	
	//Order order;
	ArrayList<Order> OrdersList;
	int DoneOrders;
	int RunningOrders;    //OrdersAfazer? n seria "porFazer" ? se n�o s� se guarda as que est�o feitas + as que est�o a correr
	
	
	
	public OrdersList() {
		
		this.OrdersList = new ArrayList<Order>();
		this.DoneOrders = 0;
		this.RunningOrders = 0;
		
	}
	
	
	public void addOrder(Order order) {
		OrdersList.add(order);
		//this.RunningOrders = OrdersList.size();
	}
	
	
	public void orderDone(int orderNumber) {
		
		OrdersList.remove(orderNumber);
		this.DoneOrders++;
		
	}
	
	
	public int LengthOrderList()
	{
		return OrdersList.size();
	}
	
	
	
	
}
