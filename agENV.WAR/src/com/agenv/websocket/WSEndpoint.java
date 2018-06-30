package com.agenv.websocket;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.agenv.services.WSSessionService;

@ServerEndpoint("/ws")
@Stateful
public class WSEndpoint {

	@EJB
	private WSSessionService wsSessionService;

	@OnOpen
	public void open(Session session) {
		wsSessionService.opened(session);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		wsSessionService.closed(session);
	}

	public WSSessionService getWsSessionService() {
		return wsSessionService;
	}

	public void setWsSessionService(WSSessionService wsSessionService) {
		this.wsSessionService = wsSessionService;
	}

}
