package mes;

import java.util.ArrayList;

public class OrdersState {

	//Order order;
	ArrayList<Order> OrdersList;
	int DoneOrders;
	int RunningOrders;



	public OrdersState() {

		this.OrdersList = new ArrayList<Order>();
		this.DoneOrders = 0;
		this.RunningOrders = 0;

	}


	public void addOrder(Order order) {
		OrdersList.add(order);
		//this.RunningOrders = OrdersList.size();
	}


	public void orderDone(Order orderNumber) {


		this.DoneOrders++;

	}


	public int LengthOrderList()
	{
		return OrdersList.size();
	}

	public Order OrdemPrioritária()
	{
		
	
			int i = -1,prazo=1000;
			for(int j=0;j<this.LengthOrderList();j++)
			{
				if(OrdersList.get(j).done==false)
				{
					if(OrdersList.get(j).PrazoEntrega()<prazo)
					{
						prazo=OrdersList.get(j).PrazoEntrega();
						i=j;
					}
				}
			}
			if(i!=-1)
				return OrdersList.get(i);
			else
				return null;

	}


}
