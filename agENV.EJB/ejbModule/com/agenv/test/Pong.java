package com.agenv.test;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

import com.agenv.model.ACLMessage;
import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.AgentType;
import com.agenv.model.Performative;

@Stateful
public class Pong extends Agent {

	@Override
	public void handleMessage(ACLMessage msg) {
		if(msg.getPerform() == Performative.REQUEST) {
			List<AID> receivers = new ArrayList<AID>();
			ACLMessage newMessage = new ACLMessage();
			receivers.add(msg.getSender());
			newMessage.setContent("Pong!!");
			newMessage.setSender(getAgentId());
			newMessage.setReceivers(receivers);
			newMessage.setPerform(Performative.INFORM);
			messenger().send(newMessage);
		}

	}

}
