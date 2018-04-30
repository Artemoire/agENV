package services;

import java.util.List;

import models.Group;

public interface GroupService {

	List<Group> getAllGroups();
	
	void createGroup(Long groupAdminId, String name);
	
	void deleteGroup(Long groupAdminId, Long groupId);
	
	void addNewUser(Long groupAdminId, Long newUserId, Long groupId);
	
	void deleteUser(Long groupAdminId, Long newUserId, Long groupId);
}
