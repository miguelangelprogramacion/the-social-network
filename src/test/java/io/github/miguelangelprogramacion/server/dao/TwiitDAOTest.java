package io.github.miguelangelprogramacion.server.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import io.github.miguelangelprogramacion.server.entities.Twiit;

@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
class TwiitDAOTest {

	@Container
	public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.2.8"))
			.withImagePullPolicy(PullPolicy.alwaysPull());

	private static TwiitDAO twiitDAO = null;

	@BeforeAll
	public static void setUp() {
		mongoDBContainer.start();
		twiitDAO = new TwiitDAO(mongoDBContainer.getHost(),mongoDBContainer.getFirstMappedPort());		
		
	}
	
	@Test
	@Order(1)
	void testInsertTwiits() {

		twiitDAO.insert(new Twiit("user2", "mensaje de test 1", new Date(1636446155000L)));
		twiitDAO.insert(new Twiit("user1", "mensaje de test 2", new Date(1636446170000L)));
		twiitDAO.insert(new Twiit("user3", "mensaje de test 3", new Date(1636446180000L)));
		twiitDAO.insert(new Twiit("user2", "mensaje de test 4", new Date(1636446188000L)));
		
		List<Twiit> listAllTwiits = twiitDAO.getAllTwiits();
		assertEquals(4, listAllTwiits.size());
	}

	@Test
	@Order(2)
	void testQueryUserTwiits() {

		List<String> users = Arrays.asList("user2");

		List<Twiit> listTwiits = twiitDAO.getUsersTwiits(users);
		assertEquals(2, listTwiits.size());
	}

	@AfterAll
	public static void tearDown() {
		mongoDBContainer.stop();
	}

}
