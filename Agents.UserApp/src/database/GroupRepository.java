package database;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.bson.Document;

import models.Group;

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

	public void saveGroup(Group group) {
		this.dbContext.getGroups().insertOne(reverseConvert(group));
	}
}
