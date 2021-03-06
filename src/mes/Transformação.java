package mes;

public class Transformação extends Order {

	int quantTotal,Penalty,PenaltyInc,exeTime,finTime,quantProcessed,quantExe,quantToBe,startTime;
	String From,To;
	int[] pathLeft = {0,0,0,0,0,0};
	int[] pathRight = {0,0,0,0,0,0};
	int[] toolUsingLeft = {0,0,0,0,0,0};
	int[] toolUsingRight = {0,0,0,0,0,0};
	int quant1 = 0;
	int quant2 = 0;
	int aux_to1 = 0;
	int aux_to2 = 0;
	boolean flag,first=true;
	boolean flagEnd = false;
	boolean flag_dividedTrans = false;
	int deadline;
	
	public Transformação(int orderNumber, String From, String to, int Quantity, int Time, int MaxDelay, int Penalty,int timeE) {
		super(orderNumber,MaxDelay,Time,timeE);
		this.From=From;
		this.To=to;
		this.quantProcessed = 0;
		this.quantExe = 0;
		this.quantToBe = Quantity;
		this.quantTotal = this.quantProcessed + this.quantExe + this.quantToBe;
		this.Penalty=Penalty;
		this.exeTime = 0;
		this.finTime = (((int)System.currentTimeMillis()-Main.start)/1000)*Quantity/8;
		this.flag = false;
		this.deadline = timeE + MaxDelay;
		this.startTime=0;
	}
	public Transformação(int id,String From,String To,int QuantidadeAproduzir,int QuantidadeProduzida,int QuantidadeEmproducao,int tempoDeSaida,int TempoDeChegada,int tempodechegadaefetiva,int maximoDelay, int PenalidadePPD,int TempoFim,int PenalidadeAtual,boolean Done,int ExeTime,int QuantTotal,boolean ActiveOrder)
	{
		super(id,maximoDelay,TempoDeChegada,tempodechegadaefetiva);
		this.From=From;
		this.To= To;
		int aux=0;
		aux=this.checkFabrica();
		System.out.println("OLAOLAOLA");
	
		if(aux>0)
		{
			this.quantTotal=QuantTotal;
			this.quantToBe= QuantidadeAproduzir;
			if(aux!= QuantidadeEmproducao)
			{
				if(aux<QuantidadeEmproducao)
				{
					this.quantProcessed=QuantidadeProduzida+QuantidadeEmproducao-aux;
					this.quantExe=aux;
				}
				else if(aux>QuantidadeEmproducao)
				{
					this.quantProcessed=QuantidadeProduzida;
					this.quantToBe=this.quantToBe+ (QuantidadeEmproducao-aux);
					this.quantExe=aux;
				}
			}
			else
			{
				this.quantProcessed=QuantidadeProduzida;
				this.quantExe=aux;
			}
		}
		else
		{
		this.quantTotal=QuantTotal;
		this.quantToBe= QuantidadeAproduzir;
		this.quantProcessed=QuantidadeProduzida+QuantidadeEmproducao;
		this.quantExe=0;
		}
		this.startTime=tempoDeSaida;
		this.Penalty=PenalidadePPD;
		this.finTime=TempoFim;
		this.PenaltyInc=PenalidadeAtual;
		this.done=Done;
		this.exeTime=ExeTime;
		this.activeOrder=ActiveOrder;
		this.deadline = maximoDelay + tempodechegadaefetiva;
		if(this.quantToBe!= this.quantTotal)
		{
			this.first=false;
		}
		
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
		if(finTime > this.deadline)
		{
			PenaltyInc= this.Penalty + this.Penalty*((int)(finTime-deadline)/50 );
			if(PenaltyInc < 0) PenaltyInc = 0;
		}
		flagEnd = true;
		Main.OL.calculateTimes();
	}
	@Override
	public String toString()
	{
		return "{ORDER Nº" + this.orderNumber + " || Type: Transformation" + " || TIME: " + this.PrazoEntrega() + "}";
	}
	void estimatePenalty()
	{
		if(finTime > this.MaxDelay)
		{
			PenaltyInc= this.Penalty + this.Penalty*((int)(finTime-deadline)/50 );
		}
	}
	public void pecaProcessada(int to)
	{	
		if(!flag_dividedTrans) {
			this.quantProcessed++;
			if(this.quantExe>0)
				this.quantExe--;
			if(this.quantExe+this.quantProcessed+this.quantToBe != this.quantTotal)
			{
				this.quantProcessed=this.quantProcessed+(this.quantTotal-this.quantToBe-this.quantExe-this.quantProcessed);
			}
		
		}
		else {
			
			
			
			if(to == this.aux_to1)
				this.quant1++;
			else if(to == this.aux_to2)
				this.quant2++;
			
			int lower = quant1;
			
			if(lower > quant2)
				lower = quant2;
			
			quantProcessed = lower;
			//System.out.println("QUANT1: " + quant1);
			//System.out.println("QUANT2: " + quant2);
			
			if(quantToBe == 0) 
				quantExe = quantTotal - quantProcessed;
			
		}
		if(this.quantProcessed == this.quantTotal)
		{
			this.FimOrdem();
		}		
			
		System.out.println(this.orderNumber +" : "+this.quantProcessed);
	}
	
	public void doOrder(PathFinder pr)
	{
		System.out.println("OLA");
		if(first)
		{
			System.out.println("OLA3");
			this.startTime=((int)System.currentTimeMillis()-Main.start)/1000;
			first = false;
			Main.OL.calculateTimes();
		}
		if(this.existePecas())
		{
			
			if(this.quantToBe > 0)
			{
				
				String Side= "";
				int[] aux=pr.buildPathTransformation(this,Main.tts);
				//int[] aux = {0,0,0,0,0,0};
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(this.quantToBe);
				String aux2 =this.convert(aux);
				if(!(aux[0] == 0 && aux[1] == 0 && aux[2] == 0 && aux[3] == 0 && aux[4] == 0 && aux[5] == 0) && quantToBe>0) {
					//Main.OL.organizeTimes(this);
					

					if(aux2.contains("1") || aux2.contains("2") || aux2.contains("3") || aux2.contains("4"))
					{
						Side = "L";
						Main.opc.Set_value("atual_piece.ordem", this.orderNumber,1);
						Main.opc.Set_value("atual_piece.tipo", Side,1);
						Main.opc.Set_value("atual_piece.path", aux,1);
						if(!flag_dividedTrans) {
							Main.opc.Set_value("atual_piece.finalType", Character.getNumericValue(this.To.charAt(1)),1);
							Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(this.From.charAt(1)),1);
						}
						else {
							
							
							if(pr.aux_trans1.pathLeft[0]!=0) {
								Main.opc.Set_value("atual_piece.finalType", Character.getNumericValue(pr.aux_trans1.To.charAt(1)),1);
								Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(pr.aux_trans1.From.charAt(1)),1);
								
							}
							else {
								Main.opc.Set_value("atual_piece.finalType", Character.getNumericValue(pr.aux_trans2.To.charAt(1)),1);
								Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(pr.aux_trans2.From.charAt(1)),1);
							}
							
							
						}
						

					}
					else if(aux2.contains("5") || aux2.contains("6") || aux2.contains("7") || aux2.contains("8"))
					{
						Side = "R";
						Main.opc.Set_value("atual_piece.ordem", this.orderNumber,4);
						Main.opc.Set_value("atual_piece.tipo", Side,4);
						Main.opc.Set_value("atual_piece.path", aux,4);
						if(!flag_dividedTrans) {
							Main.opc.Set_value("atual_piece.finalType", Character.getNumericValue(this.To.charAt(1)),4);
							Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(this.From.charAt(1)),4);
						}
						else {
							
							if(pr.aux_trans1.pathRight[0]!=0) {
								Main.opc.Set_value("atual_piece.finalType", Character.getNumericValue(pr.aux_trans1.To.charAt(1)),4);
								Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(pr.aux_trans1.From.charAt(1)),4);
								
							}
							else {
								Main.opc.Set_value("atual_piece.finalType", Character.getNumericValue(pr.aux_trans2.To.charAt(1)),4);
								Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(pr.aux_trans2.From.charAt(1)),4);
							}
							
							
						}
						
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//boolean before_flag = flag;
					if(Side.equals("L"))
					{
						while((short)Main.opc.get_Value("ordem_recebida",1)!=1){

							if((short)Main.opc.get_Value("devia_esperar",1)== 1)
							{
								flag= false;
								System.out.println("ESPERA");
								return;
							}
							flag = true;
						};
					}
					else if(Side.equals("R"))
					{
						while((short)Main.opc.get_Value("ordem_recebida",4)!=1){

							if((short)Main.opc.get_Value("devia_esperar",4)== 1)
							{
								flag= false;
								return;
							}
							flag = true;
						};
					}

					if(flag) {
						if(!flag_dividedTrans) {
							quantToBe--;
							quantExe++;
						}
						else{
							
							int bigger = pr.aux_trans1.quantToBe;
							
							if(bigger < pr.aux_trans2.quantToBe)
								bigger = pr.aux_trans2.quantToBe;
							
							quantToBe = bigger;
							
							quantExe = quantTotal - quantToBe - quantProcessed;
							
						
							
						}
						flag = false;
					}
					
					try {
						Thread.sleep(1000);
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
						System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");
					}
					

				}
			}
			else if(quantToBe==0) {
			    this.orderDisactivate();
				//pr.sys.increasePieces(this.To,this.quantTotal);  
				this.done=true;
				System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");
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
	
	@Override
	public int compareTo(Order o) {
		if(o.getClass().toString().contains("Transformação"))
		{
		if(this.equals(((Transformação)o)))
			return 0;
		else if (this.PrazoEntrega()*1/(this.Penalty/50) > ((Transformação)o).PrazoEntrega()*1/(((Transformação)o).Penalty/50))
		{
			return 1;
		}
		else
		{
			return -1;
		}
		}
		else
		{
			if(this.equals(o))
				return 0;
			else if (this.PrazoEntrega() > o.PrazoEntrega())
				return 1;
			else
				return -1;
		}
	}
	
	public Transformação getTrans(int id) {
		Transformação trans = new Transformação();
		trans.orderNumber = -1;
		
		if(id==this.getOrderNumber())
			trans = this;
		
		return trans;
	}
	public int checkFabrica()
	{
		return Main.opc.pecasNaFabrica(this.orderNumber);
	
	}
}
