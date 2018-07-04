package com.agenv.services.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.agenv.services.LoggingService;
import com.agenv.websocket.MessageDispatcher;
import com.agenv.websocket.MessageType;

@Stateless
public class LoggingServiceImpl implements LoggingService {

	@EJB
	private MessageDispatcher dispatcher;

	@Override
	public void log(String content) {
		dispatcher.broadcast(MessageType.LOG, content);
	}

}
