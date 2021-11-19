package io.github.miguelangelprogramacion.client.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.dto.response.Message;
import io.github.miguelangelprogramacion.dto.response.ResponseError;
import io.github.miguelangelprogramacion.dto.response.TimelineResponse;
import io.github.miguelangelprogramacion.dto.response.WallResponse;
import io.github.miguelangelprogramacion.server.entities.Twiit;

public class ResponseService {

	public String processCommand(IResponse response) {
		return response.processCommand(this);
	}

	public String process(Message command) {
		return null;
	}

	public String process(TimelineResponse command) {
		List<Twiit> twiits = command.getTimelineTwiits();

		Comparator<Twiit> comparator = (c1, c2) -> Long.valueOf(c2.getDate().getTime())
				.compareTo(c1.getDate().getTime());

		Collections.sort(twiits, comparator);

		Instant now = new Date(System.currentTimeMillis()).toInstant();
		StringBuilder response = new StringBuilder();
		for (Twiit twiit : twiits) {
			Instant twiitInstant = twiit.getDate().toInstant();
			Duration d = Duration.between(twiitInstant, now);
			response.append(twiit.getSms()).append(" (").append(d.getSeconds()).append(" seconds ago) \n");
		}
		return response.toString();
	}

	public String process(WallResponse command) {
		List<Twiit> twiits = command.getWallTwiits();

		Comparator<Twiit> comparator = (c1, c2) -> Long.valueOf(c2.getDate().getTime())
				.compareTo(c1.getDate().getTime());

		Collections.sort(twiits, comparator);
		Instant now = new Date(System.currentTimeMillis()).toInstant();
		StringBuilder response = new StringBuilder();
		for (Twiit twiit : twiits) {
			Instant twiitInstant = twiit.getDate().toInstant();
			Duration d = Duration.between(twiitInstant, now);
			response.append(twiit.getUser()).append(" - ").append(twiit.getSms()).append(" (").append(d.getSeconds()).append(" seconds ago) \n");
		}
		return response.toString();
	}

	public String process(ResponseError responseError) {
		StringBuilder response = new StringBuilder();
		return response.append("Error in command: '").append(responseError.getText()).append("' \n").toString();
	}
}
