package websocket.services;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Entity;

import cluster.ClusterInfo;
import cluster.ClusterNotifier;
import models.User;
import services.ActiveUserService;
import services.SessionUserService;
import websocket.context.WSUPContext;

@Stateless
public class WSResponseUserService {

	@EJB
	private ClusterNotifier clusterNotifier;

	@EJB
	private ActiveUserService activeUserService;
	
	@EJB
	private SessionUserService sessionService;

	@EJB
	private ClusterInfo clusterInfo;

	public void register(WSUPContext context) {
		context.sendMessage();
	}

	public void login(WSUPContext context) {				
		User user = context.getMessage().getBodyAs(User.class);
		activeUserService.login(user);
		sessionService.login(user, context.getSession());
		context.sendMessage();
        clusterNotifier.postAsync("/users/login", Entity.json(user));
	}

	public void getFriends(WSUPContext context) {
		context.sendMessage();		
	}
	
	public void getAll(WSUPContext context) {
		context.sendMessage();
	}
}
