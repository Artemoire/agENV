package com.agenv.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.agenv.beans.EnvBean;
import com.agenv.model.AID;
import com.agenv.model.Node;
import com.agenv.node.NodeConfig;

@Singleton
public class HandshakeSlaveService {

	@EJB
	private EnvBean envBean;

	@EJB
	private NodeConfig config;

	private Node localNode;
	private List<Node> nodes;

	private enum Step {
		Start, Registered, ReceivedNodes, Finished
	}

	private Step step = Step.Start;

	public Step getStep() {
		return step;
	}

	public void startHandshake(Node localNode) {
		if (step != Step.Start)
			throw new RuntimeException("HANDSHAKE ERR: NOT STARTED (TODO: DOMAIN EXCP)");

		this.localNode = localNode;

		List<Node> payload = new ArrayList<>();
		payload.add(localNode);

		step = Step.Registered;
		Response r = ClientBuilder.newClient().target("http://" + config.masterHost() + "/node").request()
				.post(Entity.json(payload));

		// TODO: if r.status != 204
	}

	public void receiveNodes(List<Node> nodes) {
		if (step != Step.Registered)
			throw new RuntimeException("HANDSHAKE ERR: NOT REGISTERED (TODO: DOMAIN EXCP)");

		this.nodes = nodes;
		step = Step.ReceivedNodes;
	}

	public void receiveAgents(List<AID> agents) {
		if (step != Step.ReceivedNodes)
			throw new RuntimeException("HANDSHAKE ERR: NOT RECEIVED NODES (TODO: DOMAIN EXCP)");

		this.nodes.add(0, localNode);
		envBean.init(localNode, nodes, agents);
		this.localNode = null;
		this.nodes = null;
		step = Step.Finished;
	}

	public boolean handshook() {
		return step == Step.Finished;
	}

}
