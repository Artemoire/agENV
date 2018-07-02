package com.agenv.event.listeners;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import com.agenv.model.ACLMessage;
import com.agenv.model.AID;
import com.agenv.model.Agent;

@MessageDriven(name = "MDBConsumer", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/siebog"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class MDBConsumer implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		try {
			processMessage(msg);
		} catch (JMSException ex) {
			System.out.println("No such agent: {}"+ex);
		}
	}

	private void processMessage(Message msg) throws JMSException {
		ACLMessage acl = (ACLMessage) ((ObjectMessage) msg).getObject();
		AID aid = getAid(msg, acl);
		deliverMessage(acl, aid);
	}

	private AID getAid(Message msg, ACLMessage acl) throws JMSException {
		int i = msg.getIntProperty("AIDIndex");
		return acl.receivers.get(i);
	}

	private void deliverMessage(ACLMessage msg, AID aid) {
		Agent agent = null;
		if (agent != null) {
			agent.handleMessage(msg);
		} else {
			System.out.println("No such agent: {}"+aid.getName());
		}
	}
}
