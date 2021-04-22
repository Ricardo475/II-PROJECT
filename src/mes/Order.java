package mes;

public class Order {
	
	int orderNumber,MaxD,instanteEnviado,instanteChegada;
	boolean activeOrder;
	boolean done;
	String type;
	public Order(int orderNumber,int MaxD,int instanteEnviado,int instanteChegada) {
		
		this.orderNumber = orderNumber;
		this.activeOrder = false; 
		this.done=false;
	}
	
	public Order() {
	}

	
	public int getOrderNumber() {
		return this.orderNumber;
	}
	
	boolean isTranformation(int orderNumber, String type) {
		
		if (this.orderNumber==orderNumber && this.type.compareTo(type)==0)
			return true;
		else return false;
	}
	
	Transformação getTrans(int orderNumber) {
		
		Transformação trans = new Transformação();
		trans = trans.get_trans(orderNumber);
		return trans;
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

	public void doOrder(PathFinder pr)
	{
		System.out.println("nada");
		this.orderDisactivate();
		this.done=true;
	}
	
}
