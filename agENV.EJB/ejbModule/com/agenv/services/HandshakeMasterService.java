package com.agenv.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import com.agenv.beans.EnvBean;
import com.agenv.model.Node;

@Stateless
public class HandshakeMasterService {

	@EJB
	private EnvBean envBean;

	public void registerNode(Node node) {
		// Step 0: payload is always arraylist
		List<Node> payload = new ArrayList<>();
		payload.add(node);

		// Step 1: Notify nodes of new node
		for(int i = 1; i < envBean.getNodes().size(); ++i) {
			Node myNode = envBean.getNodes().get(i);
			ClientBuilder.newClient().target("http://" + myNode.getCenter().getAddress() + "/node").request().async()
					.post(Entity.json(payload));
		}
		
		// Step 3: send my nodes to new node
		ClientBuilder.newClient().target("http://" + node.getCenter().getAddress() + "/node").request()
		.post(Entity.json(envBean.getNodes()));

		// Step 2: Add node to myself
		envBean.newNodeRegistered(node);

		// Step 4: send running agents to new node
		ClientBuilder.newClient().target("http://" + node.getCenter().getAddress() + "/agents/running").request()
				.post(Entity.json(envBean.getAgents()));
	}

}
