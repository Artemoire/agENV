package controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import models.User;
import services.ActiveUserService;
import services.UserService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class UserController {

	@EJB
	UserService userService;

	@EJB
	ActiveUserService activeUserService;	

	@SessionScoped
	User loggedInUser;

	@GET
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@POST
	public void registerUser(User user) {
		loggedInUser = userService.register(user) ? user : null;
	}

	@POST
	@Path("/login")
	public User login(User user) {
		user = userService.login(user.getUsername(), user.getPassword());
		if (user != null) {
			activeUserService.getActiveUsers().add(user);
			loggedInUser = user;			
			// set host
			System.out.println("logged in");
			return user;
		} else {
			loggedInUser = null;
		}
		return null;

	}

	@POST
	@Path("/logout")
	public void logout(User user) {
		if (loggedInUser == user) {
			activeUserService.removeActiveUser(user);
			loggedInUser = null;
		}
	}

	@POST
	@Path("/friends/{userId}/{friendId}")
	public void addFriend(@PathParam("userId") String userId, @PathParam("friendId") String friendId) {
		userService.addFriend(friendId, userId);

	}

	@DELETE
	@Path("/friends/{userId}/{friendId}")
	public void deleteFriend(@PathParam("userId") String userId, @PathParam("friendId") String friendId) {
		userService.deleteFriend(friendId, userId);

	}

	@GET
	@Path("/active")
	public List<User> activeUsers() {
		return activeUserService.getActiveUsers();
	}

}
