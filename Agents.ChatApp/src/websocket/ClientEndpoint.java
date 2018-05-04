package websocket;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
@Stateful
public class ClientEndpoint {

	@EJB
	private WSRequestHandler messageHandler;
	
	@SuppressWarnings("unchecked")
	@OnMessage
	public void receiveMessage(String message, Session session) {
		messageHandler.handleMessage(WSUPMessage.parseRequest(message), session);
	}

	@OnOpen
	public void open(Session session) throws IOException {
		// log.info("Open session:" + session.getId());
	}

	@OnClose
	public void close(Session session, CloseReason c) {
		// log.info("Closing:" + session.getId());
	}

}
