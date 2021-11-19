package io.github.miguelangelprogramacion.server.service;

import io.github.miguelangelprogramacion.dto.command.ICommand;
import io.github.miguelangelprogramacion.dto.response.IResponse;

public interface IServerService {
	IResponse processCommand(ICommand input);
}
