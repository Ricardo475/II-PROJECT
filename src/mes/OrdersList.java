package mes;

import java.util.ArrayList;

public class OrdersList {
	
	Order order;
	ArrayList<Order> OrdersList = new ArrayList<Order>();
	int DoneOrders;
	int RunningOrders;    //OrdersAfazer? n seria "porFazer" ? se n�o s� se guarda as que est�o feitas + as que est�o a correr
	
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
