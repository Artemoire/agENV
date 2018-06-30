package bean;

import java.io.Serializable;

import model.AID;

public class Agent implements Serializable{

	private Long id; 
	
	private AID agentId;

	public Agent() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AID getAgentId() {
		return agentId;
	}

	public void setAgentId(AID agentId) {
		this.agentId = agentId;
	}
	
	
}
