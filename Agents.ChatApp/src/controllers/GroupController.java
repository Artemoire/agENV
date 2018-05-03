package controllers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import apps.SharedApi;
import models.Group;
import services.ProxyGroupService;

@Path(SharedApi.GROUPS)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class GroupController {

	@EJB
	ProxyGroupService groupService;

	@POST
	public void createGroup(Group group) {
		groupService.createGroup(group);
	}

	@DELETE
	@Path("/{groupAdminId}/{groupId}")
	public void deleteGroup(@PathParam("groupAdminId") String groupAdminId, @PathParam("groupId") String groupId) {
		groupService.deleteGroup(groupAdminId, groupId);
	}

	@POST
	@Path("/addNewUsersToGroup/{groupAdminId}/{newUserId}/{groupId}")
	public void addNewUser(@PathParam("groupAdminId") String groupAdminId, @PathParam("newUserId") String newUserId,
			@PathParam("groupId") String groupId) {
		groupService.addNewUser(groupAdminId, newUserId, groupId);

	}

	@POST
	@Path("/{groupId}/{userId}")
	public void deleteUser(@PathParam("groupId") String groupId, @PathParam("userId") String userId) {
//		groupService.deleteUser(groupAdminId, newUserId, groupId);
	}
}
