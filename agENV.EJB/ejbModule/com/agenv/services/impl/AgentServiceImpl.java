package com.agenv.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

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

		try {
			type = URLDecoder.decode(type, "UTF-8");
			name = URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		AgentType agentType = AgentType.parse(type);
		Node node = envBean.findNodeByAgentType(agentType);

		if (node == null)
			throw new RuntimeException("AgentType " + type + " not found");

		if (node == envBean.getLocalNode()) {
			try {
				Context ctx = new InitialContext();
				Class<?> clazz = (Class<?>) ctx
						.lookup("java:global/" + agentType.getModule() + "/" + agentType.getName());
				ctx.close();
				Agent agent = (Agent) clazz.newInstance();
				AID aid = new AID(name, node.getCenter(), agentType);
				agent.init(aid);
				envBean.addNewLocalAgent(agent);
				List<AID> aids = new ArrayList<AID>();
				aids.add(aid);
				for (Node noddy : envBean.getNodes()) {
					ClientBuilder.newClient()
							.target("http://" + noddy.getCenter().getAddress() + "/agENV/rest/agents/running").request()
							.async().post(Entity.json(aids));
				}

			} catch (NamingException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
		
			try {
				ClientBuilder.newClient()
						.target("http://" + node.getCenter().getAddress() + "/agENV/rest/agents/running/{type}/{name}")
						.resolveTemplate("type",  URLEncoder.encode(type, "UTF-8")).resolveTemplate("name",  URLEncoder.encode(name, "UTF-8")).request().async().put(null);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stopAgent(String aid) {

		try {
			aid = URLDecoder.decode(aid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (AID.parse(aid).getHost().equals(envBean.getLocalNode().getCenter())) {
			Agent agent = envBean.findAgentByAID(AID.parse(aid));
			envBean.removeLocalAgent(agent);
			for (Node noddy : envBean.getNodes()) {
				ClientBuilder.newClient()
						.target("http://" + noddy.getCenter().getAddress() + "/agENV/rest/agents/running/delete")
						.request().async().post(Entity.json(agent.getAgentId()));
			}
		} else {
			try {
				ClientBuilder.newClient()
						.target("http://" + AID.parse(aid).getHost().getAddress() + "/agENV/rest/agents/running/{aid}")
						.resolveTemplate("aid", URLEncoder.encode(aid, "UTF-8")).request().delete();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

}
