package com.agenv.node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

	private String mylias;
	private String masterHost;
	private boolean master;

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
		masterHost = config.masterHost();
		master = config.isMaster();
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
		if (!config.isMaster())
			return;
		try {
			for (Node node : envBean.getNodes()) {
				checkNoddy(node);
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
				deleteNoddy(noddy);
			}
		}
		System.out.println("Noddy is alive");

	}

	private void deleteNoddy(Node checked) {
		envBean.removeNodeByAlias(checked.getCenter().getAlias());
		for (Node node : envBean.getNodes()) {
			ClientBuilder.newClient().target(
					"http://" + node.getCenter().getAddress() + "/agENV/rest/node/" + checked.getCenter().getAlias())
					.request().async().delete();
		}
	}

	public Node heartbeat(Node node) {
		try {
			return ClientBuilder.newClient().target("http://" + node.getCenter().getAddress() + "/agENV/rest/node/")
					.request(MediaType.APPLICATION_JSON).get().readEntity(Node.class);
		} catch (Exception e) {
			System.out.println("Can't find center with IP: " + node.getCenter().getAddress() + " and alias: "
					+ node.getCenter().getAlias());
			return null;
		}
	}

	@PreDestroy
	public void killmepls() {
		if (!master)
			ClientBuilder.newClient().target("http://" + masterHost + "/agENV/rest/node/" + mylias).request().async()
					.delete();
	}

}
