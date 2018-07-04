package com.agenv.test;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

import com.agenv.model.ACLMessage;
import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.Performative;

@Stateful
public class CNParticipant extends Agent {

	@Override
	public void handleMessage(ACLMessage msg) {
		if (msg.getPerform() == Performative.CALL_FOR_PROPOSAL) {
			List<AID> receivers = new ArrayList<AID>();
			ACLMessage newMessage = new ACLMessage();
			receivers.add(msg.getSender());
			if (Math.random() > 0.5) {
				newMessage.setPerform(Performative.ACCEPT);
				newMessage.setContent("YES <3");
			} else {
				newMessage.setPerform(Performative.REFUSE);
				newMessage.setContent("NO!!!");
			}
			newMessage.setSender(getAgentId());
			newMessage.setReceivers(receivers);
			messenger().send(newMessage);
		}

	}
}
