package services;

import java.util.List;

import models.User;


public interface UserService {

	List<User> getAll();
	boolean register(User user);
	User login(String username, String password);
	void addFriend(Long friendId, Long userId);
	void deleteFriend(Long friendId, Long userId);
}
