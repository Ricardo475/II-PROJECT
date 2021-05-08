package mes;

public class Order implements Comparable<Order>{
	
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
	
	

	public int PrazoEntrega()
	{
		return (this.instanteChegada+this.MaxDelay);
		
	}

	public void doOrder(PathFinder pr)
	{
		System.out.println("ORDER-" + this.orderNumber + " DONE!");
		this.orderDisactivate();
		this.done=true;
	}
	
	
	
	public boolean equals(Order o) {
		
		return this.PrazoEntrega() == o.PrazoEntrega();
	}
	
	@Override
	public int compareTo(Order o) {
		
		if(this.equals(o))
			return 0;
		else if (this.PrazoEntrega() > o.PrazoEntrega())
			return 1;
		else
			return -1;
	}
	
	/*
	@Override
	public String toString()
	{
		
		return "ORDER Nº" + this.orderNumber + " || TIME: " + this.PrazoEntrega();
		
	}
	*/
	
}
