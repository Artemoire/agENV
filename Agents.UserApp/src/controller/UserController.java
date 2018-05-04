package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	public Response getAllUsers() {
		return Response.ok(userService.getAll()).build();
	}

	@POST
	public Response registerUser(User user) {
		loggedInUser = userService.register(user) ? user : null;
		return Response.ok().entity("registered user").build();
	}

	@POST
	@Path("/login")
	public Response login(User user) {
		user = userService.login(user.getUsername(), user.getPassword());
		if (user != null) {
			activeUserService.getActiveUsers().add(user);
			loggedInUser = user;			
			// set host
			System.out.println("logged in");
			return Response.ok(user).build();
		} else {
			loggedInUser = null;
		}
		return Response.status(Status.BAD_REQUEST).entity("Bad username or password").build();

	}

	@POST
	@Path("/logout")
	public Response logout(User user) {
		if (loggedInUser == user) {
			activeUserService.removeActiveUser(user);
			loggedInUser = null;
			return Response.ok().entity("User logged out").build();
		}
		return Response.status(Status.BAD_REQUEST).entity("User not logged out").build();
	}

	@POST
	@Path("/friends/{userId}/{friendId}")
	public Response addFriend(@PathParam("userId") String userId, @PathParam("friendId") String friendId) {
		userService.addFriend(friendId, userId);
		return Response.ok().entity("Added friend").build();
	}

	@DELETE
	@Path("/friends/{userId}/{friendId}")
	public Response deleteFriend(@PathParam("userId") String userId, @PathParam("friendId") String friendId) {
		userService.deleteFriend(friendId, userId);
		return Response.ok().entity("deleted friend").build();
	}

	@GET
	@Path("/active")
	public Response activeUsers() {
		return Response.ok(activeUserService.getActiveUsers()).build();
	}

}
