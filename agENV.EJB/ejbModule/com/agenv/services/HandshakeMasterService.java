package com.agenv.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.agenv.beans.EnvBean;
import com.agenv.model.Node;

@Stateless
public class HandshakeMasterService {

	@EJB
	private EnvBean envBean;

	public void registerNode(Node node) {
		System.out.println("Registering new node...");
		// Step 0: payload is always arraylist
		List<Node> payload = new ArrayList<>();
		payload.add(node);

		boolean abort = false;

		System.out.println("Notifying other nodes of new node...");

		// Step 1: Notify nodes of new node
		for (int i = 1; i < envBean.getNodes().size(); ++i) {
			Node myNode = envBean.getNodes().get(i);
			ClientBuilder.newClient().target("http://" + myNode.getCenter().getAddress() + "/agENV/rest/node").request()
					.async().post(Entity.json(payload));
		}

		List<Node> nodesAndLocal = new ArrayList<>(envBean.getNodes().size() + 1);
		nodesAndLocal.add(0, envBean.getLocalNode());
		nodesAndLocal.addAll(envBean.getNodes());

		System.out.println("Sending " + nodesAndLocal.size() + " nodes to new node...");

		// Step 2: send nodes to new node
		if (!sendNodes(node, nodesAndLocal))
			if (!sendNodes(node, nodesAndLocal)) {
				abort = true;
				rollback(node);
			}

		System.out.println("Sent!");

		if (abort)
			return;

		System.out.println("Sending " + envBean.getAgents().size() + " running aids to new node...");

		// Step 3: send running agents to new node
		if (!sendMyAgents(node))
			if (!sendMyAgents(node)) {
				abort = true;
				rollback(node);
			}

		if (abort)
			return;

		System.out.println("Sent!");

		// Step 4: Add node to myself
		envBean.newNodeRegistered(node);
		System.out.println("New node finished handshake");
	}

	private boolean sendNodes(Node node, List<Node> nodes) {
		Response r = ClientBuilder.newClient().target("http://" + node.getCenter().getAddress() + "/agENV/rest/node")
				.request().post(Entity.json(nodes));
		return r.getStatus() == 204;
	}

	private boolean sendMyAgents(Node node) {
		Response r = ClientBuilder.newClient()
				.target("http://" + node.getCenter().getAddress() + "/agENV/rest/agents/running").request()
				.post(Entity.json(envBean.getAgents()));
		return r.getStatus() == 204;
	}

	private void rollback(Node node) {
		System.out.println("New node failed handshake...");
		System.out.println("Rolling back...");
		List<Node> payload = new ArrayList<>();
		payload.add(node);

		for (int i = 1; i < envBean.getNodes().size(); ++i) {
			Node myNode = envBean.getNodes().get(i);
			ClientBuilder.newClient().target(
					"http://" + myNode.getCenter().getAddress() + "/agENV/rest/node/" + node.getCenter().getAlias())
					.request().async().delete();
		}
	}

}
