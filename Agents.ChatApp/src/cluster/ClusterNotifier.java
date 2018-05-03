package cluster;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import models.Host;

@Stateless
public class ClusterNotifier {

	@EJB
	private NodeRegistry registry;

	public void postAsync(String path, Entity<?> entity) {
		for (Host h : registry.getHosts())
			ClientBuilder.newClient().target("http://" + h.getAddress() + "/ChatApp/rest" + path).request()
					.async().post(entity);
	}

}
