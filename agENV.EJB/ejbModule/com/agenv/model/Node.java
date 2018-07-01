package com.agenv.model;

import java.util.List;

public class Node {

	private AgentCenter center;
	private List<AgentType> agentTypes;
	private List<Agent> agents;

	public Node() {
	}

	public Node(AgentCenter center, List<AgentType> agentTypes, List<Agent> agents) {
		this.center = center;
		this.agentTypes = agentTypes;
		this.agents = agents;
	}

	public AgentCenter getCenter() {
		return center;
	}

	public void setCenter(AgentCenter center) {
		this.center = center;
	}

	public List<AgentType> getAgentTypes() {
		return agentTypes;
	}

	public void setAgentTypes(List<AgentType> agentTypes) {
		this.agentTypes = agentTypes;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	
	

}
