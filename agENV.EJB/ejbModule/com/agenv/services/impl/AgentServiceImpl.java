package com.agenv.services.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.agenv.beans.EnvBean;
import com.agenv.model.AID;
import com.agenv.model.AgentType;
import com.agenv.model.Node;
import com.agenv.services.AgentService;

@Stateless
public class AgentServiceImpl implements AgentService {

	@EJB
	EnvBean envBean;

	@Override
	public List<AgentType> getAllTypes() {
		return envBean.getAgentTypes();
	}

	@Override
	public List<AID> getRunningAgents() {
		return envBean.getAgents();
	}

	@Override
	public void startAgent(String type, String name) {

		Node node = envBean.findNodeByAgentType(AgentType.parse(type));

		if (node == null)
			throw new RuntimeException("AgentType " + type + " not found");
		

		if (node == envBean.getLocalNode()) {			
			// Create and notify
			
		} else {			
			// ClientBuilder.newClient().target("node.get...").request().post(entity)
		}
	}

	@Override
	public void stopAgent(String aid) {
		// TODO Auto-generated method stub

	}

}
