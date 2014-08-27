package sputnik.server;

import java.util.Vector;

import sputnik.server.util.Connection;
import sputnik.util.enumeration.ConnectionMode;
import sputnik.util.pkt.TCPPacket;
import sputnik.util.pkt.UDPPacket;

public class IOManager {
	
	private ConnectionManager con_manager;
	
	public IOManager( ConnectionManager manager ){
		con_manager = manager;
	}
	
	public void updateClients( TCPPacket tcpPacket, UDPPacket udpPacket ) {
		
		//Go through queue of player commands, updating each player's state accordingly
		for( Connection c : con_manager.getConnections() ){
			   //Process player command, add to command queue for processing.
			 //process( c.getPlayer().getCommands() );
		}
		
		/* Send the servers current state to all clients. */
		for( Connection c : con_manager.getConnections() ) {
			if( c.getConnectionMode() == ConnectionMode.LOGGED_IN ) {
				c.writeUDPPacket( udpPacket );
			}
		}
	}
	
}
