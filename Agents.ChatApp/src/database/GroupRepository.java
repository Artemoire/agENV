package database;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import models.Group;

@Stateless
public class GroupRepository {

	private HashMap<String, Group> groupCache;
	
	@PostConstruct
	private void init() {
		groupCache = new HashMap<String, Group>();
	}

	public Group getById(String groupId) {
		return groupCache.get(groupId);
	}

	public void createGroup(Group group) {
		groupCache.put(group.getId(), group);		
	}
	
	public void removeGroup(String groupId) {
		groupCache.remove(groupId);
	}
	
	
	
}
