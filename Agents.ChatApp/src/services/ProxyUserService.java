package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.Session;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import models.User;

@Stateless
public class ProxyUserService {

	@EJB
	private SessionUserService sessionService;

	@EJB
	ActiveUserService activeUserService;

	public void login(User user) {
		activeUserService.getActiveUsers().add(user);
		for (String friendId : user.getFriends()) {
			Session session = sessionService.getSessionByUserId(friendId);
			if (session != null) {
				// TODO: NOTIFY ONLINE
			}
		}
	}

	public void logout(User user) {
		activeUserService.removeActiveUser(user);
		activeUserService.getActiveUsers().add(user);
		for (String friendId : user.getFriends()) {
			Session session = sessionService.getSessionByUserId(friendId);
			if (session != null) {
				// TODO: NOTIFY )OFFLINE
			}
		}
	}

	public void addFriend(String friendId, String userId) {
		Session session = sessionService.getSessionByUserId(friendId);
		// TODO: Send Websocket message
	}

	public void deleteFriend(String friendId, String userId) {
		Session session = sessionService.getSessionByUserId(friendId);
		// TODO: Send Websocket message
	}

}
