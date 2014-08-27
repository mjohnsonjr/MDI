package sputnik.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;



/**
 * RIGHT NOW THIS CLASS IS TEST ONLY.
 * 
 * DO NOT USE THIS IN REAL CODE!
 * 
 * @author michael
 *
 */
public class Client {
	
	private String hostname;
	private int port;
	private Socket socket;
	private DatagramSocket datagramSocket;
	
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	private ObjectOutputStream datagramOutputStream;
	private ObjectInputStream datagramInputStream;
	private ByteArrayInputStream byteArrayInputStream;
	private ByteArrayOutputStream byteArrayOutputStream;
	private byte[] inputRawData;
	private byte[] outputRawData;
	private DatagramPacket outputDatagramPacket;
	private DatagramPacket inputDatagramPacket;
	
	
	public Client( String hostname, int port ) {
		
		this.hostname = hostname;
		this.port = port;
			
	}
	
	public ObjectOutputStream getOutputStream(){
		return outputStream;
	}
	
	public ObjectInputStream getInputStream(){
		return inputStream;
	}
	
	public ObjectOutputStream getDatagramOutputStream(){
		return datagramOutputStream;
	}
	
	public ObjectInputStream getDatagramInputStream(){
		return datagramInputStream;
	}

	public DatagramSocket getDatagramSocket(){
		return datagramSocket;
	}
	
	public void connect() throws UnknownHostException, SocketException {
		try {
			socket = new Socket( hostname, port );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		/* Input and Output Streams */
		try {
			this.outputStream = new ObjectOutputStream( socket.getOutputStream() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			this.inputStream = new ObjectInputStream( socket.getInputStream() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    this.datagramSocket = new DatagramSocket( 60014, new InetAddress() )/* socket.getInetAddress() */ ;
		
		this.inputRawData = new byte[ 1024 ];
		this.inputDatagramPacket = new DatagramPacket( this.inputRawData, this.inputRawData.length );
		
		this.outputRawData = new byte[ 1024 ];
		this.outputDatagramPacket = new DatagramPacket( this.outputRawData, this.outputRawData.length );
		
		try {
			this.byteArrayOutputStream = new ByteArrayOutputStream( this.outputRawData.length );
			this.datagramOutputStream = new ObjectOutputStream( this.byteArrayOutputStream );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			this.byteArrayInputStream = new ByteArrayInputStream( this.inputRawData );
//			this.datagramInputStream = new ObjectInputStream( this.byteArrayInputStream );
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
