package mes;

public class Transforma��o extends Order {
	
	int quantTotal,Penalty,exeTime,finTime,quantProcessed,quantExe,quantToBe;
	String From,To;
	int[] path = {0,0,0,0,0,0};
	boolean flag;
	
	public Transforma��o(int orderNumber, String From, String to, int Quantity, int Time, int MaxDelay, int Penalty,int timeE) {
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
		this.flag = false;
	}
	
	
	public Transforma��o() {
	}

	public void set_Path(int[] result) {
		this.path = result;
	}
	
	@Override
	public String toString()
	{
		return "{ORDER N�" + this.orderNumber + " || Type: Transformation" + " || TIME: " + this.PrazoEntrega() + "}";
	}
	

	public void doOrder(PathFinder pr)
	{
		
		
		if(quantTotal > 0)
		{
			//this.SelectPath();
			
			System.out.println("Quant: "+quantTotal);
			
			int[] aux=pr.buildPathTransformation(this,Main.tts);
			
			if(!(aux[0] == 0 && aux[1] == 0 && aux[2] == 0 && aux[3] == 0 && aux[4] == 0 && aux[5] == 0)) {
					
				Main.opc.Set_value("begin_piece.finalType", Character.getNumericValue(this.To.charAt(1)));
				Main.opc.Set_value("begin_piece.path", aux);
				Main.opc.Set_value("begin_piece.currType", Character.getNumericValue(this.From.charAt(1)));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//boolean before_flag = flag;
				
				while((short)Main.opc.get_Value("ordem_recebida",1)!=1){
					flag = true;
				};	
				
				if(flag) {
					quantTotal--;
					flag = false;
				}
				System.out.println(" qant: "+quantTotal);
				if(quantTotal==0) 
				{	
					this.orderDisactivate();
					this.done=true;
					System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");}
			}
		}
	}
	
}
