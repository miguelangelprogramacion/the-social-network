package io.github.miguelangelprogramacion.dto.command;

import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.server.service.CommandService;

public class Follows implements ICommand{

	private static final long serialVersionUID = 7200100610562731203L;
	private String userName;
	private String userToFollow;
	
	public Follows(String userName, String userToFollow) {
		super();
		this.userName = userName;
		this.userToFollow = userToFollow;
	}
	

	public String getUserName() {
		return userName;
	}



	public String getUserToFollow() {
		return userToFollow;
	}


	@Override
	public IResponse processCommand(CommandService service) {		
		return service.process(this);
	}
}
