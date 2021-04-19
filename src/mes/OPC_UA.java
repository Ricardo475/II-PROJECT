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
	String IP = "localhost", path = "|var|CODESYS Control Win V3 x64.Application.PLC_PRG.";
	private static int id_node = 4;
	private UaSubscription sub = null;

	public void connect() {
		try {
			client = OpcUaClient.create("opc.tcp://" + IP + ":4840");
			client.connect().get();
			//this.createSubscription();
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

	public Object get_Value(String VarName) {
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

	public void Set_value(String VarName, int value) {
		String var;
		var = path + VarName;
		NodeId nodeidstring = new NodeId(id_node, var);
		int i = value;

		try {
			System.out.println(client.writeValue(nodeidstring, new DataValue(new Variant((short) i))).get());
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
		for(int i:value)
		{
			this.Set_value(VarName+"["+i+"]", value[i]);
		}
	}
	
	public void createSubscription() throws Exception {

		if (sub != null) {
			return;
		}
		sub = client.getSubscriptionManager().createSubscription(1000.0).get();
		List<MonitoredItemCreateRequest> lmr= new ArrayList<>();
		
		ReadValueId readValueId = new ReadValueId(new NodeId(id_node, path + "int_var"), AttributeId.Value.uid(), null,
				QualifiedName.NULL_VALUE);
		UInteger clientHandle = sub.nextClientHandle();

		MonitoringParameters parameters = new MonitoringParameters(clientHandle, 10.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		lmr.add(new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting,
				parameters));

		ItemCreationCallback onItemCreated = (item, id) -> item.setValueConsumer(this::onSubscriptionChangeValue);

		List<UaMonitoredItem> items = sub
				.createMonitoredItems(TimestampsToReturn.Both, lmr, onItemCreated).get();

	}

	private void onSubscriptionChangeValue(UaMonitoredItem item, DataValue value) {
		System.out.println("item: " + item.getReadValueId().getNodeId().getIdentifier().toString() + " value: "
				+ value.getValue().getValue());
	}
}