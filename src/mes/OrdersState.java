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
		calculateTimes();
	}


	public void calculateTimes() {
		int begin = (((int)System.currentTimeMillis()-Main.start)/1000);
		for(Order o:this.OrdersList)
		{
			if(o.getClass().toString().contains("Transformação"))
			{
				System.out.print(o);
				if(!((Transformação)o).flagEnd)
				{
					if(((Transformação)o).first)
					{
						((Transformação)o).startTime=begin;
						String aux;
						aux=Main.pr.contructTranformations(((Transformação)o), Main.tts);
						String[] aux1=aux.split("/");
						int aux2=aux1.length-1;
						((Transformação)o).finTime= begin+ ((Transformação)o).quantTotal*35*(aux2)/8+25;
						begin=((Transformação)o).finTime;
					}
					else
					{
						String aux;
						aux=Main.pr.contructTranformations(((Transformação)o), Main.tts);
						String[] aux1=aux.split("/");
						int aux2=aux1.length-1;
						System.out.println("BEGIN: "+begin+"EXE TIME: "+((Transformação)o).exeTime+ " TTRNAS: "+aux2);
						if(aux2<2)
						{
							((Transformação)o).finTime= begin+ ((Transformação)o).quantToBe*(((Transformação)o).exeTime+7)/(7)+((Transformação)o).quantExe*(((Transformação)o).exeTime+6)/(7)+35;
						}
						else
						{
							((Transformação)o).finTime= begin+ ((Transformação)o).quantToBe*(((Transformação)o).exeTime+5)/(7-aux2)+((Transformação)o).quantExe*(((Transformação)o).exeTime+3)/(10-aux2);
						}
						begin=((Transformação)o).finTime;
					}
					((Transformação)o).estimatePenalty();
					System.out.println(((Transformação)o).finTime+ " TOTAL: " +((Transformação)o).quantTotal+ " begin: "+begin);
				}

			}
		}
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


		int i = -1,prazo=1000000;
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
			if(OrdersList.get(i).getClass().toString().contains("Transformação"))
			{
				prazo=OrdersList.get(i).PrazoEntrega()*1/(((Transformação)OrdersList.get(i)).Penalty/50);
				for(int j=0;j<this.LengthOrderList();j++)
				{
					if(OrdersList.get(j).done==false )
					{
						//System.out.println("\n ORDER: "+OrdersList.get(j).orderNumber+" Prazo "+OrdersList.get(j).PrazoEntrega()*1/(((Transformação)OrdersList.get(j)).Penalty/50)+ " compare "+ prazo);
						if(OrdersList.get(j).PrazoEntrega()*1/(((Transformação)OrdersList.get(j)).Penalty/50)<prazo)
						{
							prazo=OrdersList.get(j).PrazoEntrega()*1/(((Transformação)OrdersList.get(j)).Penalty/50);
							i=j;
						}
					}
				}
			}
			if(OrdersList.get(i).existePecas())
			{
				//System.out.println("SUPP333");
				return OrdersList.get(i);
			}
			else
			{
				System.out.println("A PROCURA");
				if(this.orderfindByTypePiece(((Transformação)OrdersList.get(i)).From, i) != null)
				{
					return this.orderfindByTypePiece(((Transformação)OrdersList.get(i)).From, i);
				}
				else 
				{
					return this.OrdemPrioritária(i);
				}
			}
		}
		else
			return null;

	}
	
	public Order OrdemPrioritária(int k)
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
				if(this.orderfindByTypePiece(((Transformação)OrdersList.get(i)).From, i) != null)
				{
					return this.orderfindByTypePiece(((Transformação)OrdersList.get(i)).From, i);
				}
				else 
				{
					return this.OrdemPrioritária(i);
				}
			}
		}
		else
		{
			return null;
		}
	}
	
	public void pecaProc(int ID, int to)
	{

		for(int i=0;i < this.OrdersList.size() ; i++)
		{
			if(this.OrdersList.get(i).orderNumber == ID)
			{
				this.OrdersList.get(i).pecaProcessada(to);
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
				if(OrdersList.get(j).getClass().getName().contains("Transformação"))
				{
					if(OrdersList.get(j).PrazoEntrega()<prazo && ((Transformação)OrdersList.get(j)).To.contains(((Transformação)OrdersList.get(pos)).From))
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
				return this.orderfindByTypePiece(((Transformação)OrdersList.get(i)).From, i);
			}
		}
		else
			return null;
	}

	
	public void organizeTimes(Transformação trans) {
		
		int endTime_running = 0;
		for(int i = 0; i < this.LengthOrderList(); i++) {
			
			Order o = this.OrdersList.get(i);
			
			if(!o.done) {
				
				if(o.toString().contains("Transformation") ) {
					
					if(o.getOrderNumber() == trans.getOrderNumber()) {
						endTime_running = trans.finTime;
						//System.out.println("TESTE TEMPOS: ORDER Nº " + o.getOrderNumber() + "|| TIME:" + endTime_running + "|| INTERATION: " + i);
						continue;
					}	
					
					else if(!o.activeOrder) {
						
						((Transformação)o).startTime = (int) (endTime_running*0.6);
						((Transformação)o).finTime = (int) (endTime_running + ((Transformação)o).quantTotal*7.5);
						endTime_running = ((Transformação)o).finTime;
						//System.out.println("UPDATED TIME: " + ((Transformação)o).exeTime);
					
					}
					
					else if(o.activeOrder) {			
						((Transformação)o).finTime = (int) (endTime_running + ((Transformação)o).quantTotal*7.5);
						endTime_running = ((Transformação)o).finTime;
					}
					
					
				}		
			}

				
				
			
			
			
		}
		
	}

}
