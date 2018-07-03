package com.agenv.node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.CDI;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.ClientBuilder;

import com.agenv.beans.EnvBean;
import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.AgentCenter;
import com.agenv.model.AgentType;
import com.agenv.model.Module;
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

	private String mylias;

	@PostConstruct
	void init() throws IOException, NamingException {
		config.init();

		Iterator<Bean<?>> beanterator = CDI.current().getBeanManager().getBeans(Agent.class).iterator();

		List<AgentType> agentTypes = new ArrayList<AgentType>();

		Context ctx = new InitialContext();

		while (beanterator.hasNext()) {
			Class<?> agentClass = beanterator.next().getBeanClass();
			String module = "agENV";
			if (agentClass.isAnnotationPresent(Module.class))
				module = agentClass.getAnnotation(Module.class).value();

			ctx.bind("java:global/" + module + "/" + agentClass.getName(), agentClass);

			agentTypes.add(new AgentType(agentClass.getName(), module));
		}

		ctx.close();
		mylias = config.nodeAlias();
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

	@PreDestroy
	public void killmepls() {
		ClientBuilder.newClient().target("http://" + config.masterHost() + "/node/" + mylias).request().async()
				.delete();
	}

}
