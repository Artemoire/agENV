package services;

import java.util.List;

import models.Group;

public interface GroupService {

	List<Group> getAllGroups(String userId);
	
	void createGroup(String groupAdminId, String name);
	
	void deleteGroup(String groupAdminId, String groupId);
	
	void addNewUser(String groupAdminId, String newUserId, String groupId);
	
	void deleteUser(String groupAdminId, String newUserId, String groupId);
}
