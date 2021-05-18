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
		String SQL1 = "\nUPDATE \"Ordens_transformacao\" SET \"QuantidadeProduzida\" = " + Trans.quantProcessed + ", \"QuantidadeEmProducao\"= " + Trans.quantExe+",\"QuantidadeAProduzir\"= "+Trans.quantToBe+ ",\"TempoDeSaida\"= "+ Trans.instanteEnviado+",\"TempoFim\"= "+ Trans.finTime+ ",\"PenalidadeAtual\" = "+Trans.PenaltyInc+",\"Done\" = "+Trans.done +" WHERE \"IDOrdem\" = "+Trans.orderNumber;
		String SQL = "INSERT INTO \"Ordens_transformacao\"" +
				" (\"IDOrdem\", \"De\", \"Para\", \"QuantTotal\",\"QuantidadeAProduzir\", \"QuantidadeProduzida\", \"QuantidadeEmProducao\", \"TempoDeSaida\", \"TempoDeChegada\", \"MaximoDelay\", \"PenalidadePorDiaDeDelay\",\"TempoFim\",\"PenalidadeAtual\",\"Done\",\"ExeTime\") VALUES " +
				" ("+Trans.orderNumber+",'"+ Trans.From+"','"+ Trans.To+"'," + Trans.quantTotal+","+Trans.quantToBe +","+ Trans.quantProcessed+","+ Trans.quantExe +"," + Trans.instanteEnviado+","+ Trans.instanteChegada +","+Trans.MaxDelay +","+Trans.Penalty+","+Trans.finTime+","+Trans.PenaltyInc+","+Trans.done+","+Trans.exeTime+")";
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
	void storeOrder(Order order)
	{
		if(order.getClass().toString().contains("Transformação"))
		{
			this.StoreOrder_Transformação((Transformação) order);
		}
		else if(order.getClass().toString().contains("Unloading"))
		{
			;
		}
		else if(order.getClass().toString().contains("ListOrders"))
		{
			;
		}
		else if(order.getClass().toString().contains("Loading"))
		{
			;
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

	//void getStatistics()

}
