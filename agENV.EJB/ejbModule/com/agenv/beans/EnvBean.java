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
	
	private boolean loaded = false;

	public void init(Node localNode, List<Node> nodes, List<Agent> agents) {
		if (loaded)
			return;
		this.nodes = nodes;
		this.agents = agents;
		this.localNode = localNode;
		this.loaded = true;		
	}

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

	public void newNodeRegistered(Node node) {
		// TODO: IMPL
	}

	public void addNewAgent(Agent agent) {
		// TODO IMPL	
	}

}
