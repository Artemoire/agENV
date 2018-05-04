package websocket.services;

import java.util.List;

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
			else
				context.error(r.getStatus(), r.readEntity(String.class));
		} else {
			// TODO: JMS?
		}
	}

	public void login(WSUPRequestContext context) {
		User user = context.getMessage().getBodyAs(User.class);
		user.setHost(clusterInfo.getCurrentHost());
		if (!clusterInfo.isMaster()) {
			String path = "/users/login";
			Response r = ClientBuilder.newClient()
					.target("http://" + clusterInfo.getMasterAddress() + "/UserApp/rest" + path).request()
					.post(Entity.json(user));
			if (r.getStatus() == 200) {
				responseService.login(context.buildResponse(WSUPResponseType.SUCCESS, r.readEntity(User.class)));	
			}				
			else
				context.error(r.getStatus(), r.readEntity(String.class));
		} else {
			// TODO: JMS?
		}
	}
	
	public void getFriends(WSUPRequestContext context) {
		User user = context.getMessage().getBodyAs(User.class);
		if (!clusterInfo.isMaster()) {
			Response r = ClientBuilder.newClient().target(
					"http://" + clusterInfo.getMasterAddress() + "/UserApp/rest/users/" + user.getId() + "/friends")
					.request().get();
			
			if (r.getStatus() == 200)
				responseService.getFriends(context.buildResponse(WSUPResponseType.SUCCESS, r.readEntity(List.class)));
			else
				context.error(r.getStatus(), r.readEntity(String.class));
		} else {
			// TODO: JMS?
		}
	}
	
	public void getAll(WSUPRequestContext context) {
		if (!clusterInfo.isMaster()) {
			Response r = ClientBuilder.newClient()
					.target("http://" + clusterInfo.getMasterAddress() + "/UserApp/rest/users").request()
					.get();
			if (r.getStatus() == 200) {
				responseService.getAll(context.buildResponse(WSUPResponseType.SUCCESS, r.readEntity(List.class)));	
			}				
			else
				context.error(r.getStatus(), r.readEntity(String.class));
		} else {
			// TODO: JMS?
		}
	}

}
