package io.github.miguelangelprogramacion.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.github.miguelangelprogramacion.dto.command.CommandError;
import io.github.miguelangelprogramacion.dto.command.Follows;
import io.github.miguelangelprogramacion.dto.command.GetTimeline;
import io.github.miguelangelprogramacion.dto.command.GetWall;
import io.github.miguelangelprogramacion.dto.command.ICommand;
import io.github.miguelangelprogramacion.dto.command.Publish;
import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.dto.response.Message;
import io.github.miguelangelprogramacion.dto.response.ResponseError;
import io.github.miguelangelprogramacion.dto.response.TimelineResponse;
import io.github.miguelangelprogramacion.dto.response.WallResponse;
import io.github.miguelangelprogramacion.server.dao.RelationsDAO;
import io.github.miguelangelprogramacion.server.dao.TwiitDAO;
import io.github.miguelangelprogramacion.server.entities.Relation;
import io.github.miguelangelprogramacion.server.entities.Twiit;
import io.github.miguelangelprogramacion.server.entities.UserRelation;

public class CommandService implements IServerService {

	private TwiitDAO twiitDAO = null;
	private RelationsDAO relationsDAO = null;
		
	public CommandService() {
		super();		
	}

	@Override
	public IResponse processCommand(ICommand command) {
		return command.processCommand(this);
	}

	public CommandService(String mongoHost, int mongoPort) {
		super();
		twiitDAO = new TwiitDAO(mongoHost, mongoPort);
		relationsDAO = new RelationsDAO(mongoHost, mongoPort);
	}

	public IResponse process(Publish command) {

		Twiit twiit = new Twiit(command.getUserName(), command.getSms(), new Date(System.currentTimeMillis()));
		twiitDAO.insert(twiit);
		return new Message("OK Publish");
	}

	public IResponse process(GetTimeline command) {
		
		List<Twiit> out = twiitDAO.getUsersTwiits(Arrays.asList(command.getUserName()));
		
		return new TimelineResponse(out);
	}

	public IResponse process(Follows command) {
		Relation relation = new Relation(command.getUserName(), command.getUserToFollow());
		relationsDAO.insert(relation);
		return new Message("OK Follows");
	}

	public IResponse process(GetWall wall) {
		
		UserRelation userRelations = relationsDAO.getUserRelations(wall.getUserName());
	
		List<String> listFollows = new ArrayList<>();
		listFollows.add(wall.getUserName());
		listFollows.addAll(userRelations.getUsersThatfollows());
		
		
		List<Twiit> out = twiitDAO.getUsersTwiits(listFollows);

		return new WallResponse(out);
	}

	public IResponse process(CommandError error) {
		return new ResponseError("Error in command: '"+error.getCommand()+"'");
	}
}
