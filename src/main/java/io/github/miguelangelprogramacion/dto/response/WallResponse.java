package io.github.miguelangelprogramacion.dto.response;

import java.util.List;

import io.github.miguelangelprogramacion.client.service.ResponseService;
import io.github.miguelangelprogramacion.server.entities.Twiit;

public class WallResponse implements IResponse{
	
	private static final long serialVersionUID = 5834313588324233768L;
	private List<Twiit> wallTwiits;

	public List<Twiit> getWallTwiits() {
		return wallTwiits;
	}

	
	public WallResponse(List<Twiit> wallTwiits) {
		super();
		this.wallTwiits = wallTwiits;
	}


	@Override
	public String processCommand(ResponseService service) {
		return service.process(this);
	}
}
