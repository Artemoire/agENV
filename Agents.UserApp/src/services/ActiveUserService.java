package services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import models.User;

@Singleton
public class ActiveUserService {

	private List<User> activeUsers;

	@PostConstruct
	public void addActiveUsers() {
		activeUsers = new ArrayList<>();
	}

	public List<User> getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(List<User> activeUsers) {
		this.activeUsers = activeUsers;
	}

	public void removeActiveUser(User userRemove) {
		for (int i = 0; i < activeUsers.size(); i++) {
			if (activeUsers.get(i).getId() == userRemove.getId()) {
				activeUsers.remove(i);
				break;
			}
		}
	}

}