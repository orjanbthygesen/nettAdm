package snmpAlert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

public class snmpAlert {
	
	public final String address;
	public static final int port = 161;
	public final static String community = "ttm4128";
	// private Snmp snmp;
	private int threshold = 10000;
	private int oldPackets = 0;
	private static String targetAddress = "129.241.209.8";
	private String trapOid = ".1.3.6.1.2.1.1.1"; 
	public snmpClient client;


	public snmpAlert(String address) {
		super();
		this.address = address;
		client = new snmpClient(address);
		Timer timer = new Timer();
		int delay = 0;
		int period = 60000;

		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					doChecks();
				} catch (IOException e) {
					System.out.println("error i get packets");
				}				
			}
		},delay, period);
	}

	public void doChecks() throws IOException {
		int packets;
		packets = client.getInPackets();
		// System.out.println("pakker :"+ packets);

		if(checkThreshold(packets)) {

		}
		else {
			trap();
		}
	}

	private boolean checkThreshold (int packets) {
		int packetDiff = packets - oldPackets;
		oldPackets = packets;

		System.out.println("Packet difference since last check: "+packetDiff+" | Number of packets: "+packets);
		if(packetDiff < threshold) {
			return true;
		}
		else
			return false;

	}

	
	private void trap() {
		snmpTrap trap = new snmpTrap(address,trapOid);
		trap.sendTrap_Version2(address,trapOid);
	}

	public static void main(String[] args) throws IOException {
		new snmpAlert(targetAddress);
	}
}