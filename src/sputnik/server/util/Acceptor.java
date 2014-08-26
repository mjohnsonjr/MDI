package sputnik.server.util;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import sputnik.util.ExceptionHandler;
import sputnik.util.Logger;
import sputnik.util.SThread;
import sputnik.util.ThreadHandler;
import sputnik.util.enumeration.LogLevel;

public class Acceptor implements Runnable {

	private SThread thread;
	private Vector<Connection> connections;
	private ServerSocket socket;
	
	public Acceptor(ServerSocket socket, Vector<Connection> connections ) {
		
		this.connections = connections;
		this.socket = socket;
		this.thread = new SThread(this);
	}
	
	public void start() {
		
		ThreadHandler.startThread( thread );
	}
	
	public void stop() throws InterruptedException {
		
		ThreadHandler.stopThread( thread );
	}
	
	
	@Override
	public void run() {
		Socket clientSocket = null;
		DatagramSocket clientDatagramSocket = null;
		Connection connection = null;
		while( thread.isRunning() ){
			try {
				clientSocket = socket.accept();
				Logger.log( "Client at host " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ".", LogLevel.DEBUG );
			} catch (IOException e) {
				ExceptionHandler.handleServerIOException( );
			}
			
			/* Add to connection pool */
			if(clientSocket != null){
				try {
					clientDatagramSocket = new DatagramSocket( new InetSocketAddress("10.30.1.126", 60013) /* socket.getInetAddress() */ );
				} catch (SocketException e) {
					ExceptionHandler.handleServerSocketException( );
				}
				connection = new Connection( clientSocket, clientDatagramSocket, null );
				connections.add( connection );
				connection.start();
			}
		}
	}
}
