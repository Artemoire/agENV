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

import services.ActiveUserService;
import services.SessionUserService;

@ServerEndpoint("/ws")
@Stateful
public class ClientEndpoint {

	@EJB
	private WSRequestHandler messageHandler;

	@EJB
	private SessionUserService sessionService;

	@EJB
	private ActiveUserService activeUserService;

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
		if (sessionService.isLoggedIn(session)) {
			activeUserService.logout(sessionService.logout(session));
		}
	}

}
