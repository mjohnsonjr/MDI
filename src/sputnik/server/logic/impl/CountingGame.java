package sputnik.server.logic.impl;

import sputnik.server.logic.ServerLogic;
import sputnik.util.pkt.TCPPacket;
import sputnik.util.pkt.UDPPacket;

public class CountingGame implements ServerLogic {

	private long counter = 0L;
	
	@Override
	public void updateOnTickEvent() {
		counter++;
	}
	
	@Override
	public UDPPacket getUDPPayload() {
		
		UDPPacket packet = new UDPPacket();
		
		Long[] dlong = new Long[1];
		dlong[0] = ( counter );
		
		packet.setData( dlong );
		return packet;
	}
	
	public long getCounter() {
		return counter;
	}

	@Override
	public TCPPacket getTCPPayload() {
		// TODO Auto-generated method stub
		return null;
	}
}
