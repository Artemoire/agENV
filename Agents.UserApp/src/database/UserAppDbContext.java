package database;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Stateful
public class UserAppDbContext {

	private MongoDatabase context;

	@PostConstruct
	private void init() {
		context = new MongoClient("localhost", 27017).getDatabase("chat");
	}

	public MongoDatabase getContext() {
		return context;
	}

	public void setContext(MongoDatabase context) {
		this.context = context;
	}

	public MongoCollection<Document> getUsers() {
		return this.context.getCollection("users");
	}

	public Document getUserByUsername(String username) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("username", username);
		return getUsers().find(whereQuery).first();
	}

	public void addUser(Document user) {
		getUsers().insertOne(user);
	}
}
