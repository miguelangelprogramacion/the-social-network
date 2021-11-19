package io.github.miguelangelprogramacion.client.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.miguelangelprogramacion.dto.response.Message;
import io.github.miguelangelprogramacion.dto.response.ResponseError;
import io.github.miguelangelprogramacion.dto.response.TimelineResponse;
import io.github.miguelangelprogramacion.dto.response.WallResponse;
import io.github.miguelangelprogramacion.server.entities.Twiit;

class ResponseServiceTest {

	private ResponseService responseService;

	@BeforeEach
	void initService() {
		responseService = new ResponseService();
	}

	@Test
	void response_error() {		
		ResponseError err = new ResponseError("error");
		String out = responseService.processCommand(err);
		assertEquals("Error in command: 'error' \n", out);
	}


	@Test
	void response_message() {
		Message err = new Message("OK Publish");
		String out = responseService.processCommand(err);
		assertNull(out);
	}

	@Test
	void response_timeline() {
		List<Twiit> twiitList = Arrays.asList(new Twiit("pepe", "sms1", new Date()), new Twiit("pepe", "sms2", new Date()));	
		TimelineResponse err = new TimelineResponse(twiitList);
		String out = responseService.processCommand(err);
		assertNotNull(out);
		//TODO: Add some regex
	}
	
	@Test
	void response_wall() {
		List<Twiit> twiitList = Arrays.asList(new Twiit("pepe", "sms1", new Date()), new Twiit("pepe", "sms2", new Date()));	
		WallResponse err = new WallResponse(twiitList);
		String out = responseService.processCommand(err);
		assertNotNull(out);
		//TODO: Add some regex
	}

}
