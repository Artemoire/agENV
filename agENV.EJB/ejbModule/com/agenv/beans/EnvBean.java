package com.agenv.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.AgentType;
import com.agenv.model.Node;

@Singleton
public class EnvBean {

	private List<Node> nodes;
	private List<AgentType> agentTypes;
	private List<AID> agents;
	private List<Agent> localAgents;
	private Node localNode;

	private boolean loaded = false;

	public void init(Node localNode, List<Node> nodes, List<AID> agents) {
		if (loaded)
			return;
		this.nodes = nodes;
		this.agents = agents;
		this.localNode = localNode;
		this.loaded = true;
		this.agentTypes = new ArrayList<AgentType>();
		generateTypes();
	}

	public List<AgentType> getAgentTypes() {
		return agentTypes;
	}

	public List<AID> getAgents() {
		return agents;
	}

	public List<Agent> getLocalAgents() {
		return localAgents;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public Node getLocalNode() {
		return localNode;
	}

	// findNodeByAgentCenter

	public Agent findAgentByAID(AID aid) {
		for (Agent agent : localAgents)
			if (agent.getAgentId().equals(aid))
				return agent;
		return null;
	}

	public Node findNodeByAgentType(AgentType type) {
		for (Node node : nodes) {
			if (node.getAgentTypes().contains(type)) {
				return node;
			}
		}
		return null;
	}

	public void newNodeRegistered(Node node) {
		nodes.add(node);
		generateTypes();
	}

	public boolean addNewAgent(AID agent) {
		for (AID a : agents) {
			if (agent.getName().equals(a.getName())) {
				return false;
			}
		}
		agents.add(agent);
		return true;

	}

	private void generateTypes() {
		agentTypes.clear();
		for (Node n : nodes)
			for (AgentType at : n.getAgentTypes())
				if (!agentTypes.contains(at))
					agentTypes.add(at);
	}

	public boolean addNewLocalAgent(Agent agent) {
		for (Agent a : localAgents) {
			if (a.getAgentId().equals(agent.getAgentId())) {
				return false;
			}
		}
		localAgents.add(agent);
		return true;
	}

	public boolean removeLocalAgent(Agent agent) {
		for (int i = 0; i < localAgents.size(); i++) {
			if (localAgents.get(i).getAgentId().equals(agent.getAgentId())) {
				localAgents.remove(localAgents.get(i));
				return true;
			}
		}
		return false;
	}

}
