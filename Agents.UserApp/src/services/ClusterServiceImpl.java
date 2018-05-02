package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import apps.SharedApi;
import chatapp.ChatApp;
import cluster.NodeRegistry;
import models.Host;

@Stateless
public class ClusterServiceImpl implements ClusterService {

	@EJB
	private NodeRegistry registry;

	// TODO: JMS Register

	@Override
	public void register(Host host) {
		host.setAlias((registry.getHosts().size() + 1) + ".ChatApp");
		for (Host h : registry.getHosts()) {
			Client c = ClientBuilder.newClient();
			c.target("http://" + h.getAddress() + ChatApp.BASE + ChatApp.Rest.BASE + SharedApi.CLUSTERS).request()
					.post(Entity.json(host));
		}
		registry.registerNode(host);
	}

	@Override
	public void unregister(String alias) {
		registry.unregisterNode(alias);
		for (Host h : registry.getHosts()) {
			Client c = ClientBuilder.newClient();
			c.target("http://" + h.getAddress() + ChatApp.BASE + ChatApp.Rest.BASE + SharedApi.CLUSTERS + "/" + alias)
					.request().delete();
		}
	}

}
