package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import database.UserRepository;
import models.User;

@Stateless
public class UserServiceImpl implements UserService {

	@EJB
	UserRepository userRepository;

	@Override
	public List<User> getAll() {
		List<User> target = new ArrayList<>();
		Iterator<User> source = userRepository.getAllUsers().iterator();
		source.forEachRemaining(target::add);
		return target;
	}

	@Override
	public boolean register(User user) {
		String username = user.getUsername();
		user.setFriends(new ArrayList<String>(0));
		User userCheck = userRepository.getUserByUsername(username);
		if (userCheck != null) {
			return false;
		}
		userRepository.saveUser(user);
		return true;

	}

	@Override
	public User login(String username, String password) {
		User userCheck = userRepository.getUserByUsername(username);
		if (userCheck != null && userCheck.getPassword().equals(password)) {
			return userCheck;
		}
		return null;
	}

	@Override
	public void addFriend(String friendId, String userId) {
		userRepository.addFriend(friendId, userId);
	}

	@Override
	public void deleteFriend(String friendId, String userId) {
		userRepository.deleteFriend(userId, friendId);

	}

	@Override
	public List<User> getFriends(String userId) {
		return userRepository.getFriends(userId);
	}

}
