package io.github.miguelangelprogramacion.server.entities;

public class Relation {

	private String user;
	private String userToFollow;
	
	public Relation(String user, String userToFollow) {
		super();
		this.user = user;
		this.userToFollow = userToFollow;
	}
	
	public String getUser() {
		return user;
	}
	
		
	public String getUserToFollow() {
		return userToFollow;
	}	
}
