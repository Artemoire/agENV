package com.agenv.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.AgentType;

public interface InitService {

	public ArrayList<AgentType> getTypes();
	public void setTypes(ArrayList<AgentType> types);
	public boolean insertAgentType(List<AgentType> agentTypes);
	public HashMap<AID, Agent> getAgents();
	public void setAgents(HashMap<AID, Agent> agents);
}
