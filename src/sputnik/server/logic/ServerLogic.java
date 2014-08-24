package sputnik.server.logic;

import sputnik.util.pkt.TCPPacket;
import sputnik.util.pkt.UDPPacket;


public interface ServerLogic {
	
	/**
	 * Called on every server tick event.
	 */
	public void updateOnTickEvent();
	
	/**
	 * Get the data that this server logic instance requires.
	 * @return UDPPacket
	 */
	public UDPPacket getUDPPayload();
	
	/**
	 * Get the data that this server logic instance requires.
	 * @return TCPPacket
	 */
	public TCPPacket getTCPPayload();
}
