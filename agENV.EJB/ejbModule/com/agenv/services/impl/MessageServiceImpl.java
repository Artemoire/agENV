package com.agenv.services.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.agenv.model.ACLMessage;
import com.agenv.services.MessageService;

@Stateless
public class MessageServiceImpl implements MessageService {

	@Override
	public void sendACLMessage(ACLMessage acl) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getPerformatives() {
		// TODO Auto-generated method stub
		return null;
	}

}
