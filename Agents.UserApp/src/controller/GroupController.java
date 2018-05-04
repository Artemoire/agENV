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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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
	public Response createGroup(@PathParam("groupAdminId") String groupAdminId, @PathParam("groupName") String groupName) {
		groupService.createGroup(groupAdminId, groupName);
		return Response.ok().entity("created group").build();
	}
	
	@DELETE
	@Path("/{groupAdminId}/{groupId}")
	public Response deleteGroup(@PathParam("groupAdminId") String groupAdminId, @PathParam("groupId") String groupId) {
		groupService.deleteGroup(groupAdminId, groupId);
		return Response.ok().entity("deleted group").build();
	}
	
	@POST
	@Path("/addNewUsersToGroup/{groupAdminId}/{newUserId}/{groupId}")
	public Response addNewUser(@PathParam("groupAdminId") String groupAdminId, @PathParam("newUserId") String newUserId, @PathParam("groupId") String groupId) {
		groupService.addNewUser(groupAdminId, newUserId, groupId);
		return Response.ok().entity("added new user").build();
	}

	@DELETE
	@Path("/deleteUsersFromGroup/{groupAdminId}/{newUserId}/{groupId}")
	public Response deleteUser(@PathParam("groupAdminId") String groupAdminId, @PathParam("newUserId") String newUserId, @PathParam("groupId") String groupId) {
		groupService.deleteUser(groupAdminId, newUserId, groupId);
		return Response.ok().entity("deleted user").build();
	}
}
