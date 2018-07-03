package com.agenv.services.impl;

import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.agenv.beans.EnvBean;
import com.agenv.model.ACLMessage;
import com.agenv.services.AgentMessenger;
import com.agenv.services.MessageService;

@Stateless
public class AgentMessengerImpl implements AgentMessenger {

	@EJB
	private EnvBean envBean;
	
	@EJB
	private MessageService messageService;

	@Override
	public void send(ACLMessage acl) {
		acl.receivers = acl.receivers.stream() /**/
				.map(x -> envBean.getAgents().get(envBean.getAgents().indexOf(x)))
				.collect(Collectors.toList());
		
		messageService.sendACLMessage(acl);
	}

}
