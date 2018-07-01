package com.agenv.event.listeners;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import com.agenv.beans.WSSessionsBean;
import com.agenv.event.LogEvent;
import com.agenv.websocket.MessageDispatcher;
import com.agenv.websocket.MessageType;

@Stateless
public class LogListener {

	@EJB
	private MessageDispatcher dispatcher;

	public void listen(@Observes LogEvent logEvent) {		
		System.out.println("EJB >> LogEvent: " + logEvent);
		dispatcher.broadcast(MessageType.LOG, logEvent.getMessage());
	}

}
