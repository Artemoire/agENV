package com.agenv.event.listeners;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import com.agenv.beans.WSSessionsBean;
import com.agenv.event.LogEvent;

@Stateless
public class LogListener {

	@EJB
	private WSSessionsBean wsSessionsBean;

	public void listen(@Observes LogEvent logEvent) {
		System.out.println("EJB >> LogEvent: " + logEvent);
		wsSessionsBean.stream().forEach(ses -> {
			try {
				ses.getBasicRemote().sendText(logEvent.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
