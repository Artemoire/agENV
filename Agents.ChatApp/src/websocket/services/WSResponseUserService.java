package websocket.services;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Entity;

import cluster.ClusterInfo;
import cluster.ClusterNotifier;
import models.User;
import services.SessionUserService;
import websocket.context.WSUPContext;

@Stateless
public class WSResponseUserService {

	@EJB
	private ClusterNotifier clusterNotifier;

	@EJB
	private SessionUserService sessionService;

	@EJB
	private ClusterInfo clusterInfo;

	public void register(WSUPContext context) {
		try {
			context.getSession().getBasicRemote().sendText(context.getMessage().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void login(WSUPContext context) {
		User user = context.getMessage().getBodyAs(User.class);
        clusterNotifier.postAsync("/users/login", Entity.json(user));
        // TODO : context.getSession() respond OK user
	}
	
}
