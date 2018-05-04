package websocket.context;

import java.io.IOException;

import javax.websocket.Session;

import websocket.WSError;
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

	public void error(int code, String message) {
		try {
			getSession().getBasicRemote()
					.sendText(getMessage().response(WSUPResponseType.FAILURE, new WSError(code, message)).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
