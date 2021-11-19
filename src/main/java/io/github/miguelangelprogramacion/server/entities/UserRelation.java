package io.github.miguelangelprogramacion.server.entities;

import java.util.Set;

public class UserRelation {
	private String user;
	private Set<String> usersThatfollows;
	
	public UserRelation(String user, Set<String> usersThatfollows) {
		super();
		this.user = user;
		this.usersThatfollows = usersThatfollows;
	}
	
	public void addRelation(Relation relation) {
		this.usersThatfollows.add(relation.getUserToFollow());	
	}
	public String getUser() {
		return user;
	}
	
	public Set<String> getUsersThatfollows() {
		return usersThatfollows;
	}
}
