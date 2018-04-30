package services;

import java.util.List;

import models.Group;

public interface GroupService {

	List<Group> getAllGroups();
	
	void createGroup(Long groupAdminId, String name);
	
	void deleteGroup(Long groupAdminId, String groupName);
	
	void addNewUser(Long groupAdminId, Long newUserId);
	
	void deleteUser(Long groupAdminId, Long newUserId);
}
