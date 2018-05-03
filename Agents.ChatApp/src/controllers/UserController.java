package controllers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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

import apps.SharedApi;
import models.User;
import services.ActiveUserService;
import services.ProxyUserService;

@Path(SharedApi.USERS)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class UserController {

	@EJB
	ProxyUserService userService;

	@POST
	@Path("/login")
	public void login(User user) {
		userService.login(user);
	}

	@POST
	@Path("/logout")
	public void logout(User user) {
		userService.logout(user);
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

}
