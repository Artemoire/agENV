package com.agenv.test;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

import com.agenv.model.ACLMessage;
import com.agenv.model.AID;
import com.agenv.model.Agent;
import com.agenv.model.AgentType;
import com.agenv.model.Module;
import com.agenv.model.Performative;

@Stateful
@Module("MojModjul")
public class Ping extends Agent {

	@Override
	public void handleMessage(ACLMessage msg) {
		if(msg.getPerform() == Performative.REQUEST) {
			List<AID> receivers = new ArrayList<AID>();
			ACLMessage newMessage = new ACLMessage();
			AID idPong = new AID(msg.getContent(),null,new AgentType(Pong.class.getName(), "agENV"));
			receivers.add(idPong);
			newMessage.setContent("Ping!!");
			newMessage.setSender(getAgentId());
			newMessage.setReceivers(receivers);
			newMessage.setPerform(Performative.REQUEST);
			messenger().send(newMessage);
		}
		if(msg.getPerform() == Performative.INFORM) {
			System.out.println("Received Pong!!");
		}
	}

}
