package websocket;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.jboss.logging.Logger;

@ServerEndpoint("/ws")
public class ClientEndpoint {

	Logger log = Logger.getLogger(this.getClass());

	@OnMessage
	public void receiveMessage(String message, Session session) {
		log.info("Received : "+ message + ", session:" + session.getId());
	}
	
	@OnOpen
	public void open(Session session) {
		log.info("Open session:" + session.getId());		 
	}
	
	@OnClose
	public void close(Session session, CloseReason c) {
		log.info("Closing:" + session.getId());
	}

}
