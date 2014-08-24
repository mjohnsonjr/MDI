package sputnik.util;

public class Player {

	private long id;
	private String email;
	//TODO: Other required player information.
	
	public Player ( ) {
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
