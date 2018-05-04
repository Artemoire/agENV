package websocket.context;

import javax.websocket.Session;

import websocket.WSUPMessage;

public class WSUPContext {

	private WSUPMessage message;
	private Session session;

	public WSUPContext(WSUPMessage message, Session session) {
		this.message = message;
		this.session = session;
	}

	public WSUPMessage getMessage() {
		return message;
	}

	public Session getSession() {
		return session;
	}

}
