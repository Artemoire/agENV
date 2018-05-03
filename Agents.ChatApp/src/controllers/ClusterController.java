package controllers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

import apps.SharedApi;
import database.ChatAppDbContext;
import models.Host;
import services.ClusterService;

@Path(SharedApi.CLUSTERS)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class ClusterController {

	@EJB
	private ClusterService clusterService;

	@EJB
	ChatAppDbContext db;

	// TODO Accept requests only from Master UserApp
	
	@POST
	public void register(Host host) {
		clusterService.register(host);
	}

	@DELETE
	@Path("/{alias}")
	public void unregister(@PathParam("alias") String alias) {
		clusterService.unregister(alias);
	}

}
