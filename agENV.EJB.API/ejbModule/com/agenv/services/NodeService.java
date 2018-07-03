package com.agenv.services;

import java.util.List;

import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.Node;

public interface NodeService {

	void addNodes(List<Node> nodes);
	void addAgents(List<AID> agents);
	void deleteAgent(Agent agent);
}
