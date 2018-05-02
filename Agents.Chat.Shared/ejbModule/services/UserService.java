package services;

import java.util.List;

import models.User;


public interface UserService {

	List<User> getAll();
	boolean register(User user);
	User login(String username, String password);
	void addFriend(String friendId, String userId);
	void deleteFriend(String friendId, String userId);
}
