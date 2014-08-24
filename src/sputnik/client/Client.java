package sputnik.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import sputnik.util.ExceptionHandler;



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
	
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	private ObjectOutputStream datagramOutputStream;
	private ObjectInputStream datagramInputStream;
	private ByteArrayInputStream byteArrayInputStream;
	private ByteArrayOutputStream byteArrayOutputStream;
	private byte[] inputRawData;
	private byte[] outputRawData;
	
	
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
		
		this.inputRawData = new byte[ 512 ];
		this.outputRawData = new byte[ 512 ];
		
		try {
			this.byteArrayOutputStream = new ByteArrayOutputStream( this.outputRawData.length );
			this.datagramOutputStream = new ObjectOutputStream( this.byteArrayOutputStream );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.byteArrayInputStream = new ByteArrayInputStream( this.inputRawData );
			this.datagramInputStream = new ObjectInputStream( this.byteArrayInputStream );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
