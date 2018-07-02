package com.agenv.services.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.agenv.beans.EnvBean;
import com.agenv.model.AID;
import com.agenv.model.Agent;
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

		AgentType agentType = AgentType.parse(type);
		Node node = envBean.findNodeByAgentType(agentType);

		if (node == null)
			throw new RuntimeException("AgentType " + type + " not found");

		if (node == envBean.getLocalNode()) {
			try {
				Context ctx = new InitialContext();
				Class<?> clazz = (Class<?>) ctx.lookup("java:global/" + agentType.getName());
				Agent agent = (Agent) clazz.newInstance();
				AID aid = new AID(name, node.getCenter(), agentType);
				agent.init(aid);
				envBean.addNewLocalAgent(agent);

			} catch (NamingException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			// ClientBuilder.newClient().target("node.get...").request().put()
		}
	}

	@Override
	public void stopAgent(String aid) {
		// TODO Auto-generated method stub

	}

}
