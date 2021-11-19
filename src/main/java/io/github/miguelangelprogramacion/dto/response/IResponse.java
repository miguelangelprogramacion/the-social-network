package io.github.miguelangelprogramacion.dto.response;

import java.io.Serializable;

import io.github.miguelangelprogramacion.client.service.ResponseService;

public interface IResponse extends Serializable{

	String processCommand(ResponseService service);
}
