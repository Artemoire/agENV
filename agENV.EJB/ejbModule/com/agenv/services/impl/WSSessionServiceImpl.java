package com.agenv.services.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.Session;

import com.agenv.beans.WSSessionsBean;
import com.agenv.services.WSSessionService;

@Stateless
public class WSSessionServiceImpl implements WSSessionService {

	@EJB
	private WSSessionsBean wsSessionsBean;

	@Override
	public void opened(Session session) {
		wsSessionsBean.open(session);
	}

	@Override
	public void closed(Session session) {
		wsSessionsBean.close(session);
	}

}
