package sputnik.server.util;

public class Tick {
	
	/* 100ms ticks */
	private final double nsPerTick;// = 100000000.0;
	
	private long prevTime, curTime;
	private long ticks;
	private long deltaT;
	
	
	public Tick( double nsPerTick ) {
		this.nsPerTick = nsPerTick;
		this.prevTime = System.nanoTime();
	}
	
	/* Did we actually update?
	 * If so, call callback in GameClient. */
	public boolean update(){
		
		curTime = System.nanoTime();
		deltaT = deltaT + ( curTime - prevTime );
		
		/* Don't update too fast. */
		if( deltaT / nsPerTick >= 1){
			ticks++;
			prevTime = curTime;
			deltaT = 0L;
			return true;
		}
		else{
			return false;
		}
	}

	public long getTicks() {
		return ticks;
	}

}
