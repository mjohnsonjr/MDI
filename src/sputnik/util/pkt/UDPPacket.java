package sputnik.util.pkt;

import java.io.Serializable;

public class UDPPacket implements Serializable {
	
	private static final long serialVersionUID = -4456279889398099120L;
	
	//private Vector<Connection> connections;
	//private Player thisPlayer;
	private Object[] data;
	
//	public Vector<Connection> getConnections() {
//		return connections;
//	}
//	public Player getThisPlayer() {
//		return thisPlayer;
//	}
		
	public Object[] getData() {
		return data;
	}
	public void setData(Object[] data) {
		this.data = data;
	}
//	public void setConnections(Vector<Connection> connections) {
//		this.connections = connections;
//	}
//	public void setThisPlayer(Player thisPlayer) {
//		this.thisPlayer = thisPlayer;
//	}
}
