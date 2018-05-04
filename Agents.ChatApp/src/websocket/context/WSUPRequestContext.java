package websocket.context;

import javax.websocket.Session;

import websocket.WSUPMessage;
import websocket.WSUPResponseType;

public class WSUPRequestContext extends WSUPContext {

	public WSUPRequestContext(WSUPMessage message, Session session) {
		super(message, session);
	}
	
	public WSUPContext buildResponse(WSUPResponseType type, Object data) {
		WSUPMessage response = getMessage().response(type, data);
		return new WSUPContext(response, getSession());
	}

}
