package com.agenv.node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.CDI;

import com.agenv.beans.EnvBean;
import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.AgentCenter;
import com.agenv.model.AgentType;
import com.agenv.model.Node;
import com.agenv.services.HandshakeSlaveService;

@Startup
@Singleton
public class NodeStartup {

	@EJB
	private NodeConfig config;

	@EJB
	private HandshakeSlaveService handshakeSlaveService;

	@EJB
	private EnvBean envBean;

	@PostConstruct
	void init() throws IOException {
		config.init();

		Iterator<Bean<?>> beanterator = CDI.current().getBeanManager().getBeans(Agent.class).iterator();

		List<AgentType> agentTypes = new ArrayList<AgentType>();

		while (beanterator.hasNext())
			agentTypes.add(new AgentType(beanterator.next().getBeanClass().getName(), null));

		Node node = new Node(new AgentCenter(config.nodeAlias(), config.nodeHost()), agentTypes);

		if (!config.isMaster()) {
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					handshakeSlaveService.startHandshake(node);
				}
			}, 5000);
		} else {
			List<Node> nodes = new ArrayList<Node>();
			envBean.init(node, nodes, new ArrayList<AID>());
		}

	}

}
