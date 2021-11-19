package io.github.miguelangelprogramacion.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

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

@RunWith(MockitoJUnitRunner.class)
class CommandServiceTest {
	@InjectMocks
	private CommandService commandService;
	private AutoCloseable closeable;
	@Mock
	private TwiitDAO twiitDAO;

	
	@Mock
	private static RelationsDAO relationsDAO;

	@BeforeEach
	void initService() {
		commandService = new CommandService();
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void error_command_test() {
		ICommand command = new CommandError("pepe ");
		IResponse out = commandService.processCommand(command);
		assertTrue(out instanceof ResponseError, "Unexpected class type (" + ResponseError.class + ")");
		ResponseError re = (ResponseError) out;
		assertEquals("Error in command: 'pepe '", re.getText());
	}

	@Test
	void follows_command_test() {

		Mockito.doNothing().when(relationsDAO).insert(Mockito.any(Relation.class));

		ICommand command = new Follows("pepe", "maria");
		IResponse out = commandService.processCommand(command);
		assertTrue(out instanceof Message, "Unexpected class type (" + Message.class + ")");
		Message m = (Message) out;
		assertEquals("OK Follows", m.getText());
	}
	
	@Test
	void get_timeline_command_test() {

		List<Twiit> twiitList = Arrays.asList(new Twiit("pepe", null, null), new Twiit("pepe", null, null));		
		when(twiitDAO.getUsersTwiits(Arrays.asList("pepe"))).thenReturn(twiitList);		

		ICommand command = new GetTimeline("pepe");
		IResponse out = commandService.processCommand(command);
		assertTrue(out instanceof TimelineResponse, "Unexpected class type (" + TimelineResponse.class + ")");
		TimelineResponse m = (TimelineResponse) out;
		assertEquals(2, m.getTimelineTwiits().size());
	}
	
	@Test
	void get_wall_command_test() {

		List<Twiit> twiitList = Arrays.asList(new Twiit("pepe", null, null), new Twiit("pepe", null, null));		
		when(relationsDAO.getUserRelations("pepe")).thenReturn(new UserRelation("pepe", Collections.emptySet()));	
		when(twiitDAO.getUsersTwiits(Arrays.asList("pepe"))).thenReturn(twiitList);		

		ICommand command = new GetWall("pepe");
		IResponse out = commandService.processCommand(command);
		assertTrue(out instanceof WallResponse, "Unexpected class type (" + WallResponse.class + ")");
		WallResponse m = (WallResponse) out;
		assertEquals(2, m.getWallTwiits().size());
	}
	
	@Test
	void publish_command_test() {

		Mockito.doNothing().when(twiitDAO).insert(Mockito.any(Twiit.class));

		ICommand command = new Publish("pepe", "a new twiit");
		IResponse out = commandService.processCommand(command);
		assertTrue(out instanceof Message, "Unexpected class type (" + Message.class + ")");
		Message m = (Message) out;
		assertEquals("OK Publish", m.getText());
	}

}
