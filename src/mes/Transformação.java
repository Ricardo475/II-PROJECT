package mes;

public class Transforma��o extends Order {
	int Quant,Penalty,TempoIni,TempoFim,PenalidadeIncurrida;
	String From,To;
	public Transforma��o(int orderNumber, String From, String to, int Quantity, int Time, int MaxDelay, int Penalty,int timeE) {
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
	
	public void doOrder(PathFinder pr)
	{
		if(Quant > 0)
		{
			//this.SelectPath();
			System.out.println("Quant: "+Quant);
			int[] aux=pr.buildPathTransformation(this,Main.tts);
			Main.opc.Set_value("begin_piece.finalType", Character.getNumericValue(this.To.charAt(1)));
			Main.opc.Set_value("begin_piece.path", aux);
			Main.opc.Set_value("begin_piece.currType", Character.getNumericValue(this.From.charAt(1)));
			while((short)Main.opc.get_Value("ordem_recebida")!=1){};
			Quant--;
			System.out.println(" qant: "+Quant);
			if(Quant==0) {	this.orderDisactivate();this.done=true;System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
