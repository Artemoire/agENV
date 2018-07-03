package com.agenv.test;

import javax.ejb.Stateful;

import com.agenv.model.ACLMessage;
import com.agenv.model.Agent;
import com.agenv.model.Module;

@Stateful
@Module("MojModjul")
public class MyAgent extends Agent {

	@Override
	public void handleMessage(ACLMessage msg) {
		// TODO Auto-generated method stub
		System.out.println("MESSENGER >>>>>>>>>>>>>" + messenger());
		System.out.println("MyAgent received " + msg.getContent());
	}

}
