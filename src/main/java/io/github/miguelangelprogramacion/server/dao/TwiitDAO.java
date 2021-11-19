package io.github.miguelangelprogramacion.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import io.github.miguelangelprogramacion.server.entities.Twiit;

public class TwiitDAO {

	private MongoClient mongoClient = null;
	private MongoDatabase database = null;
	MongoCollection<Document> twiitsCollection = null;

	public TwiitDAO(String host, int port) {
		super();
		this.mongoClient = new MongoClient(host, port);
		database = mongoClient.getDatabase("social-network");
		twiitsCollection = database.getCollection("twiit");
	}

	public void insert(Twiit twiit) {
		Document jsonTwiit = new Document("_id", new ObjectId());
		jsonTwiit.append("user", twiit.getUser()).append("sms", twiit.getSms()).append("date", twiit.getDate());
		twiitsCollection.insertOne(jsonTwiit);
	}

	public List<Twiit> getUsersTwiits(List<String> userNames) {
		BasicDBObject inQuery = new BasicDBObject();

		inQuery.put("user", new BasicDBObject("$in", userNames));

		FindIterable<Document> out = twiitsCollection.find(inQuery);
		MongoCursor<Document> it = out.iterator();
		List<Twiit> twiits = new ArrayList<>();
		while (it.hasNext()) {
			Document obj = it.next();
			twiits.add(new Twiit(obj.getString("user"), obj.getString("sms"), obj.getDate("date")));
		}
		return twiits;
	}
	
	public List<Twiit> getAllTwiits() {
		
		FindIterable<Document> out = twiitsCollection.find();
		MongoCursor<Document> it = out.iterator();
		List<Twiit> twiits = new ArrayList<>();
		while (it.hasNext()) {
			Document obj = it.next();
			twiits.add(new Twiit(obj.getString("user"), obj.getString("sms"), obj.getDate("date")));
		}
		return twiits;
	}
}
