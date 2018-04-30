package services;

import java.util.List;

import models.Group;

public interface GroupService {

	List<Group> getAllGroups();
	
	void createGroup(Long groupAdminId, String name);
}
