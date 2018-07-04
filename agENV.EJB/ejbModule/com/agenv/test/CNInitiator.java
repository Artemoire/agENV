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
public class CNInitiator extends Agent {

	@Override
	public void handleMessage(ACLMessage msg) {
		if (msg.getPerform() == Performative.REQUEST) {
			List<AID> receivers = new ArrayList<AID>();
			ACLMessage newMessage = new ACLMessage();
			for (String name : msg.getContent().split(",")) {
				AID idParticipant = new AID(name, null, new AgentType(CNParticipant.class.getName(), "agENV"));
				receivers.add(idParticipant);
			}

			newMessage.setContent("Will you marry me?");
			newMessage.setSender(getAgentId());
			newMessage.setReceivers(receivers);
			newMessage.setPerform(Performative.CALL_FOR_PROPOSAL);
			messenger().send(newMessage);
		}

	}

}
