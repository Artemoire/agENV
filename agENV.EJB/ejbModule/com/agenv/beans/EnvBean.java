package com.agenv.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.agenv.event.ApplicationMessenger;
import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.AgentType;
import com.agenv.model.Node;

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class EnvBean {

	@EJB
	private ApplicationMessenger messenger;

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
		this.localAgents = new ArrayList<Agent>();
		generateTypes();
	}

	@Lock(LockType.READ)
	public List<AgentType> getAgentTypes() {
		return agentTypes;
	}

	@Lock(LockType.READ)
	public List<AID> getAgents() {
		return agents;
	}

	@Lock(LockType.READ)
	public List<Agent> getLocalAgents() {
		return localAgents;
	}

	@Lock(LockType.READ)
	public List<Node> getNodes() {
		return nodes;
	}

	@Lock(LockType.READ)
	public Node getLocalNode() {
		return localNode;
	}

	@Lock(LockType.READ)
	public Agent findAgentByAID(AID aid) {
		for (Agent agent : localAgents)
			if (agent.getAgentId().equals(aid))
				return agent;
		return null;
	}

	@Lock(LockType.READ)
	public Node findNodeByAgentType(AgentType type) {
		if (localNode.getAgentTypes().contains(type))
			return localNode;

		for (Node node : nodes) {
			if (node.getAgentTypes().contains(type)) {
				return node;
			}
		}
		return null;
	}

	@Lock(LockType.WRITE)
	public void newNodeRegistered(Node node) {
		nodes.add(node);
		generateTypes();
	}

	@Lock(LockType.WRITE)
	public boolean addNewLocalAgent(Agent agent) {
		if (localAgents.contains(agent) || agents.contains(agent.getAgentId())) {
			messenger.log("Agent " + agent.getAgentId().toString() + " already exists!");
			return false;
		}
		localAgents.add(agent);
		agents.add(agent.getAgentId());
		messenger.log("Agent " + agent.getAgentId().toString() + " started");
		messenger.fireAgentsChanged();
		return true;
	}

	@Lock(LockType.WRITE)
	public boolean addNewAgent(AID agent) {
		if (agents.contains(agent))
			return false;
		agents.add(agent);
		messenger.fireAgentsChanged();
		return true;

	}

	@Lock(LockType.WRITE)
	public boolean removeAgent(AID aid) {
		boolean removed = agents.remove(aid);
		if (removed)
			messenger.fireAgentsChanged();
		return removed;
	}

	@Lock(LockType.WRITE)
	public boolean removeLocalAgent(Agent agent) {
		boolean removed = localAgents.remove(agent) && agents.remove(agent.getAgentId());
		if (removed) {
			messenger.log("Agent " + agent.getAgentId().toString() + " stopped");
			messenger.fireAgentsChanged();
		} else {
			messenger.log("Failed to stop agent " + agent.getAgentId().toString());
		}
		return removed;
	}

	@Lock(LockType.WRITE)
	private void generateTypes() {
		int oldSize = agentTypes.size();
		agentTypes.clear();
		agentTypes.addAll(localNode.getAgentTypes());
		for (Node n : nodes)
			for (AgentType at : n.getAgentTypes())
				if (!agentTypes.contains(at))
					agentTypes.add(at);
		if (agentTypes.size() != oldSize)
			messenger.fireAgentTypesChanged();
	}

	@Lock(LockType.WRITE)
	public void removeNodeByAlias(String alias) {
		Node toRemove = null;

		for (Node n : nodes)
			if (n.getCenter().getAlias().equals(alias))
				toRemove = n;

		if (toRemove != null) {
			deleteAgentsByNodeAlias(alias);
			nodes.remove(toRemove);
			generateTypes();
		}
	}

	@Lock(LockType.WRITE)
	private void deleteAgentsByNodeAlias(String alias) {
		List<AID> keepAgents = new ArrayList<AID>();
		for (AID aid : agents) {
			if (!aid.getHost().getAlias().equals(alias)) {
				keepAgents.add(aid);
			}
		}
		if (keepAgents.size() != agents.size()) {
			agents = keepAgents;
			messenger.fireAgentsChanged();
		}
	}

}
