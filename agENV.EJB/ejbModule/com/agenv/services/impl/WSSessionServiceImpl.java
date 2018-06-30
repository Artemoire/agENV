package com.agenv.services.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.Session;

import com.agenv.beans.WSSessionsBean;
import com.agenv.logging.ILogger;
import com.agenv.services.WSSessionService;

@Stateless
public class WSSessionServiceImpl implements WSSessionService {

	@EJB
	private WSSessionsBean wsSessionsBean;

	@EJB
	private ILogger logger;

	@Override
	public void opened(Session session) {
		wsSessionsBean.open(session);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				logger.log("Session opened: " + session.getId());
			}
		}, 4000);
	}

	@Override
	public void closed(Session session) {
		wsSessionsBean.close(session);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				logger.log("Session closed: " + session.getId());
			}
		}, 4000);
	}

}
