package database;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.types.ObjectId;

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
		Group group = new Group(document.getObjectId("_id").toHexString(), document.getString("groupAdminId"), document.getString("name"),
				(List<String>) document.get("usersIds"));
		return group;
	}

	private Document reverseConvert(Group group) {
		Document document = new Document();
		document.append("groupAdminId", group.getGroupAdminId());
		document.append("name", group.getName());
		document.append("usersIds", group.getUsersIds());

		return document;
	}

	public void createGroup(String groupAdminId, String name) {
		Group group = new Group();
		List<String> ids = new ArrayList<String>();
		ids.add(groupAdminId);
		group.setGroupAdminId(groupAdminId);
		group.setName(name);
		group.setUsersIds(ids);
		saveGroup(group);
	}

	public Group getGroupById(String groupId) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", new ObjectId(groupId));
		return convert(dbContext.getGroups().find(whereQuery).first());
	}

	public void deleteGroup(String groupAdminId, String groupId) {
		Group group = getGroupById(groupId);
		deleteGroup(group);
	}

	public void addNewUser(String groupAdminId, String newUserId, String groupId) {
		Group group = getGroupById(groupId);
		group.getUsersIds().add(newUserId);
		updateGroups(group, newUserId);
	}

	public void deleteUser(String groupAdminId, String newUserId, String groupId) {
		Group group = getGroupById(groupId);
		group.getUsersIds().remove(newUserId);
		updateGroups(group, newUserId);
	}

	public void updateGroups(Group group, String newUserId) {
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("usersIds", group.getUsersIds()));

		BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(group.getId()));

		this.dbContext.getGroups().updateOne(searchQuery, newDocument);

	}

	public void saveGroup(Group group) {
		this.dbContext.getGroups().insertOne(reverseConvert(group));
	}

	public void deleteGroup(Group group) {
		this.dbContext.getGroups().deleteOne(reverseConvert(group));
	}
}
