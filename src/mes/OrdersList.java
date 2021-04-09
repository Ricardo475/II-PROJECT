package mes;

import java.util.ArrayList;

public class OrdersList {
	
	Order order;
	ArrayList<Order> OrdersList = new ArrayList<Order>();
	int DoneOrders;
	int RunningOrders;    //OrdersAfazer? n seria "porFazer" ? se não só se guarda as que estão feitas + as que estão a correr
	
	public void addToList(Order order) {
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
