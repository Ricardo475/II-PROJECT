package mes;

public class Unloading extends Order{
	
	String unloadType, dest;
	int quantity;
	int quantityToBe,descarregada;
	boolean flag;
	
	public Unloading(int orderNumber, String unloadType, String dest, int quantity) {
		super(orderNumber,0,0,(((int)System.currentTimeMillis()-Main.start)/1000));
		this.unloadType = unloadType;
		this.dest = dest;
		this.quantity = quantity;
		quantityToBe = quantity;
		this.descarregada=0;
		flag = false;
	}
	public Unloading(int IDOrdem,String tipo,String dest,int quant,int quantPorEnviar,int quantidadeDescarregada,boolean done,boolean activeorder)
	{
		this.orderNumber=IDOrdem;
		this.unloadType=tipo;
		this.dest=dest;
		this.quantity=quant;
		this.quantityToBe=quantPorEnviar;
		this.done=done;
		this.activeOrder=activeorder;
		this.descarregada=quantidadeDescarregada;
	}
	@Override
	public String toString()
	{
		return "Unloading";
	}
	public boolean existePecas()
	{
		return true;
	}
	public void doOrder(PathFinder pr)
	{
		
		if(quantityToBe>0) {
			
			String Side = "D";
			int aux = pr.buildPathUnloading(this);
			
			if(aux!=0) {
				Main.opc.Set_value("atual_piece.tipo", Side,4);
				Main.opc.Set_value("atual_piece.path[0]", aux,4);
				Main.opc.Set_value("atual_piece.currType", Character.getNumericValue(this.unloadType.charAt(1)),4);
				
			}
			
			//quantTotal--;
			quantityToBe--;
			this.descarregada++;
			//System.out.println(this.From);
			//pr.sys.decreasePieces(this.From);
			flag = false;
			
			while((short)Main.opc.get_Value("ordem_recebida",4)!=1){
				
				flag = true;
			}
		
			
			
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			System.out.println(" qant: "+quantityToBe);
		
			if(quantityToBe==0) 
			{	
				this.orderDisactivate();
				//pr.sys.increasePieces(this.To,this.quantTotal);   //PARA JÁ FICAR ASSIM: ATUALIZAR SÓ NO FIM DA ORDEM -> FAZER É ATUALUZAR SEMPRE QUE UMA PEÇA ENTRA NO ARMAZÉM
				this.done=true;
				System.out.println("ORDEM "+this.getOrderNumber()+" ACABOU");}
			}
	}
	
}
