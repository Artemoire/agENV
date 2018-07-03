package com.agenv.services;

import com.agenv.model.ACLMessage;

public interface AgentMessenger {

	void send(ACLMessage acl);
	
}
