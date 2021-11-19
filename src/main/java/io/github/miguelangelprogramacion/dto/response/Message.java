package io.github.miguelangelprogramacion.dto.response;

import io.github.miguelangelprogramacion.client.service.ResponseService;

public class Message implements IResponse{

	private static final long serialVersionUID = -1466267247050159877L;
	private final String text;

    public Message(String text) {
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