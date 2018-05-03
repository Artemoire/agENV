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
	
}
