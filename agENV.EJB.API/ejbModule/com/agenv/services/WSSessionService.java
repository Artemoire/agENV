package com.agenv.services;

import javax.websocket.Session;

public interface WSSessionService {

	void opened(Session session);
	void closed(Session session);
	
}
