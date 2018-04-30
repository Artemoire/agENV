package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;

import database.GroupRepository;
import models.Group;

public class GroupServiceImpl implements GroupService{

	@EJB
	GroupRepository groupRepository;
	
	@Override
	public List<Group> getAllGroups() {
		List<Group> target = new ArrayList<>();
		Iterator<Group> source = groupRepository.getAllGroups().iterator();
		source.forEachRemaining(target::add);
		return target;
	}

	@Override
	public void createGroup(Long groupAdminId, String name){
		groupRepository.createGroup(groupAdminId,name);
	}
	
	@Override
	public void deleteGroup(Long groupAdminId, String groupName){
		groupRepository.deleteGroup(groupAdminId,groupName);
	}

	@Override
	public void addNewUser(Long groupAdminId, Long newUserId) {
		groupRepository.addNewUser(groupAdminId, newUserId);
		
	}

	@Override
	public void deleteUser(Long groupAdminId, Long newUserId) {
		groupRepository.deleteUser(groupAdminId, newUserId);
		
	}
	
}
