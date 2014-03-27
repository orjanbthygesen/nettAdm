package snmpAlert;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class snmpClient {
	
	public final String address;
	public static final int port = 161;
	public final static String community = "ttm4128";
	private Snmp snmp;
	// private static String targetAddress = "129.241.209.8";

	public snmpClient(String address) {
		super();
		this.address = address;
		try {
			start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void start() throws IOException {
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	}

	public void stop() throws IOException {
		snmp.close();
	}

	public String getAsString(OID oid) throws IOException {
		ResponseEvent event = get(new OID[]{oid});
		return event.getResponse().get(0).getVariable().toString();
	}

	public ResponseEvent get(OID oids[]) throws IOException {
		PDU pdu = new PDU();
	 	for (OID oid : oids) {
	 	     pdu.add(new VariableBinding(oid));
	 	}
	 	pdu.setType(PDU.GET);
	 	ResponseEvent event = snmp.send(pdu, getTarget(), null);
		if(event != null) {
	             return event;
		}
		throw new RuntimeException("GET timed out");
	}

	private Target getTarget() {
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(new UdpAddress(address+ "/" + port));
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

	public int getInPackets() throws IOException {
		String inPackets = this.getAsString(new OID("1.3.6.1.2.1.4.31.1.1.3.1"));
		return Integer.parseInt(inPackets); 
	}

	public static void main(String[] args) throws IOException {
	 	// snmpClient client = new snmpClient(targetAddress);
	}
}