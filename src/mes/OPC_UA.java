package mes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription.ItemCreationCallback;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.google.common.collect.Lists.newArrayList;

public class OPC_UA {
	private OpcUaClient client;
	String IP = "localhost", path = "|var|CODESYS Control Win V3 x64.Application.PLC_PRG.",path2="|var|CODESYS Control Win V3 x64.Application.POU.",path3="|var|CODESYS Control Win V3 x64.Application.POU_2.";
	private static int id_node = 4;
	private UaSubscription sub = null;
	int r1=1,r2=1,r3=1,r4=1;
	public void connect() {
		try {
			client = OpcUaClient.create("opc.tcp://" + IP + ":4840");
			client.connect().get();
			this.createSubscription();
		} catch (UaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Object get_Value(String VarName,int p) {
		String var ="";
		if(p== 1)
			var = path + VarName;
		else if(p==2)
			var= path2+VarName;
		else if(p==3)
			var= path3+VarName;
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

	public void Set_value(String VarName, int value) {
		String var;
		var = path + VarName;
		NodeId nodeidstring = new NodeId(id_node, var);
		int i = value;

		try {
			System.out.println(client.writeValue(nodeidstring, new DataValue(new Variant((short) i))).get() + "  ------ > " +value);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Set_value(String VarName, boolean value) {
		String var;
		var = path + VarName;
		NodeId nodeidstring = new NodeId(id_node, var);
		boolean i = value;

		try {
			System.out.println(client.writeValue(nodeidstring, new DataValue(new Variant(i))).get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Set_value(String VarName, String value) {
		String var;
		var = path + VarName;
		NodeId nodeidstring = new NodeId(id_node, var);
		String i = value;

		try {
			System.out.println(client.writeValue(nodeidstring, new DataValue(new Variant(i))).get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Set_value(String VarName, int[] value) {
		for(int i=0; i<value.length;i++)
		{
			System.out.println(i);
			this.Set_value(VarName+"["+i+"]", value[i]);
		}
	}
	
	public void createSubscription() throws Exception {

		if (sub != null) {
			return;
		}
		sub = client.getSubscriptionManager().createSubscription(10.0).get();
		List<MonitoredItemCreateRequest> lmr= new ArrayList<>();
		  
		
		ReadValueId readValueId = new ReadValueId(new NodeId(id_node, path2 + "CL1T1.Disponivel"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId2 = new ReadValueId(new NodeId(id_node, path2 + "CL1T2.Disponivel"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId3 = new ReadValueId(new NodeId(id_node, path2 + "CL1T3.Disponivel"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId4 = new ReadValueId(new NodeId(id_node, path2 + "CL1T4.Disponivel"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId5 = new ReadValueId(new NodeId(id_node, path2 + "ALT5.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId6 = new ReadValueId(new NodeId(id_node, path2 + "ALT6.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId7 = new ReadValueId(new NodeId(id_node, path3 + "CR1T1.Disponivel"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId8 = new ReadValueId(new NodeId(id_node, path3 + "CR1T2.Disponivel"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId9 = new ReadValueId(new NodeId(id_node, path3 + "CR1T3.Disponivel"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId10 = new ReadValueId(new NodeId(id_node, path3 + "CR1T4.Disponivel"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId11 = new ReadValueId(new NodeId(id_node, path3 + "ART2.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId12 = new ReadValueId(new NodeId(id_node, path3 + "ART1.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		UInteger clientHandle = sub.nextClientHandle();
		
		MonitoringParameters parameters = new MonitoringParameters(clientHandle, 10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting,
				parameters));
		
		UInteger clientHandle2 = sub.nextClientHandle();
		
		MonitoringParameters parameters2 = new MonitoringParameters(clientHandle2, 10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId2, MonitoringMode.Reporting,
				parameters2));
		
		UInteger clientHandle3 = sub.nextClientHandle();
		
		MonitoringParameters parameters3 = new MonitoringParameters(clientHandle3, 10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId3, MonitoringMode.Reporting,
				parameters3));
		
		UInteger clientHandle4 = sub.nextClientHandle();
		
		MonitoringParameters parameters4 = new MonitoringParameters(clientHandle4, 10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId4, MonitoringMode.Reporting,
				parameters4));
		UInteger clientHandle5 = sub.nextClientHandle();
		
		MonitoringParameters parameters5 = new MonitoringParameters(clientHandle5, 10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId5, MonitoringMode.Reporting,
				parameters5));
		
		UInteger clientHandle6 = sub.nextClientHandle();
		
		MonitoringParameters parameters6 = new MonitoringParameters(clientHandle6,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId6, MonitoringMode.Reporting,
				parameters6));
		
		UInteger clientHandle7 = sub.nextClientHandle();
		
		MonitoringParameters parameters7 = new MonitoringParameters(clientHandle7,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId7, MonitoringMode.Reporting,
				parameters7));
		
		UInteger clientHandle8 = sub.nextClientHandle();
		
		MonitoringParameters parameters8 = new MonitoringParameters(clientHandle8,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId8, MonitoringMode.Reporting,
				parameters8));
		
		
		UInteger clientHandle9 = sub.nextClientHandle();
		
		MonitoringParameters parameters9 = new MonitoringParameters(clientHandle9,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId9, MonitoringMode.Reporting,
				parameters9));
		
		UInteger clientHandle10 = sub.nextClientHandle();
		
		MonitoringParameters parameters10 = new MonitoringParameters(clientHandle10,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId10, MonitoringMode.Reporting,
				parameters10));
		
		UInteger clientHandle11 = sub.nextClientHandle();
		
		MonitoringParameters parameters11 = new MonitoringParameters(clientHandle11,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId11, MonitoringMode.Reporting,
				parameters11));
		
		UInteger clientHandle12 = sub.nextClientHandle();
		
		MonitoringParameters parameters12 = new MonitoringParameters(clientHandle12,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId12, MonitoringMode.Reporting,
				parameters12));
		
		ItemCreationCallback onItemCreated = (item, id) -> item.setValueConsumer(this::onSubscriptionChangeValue);

		List<UaMonitoredItem> items = sub
				.createMonitoredItems(TimestampsToReturn.Both, lmr, onItemCreated).get();

	}

	private void onSubscriptionChangeValue(UaMonitoredItem item, DataValue value) {
		String identifier =item.getReadValueId().getNodeId().getIdentifier().toString();
		
		System.out.println("item: " + identifier + " value: "+ value.getValue().getValue());
		if(identifier.contains("CL1T4"))
		{
			
			boolean state = (boolean) value.getValue().getValue();
			System.out.println("CL1T4: " + (boolean) value.getValue().getValue());
			
			if(state) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if((boolean) value.getValue().getValue()==state && (boolean) value.getValue().getValue()) Main.pr.mchs[0].state = state;
				
			if(state)
			{
				r1=0;
				Short[] aux;
				aux= (Short[]) this.get_Value("CL1T4.pieces_operated", 2);
				Main.pr.mchs[0].updateOperatedPieces(aux);
				System.out.println("Pieces " + aux[0] +": "+aux[1]);
			}
			else if(r1==0 && !(boolean) value.getValue().getValue())
			{
				r1=1;
				long aux;
				aux= (long) this.get_Value("CL1T4.OperatedTime", 2);
				System.out.println("OP: " + aux);
				Main.pr.mchs[0].updateTime((int) (aux/1000));
			}
		}
		else if(identifier.contains("CL1T3"))
		{

			boolean state = (boolean) value.getValue().getValue();
			System.out.println("CL1T3: " + (boolean) value.getValue().getValue());
			
			if(state) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if((boolean) value.getValue().getValue()==state && (boolean) value.getValue().getValue()) Main.pr.mchs[1].state = state;
			
			if(state)
			{
				r2=0;
				Short[] aux;
				aux= (Short[]) this.get_Value("CL1T3.pieces_operated", 2);
				Main.pr.mchs[1].updateOperatedPieces(aux);
				System.out.println("Pieces " + aux[0] +": " + aux[1]);
			}
			else if(r2==0 && !(boolean) value.getValue().getValue())
			{
				r2=1;
				long aux;
				aux= (long) this.get_Value("CL1T3.OperatedTime", 2);
				System.out.println("OP: " + aux);
				Main.pr.mchs[1].updateTime((int) (aux/1000));
			}
		}
		else if(identifier.contains("CL1T2"))
		{

			boolean state = (boolean) value.getValue().getValue();
			System.out.println("CL1T2: " + (boolean) value.getValue().getValue());
			
			if(state) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if((boolean) value.getValue().getValue()==state && (boolean) value.getValue().getValue()) Main.pr.mchs[2].state = state;
			
			if(state)
			{
				r3=0;
				Short[] aux;
				aux= (Short[]) this.get_Value("CL1T2.pieces_operated", 2);
				Main.pr.mchs[2].updateOperatedPieces(aux);
				System.out.println("Pieces "+ aux[0] + ": " +aux[1]);
			}
			else if(r3==0 && !(boolean) value.getValue().getValue())
			{
				r3=1;
				long aux;
				aux= (long) this.get_Value("CL1T2.OperatedTime", 2);
				System.out.println("OP: " + aux);
				Main.pr.mchs[2].updateTime((int) (aux/1000));
			}
		}
		else if(identifier.contains("CL1T1"))
		{

			boolean state = (boolean) value.getValue().getValue();
			System.out.println("CL1T1: " + (boolean) value.getValue().getValue());
			
			if(state) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if((boolean) value.getValue().getValue()==state && (boolean) value.getValue().getValue()) Main.pr.mchs[3].state = state;
			
			if(state)
			{
				r4=0;
				Short[] aux;
				aux= (Short[]) this.get_Value("CL1T1.pieces_operated", 2);
				Main.pr.mchs[3].updateOperatedPieces(aux);
				System.out.println("Pieces "+ aux[0] + ": "+aux[1]);
			}
			else if(r4==0 && !(boolean) value.getValue().getValue())
			{
				r4=1;
				long aux;
				aux= (long) this.get_Value("CL1T1.OperatedTime", 2);
				System.out.println("OP: " + aux);
				Main.pr.mchs[3].updateTime((int) (aux/1000));
			}
		}
		else if(identifier.contains("ALT5"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(!state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("Pecas_armazem", 1);
				Main.pr.sys.setPieces(aux);
				Main.pr.sys.print_quantityPieces();
			}
		}
		else if(identifier.contains("ALT6"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("Pecas_armazem", 1);
				Main.pr.sys.setPieces(aux);
				Main.pr.sys.print_quantityPieces();
			}
		}
		///////////////// CELULA DIREITA DAQUI PARA BAIXO //////////////////////////////////
		else if(identifier.contains("CR1T4"))
		{

			boolean state = (boolean) value.getValue().getValue();
			System.out.println("CR1T4: " + (boolean) value.getValue().getValue());
			
			if(state) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if((boolean) value.getValue().getValue()==state && (boolean) value.getValue().getValue()) Main.pr.mchs[4].state = state;
			
			if(state)
			{
				r3=0;
				Short[] aux;
				aux= (Short[]) this.get_Value("CR1T4.pieces_operated", 3);
				Main.pr.mchs[4].updateOperatedPieces(aux);
				System.out.println("Pieces "+ aux[0] + ": " +aux[1]);
			}
			else if(r3==0 && !(boolean) value.getValue().getValue())
			{
				r3=1;
				long aux;
				aux= (long) this.get_Value("CR1T4.OperatedTime", 3);
				System.out.println("OP: " + aux);
				Main.pr.mchs[4].updateTime((int) (aux/1000));
			}
		}
		else if(identifier.contains("CR1T3"))
		{

			boolean state = (boolean) value.getValue().getValue();
			System.out.println("CR1T3: " + (boolean) value.getValue().getValue());
			
			if(state) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if((boolean) value.getValue().getValue()==state && (boolean) value.getValue().getValue()) Main.pr.mchs[5].state = state;
			
			if(state)
			{
				r2=0;
				Short[] aux;
				aux= (Short[]) this.get_Value("CR1T3.pieces_operated", 3);
				Main.pr.mchs[5].updateOperatedPieces(aux);
				System.out.println("Pieces " + aux[0] +": " + aux[1]);
			}
			else if(r2==0 && !(boolean) value.getValue().getValue())
			{
				r2=1;
				long aux;
				aux= (long) this.get_Value("CR1T3.OperatedTime", 3);
				System.out.println("OP: " + aux);
				Main.pr.mchs[5].updateTime((int) (aux/1000));
			}
		}
		else if(identifier.contains("CR1T2"))
		{

			boolean state = (boolean) value.getValue().getValue();
			System.out.println("CR1T2: " + (boolean) value.getValue().getValue());
			
			if(state) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if((boolean) value.getValue().getValue()==state && (boolean) value.getValue().getValue()) Main.pr.mchs[6].state = state;
			
			if(state)
			{
				r3=0;
				Short[] aux;
				aux= (Short[]) this.get_Value("CR1T2.pieces_operated", 3);
				Main.pr.mchs[6].updateOperatedPieces(aux);
				System.out.println("Pieces "+ aux[0] + ": " +aux[1]);
			}
			else if(r3==0 && !(boolean) value.getValue().getValue())
			{
				r3=1;
				long aux;
				aux= (long) this.get_Value("CR1T2.OperatedTime", 3);
				System.out.println("OP: " + aux);
				Main.pr.mchs[6].updateTime((int) (aux/1000));
			}
		}
		else if(identifier.contains("CR1T1"))
		{

			boolean state = (boolean) value.getValue().getValue();
			System.out.println("CR1T1: " + (boolean) value.getValue().getValue());
			
			if(state) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if((boolean) value.getValue().getValue()==state && (boolean) value.getValue().getValue()) Main.pr.mchs[7].state = state;
			
			if(state)
			{
				r4=0;
				Short[] aux;
				aux= (Short[]) this.get_Value("CR1T1.pieces_operated", 3);
				Main.pr.mchs[7].updateOperatedPieces(aux);
				System.out.println("Pieces "+ aux[0] + ": "+aux[1]);
			}
			else if(r4==0 && !(boolean) value.getValue().getValue())
			{
				r4=1;
				long aux;
				aux= (long) this.get_Value("CR1T1.OperatedTime", 3);
				System.out.println("OP: " + aux);
				Main.pr.mchs[7].updateTime((int) (aux/1000));
			}
		}
		else if(identifier.contains("ART1"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(!state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("Pecas_armazem", 1);
				Main.pr.sys.setPieces(aux);
				Main.pr.sys.print_quantityPieces();
			}
		}
		else if(identifier.contains("ART2"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("Pecas_armazem", 1);
				Main.pr.sys.setPieces(aux);
				Main.pr.sys.print_quantityPieces();
			}
		}
	}
}