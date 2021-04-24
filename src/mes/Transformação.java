package mes;

public class Transformação extends Order {
	
	int quantTotal,Penalty,exeTime,finTime,quantProcessed,quantExe,quantToBe;
	String From,To;
	
	public Transformação(int orderNumber, String From, String to, int Quantity, int Time, int MaxDelay, int Penalty,int timeE) {
		super(orderNumber,MaxDelay,Time,timeE);
		this.From=From;
		this.To=to;
		this.Penalty=Penalty;
		this.exeTime = 0;
		this.finTime = 0;
		this.quantProcessed = 0;
		this.quantExe = 0;
		this.quantToBe = Quantity;
		this.quantTotal = this.quantProcessed + this.quantExe + this.quantToBe;
	}
	
	
	public Transformação() {
	}

	
	@Override
	public String toString()
	{
		return "transformation";
	}
	

	public void doOrder(PathFinder pr)
	{
		if(quantTotal > 0)
		{
			//this.SelectPath();
			System.out.println("Quant: "+quantTotal);
			int[] aux=pr.buildPathTransformation(this,Main.tts);
			Main.opc.Set_value("begin_piece.finalType", Character.getNumericValue(this.To.charAt(1)));
			Main.opc.Set_value("begin_piece.path", aux);
			Main.opc.Set_value("begin_piece.currType", Character.getNumericValue(this.From.charAt(1)));
			while((short)Main.opc.get_Value("ordem_recebida")!=1){};
			quantTotal--;
			System.out.println(" qant: "+quantTotal);
			if(quantTotal==0) {	this.orderDisactivate();this.done=true;System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
