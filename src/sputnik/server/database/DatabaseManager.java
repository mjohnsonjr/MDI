package sputnik.server.database;

import sputnik.util.Player;

public class DatabaseManager {

	
	//TODO: CURRENTLY STUBBED.
	private DatabaseConnection databaseConnection;
	
	
	public static Player STUBqueryPlayer() {
		
		Player player = new Player();
		player.setEmail("michael@dogkat.com");
		player.setId(9L);
		
		return player;
	}
	
}
