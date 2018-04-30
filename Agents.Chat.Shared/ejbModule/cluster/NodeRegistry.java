package cluster;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import models.Host;

@Singleton
public class NodeRegistry {
	
	// key is host alias
	private HashMap<String, Host> nodes;
	
	@PostConstruct
	private void init() {
		nodes = new HashMap<String, Host>();
	}
	
	public void registerNode(Host host) {
		nodes.put(host.getAlias(), host);
		System.out.println(nodes);
	}
	
	public void unregisterNode(String alias) {
		nodes.remove(alias);
		System.out.println(nodes);
	}
	
}
