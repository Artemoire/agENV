package controllers;

import java.util.ArrayList;

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

import apps.SharedApi;
import database.ChatAppDbContext;
import jms.JMSMessage;
import jms.JMSMethodNames;
import jms.JMSProducer;
import models.Host;
import models.User;
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
	@EJB
	JMSProducer jmsProducer;
	
	@GET
	public String get() {
		ArrayList<Object> list = new ArrayList<Object>();
		User u = new User();
		u.setUsername("tot");
		u.setPassword("totjehot");
		list.add(u);
		jmsProducer.sendMassage(new JMSMessage(JMSMethodNames.login, list));
		return "hi2";
	}
	
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
