package sputnik.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

import sputnik.server.database.DatabaseManager;
import sputnik.util.ExceptionHandler;
import sputnik.util.Logger;
import sputnik.util.Player;
import sputnik.util.SThread;
import sputnik.util.ThreadHandler;
import sputnik.util.enumeration.ConnectionMode;
import sputnik.util.enumeration.LogLevel;
import sputnik.util.pkt.LoginPacket;
import sputnik.util.pkt.UDPPacket;

public class Connection implements Runnable {

	private Socket clientSocket;
	private DatagramSocket clientDatagramSocket;
	private Player player; 
	private ConnectionMode connectionMode;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	private ObjectOutputStream datagramOutputStream;
	private ObjectInputStream datagramInputStream;
	private ByteArrayInputStream byteArrayInputStream;
	private ByteArrayOutputStream byteArrayOutputStream;
	private byte[] inputRawData;
	private byte[] outputRawData;
	private DatagramPacket inputDatagramPacket;
	private DatagramPacket outputDatagramPacket;
	
	/* Threading */
	private SThread thread;
	
	/*
	 * Add info about authentication..
	 * Player info?
	 * some other specific things about this connection.
	 */
	
	public Connection( Socket clientSocket, DatagramSocket clientDatagramSocket, Player player ) {
		
		this.thread = new SThread(this);
		this.clientSocket = clientSocket;
		this.clientDatagramSocket = clientDatagramSocket;
		this.player = player;
		this.connectionMode = ConnectionMode.PRE_LOGIN;
		
		//TODO: Maybe break these into their own class.
		/* Input and Output Streams (TCP) */
		try {
			this.outputStream = new ObjectOutputStream( clientSocket.getOutputStream() );
		} catch (IOException e) {
			ExceptionHandler.handleIOException( this );
		}
		
		try {
			this.inputStream = new ObjectInputStream( clientSocket.getInputStream() );
		} catch (IOException e) {
			ExceptionHandler.handleIOException( this );
		}
		
		/* Input and Output (Datagram) */
		this.inputRawData = new byte[ 512 ];
		this.inputDatagramPacket = new DatagramPacket( this.inputRawData, this.inputRawData.length );
		
		this.outputRawData = new byte[ 512 ];
		this.outputDatagramPacket = new DatagramPacket( this.outputRawData, this.outputRawData.length );
		
		try {
			this.byteArrayOutputStream = new ByteArrayOutputStream( this.outputRawData.length );
			this.datagramOutputStream = new ObjectOutputStream( this.byteArrayOutputStream );
		} catch (IOException e) {
			ExceptionHandler.handleIOException( this );
		}
		
		try {
			this.byteArrayInputStream = new ByteArrayInputStream( this.inputRawData );
			this.datagramInputStream = new ObjectInputStream( this.byteArrayInputStream );
		} catch (IOException e) {
			ExceptionHandler.handleIOException( this );
		}
	}
	
	public void writeUDPPacket(UDPPacket udpPacket) {
		try {
			this.datagramOutputStream.writeObject( udpPacket );
		} catch (IOException e) {
			ExceptionHandler.handleIOException( this );
		}		
		
		/* Write to packet buf location, send. */
		this.outputRawData = this.byteArrayOutputStream.toByteArray();
		try {
			this.clientDatagramSocket.send(this.outputDatagramPacket );
		} catch (IOException e) {
			ExceptionHandler.handleIOException( this );
		}
	}
	
	/* Login packet received from DATA INPUT STREAM */
	public void login( LoginPacket loginPacket ) {
		
	}
	
	public void start(){

		ThreadHandler.startThread( thread );
	}
	
	public void stop() throws InterruptedException{

		ThreadHandler.stopThread( thread );
	}
	
	public Socket getClientSocket() {
		return clientSocket; 
	}
	
	public void close() {
		try {
			this.clientSocket.shutdownInput();
			this.clientSocket.close();
		} catch (IOException e) {
			//TODO: WARNING, may infinite loop.
			ExceptionHandler.handleIOException( this );
		}
	}
	
	@Override
	public boolean equals( Object o ){
		
		//TODO:
		
		return false;
		
	}
	public ConnectionMode getConnectionMode() {
		return connectionMode;
	}
	public Player getPlayer(){
		return player;
	}
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}

	public ObjectInputStream getInputStream() {
		return inputStream;
	}
	public ObjectOutputStream getDatagramOutputStream() {
		return outputStream;
	}

	public ObjectInputStream getDatagramInputStream() {
		return inputStream;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setOutputStream(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void setInputStream(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setConnectionMode(ConnectionMode connectionMode) {
		this.connectionMode = connectionMode;
	}

	@Override
	public void run() {
		
		while( this.thread.isRunning() ){
			
			switch( this.connectionMode ){
				
				case PRE_LOGIN:
					Logger.log( "IN PRELOGIN MODE WITH " + this.clientSocket.getInetAddress() + ".", LogLevel.DEBUG );
					
					Object readObject = null;
					
					/* Block for login packet */
					try {
						while( readObject == null ) {

							readObject = inputStream.readObject();
							
							if( !( readObject instanceof LoginPacket ) ){
								readObject = null;
								Logger.log( "RECEIVED BAD PKT " + this.clientSocket.getInetAddress() + ".", LogLevel.DEBUG );
								continue;
							}
							
							Logger.log( "RECEIVED TCP LOGIN PKT " + this.clientSocket.getInetAddress() + ".", LogLevel.DEBUG );
							
							//TODO: Handle this packet.
							//Query DB, load data, adjust state accoringly.
							Player player = DatabaseManager.STUBqueryPlayer();
							this.setPlayer( player );
							
							/* After verification, set mode */
							this.setConnectionMode( ConnectionMode.LOGGED_IN );
						}
					} catch (ClassNotFoundException e) {
						Logger.log( "CLASS NOT FOUND FATAL " + this.clientSocket.getInetAddress() + ".", LogLevel.STABLE );
						e.printStackTrace();
					} catch (IOException e) {
						ExceptionHandler.handleIOException( this );
					}
					
					break;
					
				case LOGGED_IN:
					Logger.log( "LOGGED IN " + this.clientSocket.getInetAddress() + ".", LogLevel.DEBUG );
					
					/* Once in LOGGED_IN state, ServerLogic class takes over ALL communications behavior! (updateClients) */
					
					
					break;
			default:
				break;
			}
		}
	}
}
