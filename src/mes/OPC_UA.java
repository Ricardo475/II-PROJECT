package mes;

import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class OPC_UA {
	private OpcUaClient client;
	String IP="192.168.1.78",path="|var|CODESYS Control Win V3 x64.Application.PLC_PRG.";
	private static int id_node=4;
	
	public void connect()
	{
		try {
		 client=OpcUaClient.create("opc.tcp://" + IP + ":4840");
		 client.connect().get();
		} catch (UaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Object get_Value( String VarName) {
		String var;
		var = path + VarName;
		NodeId nodeidstring = new NodeId(id_node, var);
		DataValue value;
		
		try {
		value = client.readValue(0, TimestampsToReturn.Both, nodeidstring).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return value.getValue().getValue();
	}
}