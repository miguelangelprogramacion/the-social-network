package io.github.miguelangelprogramacion.dto.response;

import java.util.List;

import io.github.miguelangelprogramacion.client.service.ResponseService;
import io.github.miguelangelprogramacion.server.entities.Twiit;

public class TimelineResponse implements IResponse{
	
	private static final long serialVersionUID = 5834313588324233768L;
	private List<Twiit> timelineTwiits;

	public List<Twiit> getTimelineTwiits() {
		return timelineTwiits;
	}

	
	public TimelineResponse(List<Twiit> timelineTwiits) {
		super();
		this.timelineTwiits = timelineTwiits;
	}


	@Override
	public String processCommand(ResponseService service) {
		return service.process(this);
	}
}
