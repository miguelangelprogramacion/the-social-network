package io.github.miguelangelprogramacion.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import io.github.miguelangelprogramacion.server.entities.Relation;
import io.github.miguelangelprogramacion.server.entities.UserRelation;

public class RelationsDAO {
	private MongoClient mongoClient = null;
	private MongoDatabase database = null;
	MongoCollection<Document> relationsCollection = null;
	private static final String MONGOTABLE = "follows"; 
	
	public RelationsDAO(String host, int port) {
		super();
		this.mongoClient = new MongoClient(host, port);
		database = mongoClient.getDatabase("social-network");
		relationsCollection = database.getCollection("relation");
	}

	public void insert(Relation relation) {
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("user", relation.getUser());
		Document userDocument = relationsCollection.findOneAndDelete(whereQuery);
		
		UserRelation userRelations = null;
		if (userDocument!=null) userRelations = new UserRelation(relation.getUser(),new HashSet<> (userDocument.getList(MONGOTABLE, String.class)));
		
		if (userRelations !=null) userRelations.addRelation(relation);
		else userRelations = new UserRelation(relation.getUser(), new HashSet<>(Arrays.asList(relation.getUserToFollow())));
		
		Document jsonRelation = new Document("_id", new ObjectId());
		jsonRelation.append("user", userRelations.getUser()).append(MONGOTABLE, userRelations.getUsersThatfollows());
		
		relationsCollection.insertOne(jsonRelation);
	}

	public UserRelation getUserRelations(String user) {

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("user", user);
		FindIterable<Document> cursor = relationsCollection.find(whereQuery);
		Document userDocument = cursor.first();
		UserRelation userRelation;
		if (userDocument!=null) userRelation = new UserRelation(user,new HashSet<> (userDocument.getList(MONGOTABLE, String.class)));
		else userRelation = new UserRelation(user,new HashSet<> ()); 
		return userRelation;
	}

	public List<UserRelation> getAllUserRelation() {

		FindIterable<Document> out = relationsCollection.find();
		MongoCursor<Document> it = out.iterator();
		List<UserRelation> relations = new ArrayList<>();
		while (it.hasNext()) {			
			Document obj = it.next();			
			relations.add(new UserRelation(obj.getString("user"), new HashSet<>(obj.getList(MONGOTABLE, String.class))));
		}
		return relations;
	}
}
