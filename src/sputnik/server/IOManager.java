package sputnik.server;

import java.util.Vector;

import sputnik.server.util.Connection;
import sputnik.util.SThread;
import sputnik.util.ThreadHandler;
import sputnik.util.enumeration.ConnectionMode;
import sputnik.util.pkt.TCPPacket;
import sputnik.util.pkt.UDPPacket;

public class IOManager implements Runnable {

	private Vector<Connection> connections;
	private int port;
	
	/* Threading */
	private SThread thread;
	
	public IOManager( Vector<Connection> connections, int port ){
		this.connections = connections;
		this.port = port;
		this.thread = new SThread( this );
	}


	public void start() {
		ThreadHandler.startThread( thread );
	}
	
	public void stop() {
		ThreadHandler.stopThread( thread );
	}
	
	public void updateClients( TCPPacket tcpPacket, UDPPacket udpPacket ) {
		
		//Go through queue of player commands, updating each player's state accordingly
		for( Connection c : connections ){
			   //Process player command, add to command queue for processing.
			 //process( c.getPlayer().getCommands() );
		}
		
		/* Send the servers current state to all clients. */
		for( Connection c : connections ) {
			if( c.getConnectionMode() == ConnectionMode.LOGGED_IN ) {
				c.writeUDPPacket( udpPacket );
			}
		}
	}

	@Override
	public void run() {
		
		while( this.thread.isRunning() ){
			//On game tick, update.
		}
		
	}
	
}
