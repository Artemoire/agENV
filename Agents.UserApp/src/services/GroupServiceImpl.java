package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import database.GroupRepository;
import models.Group;
@Stateless
public class GroupServiceImpl implements GroupService {

	@EJB
	GroupRepository groupRepository;

	@Override
	public List<Group> getAllGroups(String userId) {
		List<Group> target = new ArrayList<>();
		Iterator<Group> source = groupRepository.getAllGroups().iterator();
		source.forEachRemaining(target::add);
		return target;
	}

	@Override
	public void createGroup(String groupAdminId, String name) {
		groupRepository.createGroup(groupAdminId, name);
	}

	@Override
	public void deleteGroup(String groupAdminId, String groupId) {
		groupRepository.deleteGroup(groupAdminId, groupId);
	}

	@Override
	public void addNewUser(String groupAdminId, String newUserId, String groupId) {
		groupRepository.addNewUser(groupAdminId, newUserId, groupId);

	}

	@Override
	public void deleteUser(String groupAdminId, String newUserId, String groupId) {
		groupRepository.deleteUser(groupAdminId, newUserId, groupId);

	}

}
