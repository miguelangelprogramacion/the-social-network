package io.github.miguelangelprogramacion.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import io.github.miguelangelprogramacion.client.SocialNetworkClient;
import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.dto.response.Message;
import io.github.miguelangelprogramacion.dto.response.TimelineResponse;
import io.github.miguelangelprogramacion.dto.response.WallResponse;
import io.github.miguelangelprogramacion.server.MultiServer;

@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
class IntegrationIT {

	private MultiServer server;

	@Container
	public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.2.8"))
			.withImagePullPolicy(PullPolicy.alwaysPull());

	
	@BeforeEach
	void initService() {
		server = new MultiServer(mongoDBContainer.getHost(), mongoDBContainer.getFirstMappedPort());
	}

	@AfterEach
	void closeService() throws Exception {
		server.stop();
	}
	
	@BeforeAll
	public static void setUp() {
		mongoDBContainer.start();
	}

	@Test
	void integrationTest() {

		MyRunnable mr = new MyRunnable(server);
		Thread tcontrol = new Thread(mr);
		tcontrol.start();

		SocialNetworkClient socialNetworkClient = new SocialNetworkClient("localhost", 8887);
		try {
			Thread.sleep(2000);
			String inputMsg = "Alice -> I love the weather today";
			socialNetworkClient.startConnection();
			IResponse response = socialNetworkClient.sendMessage(inputMsg);
			assertTrue(response instanceof Message, "Unexpected class type (" + Message.class + ")");
			Message sms = (Message) response;
			assertEquals("OK Publish", sms.getText());
						
			
			inputMsg = "Alice -> it is getting cold";			
			response = socialNetworkClient.sendMessage(inputMsg);
			assertTrue(response instanceof Message, "Unexpected class type (" + Message.class + ")");
			sms = (Message) response;
			assertEquals("OK Publish", sms.getText());
						
			inputMsg = "Alice";			
			response = socialNetworkClient.sendMessage(inputMsg);
			assertTrue(response instanceof TimelineResponse, "Unexpected class type (" + TimelineResponse.class + ")");			
			TimelineResponse tlr = (TimelineResponse)response;
			assertEquals(2, tlr.getTimelineTwiits().size());
						
			inputMsg = "Pepe -> yes, autumn is coming";			
			response = socialNetworkClient.sendMessage(inputMsg);
			assertTrue(response instanceof Message, "Unexpected class type (" + Message.class + ")");
			sms = (Message) response;
			assertEquals("OK Publish", sms.getText());
						
			inputMsg = "Alice follows Pepe";
			response = socialNetworkClient.sendMessage(inputMsg);
			assertTrue(response instanceof Message, "Unexpected class type (" + Message.class + ")");
			sms = (Message) response;
			assertEquals("OK Follows", sms.getText());
						
			inputMsg = "Alice wall";			
			response = socialNetworkClient.sendMessage(inputMsg);
			assertTrue(response instanceof WallResponse, "Unexpected class type (" + WallResponse.class + ")");
			WallResponse wr = (WallResponse)response;
			assertEquals(3, wr.getWallTwiits().size());
			socialNetworkClient.stopConnection();

		} catch (IOException | ClassNotFoundException | InterruptedException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private class MyRunnable implements Runnable {

		MultiServer server;

		public MyRunnable(MultiServer server) {
			super();
			this.server = server;
		}

		public void run() {
			server.start(8887);
		}
	}
}
