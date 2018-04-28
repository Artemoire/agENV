package services;

import javax.ejb.Stateless;

import models.User;

@Stateless
public class UserServiceImpl implements UserService {

	@Override
	public User test() {
		// TODO Auto-generated method stub
		User user = new User("Isidora", "Aleksic");
		return user;
	}

}
