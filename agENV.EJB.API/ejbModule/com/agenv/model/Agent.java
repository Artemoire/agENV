package com.agenv.model;

import java.io.Serializable;

import com.agenv.model.AID;

public class Agent implements Serializable{
	
	private AID agentId;

	public Agent() {
		
	}

	public AID getAgentId() {
		return agentId;
	}

	public void setAgentId(AID agentId) {
		this.agentId = agentId;
	}
	
	
}
