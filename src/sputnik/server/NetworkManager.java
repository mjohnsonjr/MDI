package sputnik.server;

import java.util.Vector;

import sputnik.server.util.Connection;
import sputnik.util.pkt.TCPPacket;
import sputnik.util.pkt.UDPPacket;

/**
 * Master of ALL network communications.
 * @author michael
 *
 */
public class NetworkManager {
	
	private ConnectionManager connectionManager;
	private IOManager ioManager;
	private Vector<Connection> connections;
	private int port;
	
	public NetworkManager( Vector<Connection> connections, int port ) {
		
		/* Create the connection manager */
		this.connectionManager = new ConnectionManager( connections, port );
		/* Create the IO (Datagram) Manager */
		this.ioManager = new IOManager( connections, port );
		
	}
	
	public void startIOManager(){
		this.ioManager.start();
	}
	
	public void startConnectionManager(){
		this.connectionManager.start();
	}
	
	public void startEverything(){
		startIOManager();
		startConnectionManager();
	}
	
	public void stopIOManager(){
		this.ioManager.stop();
	}
	
	public void stopConnectionManager(){
		this.connectionManager.stop();
	}
	
	public void stopEverything(){
		stopIOManager();
		stopConnectionManager();
	}
	
	public void updateClients( TCPPacket tcpPacket, UDPPacket udpPacket ) {
		
		this.ioManager.updateClients(tcpPacket, udpPacket);
		
	}
}
