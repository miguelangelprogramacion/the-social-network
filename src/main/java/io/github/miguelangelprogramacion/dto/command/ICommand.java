package io.github.miguelangelprogramacion.dto.command;

import java.io.Serializable;

import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.server.service.CommandService;

public interface ICommand extends Serializable{

	IResponse processCommand(CommandService service);
}
