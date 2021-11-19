package io.github.miguelangelprogramacion.dto.command;

import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.server.service.CommandService;

public class GetTimeline implements ICommand{

	private static final long serialVersionUID = 5685562704476010594L;
	private String userName;
	
	public GetTimeline(String userName) {
		super();
		this.userName = userName;
	}
	

	public String getUserName() {
		return userName;
	}

	@Override
	public IResponse processCommand(CommandService service) {		
		return service.process(this);
	}
}
