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
		User userCheck = userRepository.getUserByUsername(username);
		if (userCheck != null) {
			return false;
		}
		userRepository.saveUser(user);
		return true;

	}

}
