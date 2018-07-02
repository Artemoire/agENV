package com.agenv.model;

import java.util.List;

public class Node {

	private AgentCenter center;
	private List<AgentType> agentTypes;

	public Node() {
	}

	public Node(AgentCenter center, List<AgentType> agentTypes) {
		this.center = center;
		this.agentTypes = agentTypes;
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

}
