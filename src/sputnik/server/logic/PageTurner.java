package sputnik.server.logic;

import java.util.Vector;

import sputnik.server.NetworkManager;
import sputnik.server.util.Connection;
import sputnik.server.util.Tick;
import sputnik.util.SThread;
import sputnik.util.ThreadHandler;

public class PageTurner implements Runnable {

	private SThread thread;
	private Tick tick;
	private ServerLogic serverLogic;
	private NetworkManager networkManager;
	
	/* Constructors */
	public PageTurner( ServerLogic serverLogic, double nsPerTick, Vector<Connection> connections, int port ) {
		this.thread = new SThread(this);
		this.serverLogic = serverLogic;
		this.tick = new Tick( nsPerTick );
		this.networkManager = new NetworkManager( connections, port );
	}
	
	/* End Constructors */
	
	public long getTickCount() {
		return this.tick.getTicks();
	}
	
	public NetworkManager getNetworkManager() {
		return networkManager;
	}
	
	public void startDefaults() {
		this.networkManager.startEverything();
		ThreadHandler.startThread( thread );
	}
	
	public void start(){
		ThreadHandler.startThread( thread );
	}
	
	public void stop() {
		ThreadHandler.stopThread( thread );
	}

	@Override
	public void run() {
		
		boolean tickEvent = false;
		
		while( this.thread.isRunning() ) {
			tickEvent = this.tick.update();
			
			/* On tick rollover event, update logic */
			if( tickEvent ) { 
				this.serverLogic.updateOnTickEvent();
				/* NOW WE NEED TO SEND UPDATE PACKETS TO ALL CLIENTS.  HOW? */
				this.networkManager.updateClients(this.serverLogic.getTCPPayload(), this.serverLogic.getUDPPayload());
			}
		}
		
	}
}
