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
		String SQL1 = "\nUPDATE \"Ordens_transformacao\" SET \"QuantidadeProduzida\" = " + Trans.quantProcessed + ", \"QuantidadeEmProducao\"= " + Trans.quantExe+",\"QuantidadeAProduzir\"= "+Trans.quantToBe+ ",\"TempoDeSaida\"= "+ Trans.instanteEnviado+",\"TempoFim\"= "+ Trans.finTime+ ",\"PenalidadeAtual\" = "+Trans.PenaltyInc+",\"Done\" = "+Trans.done +",\"ActiveOrder\" = "+Trans.activeOrder+",\"ExeTime\" = "+Trans.exeTime+" WHERE \"IDOrdem\" = "+Trans.orderNumber;
		String SQL = "INSERT INTO \"Ordens_transformacao\"" +
				" (\"IDOrdem\", \"De\", \"Para\", \"QuantTotal\",\"QuantidadeAProduzir\", \"QuantidadeProduzida\", \"QuantidadeEmProducao\", \"TempoDeSaida\", \"TempoDeChegada\", \"MaximoDelay\", \"PenalidadePorDiaDeDelay\",\"TempoFim\",\"PenalidadeAtual\",\"Done\",\"ExeTime\",\"ActiveOrder\") VALUES " +
				" ("+Trans.orderNumber+",'"+ Trans.From+"','"+ Trans.To+"'," + Trans.quantTotal+","+Trans.quantToBe +","+ Trans.quantProcessed+","+ Trans.quantExe +"," + Trans.instanteEnviado+","+ Trans.instanteChegada +","+Trans.MaxDelay +","+Trans.Penalty+","+Trans.finTime+","+Trans.PenaltyInc+","+Trans.done+","+Trans.exeTime+","+Trans.activeOrder+")";
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
					int MaximoDelay = rs.getInt("MaximoDelay");
					int Penalidapp  = rs.getInt("PenalidadePorDiaDeDelay");
					int TempoFim = rs.getInt("TempoFim");
					int PenalidadeAtual= rs.getInt("PenalidadeAtual");
					boolean done= rs.getBoolean("Done");
					int ExeTime = rs.getInt("ExeTime");
					int QuantTotal=rs.getInt("QuantTotal");
					boolean ActiveOrder= rs.getBoolean("ActiveOrder");
					Transformação aux = new Transformação(IDOrdem,de,Para,QuantidadeAProduzir,QuantidadeProduzida,QuantidadeEmProducao,TempoDeSaida,TempoDeChegada,MaximoDelay,Penalidapp,TempoFim,PenalidadeAtual,done,ExeTime,QuantTotal,ActiveOrder);
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
	ArrayList<Order> getOrdersList(){
		ArrayList<Order> ordersList = new ArrayList<Order>();


		return ordersList;

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
	void store_armazem(SystemState s)
	{
		String SQL= "INSERT INTO \"Armazem\" (\"ID\",\"P1\",\"P2\",\"P3\",\"P4\",\"P5\",\"P6\",\"P7\",\"P8\",\"P9\") VALUES ("+1+","+s.nP1Warehouse+","+s.nP2Warehouse+","+s.nP3Warehouse+","+s.nP4Warehouse+","+s.nP5Warehouse+","+s.nP6Warehouse+","+s.nP7Warehouse+","+s.nP8Warehouse+","+s.nP9Warehouse+")";
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
}
