package controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.User;
import services.UserService;

@Path("/users")


public class UserController {
	
	UserService userService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	List<User> getAllUsers() {
		return userService.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	void registerUser(User user) {
		 userService.register(user);
	}
}
