package websocket.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import cluster.ClusterInfo;
import models.User;
import websocket.WSUPResponseType;
import websocket.context.WSUPRequestContext;

@Stateless
public class WSRequestUserService {

	@EJB
	private ClusterInfo clusterInfo;

	@EJB
	private WSResponseUserService responseService;

	public void register(WSUPRequestContext context) {
		if (!clusterInfo.isMaster()) {
			Response r = ClientBuilder.newClient()
					.target("http://" + clusterInfo.getMasterAddress() + "/UserApp/rest/users").request()
					.post(Entity.json(context.getMessage().getBodyAs(User.class)));
			
			if (r.getStatus() == 200)
				responseService.register(context.buildResponse(WSUPResponseType.SUCCESS, ""));
		} else {
			// TODO: JMS?
		}
	}

	public void login(WSUPRequestContext context) {
		User user = context.getMessage().getBodyAs(User.class);
		user.setHost(clusterInfo.getCurrentHost());
		if (!clusterInfo.isMaster()) {
			String path = "/users/login";
			user = ClientBuilder.newClient().target("http://" + clusterInfo.getMasterAddress() + "/UserApp/rest" + path)
					.request().post(Entity.json(user)).readEntity(User.class);
			// context.response(WSUPResponseType.Success, user) or somethiung idk
			responseService.login(context.buildResponse(WSUPResponseType.SUCCESS, user));
		} else {
			// TODO: JMS?
		}
	}

}
