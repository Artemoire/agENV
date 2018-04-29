package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cluster.NodeRegistry;
import models.Host;

@Stateless
public class ClusterServiceImpl  implements ClusterService {

	@EJB
	private NodeRegistry registry;
	
	@Override
	public void register(Host host) {
		registry.registerNode(host);
	}

	@Override
	public void unregister(String alias) {
		registry.unregisterNode(alias);
	}

}
