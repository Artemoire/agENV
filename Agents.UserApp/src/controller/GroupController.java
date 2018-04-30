package controller;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import services.GroupService;

public class GroupController {

	@EJB
	GroupService groupService;

	@POST
	@Path("/groups/{groupAdminId}/{groupName}")
	public void createGroup(@PathParam("groupAdminId") Long groupAdminId, @PathParam("groupName") String groupName) {
		groupService.createGroup(groupAdminId, groupName);
	}
}
