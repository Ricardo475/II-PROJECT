package mes;

public class Transformação extends Order {
	
	int quantTotal,Penalty,exeTime,finTime,quantProcessed,quantExe,quantToBe;
	String From,To;
	int[] path = {0,0,0,0,0,0};
	boolean[] toolUsing = {false,false,false};
	boolean flag;
	
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
		this.flag = false;
	}
	
	
	public Transformação() {
	}

	public void set_Path(int[] result) {
		this.path = result;
	}
	
	@Override
	public String toString()
	{
		return "{ORDER Nº" + this.orderNumber + " || Type: Transformation" + " || TIME: " + this.PrazoEntrega() + "}";
	}
	

	public void doOrder(PathFinder pr)
	{
		
		if(quantTotal > 0)
		{
			//this.SelectPath();
			
			System.out.println("Quant: "+quantTotal);
			
			int[] aux=pr.buildPathTransformation(this,Main.tts);
			
			if(!(aux[0] == 0 && aux[1] == 0 && aux[2] == 0 && aux[3] == 0 && aux[4] == 0 && aux[5] == 0)) {
				Main.opc.Set_value("atual_piece.tipo", "L");
				Main.opc.Set_value("atual_piece.finalType", Character.getNumericValue(this.To.charAt(1)));
				Main.opc.Set_value("atual_piece.path", aux);
				Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(this.From.charAt(1)));
				
				
				//boolean before_flag = flag;
				
				while((short)Main.opc.get_Value("ordem_recebida",1)!=1){
					flag = true;
				};	
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(flag) {
					quantTotal--;
					pr.sys.decreasePieces(this.To);
					flag = false;
				}
				System.out.println(" qant: "+quantTotal);
				if(quantTotal==0) 
				{	
					this.orderDisactivate();
					pr.sys.increasePieces(this.From,this.quantTotal);   //PARA JÁ FICAR ASSIM: ATUALIZAR SÓ NO FIM DA ORDEM -> FAZER É ATUALUZAR SEMPRE QUE UMA PEÇA ENTRA NO ARMAZÉM
					this.done=true;
					System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");}
			}
		}
	}
	
}
