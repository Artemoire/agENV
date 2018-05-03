package websocket;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import cluster.ClusterInfo;
import cluster.ClusterNotifier;
import models.User;
import services.SessionUserService;

@Stateless
public class WSUserService {

	@EJB
	private ClusterNotifier clusterNotifier;

	@EJB
	private SessionUserService sessionService;

	@EJB
	private ClusterInfo clusterInfo;

	public void register(User user) {
		if (!clusterInfo.isMaster()) {
			ClientBuilder.newClient().target("http://" + clusterInfo.getMasterAddress() + "/UserApp/rest/users")
					.request().post(Entity.json(user));
		} else {
			// TODO: JMS?
		}
	}

	public void login(User user) {
		user.setHost(clusterInfo.getCurrentHost());
		if (!clusterInfo.isMaster()) {
			String path = "/users/login";
			user = ClientBuilder.newClient().target("http://" + clusterInfo.getMasterAddress() + "/UserApp/rest" + path)
					.request().post(Entity.json(user)).readEntity(User.class);
			if(user != null)
				clusterNotifier.postAsync(path, Entity.json(user));
		} else {
			// TODO: JMS?
		}
	}

}
