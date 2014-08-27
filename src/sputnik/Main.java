package sputnik;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.Vector;

import sputnik.client.Client;
import sputnik.server.ConnectionManager;
import sputnik.server.logic.impl.CountingGame;
import sputnik.server.util.Connection;
import sputnik.util.pkt.LoginPacket;
import sputnik.util.pkt.UDPPacket;

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
	private static ConnectionManager con_manager;
	
	static int port = 55555;
	
	
	public static void main(String[] args) throws IOException {
		
		switch ( mode ){
		
		case SERVER:
			con_manager = new ConnectionManager(port);
			System.out.println("Started Server.");
			
			break;
			
			
		case CLIENT:
			
			Client client = new Client( "localhost", port );
			client.connect();
			Object udpPacket = null;
			byte[] buf = new byte[1024];
			
			while(true){
				
				LoginPacket packet = new LoginPacket();
				packet.setUsername("VOXIDE");
				packet.setPassword("PASSWORD");
				
				/* Write object */
				client.getOutputStream().writeObject(packet);
				
				
				DatagramPacket datagramPacket = new DatagramPacket( buf, buf.length );
				
				System.out.println("1");
				client.getDatagramSocket().receive( datagramPacket );
				System.out.println("2");
				ByteArrayInputStream byteStream = new ByteArrayInputStream( buf );
                
				ObjectInputStream is = new ObjectInputStream(new BufferedInputStream( byteStream ) );
				System.out.println("3");
				try {
				     udpPacket = is.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
//				try {
//					udpPacket = client.getDatagramInputStream().readObject();
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
				System.out.println("OUT!");
				if( udpPacket instanceof UDPPacket ) { 
					System.out.println("IN!");
					Long[] counter =  ( Long[] ) ( ( ( UDPPacket ) udpPacket).getData() ); 
					System.out.println( "THE COUNT!: " + counter[0] );
					//client.getInputStream().
				}
			}
			
		default:
			break;
					
		}	
	}
}
