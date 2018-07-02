package com.agenv.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import com.agenv.model.ACLMessage;
import com.agenv.model.Performative;
import com.agenv.services.MessageService;

@Stateless
public class MessageServiceImpl implements MessageService {

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:/queue/mojQueue")
	private Queue queue;
	private ArrayList<Performative> performs = new ArrayList<Performative>();

	@Override
	public void sendACLMessage(ACLMessage acl) {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer sender = session.createProducer(queue);
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(acl);
			sender.send(objectMessage);

			sender.close();
			session.close();
			connection.close();

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Performative> getPerformatives() {
		for (Performative p : Performative.values()) {
			performs.add(p);
		}
		return performs;
	}

}
