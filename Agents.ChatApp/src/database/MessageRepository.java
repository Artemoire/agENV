package database;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import models.Message;

@Stateless
public class MessageRepository {

	@EJB
	private ChatAppDbContext dbContext;

	public Iterable<Message> getMessages() {
		return dbContext.getMessages().find().map(this::convert);
	}

	private Message convert(Document document) {
		return new Message(document.getObjectId("_id").toHexString(), document.getString("receiver"), document.getString("sender"), document.getString("content"),
				document.getDate("date"));
	}

}
