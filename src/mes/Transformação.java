package mes;

public class Transforma��o extends Order {
	int Quant,MaxD,Penalty,instanteEnviado,instanteChegada,TempoIni,TempoFim,PenalidadeIncurrida;
	String From,To;
	public Transforma��o(int orderNumber, String From, String to, int Quantity, int Time, int MaxDelay, int Penalty) {
		super(orderNumber);
		this.From=From;
		this.To=to;
		this.Quant=Quantity;
		this.instanteEnviado=Time;
		this.MaxD=MaxDelay;
		this.Penalty=Penalty;
	}

}
