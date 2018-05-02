package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import apps.SharedApi;
import models.Host;
import services.ClusterService;

@Path(SharedApi.CLUSTERS)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class ClusterController {

	@EJB
	private ClusterService clusterService;

	@Context
	private HttpServletRequest request;

	@POST
	public String register() {
		Host host = new Host(request.getRemoteAddr() + ":" + request.getServerPort(), "");
		clusterService.register(host);
		return host.getAlias();
	}

	@DELETE
	@Path("/{alias}")
	public void unregister(@PathParam("alias") String alias) {
		clusterService.unregister(alias);
	}

}
