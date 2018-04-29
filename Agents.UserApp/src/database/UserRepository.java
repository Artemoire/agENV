package database;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

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
		return convert(this.dbContext.getUserByUsername(username));
	}

	private User convert(Document document) {
		User user = new User(document.getString("name"), document.getString("surname"), document.getString("username"),
				null);
		user.setPassword(document.getString("password"));
		return user;
	}

	private Document reverseConvert(User user) {
		Document document = new Document();
		document.append("name", user.getName());
		document.append("surname", user.getSurname());
		document.append("username", user.getUsername());
		document.append("password", user.getPassword());

		return document;
	}

	public void saveUser(User user) {
		this.dbContext.addUser(reverseConvert(user));
	}

}
