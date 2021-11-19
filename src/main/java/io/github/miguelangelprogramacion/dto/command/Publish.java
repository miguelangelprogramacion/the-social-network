package io.github.miguelangelprogramacion.dto.command;

import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.server.service.CommandService;

public class Publish implements ICommand{

	private static final long serialVersionUID = 4292822456210399814L;
	private String userName;
	private String sms;
	
	public Publish(String userName, String sms) {
		super();
		this.userName = userName;
		this.sms = sms;
	}

	
	public String getUserName() {
		return userName;
	}


	public String getSms() {
		return sms;
	}


	@Override
	public IResponse processCommand(CommandService service) {		
		return service.process(this);
	}
}
