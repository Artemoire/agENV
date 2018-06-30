package com.agenv.event.messengers;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.agenv.event.LogEvent;
import com.agenv.logging.ILogger;

@Stateless
public class LogMessenger implements ILogger {

	@Inject
	Event<LogEvent> events;

	public void log(String message) {
		events.fire(new LogEvent(message));
	}

}
