package database;

import javax.annotation.PostConstruct;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ChatAppDbContext {

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
	
}
