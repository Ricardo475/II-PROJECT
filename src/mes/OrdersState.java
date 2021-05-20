package mes;

import java.util.ArrayList;
import java.util.PriorityQueue;


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

		PriorityQueue<Order> pqOrdem = new PriorityQueue<Order>();
		ArrayList<Order> aux = new ArrayList<Order>();
		System.out.println("REGISTA ORDEM");
		pqOrdem.add(order);
		for(int i=0; i < OrdersList.size(); i++) {

			Order o_aux = new Order();
			o_aux = OrdersList.get(i);
			pqOrdem.add(o_aux);
		}

		while(!pqOrdem.isEmpty()) {

			Order o_aux = pqOrdem.poll();
			aux.add(o_aux);
		}
		Main.DB.storeOrder(order);
		OrdersList = aux;
		//this.RunningOrders = OrdersList.size();
	}


	public void orderDone(Order orderNumber) {


		this.DoneOrders++;

	}


	public int LengthOrderList()
	{
		return OrdersList.size();
	}

	public Order OrdemPriorit�ria()
	{


		int i = -1,prazo=1000;
		for(int j=0;j<this.LengthOrderList();j++)
		{
			if(OrdersList.get(j).done==false )
			{
				if(OrdersList.get(j).PrazoEntrega()<prazo)
				{
					prazo=OrdersList.get(j).PrazoEntrega();
					i=j;
				}
			}
		}
		if(i!=-1)
		{
			if(OrdersList.get(i).existePecas())
			{
				System.out.println("SUPP333");
				return OrdersList.get(i);
			}
			else
			{
				System.out.println("A PROCURA");
				if(this.orderfindByTypePiece(((Transforma��o)OrdersList.get(i)).From, i) != null)
				{
					return this.orderfindByTypePiece(((Transforma��o)OrdersList.get(i)).From, i);
				}
				else 
				{
					return this.OrdemPriorit�ria(i);
				}
			}
		}
		else
			return null;

	}
	
	public Order OrdemPriorit�ria(int k)
	{


		int i = -1,prazo=1000;
		for(int j=0;j<this.LengthOrderList();j++)
		{
			if(OrdersList.get(j).done==false  && k!= j)
			{
				if(OrdersList.get(j).PrazoEntrega()<prazo)
				{
					prazo=OrdersList.get(j).PrazoEntrega();
					i=j;
				}
			}
		}
		if(i!= -1)
		{
			if(OrdersList.get(i).existePecas())
			{
				return OrdersList.get(i);
			}
			else
			{
				System.out.println("A PROCURA");
				if(this.orderfindByTypePiece(((Transforma��o)OrdersList.get(i)).From, i) != null)
				{
					return this.orderfindByTypePiece(((Transforma��o)OrdersList.get(i)).From, i);
				}
				else 
				{
					return this.OrdemPriorit�ria(i);
				}
			}
		}
		else
		{
			return null;
		}
	}
	
	public void pecaProc(int ID)
	{

		for(int i=0;i < this.OrdersList.size() ; i++)
		{
			if(this.OrdersList.get(i).orderNumber == ID)
			{
				this.OrdersList.get(i).pecaProcessada();
				Main.DB.storeOrder(this.OrdersList.get(i));
			}
		}
	}
	public void printOrders() {
		System.out.println(OrdersList);

	}
	private Order orderfindByTypePiece(String piece,int pos)
	{

		int i = -1,prazo=1000;
		for(int j=0;j<this.LengthOrderList();j++)
		{
			if(OrdersList.get(j).done==false  && i != pos)
			{
				if(OrdersList.get(j).getClass().getName().contains("Transforma��o"))
				{
					if(OrdersList.get(j).PrazoEntrega()<prazo && ((Transforma��o)OrdersList.get(j)).To.contains(((Transforma��o)OrdersList.get(pos)).From))
					{
						prazo=OrdersList.get(j).PrazoEntrega();
						i=j;
					}
				}
			}
		}

		if(i!=-1)
		{
			if(OrdersList.get(i).existePecas())
			{
				return OrdersList.get(i);
			}
			else
			{
				System.out.println("A PROCURA"+"i");
				return this.orderfindByTypePiece(((Transforma��o)OrdersList.get(i)).From, i);
			}
		}
		else
			return null;
	}

}
