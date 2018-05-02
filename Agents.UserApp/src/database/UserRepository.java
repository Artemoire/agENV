package database;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

import models.User;

@Stateless
public class UserRepository {

	@EJB
	private UserAppDbContext dbContext;

	public UserAppDbContext getDbContext() {
		return dbContext;
	}

	public void setDbContext(UserAppDbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Iterable<User> getAllUsers() {
		return this.dbContext.getUsers().find().map(this::convert);
	}

	public User getUserByUsername(String username) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("username", username);
		return convert(dbContext.getUsers().find(whereQuery).first());
	}

	public void addFriend(String friendId, String userId) {
		User user = getUserById(userId);
		User friend = getUserById(friendId);
		user.getFriends().add(friendId);
		friend.getFriends().add(userId);
		updateFriends(user, friendId);
		updateFriends(friend, userId);
	}

	public void deleteFriend(String userId, String friendId) {
		User user = getUserById(userId);
		User friend = getUserById(friendId);
		user.getFriends().remove(friendId);
		friend.getFriends().remove(userId);
		updateFriends(user, friendId);
		updateFriends(friend, user.getId());

	}

	public void updateFriends(User user, String friendId) {
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("friends", user.getFriends()));

		BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(user.getId()));

		this.dbContext.getUsers().updateOne(searchQuery, newDocument);

	}

	public User getUserById(String userId) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", new ObjectId(userId));
		return convert(dbContext.getUsers().find(whereQuery).first());
	}

	private User convert(Document document) {
		if(document==null) {
			return null;
		}
		User user = new User(document.getObjectId("_id").toHexString(), (List<String>) document.get("friends"), document.getString("name"),
				document.getString("surname"), document.getString("username"), null);
		user.setPassword(document.getString("password"));
		return user;
	}

	private Document reverseConvert(User user) {
		Document document = new Document();
		document.append("friends", user.getFriends());
		document.append("name", user.getName());
		document.append("surname", user.getSurname());
		document.append("username", user.getUsername());
		document.append("password", user.getPassword());

		return document;
	}

	public void saveUser(User user) {
		this.dbContext.getUsers().insertOne(reverseConvert(user));
	}

}
