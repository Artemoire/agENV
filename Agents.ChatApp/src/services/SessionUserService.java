package services;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.websocket.Session;

import models.User;

@Singleton
public class SessionUserService {

	HashMap<Session, User> sessionUsers;

	@PostConstruct
	private void init() {
		sessionUsers = new HashMap<>();
	}

	public boolean isLoggedIn(Session session) {
		return sessionUsers.containsKey(session);
	}

	public Session getSessionByUserId(String userId) {
		for (Session session : sessionUsers.keySet()) {
			if (sessionUsers.get(session).getId().equals(userId))
				return session;
		}
		return null;
	}

	public void login(User user, Session session) {
		sessionUsers.put(session, user);
	}

	public User logout(Session session) {
		User user = sessionUsers.get(session);
		sessionUsers.remove(session);
		return user;
	}

}
