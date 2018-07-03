package com.agenv.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.agenv.beans.EnvBean;
import com.agenv.event.ApplicationMessengerImpl;
import com.agenv.model.Node;

@Stateless
public class HandshakeMasterService {

	@EJB
	private EnvBean envBean;

	public void registerNode(Node node) {
		// Step 0: payload is always arraylist
		List<Node> payload = new ArrayList<>();
		payload.add(node);

		boolean abort = false;

		// Step 1: Notify nodes of new node
		for (int i = 1; i < envBean.getNodes().size(); ++i) {
			Node myNode = envBean.getNodes().get(i);
			ClientBuilder.newClient().target("http://" + myNode.getCenter().getAddress() + "/node").request().async()
					.post(Entity.json(payload));
		}

		// Step 2: send nodes to new node
		if (!sendNodes(node))
			if (!sendNodes(node)) {
				abort = true;
				rollback(node);
			}

		if (abort)
			return;

		// Step 3: send running agents to new node
		if (!sendMyAgents(node))
			if (!sendMyAgents(node)) {
				abort = true;
				rollback(node);
			}

		if (abort)
			return;

		// Step 4: Add node to myself
		envBean.newNodeRegistered(node);
	}

	private boolean sendNodes(Node node) {
		Response r = ClientBuilder.newClient().target("http://" + node.getCenter().getAddress() + "/node").request()
				.post(Entity.json(envBean.getNodes()));
		return r.getStatus() == 204;
	}

	private boolean sendMyAgents(Node node) {
		Response r = ClientBuilder.newClient().target("http://" + node.getCenter().getAddress() + "/agents/running")
				.request().post(Entity.json(envBean.getAgents()));
		return r.getStatus() == 204;
	}

	private void rollback(Node node) {
		List<Node> payload = new ArrayList<>();
		payload.add(node);

		for (int i = 1; i < envBean.getNodes().size(); ++i) {
			Node myNode = envBean.getNodes().get(i);
			ClientBuilder.newClient()
					.target("http://" + myNode.getCenter().getAddress() + "/node/" + node.getCenter().getAlias())
					.request().async().delete();
		}
	}

}
