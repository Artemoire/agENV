package com.agenv.node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.CDI;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

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

	@Schedules({ @Schedule(hour = "*", minute = "*", second = "*/60"), })
	public void checkHeartbeats() {

		try {
			Node node = envBean.getLocalNode();
			for (Node noddy : envBean.getNodes()) {
				if (!noddy.getCenter().getAddress().equals(node.getCenter().getAddress())) {
					checkNoddy(noddy);
				}
			}
		} catch (Exception e) {
			System.out.println("Noddy is dead :(!");
			e.printStackTrace();

		}

	}

	public void checkNoddy(Node noddy) {
		Node checked = heartbeat(noddy);
		if (checked == null) {
			checked = heartbeat(noddy);
			if (checked == null) {
			    deleteNode(noddy);
			}
		}
		System.out.println("Noddy is alive");

	}

	public void deleteNode(Node noddy) {
		ClientBuilder.newClient().target("http://" + noddy.getCenter().getAddress() + "/agents/running/delete")
				.request().async().post(Entity.json(noddy.getCenter().getAlias()));
	}

	public Node heartbeat(Node node) {

		try {
			return ClientBuilder.newClient().target("http://" + node.getCenter().getAddress() + ":8080/node/")
					.request(MediaType.APPLICATION_JSON).get().readEntity(Node.class);

		} catch (Exception e) {
			System.out.println("Can't find center with IP: " + node.getCenter().getAddress() + " and alias: "
					+ node.getCenter().getAlias());
			return null;
		}

	}

}
