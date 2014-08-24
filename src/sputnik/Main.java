package sputnik;

import java.io.IOException;
import java.util.Vector;

import sputnik.client.Client;
import sputnik.server.logic.PageTurner;
import sputnik.server.logic.impl.CountingGame;
import sputnik.server.util.Connection;
import sputnik.util.pkt.LoginPacket;

/**
 * THIS IS A TESTING CLASS.
 * @author michael
 *
 */
public class Main {
	
	public enum Mode {
		SERVER, CLIENT
	}
	
	//private static Mode mode = Mode.SERVER;
	private static Mode mode = Mode.CLIENT;

	static /* Server Vars */
	Vector<Connection> connections = new Vector<Connection>();
	static int port = 55555;
	
	
	public static void main(String[] args) throws IOException {
		
		switch ( mode ){
		
		case SERVER:
			
			PageTurner pageTurner = new PageTurner(new CountingGame(), 100000000000.0, connections, port);
			pageTurner.startDefaults();
			
			System.out.println("Started Server.");
			
			break;
			
			
		case CLIENT:
			
			Client client = new Client( "localhost", port );
			client.connect();
			Object udpPacket = null;
			
			while(true){
				
				LoginPacket packet = new LoginPacket();
				packet.setUsername("VOXIDE");
				packet.setPassword("PASSWORD");
				
				/* Write object */
				client.getOutputStream().writeObject(packet);
				
//				if( udpPacket instanceof UDPPacket ) { 
//					Long[] counter =  ( Long[] ) ( ( ( UDPPacket ) udpPacket).getData() ); 
//					System.out.println( "THE COUNT!: " + counter[0] );
//					//client.getInputStream().
//				}
			}
			
			
		default:
			break;
					
		}	
	}
	
}
