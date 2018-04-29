package services;

import java.util.List;

import models.User;


public interface UserService {

	List<User> getAll();
	boolean register(User user);
}
