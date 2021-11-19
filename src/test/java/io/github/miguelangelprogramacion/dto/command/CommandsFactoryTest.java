package io.github.miguelangelprogramacion.dto.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.github.miguelangelprogramacion.dto.command.CommandError;
import io.github.miguelangelprogramacion.dto.command.CommandsFactory;
import io.github.miguelangelprogramacion.dto.command.Follows;
import io.github.miguelangelprogramacion.dto.command.GetTimeline;
import io.github.miguelangelprogramacion.dto.command.GetWall;
import io.github.miguelangelprogramacion.dto.command.ICommand;
import io.github.miguelangelprogramacion.dto.command.Publish;

class CommandsFactoryTest {

	@Test
	void get_timeline_command() {
		ICommand salida = CommandsFactory.getCommand("Pepe");
		assertTrue(salida instanceof GetTimeline, "Unexpected class type ("+GetTimeline.class+")");
	}
	
	@Test
	void publish_command() {
		
		ICommand salida = CommandsFactory.getCommand("Amelie -> lo que tengo que decir");
		assertTrue(salida instanceof Publish, "Unexpected class type ("+Publish.class+")");	

	}
	
	@Test
	void follows_command() {
		
		ICommand salida = CommandsFactory.getCommand("Charlie follows charlie");
		assertTrue(salida instanceof Follows, "Unexpected class type ("+Follows.class+")");
	}
	
	@Test
	void get_wall_command() {
		
		ICommand salida = CommandsFactory.getCommand("Charlie wall");
		assertTrue(salida instanceof GetWall, "Unexpected class type ("+GetWall.class+")");
	}
	
	@Test
	void error_command() {
		
		ICommand salida = CommandsFactory.getCommand("Charlie asdf sadf asdf asdf ");
		assertTrue(salida instanceof CommandError, "Unexpected class type ("+CommandError.class+")");

	}
	
	@Test
	void another_error_command() {
		
		ICommand salida = CommandsFactory.getCommand("Charlie ");
		assertTrue(salida instanceof CommandError, "Unexpected class type ("+CommandError.class+")");

	}

}
