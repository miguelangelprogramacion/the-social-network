package io.github.miguelangelprogramacion.server.entities;

import java.io.Serializable;
import java.util.Date;

public class Twiit implements Serializable{

	private static final long serialVersionUID = -8522883283132263950L;
	private String user;
	private String sms;
	private Date date;
	
	public Twiit(String user, String sms, Date date) {
		super();
		this.user = user;
		this.sms = sms;
		this.date = date;
	}
	
	
	public String getUser() {
		return user;
	}
	
	public String getSms() {
		return sms;
	}
	
	public Date getDate() {
		return date;
	}
}
