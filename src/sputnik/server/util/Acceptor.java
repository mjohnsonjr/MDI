package sputnik.server.util;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import sputnik.server.ConnectionManager;
import sputnik.util.ExceptionHandler;
import sputnik.util.Logger;
import sputnik.util.enumeration.LogLevel;

public class Acceptor implements Runnable {
	private ServerSocket socket;
	private int port;
	private ConnectionManager con_man;
	
	public Acceptor( int port, ConnectionManager con_man ) {
		this.port = port;
		this.con_man = con_man;
	}
	
	@Override
	public void run() {
		
		try {
			this.socket = new ServerSocket( port );
		} catch (IOException e) {
			/* This exception is OKAY, it only occurs on server start. */
			e.printStackTrace();
		}
		
		Socket clientSocket = null;
		DatagramSocket clientDatagramSocket = null;
		Connection connection = null;
		while( true ){
			try {
				clientSocket = socket.accept();
				Logger.log( "Client at host " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ".", LogLevel.DEBUG );
			} catch (IOException e) {
				ExceptionHandler.handleServerIOException( );
				System.out.println("HOPEFULLY THIS HAPPENED BECAUSE WE WERE SHUTTING DOWN SERVER \n (java has stupid way of stopping blocked threads)");
				break;
			} 
			/* Add to connection pool */
			if(clientSocket != null){
				try {
					clientDatagramSocket = new DatagramSocket( 60013 ) /* socket.getInetAddress() */ ;
				} catch (SocketException e) {
					ExceptionHandler.handleServerSocketException( );
				}
				connection = new Connection( clientSocket, clientDatagramSocket );
				con_man.add_connection( connection );
				//TODO:start connection thread here
				//connection.start();
			}
		}
	}
	
	public void shutdown()
	{
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IN ACCEPTOR SHUTDOWN METHOD");
			e.printStackTrace();
		}
	}
}
