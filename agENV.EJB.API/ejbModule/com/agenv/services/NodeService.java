package com.agenv.services;

import java.util.List;

import com.agenv.model.AID;
import com.agenv.model.Node;

public interface NodeService {

	void addNodes(List<Node> nodes);

	void addAgents(List<AID> agents);

	void removeNode(String alias);

	void deleteAgent(AID agent);

	Node getNode();
}
