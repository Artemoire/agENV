package com.agenv.model;

import java.io.Serializable;

import com.agenv.model.AID;

public abstract class Agent implements Serializable{
	
	private AID agentId;

	public Agent() {
		
	}

	public Agent(AID agentId) {
		super();
		this.agentId = agentId;
	}

	public AID getAgentId() {
		return agentId;
	}

	public void setAgentId(AID agentId) {
		this.agentId = agentId;
	}

	public abstract void handleMessage(ACLMessage msg);

	public void init(AID aid) {
		agentId = aid;
	}
	
	
}
