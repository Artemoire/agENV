package com.agenv.services;

import java.util.List;

import com.agenv.model.ACLMessage;

public interface MessageService {

	public void sendACLMessage(ACLMessage acl);
	public List<String> getPerformatives();
}
