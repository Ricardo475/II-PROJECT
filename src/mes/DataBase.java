package mes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {

	private final String url = "jdbc:postgresql://db.fe.up.pt:5432/sinf2021a33?currentSchema=exemplo";
	private final String user = "sinf2021a33";
	private final String password = "VXPLSdpR";


	public Connection connect() throws SQLException {
		//connect to db postgresql
		Connection conn = null;
		conn = DriverManager.getConnection(url, user, password);

		if(conn != null) {
			//System.out.println("Connection OK!");
			Statement stmt = conn.createStatement();
			stmt.execute("SET search_path TO \"exemplo\" ");
			stmt.close();
		} 
		return conn;
	}

	void StoreOrder_Transformação(Transformação Trans) {
		String SQL1 = "\nUPDATE \"Ordens_transformacao\" SET \"QuantidadeProduzida\" = " + Trans.quantProcessed + ", \"QuantidadeEmProducao\"= " + Trans.quantExe+",\"QuantidadeAProduzir\"= "+Trans.quantToBe+ ",\"TempoDeSaida\"= "+ Trans.startTime+",\"TempoFim\"= "+ Trans.finTime+ ",\"PenalidadeAtual\" = "+Trans.PenaltyInc+",\"Done\" = "+Trans.done +",\"ActiveOrder\" = "+Trans.activeOrder+",\"ExeTime\" = "+Trans.exeTime+" WHERE \"IDOrdem\" = "+Trans.orderNumber;
		String SQL = "INSERT INTO \"Ordens_transformacao\"" +
				" (\"IDOrdem\", \"De\", \"Para\", \"QuantTotal\",\"QuantidadeAProduzir\", \"QuantidadeProduzida\", \"QuantidadeEmProducao\", \"TempoDeSaida\", \"TempoDeChegada\",\"TempoDeChegadaEfetivo\", \"MaximoDelay\", \"PenalidadePorDiaDeDelay\",\"TempoFim\",\"PenalidadeAtual\",\"Done\",\"ExeTime\",\"ActiveOrder\") VALUES " +
				" ("+Trans.orderNumber+",'"+ Trans.From+"','"+ Trans.To+"'," + Trans.quantTotal+","+Trans.quantToBe +","+ Trans.quantProcessed+","+ Trans.quantExe +"," + Trans.startTime+","+ Trans.instanteEnviado+"," +Trans.instanteChegada+","+Trans.MaxDelay +","+Trans.Penalty+","+Trans.finTime+","+Trans.PenaltyInc+","+Trans.done+","+Trans.exeTime+","+Trans.activeOrder+")";
		if(this.existeOrderTrans(Trans.orderNumber))
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL1);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		else
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

	}
	boolean existeOrderTrans(int num)
	{
		int count=0;
		String SQL = "SELECT count(*) FROM \"Ordens_transformacao\" WHERE \"IDOrdem\" = "+ num;
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		if(count == 1) return true;
		return false;
	}
	void FillTransOrders()
	{
		String SQL = "SELECT * FROM \"Ordens_transformacao\"";
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(SQL)) {
				while (rs.next()) {	
					int IDOrdem = rs.getInt("IDOrdem");
					String de=rs.getString("De");
					String Para = rs.getString("Para");
					int QuantidadeAProduzir= rs.getInt("QuantidadeAProduzir");
					int QuantidadeProduzida= rs.getInt("QuantidadeProduzida");
					int QuantidadeEmProducao= rs.getInt("QuantidadeEmProducao");
					int TempoDeSaida= rs.getInt("TempoDeSaida");
					int TempoDeChegada= rs.getInt("TempoDeChegada");
					int tempoChegadaefetivo= rs.getInt("TempoDeChegadaEfetivo");
					int MaximoDelay = rs.getInt("MaximoDelay");
					int Penalidapp  = rs.getInt("PenalidadePorDiaDeDelay");
					int TempoFim = rs.getInt("TempoFim");
					int PenalidadeAtual= rs.getInt("PenalidadeAtual");
					boolean done= rs.getBoolean("Done");
					int ExeTime = rs.getInt("ExeTime");
					int QuantTotal=rs.getInt("QuantTotal");
					boolean ActiveOrder= rs.getBoolean("ActiveOrder");
					Transformação aux = new Transformação(IDOrdem,de,Para,QuantidadeAProduzir,QuantidadeProduzida,QuantidadeEmProducao,TempoDeSaida,TempoDeChegada,tempoChegadaefetivo,MaximoDelay,Penalidapp,TempoFim,PenalidadeAtual,done,ExeTime,QuantTotal,ActiveOrder);
					Main.OL.addOrder(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

		
	}
	int numberOfTransOrders()
	{
		int count = 0;
		String SQL = "SELECT count(*) FROM \"Ordens_transformacao\"";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return count;
	}





	void storeOrder_Unloading(Unloading order)
	{
		String SQL = "INSERT INTO \"Ordens_de_descarga\" (\"IDOrdem\",\"Tipo\",\"Destino\",\"Quantidade\",\"QuantidadePorEnviar\",\"QuantidadeDescarregada\",\"Done\",\"ActiveOrder\") VALUES "+"("+order.orderNumber+",'"+order.unloadType+"','"+order.dest+"',"+order.quantity+","+order.quantityToBe+","+(order.quantityToBe+order.quantity-order.quantityToBe)+","+order.done+","+order.activeOrder+")";
		String SQL1= "UPDATE \"Ordens_de_descarga\" SET \"QuantidadePorEnviar\" = "+order.quantityToBe+", \"QuantidadeDescarregada\" = "+(order.quantityToBe+order.quantity-order.quantityToBe)+", \"Done\" = "+ order.done+", \"ActiveOrder\" = "+order.activeOrder+ " WHERE \"IDOrdem\" = "+order.orderNumber;
		if(this.existeOrderUnload(order.orderNumber))
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL1);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		else
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	boolean existeOrderUnload(int num)
	{
		int count=0;
		String SQL = "SELECT count(*) FROM \"Ordens_de_descarga\" WHERE \"IDOrdem\" = "+ num;
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		if(count == 1) return true;
		return false;
	}
	void FillUnloadOrders()
	{
		String SQL = "SELECT * FROM \"Ordens_de_descarga\"";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {	
				int IDOrdem = rs.getInt("IDOrdem");
				String tipo=rs.getString("Tipo");
				String Destino = rs.getString("Destino");
				int Quantidade= rs.getInt("Quantidade");
				int QuantidadePorenviar= rs.getInt("QuantidadePorEnviar");
				int QuantidadeDescarregada= rs.getInt("QuantidadeDescarregada");
				boolean done= rs.getBoolean("Done");
				boolean ActiveOrder= rs.getBoolean("ActiveOrder");
				Unloading aux = new Unloading(IDOrdem,tipo,Destino,Quantidade,QuantidadePorenviar,done,ActiveOrder);
				Main.OL.addOrder(aux);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		
	}
	void storeOrder_loading(Loading order)
	{
		String SQL="INSERT INTO \"Carga\" (\"CargaID\",\"PieceType\",\"Tempo\") VALUES ("+order.orderNumber+",'"+order.pieceType+"',"+ order.instanteChegada+")";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();) {

			stmt.executeUpdate(SQL);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	boolean existeOrderloading(int num)
	{
		int count=0;
		String SQL = "SELECT count(*) FROM \"Carga\" WHERE \"IDOrdem\" = "+ num;
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		if(count == 1) return true;
		return false;
	}
	void FillLoadOrders()
	{
		String SQL = "SELECT * FROM \"Carga\"";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {	
				int IDOrdem = rs.getInt("CargaID");
				String tipo=rs.getString("PieceType");
				int Time= rs.getInt("Tempo");
				Loading aux = new Loading(IDOrdem,tipo,Time);
				Main.OL.addOrder(aux);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	void storeOrder(Order order)
	{
		if(order.getClass().toString().contains("Transformação"))
		{
			this.StoreOrder_Transformação((Transformação) order);
		}
		else if(order.getClass().toString().contains("Unloading"))
		{
			System.out.println("OLA");
			this.storeOrder_Unloading((Unloading)order);;
		}
		else if(order.getClass().toString().contains("ListOrders"))
		{
			;
		}
		else if(order.getClass().toString().contains("Loading"))
		{
			this.storeOrder_loading((Loading)order);
		}
		else if(order.getClass().toString().contains("Order"))
		{
			;
		}

	}
	void getOrdersList(){
		this.FillTransOrders();
		this.FillUnloadOrders();
		this.FillLoadOrders();
	}
	void store_maquina(Machine m)
	{
		String SQL="INSERT INTO \"Maquinas\" (\"MaquinaID\",\"TempoTotal\",\"Tool\",\"P1\",\"P2\",\"P3\",\"P4\",\"P5\",\"P6\") VALUES ("+ m.machineID+","+m.totalOperatingTime+",'"+m.tool+"',"+m.nP1+","+m.nP2+","+m.nP3+","+m.nP4+","+m.nP5+","+m.nP6+")";
		String SQL1= "UPDATE \"Maquinas\" SET \"TempoTotal\" = "+m.totalOperatingTime+", \"Tool\" = '"+m.tool+"', \"P1\"="+m.nP1+", \"P2\" = "+m.nP2+", \"P3\" = "+m.nP3+", \"P4\" = "+m.nP4+", \"P5\" = "+m.nP5+",\"P6\" = "+m.nP6+" WHERE \"MaquinaID\" = "+m.machineID;
		if(this.existeMachine(m.machineID))
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL1);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		else
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	boolean existeMachine(int num)
	{
		int count=0;
		String SQL = "SELECT count(*) FROM \"Maquinas\" WHERE \"MaquinaID\" = "+ num;
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		if(count == 1) return true;
		return false;
	}
	public Machine[] get_maquinaID()
	{
		int i=0;
		Machine[] m= new Machine[8];
		String SQL = "SELECT * FROM \"Maquinas\" ORDER BY \"MaquinaID\" ASC";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {	
				int IDOrdem = rs.getInt("MaquinaID");
				String tool=rs.getString("Tool");
				int Time= rs.getInt("TempoTotal");
				int p1= rs.getInt("P1");
				int p2= rs.getInt("P2");
				int p3= rs.getInt("P3");
				int p4= rs.getInt("P4");
				int p5= rs.getInt("P5");
				int p6= rs.getInt("P6");
				Machine  aux = new Machine(IDOrdem,tool,Time,p1,p2,p3,p4,p5,p6);
				m[i]=aux;
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return m;
	}
	void store_armazem(SystemState s)
	{
		String SQL= "INSERT INTO \"Armazem\" (\"ID\",\"P1\",\"P2\",\"P3\",\"P4\",\"P5\",\"P6\",\"P7\",\"P8\",\"P9\",\"startTime\") VALUES ("+1+","+s.nP1Warehouse+","+s.nP2Warehouse+","+s.nP3Warehouse+","+s.nP4Warehouse+","+s.nP5Warehouse+","+s.nP6Warehouse+","+s.nP7Warehouse+","+s.nP8Warehouse+","+s.nP9Warehouse+","+Main.start+")";
		String SQL1= "UPDATE \"Armazem\" SET \"P1\" = "+s.nP1Warehouse+", \"P2\" = "+s.nP2Warehouse+",\"P3\" = "+s.nP3Warehouse+",\"P4\" = "+s.nP4Warehouse+",\"P5\" = "+s.nP5Warehouse+",\"P6\" = "+s.nP6Warehouse+", \"P7\" = "+s.nP7Warehouse + ",\"P8\" = "+s.nP8Warehouse + ",\"P9\" = "+s.nP9Warehouse+"WHERE \"ID\" = "+1;
		if(this.existeArm(1))
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL1);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		else
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	boolean existeArm(int num)
	{
		int count=0;
		String SQL = "SELECT count(*) FROM \"Armazem\" WHERE \"ID\" = "+ num;
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		if(count == 1) return true;
		return false;
	}
	public SystemState get_armazem()
	{
		String SQL = "SELECT * FROM \"Armazem\"";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {	
				int IDOrdem = rs.getInt("ID");
				int p1= rs.getInt("P1");
				int p2= rs.getInt("P2");
				int p3= rs.getInt("P3");
				int p4= rs.getInt("P4");
				int p5= rs.getInt("P5");
				int p6= rs.getInt("P6");
				int p7= rs.getInt("P7");
				int p8= rs.getInt("P8");
				int p9= rs.getInt("P9");
				SystemState aux =new SystemState(p1,p2,p3,p4,p5,p6,p7,p8,p9);
				return aux;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null; 
	}
	public int get_startTime()
	{
		String SQL= "SELECT \"startTime\" FROM \"Armazem\"";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {	
				int p1= rs.getInt("startTime");
				return p1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return -1;
	}
	//void getStatistics()
	void store_pusher(Pusher p)
	{
		String SQL ="INSERT INTO \"Pushers\" (\"IDSaida\", \"P1\",\"P2\",\"P3\",\"P4\",\"P5\",\"P6\",\"P7\",\"P8\",\"P9\") VALUES ("+p.pusherID+","+p.p1Pieces_unloaded+","+p.p2Pieces_unloaded+","+p.p3Pieces_unloaded+","+p.p4Pieces_unloaded+","+p.p5Pieces_unloaded+","+p.p6Pieces_unloaded+","+p.p7Pieces_unloaded+","+p.p8Pieces_unloaded+","+p.p9Pieces_unloaded+")";
		String SQL1="UPDATE \"Pushers\" SET \"P1\" = "+p.p1Pieces_unloaded+", \"P2\" = "+p.p2Pieces_unloaded + ", \"P3\" = "+p.p3Pieces_unloaded+", \"P4\" = "+p.p4Pieces_unloaded + ", \"P5\" = "+p.p5Pieces_unloaded+ ",\"P6\" = "+p.p6Pieces_unloaded +",\"P7\" = "+p.p7Pieces_unloaded+",\"P8\" = "+p.p8Pieces_unloaded+",\"P9\" = "+p.p9Pieces_unloaded +"WHERE \"IDSaida\" = "+p.pusherID;

		if(this.existepUSHER(p.pusherID))
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL1);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		else
		{
			try (Connection conn = connect();
					Statement stmt = conn.createStatement();) {

				stmt.executeUpdate(SQL);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	boolean existepUSHER(int num)
	{
		int count=0;
		String SQL = "SELECT count(*) FROM \"Pushers\" WHERE \"IDSaida\" = "+ num;
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		if(count == 1) return true;
		return false;
	}
	public Pusher[] get_pushers()
	{
		Pusher[] p= new Pusher[3];
		String SQL = "SELECT * FROM \"Pushers\" ORDER BY \"IDSaida\" ASC";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)) {
			while (rs.next()) {	
				int IDOrdem = rs.getInt("IDSaida");
				int p1= rs.getInt("P1");
				int p2= rs.getInt("P2");
				int p3= rs.getInt("P3");
				int p4= rs.getInt("P4");
				int p5= rs.getInt("P5");
				int p6= rs.getInt("P6");
				int p7= rs.getInt("P7");
				int p8= rs.getInt("P8");
				int p9= rs.getInt("P9");
				Pusher aux =new Pusher(IDOrdem,p1,p2,p3,p4,p5,p6,p7,p8,p9);
				p[IDOrdem]=aux;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return p;
		
	}
}
