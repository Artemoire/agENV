package com.agenv.services.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.agenv.event.ApplicationMessenger;
import com.agenv.services.LoggingService;

@Stateless
public class LoggingServiceImpl implements LoggingService {

	@EJB
	private ApplicationMessenger messenger;

	@Override
	public void log(String content) {
		messenger.log(content);
	}

}
