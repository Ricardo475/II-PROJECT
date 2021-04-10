package mes;

public class Transformação extends Order {
	int Quant,Penalty,TempoIni,TempoFim,PenalidadeIncurrida;
	String From,To;
	public Transformação(int orderNumber, String From, String to, int Quantity, int Time, int MaxDelay, int Penalty,int timeE) {
		super(orderNumber,MaxDelay,Time,timeE);
		this.From=From;
		this.To=to;
		this.Quant=Quantity;
		this.MaxD=MaxDelay;
		this.Penalty=Penalty;
	}
	
	@Override
	public String toString()
	{
		return "ola2";
	}
	
	public void doOrder()
	{
		if(Quant > 0)
		{
			//this.SelectPath();
			System.out.println("Quant: "+Quant);
			Quant--;
			if(Quant==0) {	this.orderDisactivate();this.done=true;System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
