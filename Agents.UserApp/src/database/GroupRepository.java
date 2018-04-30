package database;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import com.mongodb.BasicDBObject;

import models.Group;

@Stateless
public class GroupRepository {

	@EJB
	private GroupAppDbContext dbContext;

	public GroupAppDbContext getDbContext() {
		return dbContext;
	}

	public void setDbContext(GroupAppDbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Iterable<Group> getAllGroups() {
		return this.dbContext.getGroups().find().map(this::convert);
	}

	private Group convert(Document document) {
		Group group = new Group(document.getLong("id"), document.getLong("groupAdminId"), document.getString("name"),
				(List<Long>) document.get("usersIds"));
		return group;
	}

	private Document reverseConvert(Group group) {
		Document document = new Document();
		document.append("id", group.getId());
		document.append("groupAdminId", group.getGroupAdminId());
		document.append("name", group.getName());
		document.append("usersIds", group.getUsersIds());

		return document;
	}

	public void createGroup(Long groupAdminId, String name) {
		Group group = new Group();
		List<Long> ids = new ArrayList<Long>();
		ids.add(groupAdminId);
		group.setGroupAdminId(groupAdminId);
		group.setName(name);
		group.setId(this.dbContext.getGroups().count() + 1);
		group.setUsersIds(ids);
		saveGroup(group);
	}

	public Group getGroupById(Long groupId) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("groupId", groupId);
		return convert(dbContext.getGroups().find(whereQuery).first());
	}

	public void deleteGroup(Long groupAdminId, Long groupId) {
		Group group = getGroupById(groupId);
		deleteGroup(group);
	}

	public void addNewUser(Long groupAdminId, Long newUserId, Long groupId) {
		Group group = getGroupById(groupId);
		group.getUsersIds().add(newUserId);
		updateGroups(group, newUserId);
	}

	public void deleteUser(Long groupAdminId, Long newUserId, Long groupId) {
		Group group = getGroupById(groupId);
		group.getUsersIds().remove(newUserId);
		updateGroups(group, newUserId);
	}

	public void updateGroups(Group group, Long newUserId) {
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("usersIds", group.getUsersIds()));

		BasicDBObject searchQuery = new BasicDBObject().append("id", group.getId());

		this.dbContext.getGroups().updateOne(searchQuery, newDocument);

	}

	public void saveGroup(Group group) {
		this.dbContext.getGroups().insertOne(reverseConvert(group));
	}

	public void deleteGroup(Group group) {
		this.dbContext.getGroups().deleteOne(reverseConvert(group));
	}
}
