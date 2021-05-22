package mes;

public class Transformação extends Order {

	int quantTotal,Penalty,PenaltyInc,exeTime,finTime,quantProcessed,quantExe,quantToBe;
	String From,To;
	int[] pathLeft = {0,0,0,0,0,0};
	int[] pathRight = {0,0,0,0,0,0};
	boolean[] toolUsing = {false,false,false};
	boolean flag;

	public Transformação(int orderNumber, String From, String to, int Quantity, int Time, int MaxDelay, int Penalty,int timeE) {
		super(orderNumber,MaxDelay,Time,timeE);
		this.From=From;
		this.To=to;
		this.Penalty=Penalty;
		this.exeTime = 0;
		this.finTime = exeTime*quantTotal;
		this.quantProcessed = 0;
		this.quantExe = 0;
		this.quantToBe = Quantity;
		this.quantTotal = this.quantProcessed + this.quantExe + this.quantToBe;
		this.flag = false;
	}
	public Transformação(int id,String From,String To,int QuantidadeTotal,int QuantidadeProduzida,int QuantidadeEmproducao,int tempoDeSaida,int TempoDeChegada,int maximoDelay, int PenalidadePPD,int TempoFim,int PenalidadeAtual,boolean Done,int ExeTime,int QuantTotal,boolean ActiveOrder)
	{
		
	}

	public Transformação() {
	}

	public void set_PathLeft(int[] result) {
		this.pathLeft = result;
	}
	public void set_PathRight(int[] result) {
		this.pathRight = result;
	}
	void orderDisactivate() {

		this.activeOrder=false;

	}
	void FimOrdem() {
		this.finTime = (((int)System.currentTimeMillis()-Main.start)/1000);
		if(finTime > this.MaxDelay)
		{
			PenaltyInc= this.Penalty + this.Penalty*((int)(finTime-MaxDelay)/50);
		}
	}
	@Override
	public String toString()
	{
		return "{ORDER Nº" + this.orderNumber + " || Type: Transformation" + " || TIME: " + this.PrazoEntrega() + "}";
	}

	public void pecaProcessada()
	{
		this.quantProcessed++;
		this.quantExe--;
		if(this.quantProcessed == this.quantTotal)
		{
			this.FimOrdem();
		}
		System.out.println(this.orderNumber +" : "+this.quantProcessed);
	}
	public void doOrder(PathFinder pr)
	{
		if(this.existePecas())
		{
			if(quantToBe > 0)
			{
				//this.SelectPath();

				//System.out.println("Quant: "+quantToBe);
				//System.out.println("Quant: "+quantTotal);
				String Side= "";
				int[] aux=pr.buildPathTransformation(this,Main.tts);
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
				//System.out.println(aux.length);
				String aux2 =this.convert(aux);
				if(!(aux[0] == 0 && aux[1] == 0 && aux[2] == 0 && aux[3] == 0 && aux[4] == 0 && aux[5] == 0)) {
					if(aux2.contains("1") || aux2.contains("2") || aux2.contains("3") || aux2.contains("4"))
					{
						Side = "L";
					}
					else if(aux2.contains("5") || aux2.contains("6") || aux2.contains("7") || aux2.contains("8"))
					{
						Side = "R";
					}
					Main.opc.Set_value("atual_piece.ordem", this.orderNumber);
					Main.opc.Set_value("atual_piece.tipo", Side);
					Main.opc.Set_value("atual_piece.finalType", Character.getNumericValue(this.To.charAt(1)));
					Main.opc.Set_value("atual_piece.path", aux);
					Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(this.From.charAt(1)));


					//boolean before_flag = flag;
					if(Side.equals("L"))
					{
						int i = 0;
						while((short)Main.opc.get_Value("ordem_recebida",1)!=1){
							if(i == 0) {
								quantToBe--;
								quantExe++;
								pr.totalTransf++;
								i++;
							}
							if((short)Main.opc.get_Value("devia_esperar",1)== 1)
							{
								flag= false;
								return;
							}
							flag = true;
						};
					}
					else if(Side.equals("R"))
					{
						int i = 0;
						while((short)Main.opc.get_Value("ordem_recebida2",1)!=1){
							if(i == 0) {
								quantToBe--;
								quantExe++;
								pr.totalTransf++;
								i++;
							}
							if((short)Main.opc.get_Value("devia_esperar2",1)== 1)
							{
								flag= false;
								return;
							}
							flag = true;
						};
					}
					/*
					if(flag) {
						//quantTotal--;
						quantToBe--;
						quantExe++;
						//System.out.println(this.From);
						//pr.sys.decreasePieces(this.From);
						flag = false;
					}
					*/
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println(" qant: "+quantToBe);
					if(quantToBe==0) 
					{	
						this.orderDisactivate();
						//pr.sys.increasePieces(this.To,this.quantTotal);  
						this.done=true;
						System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");}
				}
			}
		}
		else
		{
			System.out.println("OUT OF PIECES");
		}
	}
	public String convert(int[] ara)
	{
		String a="";
		for(int i=0; i < ara.length ; i++)
		{
			a=a+ara[i];
		}
		return a;
	}
	public boolean existePecas()
	{
		if(Main.pr.sys.getPecas(this.From) > 0)
		{
			return true;
		}
		return false;
	}
}
