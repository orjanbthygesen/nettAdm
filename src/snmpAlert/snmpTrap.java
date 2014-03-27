package snmpAlert;

import java.io.IOException;
import java.util.Date;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import snmpAlert.snmpAlert;

	public class snmpTrap{
 
	    //Ideally Port 162 should be used to send receive Trap, any other available Port can be used
	    public static final int port = 162;
	    public snmpTrap(String ipAddress, String Oid) {
	    	sendTrap_Version2(ipAddress, Oid);
		}
	    
	    private static String targetAddress = "129.241.209.8";
	    snmpClient test = new snmpClient(targetAddress);
	   
	    private int count(){
	    	int co = 0;
	    	try {
				co = test.getInPackets();
				return co; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return co;
	    }
	   
	   int count = count();
	
	    /**
	     * This methods sends the V1 trap to the Localhost in port 162
	     */
	    public void sendTrap_Version2(String ipAddress, String Oid) {
	        try {
	            // Create Transport Mapping
	            TransportMapping transport = new DefaultUdpTransportMapping();
	            transport.listen();
 
	            // Create Target
	            CommunityTarget cTarget = new CommunityTarget();
	            cTarget.setCommunity(new OctetString(snmpAlert.community));
	            cTarget.setVersion(SnmpConstants.version2c);
	            cTarget.setAddress(new UdpAddress(ipAddress + "/" + port));
	            cTarget.setRetries(1);
	            cTarget.setTimeout(5000);

	            // Create PDU for V2
	            PDU pdu = new PDU();
	            		
	            // need to specify the system up time
	            pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new OctetString(new Date().toString())));
	            pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(Oid)));
	           //  pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress, new IpAddress(ipAddress)));
	            pdu.add(new VariableBinding(new OID(Oid), new OctetString("Sahara lab")));
	            pdu.add(new VariableBinding(new OID(Oid), new OctetString("Number of packets: "+count)));
	   
	          

	            pdu.setType(PDU.TRAP);
 
	            // Send the PDU
	            Snmp snmp = new Snmp(transport);
	            snmp.send(pdu, cTarget);
	            snmp.close();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }

	}