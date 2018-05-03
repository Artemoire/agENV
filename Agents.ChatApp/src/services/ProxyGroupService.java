package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.Session;

import database.GroupRepository;
import models.Group;

@Stateless
public class ProxyGroupService {

	@EJB
	private SessionUserService sessionService;
	
	@EJB
	private GroupRepository groupRepository;
	
	public void createGroup(Group group) {
		groupRepository.createGroup(group);
		for (String userId : group.getUsersIds()) {
			Session session = sessionService.getSessionByUserId(userId);
			if (session != null) {
				// TODO: send msg added
			}
		}
	}

	public void deleteGroup(String groupAdminId, String groupId) {
		Group group = groupRepository.getById(groupId);
		for(String userId: group.getUsersIds())
		{
			Session session = sessionService.getSessionByUserId(userId);
			if (session != null) {
				// TODO: send msg deleted
			}
		}
		groupRepository.removeGroup(groupId);
	}

	public void addNewUser(String groupAdminId, String newUserId, String groupId) {
		Group group = groupRepository.getById(groupId);
		for(String userId: group.getUsersIds())
		{
			Session session = sessionService.getSessionByUserId(userId);
			if (session != null) {
				// TODO: send msg added user
			}
		}
	}

	public void deleteUser(String groupAdminId, String newUserId, String groupId) {
		Group group = groupRepository.getById(groupId);
		for(String userId: group.getUsersIds())
		{
			Session session = sessionService.getSessionByUserId(userId);
			if (session != null) {
				// TODO: send msg removed user
			}
		}
	}

}
