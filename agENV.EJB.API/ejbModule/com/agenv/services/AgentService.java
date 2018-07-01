package com.agenv.services;

import java.util.List;

import com.agenv.model.Agent;
import com.agenv.model.AgentType;

public interface AgentService {

	
	public List<AgentType> getAllTypes();
	public List<Agent> getRunningAgents();
	public void startAgent(String type, String name);
	public void stopAgent(String aid);
}
