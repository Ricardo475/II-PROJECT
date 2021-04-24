package mes;

public class Order {
	
	int orderNumber,MaxDelay,instanteEnviado,instanteChegada;
	boolean activeOrder;
	boolean done;
	
	public Order(int orderNumber,int MaxDelay,int instanteEnviado,int instanteChegada) {
		
		this.orderNumber = orderNumber;
		this.activeOrder = false; 
		this.MaxDelay = MaxDelay;
		this.instanteEnviado = instanteEnviado;
		this.instanteChegada = instanteChegada;
		this.done=false;
	}
	
	public Order() {
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
		return this.instanteChegada+this.MaxDelay;
		
	}

	public void doOrder(PathFinder pr)
	{
		System.out.println("nada");
		this.orderDisactivate();
		this.done=true;
	}
	
}
