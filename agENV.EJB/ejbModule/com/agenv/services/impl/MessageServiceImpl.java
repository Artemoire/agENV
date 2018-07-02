package com.agenv.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import com.agenv.beans.EnvBean;
import com.agenv.model.ACLMessage;
import com.agenv.model.AID;
import com.agenv.model.AgentCenter;
import com.agenv.model.Performative;
import com.agenv.services.MessageService;

@Stateless
public class MessageServiceImpl implements MessageService {

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:/queue/mojQueue")
	private Queue queue;
	private ArrayList<Performative> performs = new ArrayList<Performative>();

	@EJB
	EnvBean envBean;

	@Override
	public void sendACLMessage(ACLMessage acl) {
		ArrayList<AgentCenter> agentCenters = new ArrayList<AgentCenter>();
		for (int i = 0; i < acl.receivers.size(); ++i) {
			AID receiver = acl.receivers.get(i);
			if (receiver.getHost().equals(envBean.getLocalNode().getCenter())) {
				sendJMS(acl, i);
			} else if (!agentCenters.contains(receiver.getHost())) {
				agentCenters.add(receiver.getHost());
			}

		}
		for (AgentCenter agentCenter : agentCenters) {
			ClientBuilder.newClient().target("http://" + agentCenter.getAddress() + "/forward").request().async()
					.post(Entity.json(acl));
		}
	}

	@Override
	public List<Performative> getPerformatives() {
		for (Performative p : Performative.values()) {
			performs.add(p);
		}
		return performs;
	}

	@Override
	public void forwardACLMessages(ACLMessage acl) {
		for (int i = 0; i < acl.receivers.size(); ++i) {
			AID receiver = acl.receivers.get(i);
			if (receiver.getHost().equals(envBean.getLocalNode().getCenter())) {
				sendJMS(acl, i);
			}
		}

	}

	private void sendJMS(ACLMessage acl, int index) {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer sender = session.createProducer(queue);
			ObjectMessage objectMessage = session.createObjectMessage();

			objectMessage.setIntProperty("index", index);
			objectMessage.setObject(acl);
			sender.send(objectMessage);

			sender.close();
			session.close();
			connection.close();

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
