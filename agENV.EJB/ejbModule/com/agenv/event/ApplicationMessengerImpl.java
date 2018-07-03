package com.agenv.event;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ApplicationMessengerImpl implements ApplicationMessenger {

	@Inject
	Event<LogEvent> logEvents;
	
	@Inject
	Event<AppStateChangedEvent> stateEvents;  

	@Override
	public void log(String message) {
		logEvents.fire(new LogEvent(message));
	}
	
	@Override
	public void fireAgentsChanged() {
		stateEvents.fire(AppStateChangedEvent.runningAgentsChanged);
	}
	
	@Override
	public void fireAgentTypesChanged() {
		stateEvents.fire(AppStateChangedEvent.agentTypesChanged);
	}

}
