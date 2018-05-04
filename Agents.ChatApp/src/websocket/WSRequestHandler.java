package websocket;

import java.util.HashMap;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.Session;

import models.User;
import services.SessionUserService;
import websocket.context.WSUPRequestContext;
import websocket.services.WSRequestUserService;

@Stateless
public class WSRequestHandler {

	@EJB
	private SessionUserService sessionService;

	@EJB
	private WSRequestUserService userService;

	private HashMap<String, Consumer<WSUPRequestContext>> unauthServiceConsumerMap;
	private HashMap<String, Consumer<WSUPRequestContext>> authServiceConsumerMap;

	@PostConstruct
	private void init() {
		unauthServiceConsumerMap = new HashMap<>();
		authServiceConsumerMap = new HashMap<>();
		unauthServiceConsumerMap.put(WSUPRequestType.CREATE + "/" + WSUPChatContext.LOGIN, userService::login);
		unauthServiceConsumerMap.put(WSUPRequestType.CREATE + "/" + WSUPChatContext.REGISTER, userService::register);
		authServiceConsumerMap.put(WSUPRequestType.READ + "/" + WSUPChatContext.FRIENDS, userService::getFriends);
		authServiceConsumerMap.put(WSUPRequestType.READ + "/" + WSUPChatContext.USERS, userService::getAll);
	}

	public void handleMessage(WSUPMessage message, Session session) {
		WSUPRequestContext context = new WSUPRequestContext(message, session);
		HashMap<String, Consumer<WSUPRequestContext>> consumerMap = null;
		if (!sessionService.isLoggedIn(session)) {
			consumerMap = unauthServiceConsumerMap;
		} else {
			consumerMap = authServiceConsumerMap;
		}
		Consumer<WSUPRequestContext> consumer = consumerMap.get(message.toActionString());
		if (consumer != null)
			consumer.accept(context);

	}

}
