package mes;

public class Transformação extends Order {
	int Quant,MaxD,Penalty,instanteEnviado,instanteChegada,TempoIni,TempoFim,PenalidadeIncurrida;
	String From,To;
	public Transformação(int orderNumber, String From, String to, int Quantity, int Time, int MaxDelay, int Penalty,int timeE) {
		super(orderNumber);
		this.From=From;
		this.To=to;
		this.Quant=Quantity;
		this.instanteEnviado=Time;
		this.MaxD=MaxDelay;
		this.Penalty=Penalty;
		this.instanteChegada=timeE;
	}
	
	@Override
	public String toString()
	{
		
		return "ola2";
		
	}
	
	public int PrazoEntrega()
	{
		return this.instanteChegada+this.MaxD;
		
	}
	
	
}
