package com.agenv.event.listeners;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.agenv.beans.EnvBean;
import com.agenv.model.ACLMessage;
import com.agenv.model.AID;
import com.agenv.model.Agent;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/mojQueue") })
public class MDBConsumer implements MessageListener {

	@EJB
	EnvBean envBean;
	
	@Override
	public void onMessage(Message msg) {
		try {			
			ACLMessage acl = (ACLMessage) ((ObjectMessage) msg).getObject();

			int i = msg.getIntProperty("idx");
			AID aid = acl.receivers.get(i);
			
			Agent agent = envBean.findAgentByAID(aid);
			if (agent != null) {
				agent.handleMessage(acl);
			} else {
				System.out.println("No such agent: {}"+aid.getName());
			}
			
		} catch (JMSException ex) {
			System.out.println("No such agent: "+ex);
		}
	}
}
