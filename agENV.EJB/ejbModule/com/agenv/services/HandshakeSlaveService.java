package com.agenv.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import com.agenv.beans.EnvBean;
import com.agenv.model.Agent;
import com.agenv.model.Node;

@Singleton
public class HandshakeSlaveService {

	@EJB
	private EnvBean envBean;

	private Node localNode;
	private List<Node> nodes;

	private enum Step {
		Start, Registered, ReceivedNodes, Finished
	}

	private Step step;

	public Step getStep() {
		return step;
	}

	public void startHandshake(Node localNode) {
		if (step != Step.Start)
			throw new RuntimeException("HANDSHAKE ERR: NOT STARTED (TODO: DOMAIN EXCP)");

		this.localNode = localNode;

		// TODO: Post async
		step = Step.Registered;
	}

	public void receiveNodes(List<Node> nodes) {
		if (step != Step.Registered)
			throw new RuntimeException("HANDSHAKE ERR: NOT REGISTERED (TODO: DOMAIN EXCP)");

		this.nodes = nodes;
		step = Step.ReceivedNodes;
	}

	public void receiveAgents(List<Agent> agents) {
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
