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
	String IP = "localhost", path = "|var|CODESYS Control Win V3 x64.Application.PLC_PRG.",path2="|var|CODESYS Control Win V3 x64.Application.POU.",path3="|var|CODESYS Control Win V3 x64.Application.POU_2.",path4 = "|var|CODESYS Control Win V3 x64.Application.PLC_PRG2.",path5="|var|CODESYS Control Win V3 x64.Application.GVL.";
	private static int id_node = 4;
	private UaSubscription sub = null;
	int r1=1,r2=1,r3=1,r4=1,r5=1,r6=1,r7=1,r8=1,r9=1,r10=1;


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
		if(p== 1) //plc_prg
			var = path + VarName;
		else if(p==2) //pou
			var= path2+VarName;
		else if(p==3) // pou2
			var= path3+VarName;
		else if(p==4) // plc_prg2
			var= path4+VarName;
		else if(p==5) //gvl
			var= path5+VarName;
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

	public void Set_value(String VarName, int value,int p) {
		String var="";
		if(p== 1)
			var = path + VarName;
		else if(p==2)
			var= path2+VarName;
		else if(p==3)
			var= path3+VarName;
		else if(p==4)
			var= path4+VarName;
		else if(p==5)
			var= path5+VarName;
		NodeId nodeidstring = new NodeId(id_node, var);
		int i = value;

		try {
			System.out.println(client.writeValue(nodeidstring, new DataValue(new Variant((short) i))).get() + "  ------ > " +value);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Set_value(String VarName, boolean value,int p) {
		String var="";
		if(p== 1)
			var = path + VarName;
		else if(p==2)
			var= path2+VarName;
		else if(p==3)
			var= path3+VarName;
		else if(p==4)
			var= path4+VarName;
		else if(p==5)
			var= path5+VarName;
		NodeId nodeidstring = new NodeId(id_node, var);
		boolean i = value;

		try {
			System.out.println(client.writeValue(nodeidstring, new DataValue(new Variant(i))).get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Set_value(String VarName, String value,int p) {
		String var="";
		if(p== 1)
			var = path + VarName;
		else if(p==2)
			var= path2+VarName;
		else if(p==3)
			var= path3+VarName;
		else if(p==4)
			var= path4+VarName;
		else if(p==5)
			var= path5+VarName;;
			NodeId nodeidstring = new NodeId(id_node, var);
			String i = value;

			try {
				System.out.println(client.writeValue(nodeidstring, new DataValue(new Variant(i))).get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void Set_value(String VarName, int[] value,int p) {
		for(int i=0; i<value.length;i++)
		{
			//System.out.println(i);
			this.Set_value(VarName+"["+i+"]", value[i],p);
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
		ReadValueId readValueId15 = new ReadValueId(new NodeId(id_node, path2 + "CL1T1.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId16 = new ReadValueId(new NodeId(id_node, path2 + "CL1T2.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId17 = new ReadValueId(new NodeId(id_node, path2 + "CL1T3.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId18 = new ReadValueId(new NodeId(id_node, path2 + "CL1T4.Sensor"), AttributeId.Value.uid(), null,
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
		ReadValueId readValueId19 = new ReadValueId(new NodeId(id_node, path3 + "CR1T1.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId20 = new ReadValueId(new NodeId(id_node, path3 + "CR1T2.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId21 = new ReadValueId(new NodeId(id_node, path3 + "CR1T3.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId22 = new ReadValueId(new NodeId(id_node, path3 + "CR1T4.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId11 = new ReadValueId(new NodeId(id_node, path3 + "ART2.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId12 = new ReadValueId(new NodeId(id_node, path3 + "ART1.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId13 = new ReadValueId(new NodeId(id_node, path3 + "CR2T1b.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId14 = new ReadValueId(new NodeId(id_node, path3 + "CR2T7b.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId23 = new ReadValueId(new NodeId(id_node, path3 + "CR2T3.GOINGTOPUSHER"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId24 = new ReadValueId(new NodeId(id_node, path3 + "CR2T4.GOINGTOPUSHER"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId25 = new ReadValueId(new NodeId(id_node, path3 + "CR2T5.GOINGTOPUSHER"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId26 = new ReadValueId(new NodeId(id_node, path3 + "CR0T6.Sensor"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		ReadValueId readValueId27 = new ReadValueId(new NodeId(id_node, path2 + "CL0T6.Sensor"), AttributeId.Value.uid(), null,
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

		UInteger clientHandle13 = sub.nextClientHandle();

		MonitoringParameters parameters13 = new MonitoringParameters(clientHandle13,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId13, MonitoringMode.Reporting,
				parameters13));

		UInteger clientHandle14 = sub.nextClientHandle();

		MonitoringParameters parameters14 = new MonitoringParameters(clientHandle14,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId14, MonitoringMode.Reporting,
				parameters14));

		UInteger clientHandle15 = sub.nextClientHandle();

		MonitoringParameters parameters15 = new MonitoringParameters(clientHandle15,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId15, MonitoringMode.Reporting,
				parameters15));

		UInteger clientHandle16 = sub.nextClientHandle();

		MonitoringParameters parameters16 = new MonitoringParameters(clientHandle16,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId16, MonitoringMode.Reporting,
				parameters16));

		UInteger clientHandle17 = sub.nextClientHandle();

		MonitoringParameters parameters17 = new MonitoringParameters(clientHandle17,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId17, MonitoringMode.Reporting,
				parameters17));

		UInteger clientHandle18 = sub.nextClientHandle();

		MonitoringParameters parameters18 = new MonitoringParameters(clientHandle18,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId18, MonitoringMode.Reporting,
				parameters18));

		UInteger clientHandle19 = sub.nextClientHandle();

		MonitoringParameters parameters19 = new MonitoringParameters(clientHandle19,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId19, MonitoringMode.Reporting,
				parameters19));

		UInteger clientHandle20 = sub.nextClientHandle();

		MonitoringParameters parameters20 = new MonitoringParameters(clientHandle20,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId20, MonitoringMode.Reporting,
				parameters20));

		UInteger clientHandle21 = sub.nextClientHandle();

		MonitoringParameters parameters21 = new MonitoringParameters(clientHandle21,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId21, MonitoringMode.Reporting,
				parameters21));

		UInteger clientHandle22 = sub.nextClientHandle();

		MonitoringParameters parameters22 = new MonitoringParameters(clientHandle22,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId22, MonitoringMode.Reporting,
				parameters22));

		UInteger clientHandle23 = sub.nextClientHandle();

		MonitoringParameters parameters23 = new MonitoringParameters(clientHandle23,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId23, MonitoringMode.Reporting,
				parameters23));

		UInteger clientHandle24 = sub.nextClientHandle();

		MonitoringParameters parameters24 = new MonitoringParameters(clientHandle24,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId24, MonitoringMode.Reporting,
				parameters24));

		UInteger clientHandle25 = sub.nextClientHandle();

		MonitoringParameters parameters25 = new MonitoringParameters(clientHandle25,10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId25, MonitoringMode.Reporting,
				parameters25));

		ItemCreationCallback onItemCreated = (item, id) -> item.setValueConsumer(this::onSubscriptionChangeValue);

		List<UaMonitoredItem> items = sub
				.createMonitoredItems(TimestampsToReturn.Both, lmr, onItemCreated).get();



		UInteger clientHandle26 = sub.nextClientHandle();

		MonitoringParameters parameters26 = new MonitoringParameters(clientHandle26, 10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId26, MonitoringMode.Reporting,
				parameters26));




		UInteger clientHandle27 = sub.nextClientHandle();

		MonitoringParameters parameters27 = new MonitoringParameters(clientHandle27, 10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
				);

		lmr.add(new MonitoredItemCreateRequest(readValueId27, MonitoringMode.Reporting,
				parameters27));
	}

	private void onSubscriptionChangeValue(UaMonitoredItem item, DataValue value) {
		String identifier =item.getReadValueId().getNodeId().getIdentifier().toString();

		System.out.println("item: " + identifier + " value: "+ value.getValue().getValue());

		if(identifier.contains("CL1T4.Disponivel"))
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

			if((boolean) value.getValue().getValue()==state) Main.pr.mchs[0].state = state;

		}
		else if(identifier.contains("CL1T4.Sensor"))
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

			if(state)
			{
				r1=0;

			}
			else if(r1==0 && !(boolean) value.getValue().getValue())
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CL1T4.pieces_operated", 2);
				Main.pr.mchs[0].updateOperatedPieces(aux);
				System.out.println("Pieces " +": "+aux[1]);
				r1=1;
				long aux1;
				aux1= (long) this.get_Value("CL1T4.OperatedTime", 2);
				System.out.println("OP: " + aux1);
				Main.pr.mchs[0].updateTime((int) (aux1/1000));
			}
		}
		else if(identifier.contains("CL1T3.Disponivel"))
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

			if((boolean) value.getValue().getValue()==state) Main.pr.mchs[1].state = state;


		}
		else if(identifier.contains("CL1T3.Sensor"))
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

			if(state)
			{
				r2=0;

			}
			else if(r2==0 && !(boolean) value.getValue().getValue())
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CL1T3.pieces_operated", 2);
				Main.pr.mchs[1].updateOperatedPieces(aux);
				System.out.println("Pieces " +": " + aux[1]);
				r2=1;
				long aux1;
				aux1= (long) this.get_Value("CL1T3.OperatedTime", 2);
				System.out.println("OP: " + aux1);
				Main.pr.mchs[1].updateTime((int) (aux1/1000));
			}
		}
		else if(identifier.contains("CL1T2.Disponivel"))
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

			if((boolean) value.getValue().getValue()==state) Main.pr.mchs[2].state = state;


		}
		else if(identifier.contains("CL1T2.Sensor"))
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

			if(state)
			{
				r3=0;

			}
			else if(r3==0 && !(boolean) value.getValue().getValue())
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CL1T2.pieces_operated", 2);
				Main.pr.mchs[2].updateOperatedPieces(aux);
				System.out.println("Pieces " + ": " +aux[1]);
				r3=1;
				long aux1;
				aux1= (long) this.get_Value("CL1T2.OperatedTime", 2);
				System.out.println("OP: " + aux1);
				Main.pr.mchs[2].updateTime((int) (aux1/1000));
			}
		}
		else if(identifier.contains("CL1T1.Disponivel"))
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

			if((boolean) value.getValue().getValue()==state ) Main.pr.mchs[3].state = state;

		}
		else if(identifier.contains("CL1T1.Sensor"))
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

			if(state)
			{
				r4=0;

			}
			else if(r4==0 && !(boolean) value.getValue().getValue())
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CL1T1.pieces_operated", 2);
				Main.pr.mchs[3].updateOperatedPieces(aux);
				System.out.println("Pieces " + ": "+aux[1]);
				r4=1;
				long aux1;
				aux1= (long) this.get_Value("CL1T1.OperatedTime", 2);
				System.out.println("OP: " + aux1);
				Main.pr.mchs[3].updateTime((int) (aux1/1000));
			}
		}
		else if(identifier.contains("ALT5"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(!state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("Pecas_armazem", 5); 
				if(aux!=null)
					Main.pr.sys.setPieces(aux);
				//Main.pr.sys.print_quantityPieces();


			}

			if(state)
			{
				Main.OL.pecaProc((short) this.get_Value("ALT5.curr_piece.ordem", 2));
			}

		}
		else if(identifier.contains("ALT6"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("Pecas_armazem", 5);
				if(aux!=null)
					Main.pr.sys.setPieces(aux);
				//Main.pr.sys.print_quantityPieces();
			}
		}
		else if(identifier.contains("CL0T6")) {

			boolean state = (boolean) value.getValue().getValue();

			if(state) {

				r9 = 0;
			}
			else if(r9 == 0 && !(boolean) value.getValue().getValue()) {

				boolean aux;
				aux = (boolean) value.getValue().getValue();
				//Main.pr.flag1 = true;
				r9 = 1;
			}
		}
		///////////////// CELULA DIREITA DAQUI PARA BAIXO //////////////////////////////////
		else if(identifier.contains("CR1T4.Disponivel"))
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

			if((boolean) value.getValue().getValue()==state) Main.pr.mchs[4].state = state;

		}
		else if(identifier.contains("CR1T4.Sensor"))
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

			if(state)
			{
				r5=0;

			}
			else if(r5==0 && !(boolean) value.getValue().getValue())
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CR1T4.pieces_operated", 3);
				Main.pr.mchs[4].updateOperatedPieces(aux);
				System.out.println("Pieces "+ aux[0] + ": " +aux[1]);
				r5=1;
				long aux1;
				aux1= (long) this.get_Value("CR1T4.OperatedTime", 3);
				System.out.println("OP: " + aux1);
				Main.pr.mchs[4].updateTime((int) (aux1/1000));
			}
		}
		else if(identifier.contains("CR1T3.Disponivel"))
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

			if((boolean) value.getValue().getValue()==state) Main.pr.mchs[5].state = state;

		}
		else if(identifier.contains("CR1T3.Sensor"))
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

			if(state)
			{
				r6=0;

			}
			else if(r6==0 && !(boolean) value.getValue().getValue())
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CR1T3.pieces_operated", 3);
				Main.pr.mchs[5].updateOperatedPieces(aux);
				System.out.println("Pieces " + aux[0] +": " + aux[1]);
				r6=1;
				long aux1;
				aux1= (long) this.get_Value("CR1T3.OperatedTime", 3);
				System.out.println("OP: " + aux1);
				Main.pr.mchs[5].updateTime((int) (aux1/1000));
			}
		}
		else if(identifier.contains("CR1T2.Disponivel"))
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

			if((boolean) value.getValue().getValue()==state) Main.pr.mchs[6].state = state;

		}
		else if(identifier.contains("CR1T2.Sensor"))
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

			if(state)
			{
				r7=0;

			}
			else if(r7==0 && !(boolean) value.getValue().getValue())
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CR1T2.pieces_operated", 3);
				Main.pr.mchs[6].updateOperatedPieces(aux);
				System.out.println("Pieces "+ aux[0] + ": " +aux[1]);
				r7=1;
				long aux1;
				aux1= (long) this.get_Value("CR1T2.OperatedTime", 3);
				System.out.println("OP: " + aux1);
				Main.pr.mchs[6].updateTime((int) (aux1/1000));
			}
		}
		else if(identifier.contains("CR1T1.Disponivel"))
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

			if((boolean) value.getValue().getValue()==state) Main.pr.mchs[7].state = state;

		}
		else if(identifier.contains("CR1T1.Sensor"))
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

			if(state)
			{
				r8=0;

			}
			else if(r8==0 && !(boolean) value.getValue().getValue())
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CR1T1.pieces_operated", 3);
				Main.pr.mchs[7].updateOperatedPieces(aux);
				System.out.println("Pieces "+ aux[0] + ": "+aux[1]);
				r8=1;
				long aux1;
				aux1= (long) this.get_Value("CR1T1.OperatedTime", 3);
				System.out.println("OP: " + aux1);
				Main.pr.mchs[7].updateTime((int) (aux1/1000));
			}
		}
		else if(identifier.contains("ART1"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(!state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("Pecas_armazem", 5);
				if(aux!=null)
					Main.pr.sys.setPieces(aux);
			}


			if(state)
			{
				Main.OL.pecaProc((short) this.get_Value("ART1.curr_piece.ordem", 3));
			}

		}
		else if(identifier.contains("ART2"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("Pecas_armazem", 5);
				if(aux!=null)
					Main.pr.sys.setPieces(aux);
				//Main.pr.sys.print_quantityPieces();
			}
		}
		else if(identifier.contains("CR2T3"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CR2T3.piecesOut", 3);
				Main.pr.pshs[0].setPusher(aux);;
				//Main.pr.sys.print_quantityPieces();
			}
		}
		else if(identifier.contains("CR2T4"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CR2T4.piecesOut", 3);
				Main.pr.pshs[1].setPusher(aux);;
				//Main.pr.sys.print_quantityPieces();
			}
		}
		else if(identifier.contains("CR2T5"))
		{
			boolean state = (boolean) value.getValue().getValue();
			if(state)
			{
				Short[] aux;
				aux= (Short[]) this.get_Value("CR2T5.piecesOut", 3);
				Main.pr.pshs[2].setPusher(aux);;
				//Main.pr.sys.print_quantityPieces();
			}
		}
		else if(identifier.contains("CR2T1b") || identifier.contains("CR2T7b"))
		{
			int lowest_no = 0;
			Loading load = null;
			boolean state = (boolean) value.getValue().getValue();

			if(state) {

				for(int i = 0; i < Main.OL.OrdersList.size();i++) {

					Order o = Main.OL.OrdersList.get(i);

					if(o.getOrderNumber()==lowest_no) 
						lowest_no++;
				}

				if(identifier.contains("CR2T1b")) {
					System.out.println("CR2T1b: " + state);
					load = new Loading(lowest_no,"P1",(((int)System.currentTimeMillis()-Main.start)/1000));	
				}

				else if(identifier.contains("CR2T7b")) {
					System.out.println("CR2T7b: " + state);
					load = new Loading(lowest_no,"P2",(((int)System.currentTimeMillis()-Main.start)/1000));	
				}

				((Order)load).activeOrder = true;
				((Order)load).done = true;
				Main.OL.addOrder(load);
			}
		}
		else if(identifier.contains("CR0T6")) {

			boolean state = (boolean) value.getValue().getValue();

			if(state) {

				r10 = 0;
			}
			else if(r10 == 0 && !(boolean) value.getValue().getValue()) {

				boolean aux;
				aux = (boolean) value.getValue().getValue();
				//Main.pr.flag2 = true;
				r10 = 1;
			}
		}
	}
}