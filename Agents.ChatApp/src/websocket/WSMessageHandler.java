package websocket;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.Session;

import models.User;
import services.SessionUserService;

@Stateless
public class WSMessageHandler {

	@EJB
	private SessionUserService sessionService;
	
	@EJB
	private WSUserService userService;

	public void handleMessage(WSUPMessage message, Session session) {
		if (sessionService.isLoggedIn(session)) {
			sessioned(message, session);
		} else {
			sessionless(message, session);
		}
	}

	private void sessioned(WSUPMessage message, Session session) {
		WSUPChatContext ctx = WSUPChatContext.valueOf(message.getContext());
		if (ctx == WSUPChatContext.LOGIN || ctx == WSUPChatContext.REGISTER)
			return; // TODO: ERR		
	}

	private void sessionless(WSUPMessage message, Session session) {
		WSUPChatContext ctx = WSUPChatContext.valueOf(message.getContext());
		if (ctx != WSUPChatContext.LOGIN && ctx != WSUPChatContext.REGISTER)
			return; // TODO: ERR

		if (ctx == WSUPChatContext.LOGIN)  {
			userService.login(message.getBodyAs(User.class)); 
				
		} else if (ctx == WSUPChatContext.REGISTER) {
			userService.register(message.getBodyAs(User.class));
		}
	}

}
