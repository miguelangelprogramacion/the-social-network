package io.github.miguelangelprogramacion.server.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import io.github.miguelangelprogramacion.server.entities.Relation;
import io.github.miguelangelprogramacion.server.entities.UserRelation;

@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
class RelationsDAOTest {

	@Container
	public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.2.8"))
			.withImagePullPolicy(PullPolicy.alwaysPull());

	private static RelationsDAO relationsDAO = null;
	
	@BeforeAll
	public static void setUp() {
		mongoDBContainer.start();
		relationsDAO = new RelationsDAO(mongoDBContainer.getHost(),mongoDBContainer.getFirstMappedPort());	
	}
	
	
	
	@Test
	@Order(1)
	void insert_one_relation_and_retrieve() {
		relationsDAO.insert(new Relation("USER1", "USER2"));
		
		UserRelation relations = relationsDAO.getUserRelations("USER1");
		assertEquals("USER1", relations.getUser());
		assertEquals(1, relations.getUsersThatfollows().size());
		
		
	}
	
		
	@Test
	@Order(2)
	void append_more_relations_and_retrieve() {
		relationsDAO.insert(new Relation("USER1", "USER3"));
		relationsDAO.insert(new Relation("USER1", "USER4"));
		

		UserRelation relations = relationsDAO.getUserRelations("USER1");
		assertEquals("USER1", relations.getUser());
		assertEquals(3, relations.getUsersThatfollows().size());
	}
	
	
	@Test
	@Order(3)
	void get_all_relations() {
		List<UserRelation> list = relationsDAO.getAllUserRelation();
		assertEquals(1, list.size());
	}
	


	@AfterAll
	public static void tearDown() {
		mongoDBContainer.stop();
	}

}
