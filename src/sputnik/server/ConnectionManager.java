package sputnik.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import sputnik.server.util.Acceptor;
import sputnik.server.util.Connection;
import sputnik.util.enumeration.ConnectionMode;

/**
 * TODO: Maybe purge the acceptor/clean this up somehow.
 * @author michael
 *
 */
public class ConnectionManager {
	/* List of connected Clients */
	private ServerSocket socket;
	private Acceptor acceptor;
	private Thread acceptor_thread;
	private int port;
	private Vector<Connection> connections;
	
	public ConnectionManager( int port ) {
		connections = new Vector<Connection>();
		this.port = port;
		this.acceptor = new Acceptor(port, this);
		acceptor_thread = new Thread(acceptor);
		acceptor_thread.start();
	}
	
	public void add_connection(Connection con )
	{
		connections.add(con);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Vector<Connection> getConnections() {
		return connections;
	}

	public void setConnections(Vector<Connection> connections) {
		this.connections = connections;
	}
	
	public boolean shutdown_acceptor()
	{
		acceptor.shutdown();
		try {
			acceptor_thread.join();
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
