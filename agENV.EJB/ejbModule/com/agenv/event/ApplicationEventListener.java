package com.agenv.event;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import com.agenv.websocket.MessageDispatcher;
import com.agenv.websocket.MessageType;

@Stateless
public class ApplicationEventListener {

	@EJB
	private MessageDispatcher dispatcher;

	public void listenForLogs(@Observes LogEvent logEvent) {
		System.out.println("EJB >> LogEvent: " + logEvent);
		dispatcher.broadcast(MessageType.LOG, logEvent.getMessage());
	}

	public void listenForAppStateChanges(@Observes AppStateChangedEvent stateChange) {
		if (stateChange == AppStateChangedEvent.agentTypesChanged)
			dispatcher.broadcast(MessageType.REFRESH_TYPES, null);
		else if (stateChange == AppStateChangedEvent.runningAgentsChanged)
			dispatcher.broadcast(MessageType.REFRESH_RUNNING, null);
		else
			throw new RuntimeException("Illegal Application State Change Event.");
	}

}
