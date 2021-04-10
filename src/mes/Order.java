package mes;

public class Order {
	
	int orderNumber,MaxD,instanteEnviado,instanteChegada;
	boolean activeOrder;
	boolean done;
	public Order(int orderNumber,int MaxD,int instanteEnviado,int instanteChegada) {
		
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
	
	public int PrazoEntrega()
	{
		return this.instanteChegada+this.MaxD;
		
	}

	public void doOrder()
	{
		System.out.println("nada");
		this.orderDisactivate();
		this.done=true;
	}
	
}
