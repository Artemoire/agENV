package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import services.GroupService;

@Path("/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class GroupController {

	@EJB
	GroupService groupService;

	@POST
	@Path("/{groupAdminId}/{groupName}")
	public void createGroup(@PathParam("groupAdminId") Long groupAdminId, @PathParam("groupName") String groupName) {
		groupService.createGroup(groupAdminId, groupName);
	}
	
	@DELETE
	@Path("/{groupAdminId}/{groupName}")
	public void deleteGroup(@PathParam("groupAdminId") Long groupAdminId, @PathParam("groupName") String groupName) {
		groupService.deleteGroup(groupAdminId, groupName);
	}
	
	@POST
	@Path("/addNewUsersFromGroup/{groupAdminId}/{newUserId}")
	public void addNewUser(@PathParam("groupAdminId") Long groupAdminId, @PathParam("newUserId") Long newUserId) {
		groupService.addNewUser(groupAdminId, newUserId);

	}

	@DELETE
	@Path("/deleteUsersFromGroup/{groupAdminId}/{newUserId}")
	public void deleteUser(@PathParam("groupAdminId") Long groupAdminId, @PathParam("newUserId") Long newUserId) {
		groupService.deleteUser(groupAdminId, newUserId);

	}
}
