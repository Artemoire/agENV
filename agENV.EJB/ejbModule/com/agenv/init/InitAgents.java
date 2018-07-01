package com.agenv.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Singleton;

import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.AgentType;
import com.agenv.services.InitService;

@Singleton
public class InitAgents implements InitService{

	private ArrayList<AgentType> types = new ArrayList<>();

	private HashMap<AID, Agent> agents = new HashMap<>();
	
	public ArrayList<AgentType> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<AgentType> types) {
		this.types = types;
	}
	
	
	public boolean insertAgentType(List<AgentType> agentTypes) { 
		for(AgentType type: agentTypes) {
			if(type.getName().equals(type.getName()) && type.getModule().equals(type.getModule())) {
				return false;
			}
			types.add(type);
		}
		return true;
	}
	
	public HashMap<AID, Agent> getAgents() {
		return agents;
	}

	public void setAgents(HashMap<AID, Agent> agents) {
		this.agents = agents;
	}
	
	
	
	
	
}
