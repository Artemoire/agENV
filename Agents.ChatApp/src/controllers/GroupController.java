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
import services.ProxyGroupService;

@Path(SharedApi.GROUPS)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class GroupController {

	@EJB
	ProxyGroupService groupService;

	@POST
	@Path("/{groupAdminId}/{groupName}")
	public void createGroup(@PathParam("groupAdminId") String groupAdminId, @PathParam("groupName") String groupName) {
		groupService.createGroup(groupAdminId, groupName);
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

	@DELETE
	@Path("/deleteUsersFromGroup/{groupAdminId}/{newUserId}/{groupId}")
	public void deleteUser(@PathParam("groupAdminId") String groupAdminId, @PathParam("newUserId") String newUserId,
			@PathParam("groupId") String groupId) {
		groupService.deleteUser(groupAdminId, newUserId, groupId);

	}
}
