package com.agenv.services;

import java.util.List;

import com.agenv.model.ACLMessage;
import com.agenv.model.Performative;

public interface MessageService {

	public void sendACLMessage(ACLMessage acl);
	public List<Performative> getPerformatives();
	public void forwardACLMessages(ACLMessage acl);
}
