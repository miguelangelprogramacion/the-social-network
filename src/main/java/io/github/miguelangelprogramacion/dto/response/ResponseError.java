package io.github.miguelangelprogramacion.dto.response;

import io.github.miguelangelprogramacion.client.service.ResponseService;

public class ResponseError implements IResponse {

	private static final long serialVersionUID = -8058652724183001556L;
	private final String text;

	public ResponseError(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String processCommand(ResponseService service) {
		return service.process(this);
	}
}