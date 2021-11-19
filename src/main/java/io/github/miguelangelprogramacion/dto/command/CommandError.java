package io.github.miguelangelprogramacion.dto.command;

import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.server.service.CommandService;

public class CommandError implements ICommand{

	private static final long serialVersionUID = 599743692486440586L;
	String command;
		
	public CommandError(String command) {
		super();
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	@Override
	public IResponse processCommand(CommandService service) {		
		return service.process(this);
	}

}
