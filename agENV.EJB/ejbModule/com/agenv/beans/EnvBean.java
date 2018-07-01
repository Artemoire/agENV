package com.agenv.beans;

import java.util.List;

import javax.ejb.Singleton;

import com.agenv.model.Agent;
import com.agenv.model.AgentType;
import com.agenv.model.Node;

@Singleton
public class EnvBean {

	private List<Node> nodes;
	private List<AgentType> agentTypes;
	private List<Agent> agents;
	private Node localNode;

	public List<AgentType> getAgentTypes() {
		return agentTypes;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public Node getLocalNode() {
		return localNode;
	}

	// findNodeByAgentCenter

	public Node findNodeByAgentType(AgentType type) {
		for (Node node : nodes) {
			if (node.getAgentTypes().contains(type)) {
				return node;
			}
		}
		return null;
	}

}
